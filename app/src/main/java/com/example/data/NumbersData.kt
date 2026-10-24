package com.example.data

object NumbersData {

    private val banglaDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

    fun toBanglaNumeral(num: Int): String {
        return num.toString().map { char ->
            if (char in '0'..'9') banglaDigits[char - '0'] else char
        }.joinToString("")
    }

    private val banglaWords1To20 = listOf(
        "", "এক", "দুই", "তিন", "চার", "পাঁচ", "ছয়", "সাত", "আট", "নয়", "দশ",
        "এগারো", "বারো", "তেরো", "চোদ্দ", "পনেরো", "ষোলো", "সতেরো", "আঠারো", "উনিশ", "বিশ"
    )

    private val englishWords1To20 = listOf(
        "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
        "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty"
    )

    private val countingEmojis = listOf(
        "🍎", "⭐", "🎈", "🦋", "🍬", "🌸", "🚗", "🐥", "🐬", "🧁",
        "🍓", "🧸", "🐸", "🚀", "🍕", "🐶", "🐝", "🍭", "🍇", "⚽"
    )

    fun getBanglaWord(num: Int): String {
        if (num in 1..20) return banglaWords1To20[num]
        return when (num) {
            30 -> "ত্রিশ"
            40 -> "চল্লিশ"
            50 -> "পঞ্চাশ"
            60 -> "ষাট"
            70 -> "সত্তর"
            80 -> "আশি"
            90 -> "নব্বই"
            100 -> "একশত"
            else -> "${toBanglaNumeral(num)}"
        }
    }

    fun getEnglishWord(num: Int): String {
        if (num in 1..20) return englishWords1To20[num]
        val tens = listOf("", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety")
        val ones = listOf("", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")
        return when {
            num == 100 -> "One Hundred"
            num < 100 -> {
                val t = num / 10
                val o = num % 10
                if (o == 0) tens[t] else "${tens[t]} ${ones[o]}"
            }
            else -> num.toString()
        }
    }

    val numbers1To100: List<NumberItem> = (1..100).map { i ->
        NumberItem(
            value = i,
            banglaNumeral = toBanglaNumeral(i),
            englishNumeral = i.toString(),
            banglaWord = getBanglaWord(i),
            englishWord = getEnglishWord(i),
            emoji = countingEmojis[(i - 1) % countingEmojis.size],
            strokes = listOf(
                Stroke(listOf(Waypoint(0.5f, 0.2f), Waypoint(0.5f, 0.8f)))
            )
        )
    }
}
