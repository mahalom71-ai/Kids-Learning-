package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.audio.KidsAudioService
import com.example.data.ProgressRepository
import com.example.ui.screens.MainScreen
import com.example.ui.theme.KidsBornomalaTheme

class MainActivity : ComponentActivity() {

    private lateinit var audioService: KidsAudioService
    private lateinit var progressRepository: ProgressRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioService = KidsAudioService(this)
        progressRepository = ProgressRepository(this)

        setContent {
            KidsBornomalaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(
                        audioService = audioService,
                        progressRepository = progressRepository
                    )
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        audioService.stopMelody()
    }

    override fun onDestroy() {
        super.onDestroy()
        audioService.release()
    }
}
