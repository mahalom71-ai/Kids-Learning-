package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.ToneGenerator
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale

class KidsAudioService(private val context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private var toneGenerator: ToneGenerator? = null
    private var isBengaliSupported = false
    private var currentMelodyJob: Job? = null
    private val audioScope = CoroutineScope(Dispatchers.Default)

    var isMuted: Boolean = false

    init {
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 85)
        } catch (e: Exception) {
            Log.e("KidsAudioService", "Error creating ToneGenerator", e)
        }
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.e("KidsAudioService", "Error creating TextToSpeech", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsReady = true
            tts?.setSpeechRate(0.85f) // slightly slower for young learners
            tts?.setPitch(1.15f) // friendly, warm kid-directed pitch

            val bnLocale = Locale("bn", "BD")
            val result = tts?.isLanguageAvailable(bnLocale)
            if (result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                isBengaliSupported = true
            } else {
                val genericBn = Locale("bn")
                val res2 = tts?.isLanguageAvailable(genericBn)
                isBengaliSupported = (res2 == TextToSpeech.LANG_AVAILABLE || res2 == TextToSpeech.LANG_COUNTRY_AVAILABLE)
            }
        }
    }

    fun speak(text: String, isBengali: Boolean = false, onComplete: (() -> Unit)? = null) {
        if (isMuted || !isTtsReady) {
            onComplete?.invoke()
            return
        }

        try {
            if (isBengali) {
                val bnLocale = Locale("bn", "BD")
                val langResult = tts?.setLanguage(bnLocale)
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.setLanguage(Locale("bn"))
                }
            } else {
                tts?.setLanguage(Locale.US)
            }

            val utteranceId = "utterance_${System.currentTimeMillis()}"
            if (onComplete != null) {
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {}
                    override fun onDone(utteranceId: String?) {
                        audioScope.launch(Dispatchers.Main) { onComplete() }
                    }
                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) {
                        audioScope.launch(Dispatchers.Main) { onComplete() }
                    }
                })
            }

            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        } catch (e: Exception) {
            Log.e("KidsAudioService", "TTS speak failed", e)
            onComplete?.invoke()
        }
    }

    fun playPop() {
        if (isMuted) return
        audioScope.launch {
            try {
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 70)
            } catch (e: Exception) {
                // fallback
            }
        }
    }

    fun playSparkle() {
        if (isMuted) return
        audioScope.launch {
            try {
                toneGenerator?.startTone(ToneGenerator.TONE_DTMF_D, 90)
                delay(60)
                toneGenerator?.startTone(ToneGenerator.TONE_DTMF_0, 110)
            } catch (e: Exception) {
                // fallback
            }
        }
    }

    fun playSuccess() {
        if (isMuted) return
        audioScope.launch {
            try {
                toneGenerator?.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 180)
                delay(120)
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 250)
            } catch (e: Exception) {
                // fallback
            }
        }
    }

    fun playStarEarned() {
        if (isMuted) return
        audioScope.launch {
            try {
                toneGenerator?.startTone(ToneGenerator.TONE_DTMF_1, 80)
                delay(90)
                toneGenerator?.startTone(ToneGenerator.TONE_DTMF_5, 100)
                delay(100)
                toneGenerator?.startTone(ToneGenerator.TONE_DTMF_9, 200)
            } catch (e: Exception) {
                // fallback
            }
        }
    }

    /**
     * Plays a musical melody sequence for rhymes (frequencies in Hz and durations in ms).
     */
    fun playMelody(notes: List<Pair<Double, Int>>, onNoteProgress: ((Int) -> Unit)? = null, onFinish: (() -> Unit)? = null) {
        stopMelody()
        if (isMuted) {
            onFinish?.invoke()
            return
        }

        currentMelodyJob = audioScope.launch {
            try {
                for (index in notes.indices) {
                    if (!isActive) break
                    val (freq, durationMs) = notes[index]
                    onNoteProgress?.invoke(index)
                    if (freq > 0) {
                        playToneSine(freq, durationMs)
                    } else {
                        delay(durationMs.toLong())
                    }
                    delay(30) // slight pause between notes
                }
            } catch (e: Exception) {
                Log.e("KidsAudioService", "Error playing melody", e)
            } finally {
                onFinish?.invoke()
            }
        }
    }

    fun stopMelody() {
        currentMelodyJob?.cancel()
        currentMelodyJob = null
        try {
            tts?.stop()
        } catch (_: Exception) {}
    }

    private fun playToneSine(frequencyHz: Double, durationMs: Int) {
        val sampleRate = 22050
        val numSamples = (durationMs * sampleRate / 1000)
        val generatedSnd = ByteArray(2 * numSamples)

        for (i in 0 until numSamples) {
            val angle = 2.0 * Math.PI * i / (sampleRate / frequencyHz)
            // Apply slight attack/decay envelope to prevent clicking
            val envelope = when {
                i < 200 -> i / 200.0
                i > numSamples - 300 -> (numSamples - i) / 300.0
                else -> 1.0
            }
            val sample = (Math.sin(angle) * 32767.0 * 0.45 * envelope).toInt().toShort()
            val index = i * 2
            generatedSnd[index] = (sample.toInt() and 0x00ff).toByte()
            generatedSnd[index + 1] = ((sample.toInt() and 0xff00) ushr 8).toByte()
        }

        var track: AudioTrack? = null
        try {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            val format = AudioFormat.Builder()
                .setSampleRate(sampleRate)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()

            track = AudioTrack(
                attributes,
                format,
                generatedSnd.size,
                AudioTrack.MODE_STATIC,
                AudioManager.AUDIO_SESSION_ID_GENERATE
            )
            track.write(generatedSnd, 0, generatedSnd.size)
            track.play()
            Thread.sleep(durationMs.toLong())
        } catch (e: Exception) {
            // Ignore interruption
        } finally {
            try {
                track?.stop()
                track?.release()
            } catch (_: Exception) {}
        }
    }

    fun release() {
        stopMelody()
        try {
            toneGenerator?.release()
            toneGenerator = null
        } catch (_: Exception) {}
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
        } catch (_: Exception) {}
    }
}
