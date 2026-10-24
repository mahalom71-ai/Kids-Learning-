package com.example.data

object AlphabetData {

    private fun sampleWaypoints(shape: String): List<Stroke> {
        return when (shape) {
            "A" -> listOf(
                Stroke(listOf(Waypoint(0.5f, 0.15f), Waypoint(0.2f, 0.85f))),
                Stroke(listOf(Waypoint(0.5f, 0.15f), Waypoint(0.8f, 0.85f))),
                Stroke(listOf(Waypoint(0.35f, 0.55f), Waypoint(0.65f, 0.55f)))
            )
            "B" -> listOf(
                Stroke(listOf(Waypoint(0.25f, 0.15f), Waypoint(0.25f, 0.85f))),
                Stroke(listOf(Waypoint(0.25f, 0.15f), Waypoint(0.7f, 0.25f), Waypoint(0.7f, 0.45f), Waypoint(0.25f, 0.5f))),
                Stroke(listOf(Waypoint(0.25f, 0.5f), Waypoint(0.75f, 0.6f), Waypoint(0.75f, 0.8f), Waypoint(0.25f, 0.85f)))
            )
            "C" -> listOf(
                Stroke(listOf(Waypoint(0.8f, 0.25f), Waypoint(0.45f, 0.15f), Waypoint(0.25f, 0.5f), Waypoint(0.45f, 0.85f), Waypoint(0.8f, 0.75f)))
            )
            "circle" -> listOf(
                Stroke(listOf(Waypoint(0.5f, 0.15f), Waypoint(0.2f, 0.4f), Waypoint(0.3f, 0.8f), Waypoint(0.7f, 0.8f), Waypoint(0.8f, 0.4f), Waypoint(0.5f, 0.15f)))
            )
            "bangla_matra" -> listOf(
                Stroke(listOf(Waypoint(0.2f, 0.18f), Waypoint(0.8f, 0.18f))),
                Stroke(listOf(Waypoint(0.4f, 0.22f), Waypoint(0.3f, 0.45f), Waypoint(0.5f, 0.6f), Waypoint(0.75f, 0.55f))),
                Stroke(listOf(Waypoint(0.68f, 0.18f), Waypoint(0.68f, 0.85f)))
            )
            else -> listOf(
                Stroke(listOf(Waypoint(0.2f, 0.2f), Waypoint(0.8f, 0.2f))),
                Stroke(listOf(Waypoint(0.5f, 0.2f), Waypoint(0.3f, 0.6f), Waypoint(0.7f, 0.8f)))
            )
        }
    }

    val banglaVowels = listOf(
        LetterItem(
            id = "bn_v_1",
            character = "অ",
            name = "স্বর অ",
            pronunciation = "Shor-o",
            word = "অজগর",
            wordMeaning = "Python / অজগর আসছে তেড়ে",
            emoji = "🐍",
            type = LetterType.BANGLA_VOWEL,
            strokes = listOf(
                Stroke(listOf(Waypoint(0.2f, 0.2f), Waypoint(0.8f, 0.2f))), // মাত্রা
                Stroke(listOf(Waypoint(0.4f, 0.25f), Waypoint(0.28f, 0.38f), Waypoint(0.38f, 0.52f), Waypoint(0.52f, 0.48f))), // গোল
                Stroke(listOf(Waypoint(0.48f, 0.5f), Waypoint(0.55f, 0.75f), Waypoint(0.75f, 0.75f))),
                Stroke(listOf(Waypoint(0.75f, 0.2f), Waypoint(0.75f, 0.85f)))
            )
        ),
        LetterItem(
            id = "bn_v_2",
            character = "আ",
            name = "স্বর আ",
            pronunciation = "Shor-aa",
            word = "আম",
            wordMeaning = "Mango / আমটি খাব পেড়ে",
            emoji = "🥭",
            type = LetterType.BANGLA_VOWEL,
            strokes = listOf(
                Stroke(listOf(Waypoint(0.18f, 0.2f), Waypoint(0.82f, 0.2f))),
                Stroke(listOf(Waypoint(0.35f, 0.25f), Waypoint(0.25f, 0.4f), Waypoint(0.4f, 0.52f), Waypoint(0.55f, 0.7f))),
                Stroke(listOf(Waypoint(0.6f, 0.2f), Waypoint(0.6f, 0.85f))),
                Stroke(listOf(Waypoint(0.75f, 0.2f), Waypoint(0.75f, 0.85f)))
            )
        ),
        LetterItem(
            id = "bn_v_3",
            character = "ই",
            name = "হ্রস্ব ই",
            pronunciation = "Hroshwo-I",
            word = "ইলিশ",
            wordMeaning = "Hilsa / ইলিশ ভাজা খেতে মজা",
            emoji = "🐟",
            type = LetterType.BANGLA_VOWEL,
            strokes = listOf(
                Stroke(listOf(Waypoint(0.25f, 0.25f), Waypoint(0.78f, 0.25f))),
                Stroke(listOf(Waypoint(0.5f, 0.25f), Waypoint(0.55f, 0.12f), Waypoint(0.7f, 0.15f))),
                Stroke(listOf(Waypoint(0.38f, 0.28f), Waypoint(0.3f, 0.45f), Waypoint(0.45f, 0.65f), Waypoint(0.68f, 0.82f)))
            )
        ),
        LetterItem(
            id = "bn_v_4",
            character = "ঈ",
            name = "দীর্ঘ ঈ",
            pronunciation = "Deergho-Ee",
            word = "ঈগল",
            wordMeaning = "Eagle / ঈগল পাখি পাছে ধরে",
            emoji = "🦅",
            type = LetterType.BANGLA_VOWEL,
            strokes = sampleWaypoints("bangla_matra")
        ),
        LetterItem(
            id = "bn_v_5",
            character = "উ",
            name = "হ্রস্ব উ",
            pronunciation = "Hroshwo-U",
            word = "উট",
            wordMeaning = "Camel / উট চলেছে মুখটি তুলে",
            emoji = "🐪",
            type = LetterType.BANGLA_VOWEL,
            strokes = sampleWaypoints("bangla_matra")
        ),
        LetterItem(
            id = "bn_v_6",
            character = "ঊ",
            name = "দীর্ঘ ঊ",
            pronunciation = "Deergho-Oo",
            word = "ঊষা",
            wordMeaning = "Dawn / ঊষার আলো রাঙা ভোরে",
            emoji = "🌅",
            type = LetterType.BANGLA_VOWEL,
            strokes = sampleWaypoints("bangla_matra")
        ),
        LetterItem(
            id = "bn_v_7",
            character = "ঋ",
            name = "ঋ",
            pronunciation = "Ri",
            word = "ঋষি",
            wordMeaning = "Sage / ঋষি মশাই বসেন পূজায়",
            emoji = "🧘",
            type = LetterType.BANGLA_VOWEL,
            strokes = sampleWaypoints("bangla_matra")
        ),
        LetterItem(
            id = "bn_v_8",
            character = "এ",
            name = "এ",
            pronunciation = "E",
            word = "একতারা",
            wordMeaning = "Ektara / একতারা বাজে সুরে সুরে",
            emoji = "🪕",
            type = LetterType.BANGLA_VOWEL,
            strokes = listOf(
                Stroke(listOf(Waypoint(0.28f, 0.35f), Waypoint(0.22f, 0.48f), Waypoint(0.35f, 0.6f))),
                Stroke(listOf(Waypoint(0.35f, 0.6f), Waypoint(0.7f, 0.3f), Waypoint(0.55f, 0.82f)))
            )
        ),
        LetterItem(
            id = "bn_v_9",
            character = "ঐ",
            name = "ঐ",
            pronunciation = "Oi",
            word = "ঐরাবত",
            wordMeaning = "Elephant / ঐরাবত ওই বিরাট প্রাণী",
            emoji = "🐘",
            type = LetterType.BANGLA_VOWEL,
            strokes = sampleWaypoints("bangla_matra")
        ),
        LetterItem(
            id = "bn_v_10",
            character = "ও",
            name = "ও",
            pronunciation = "O",
            word = "ওল",
            wordMeaning = "Yam / ওল খেলে ধরবে গলা",
            emoji = "🥔",
            type = LetterType.BANGLA_VOWEL,
            strokes = sampleWaypoints("circle")
        ),
        LetterItem(
            id = "bn_v_11",
            character = "ঔ",
            name = "ঔ",
            pronunciation = "Ou",
            word = "ঔষধ",
            wordMeaning = "Medicine / ঔষধ খেলে রোগ সারবে",
            emoji = "💊",
            type = LetterType.BANGLA_VOWEL,
            strokes = sampleWaypoints("circle")
        )
    )

    val banglaConsonants = listOf(
        LetterItem("bn_c_1", "ক", "", "ক", "Ko", "কলা", "Banana", "🍌", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_2", "খ", "", "খ", "Kho", "খরগোশ", "Rabbit", "🐇", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_3", "গ", "", "গ", "Go", "গরু", "Cow", "🐄", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_4", "ঘ", "", "ঘ", "Gho", "ঘুড়ি", "Kite", "🪁", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_5", "ঙ", "", "ঙ", "Umo", "ব্যাঙ", "Frog", "🐸", LetterType.BANGLA_CONSONANT, sampleWaypoints("circle")),
        LetterItem("bn_c_6", "চ", "", "চ", "Cho", "চাঁদ", "Moon", "🌙", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_7", "ছ", "", "ছ", "Chho", "ছাতা", "Umbrella", "☂️", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_8", "জ", "", "জ", "Borgio-Jo", "জাহাজ", "Ship", "🚢", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_9", "ঝ", "", "ঝ", "Jho", "ঝুড়ি", "Basket", "🧺", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_10", "ঞ", "", "ঞ", "Iyo", "মিঞা", "Cat / Gentleman", "🐱", LetterType.BANGLA_CONSONANT, sampleWaypoints("circle")),
        LetterItem("bn_c_11", "ট", "", "ট", "To", "টিয়া", "Parrot", "🦜", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_12", "ঠ", "", "ঠ", "Tho", "ঠেলাগাড়ি", "Cart", "🛒", LetterType.BANGLA_CONSONANT, sampleWaypoints("circle")),
        LetterItem("bn_c_13", "ড", "", "ড", "Do", "ডালিম", "Pomegranate", "🍎", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_14", "ঢ", "", "ঢ", "Dho", "ঢাক", "Drum", "🥁", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_15", "ণ", "", "ণ", "Murdhanya-No", "হরিণ", "Deer", "🦌", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_16", "ত", "", "ত", "To", "তিমি", "Whale", "🐋", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_17", "থ", "", "থ", "Tho", "থালা", "Plate", "🍽️", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_18", "দ", "", "দ", "Do", "দোয়েল", "Magpie Robin", "🐦", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_19", "ধ", "", "ধ", "Dho", "ধনুক", "Bow", "🏹", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_20", "ন", "", "ন", "Donto-No", "নৌকা", "Boat", "⛵", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_21", "প", "", "প", "Po", "পাখি", "Bird", "🕊️", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_22", "ফ", "", "ফ", "Pho", "ফুল", "Flower", "🌸", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_23", "ব", "", "ব", "Bo", "বাঘ", "Royal Bengal Tiger", "🐅", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_24", "ভ", "", "ভ", "Bho", "ভালুক", "Bear", "🐻", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_25", "ম", "", "ম", "Mo", "মাছ", "Fish", "🐠", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_26", "য", "", "য", "Ontostho-Zo", "যাতাকল", "Mill", "⚙️", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_27", "র", "", "র", "Ro", "রথ", "Chariot", "🎪", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_28", "ল", "", "ল", "Lo", "লাটিম", "Spinning Top", "🪀", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_29", "শ", "", "শ", "Talobyo-Sho", "শাপলা", "Water Lily", "🪷", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_30", "ষ", "", "ষ", "Murdhanya-Sho", "ষাঁড়", "Bull", "🐂", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_31", "স", "", "স", "Donto-So", "সিংহ", "Lion", "🦁", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_32", "হ", "", "হ", "Ho", "হাতি", "Elephant", "🐘", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_33", "ড়", "", "ড়", "Dae-Rro", "গাড়ি", "Car", "🚗", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_34", "ঢ়", "", "ঢ়", "Dhae-Rro", "আষাঢ়", "Rainy Season", "🌧️", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_35", "য়", "", "য়", "Ontostho-Yo", "ময়না", "Myna Bird", "🦜", LetterType.BANGLA_CONSONANT, sampleWaypoints("bangla_matra")),
        LetterItem("bn_c_36", "ৎ", "", "ৎ", "Khondo-To", "মৎস্য", "Fish", "🐟", LetterType.BANGLA_CONSONANT, sampleWaypoints("circle")),
        LetterItem("bn_c_37", "ং", "", "ং", "Onushor", "রংধনু", "Rainbow", "🌈", LetterType.BANGLA_CONSONANT, sampleWaypoints("circle")),
        LetterItem("bn_c_38", "ঃ", "", "ঃ", "Bisorgo", "দুঃখ", "Kind Heart", "❤️", LetterType.BANGLA_CONSONANT, sampleWaypoints("circle")),
        LetterItem("bn_c_39", "ঁ", "", "ঁ", "Chondrobindu", "চাঁদ", "Crescent Moon", "🌙", LetterType.BANGLA_CONSONANT, sampleWaypoints("circle"))
    )

    val englishLetters = listOf(
        LetterItem("en_a", "A", "a", "A", "Ay", "Apple", "A juicy sweet apple", "🍎", LetterType.ENGLISH_ALPHABET, sampleWaypoints("A")),
        LetterItem("en_b", "B", "b", "B", "Bee", "Ball", "A bouncy red ball", "⚽", LetterType.ENGLISH_ALPHABET, sampleWaypoints("B")),
        LetterItem("en_c", "C", "c", "C", "See", "Cat", "A cute playful kitten", "🐱", LetterType.ENGLISH_ALPHABET, sampleWaypoints("C")),
        LetterItem("en_d", "D", "d", "D", "Dee", "Dog", "A loyal friendly puppy", "🐶", LetterType.ENGLISH_ALPHABET, sampleWaypoints("B")),
        LetterItem("en_e", "E", "e", "E", "Ee", "Elephant", "A big cheerful elephant", "🐘", LetterType.ENGLISH_ALPHABET, sampleWaypoints("bangla_matra")),
        LetterItem("en_f", "F", "f", "F", "Ef", "Fish", "Swimming in the clear blue sea", "🐠", LetterType.ENGLISH_ALPHABET, sampleWaypoints("bangla_matra")),
        LetterItem("en_g", "G", "g", "G", "Jee", "Giraffe", "Tall animal with long neck", "🦒", LetterType.ENGLISH_ALPHABET, sampleWaypoints("C")),
        LetterItem("en_h", "H", "h", "H", "Aych", "House", "A warm sweet home", "🏠", LetterType.ENGLISH_ALPHABET, sampleWaypoints("A")),
        LetterItem("en_i", "I", "i", "I", "Eye", "Ice Cream", "Cold and sweet treat", "🍦", LetterType.ENGLISH_ALPHABET, sampleWaypoints("bangla_matra")),
        LetterItem("en_j", "J", "j", "J", "Jay", "Juice", "Fresh and healthy juice", "🧃", LetterType.ENGLISH_ALPHABET, sampleWaypoints("C")),
        LetterItem("en_k", "K", "k", "K", "Kay", "Kite", "Flying high in the sky", "🪁", LetterType.ENGLISH_ALPHABET, sampleWaypoints("A")),
        LetterItem("en_l", "L", "l", "L", "El", "Lion", "The brave king of the jungle", "🦁", LetterType.ENGLISH_ALPHABET, sampleWaypoints("bangla_matra")),
        LetterItem("en_m", "M", "m", "M", "Em", "Monkey", "Playful swinging monkey", "🐒", LetterType.ENGLISH_ALPHABET, sampleWaypoints("A")),
        LetterItem("en_n", "N", "n", "N", "En", "Nest", "Cozy bird home with eggs", "🪺", LetterType.ENGLISH_ALPHABET, sampleWaypoints("A")),
        LetterItem("en_o", "O", "o", "O", "Oh", "Orange", "Bright citrus juicy fruit", "🍊", LetterType.ENGLISH_ALPHABET, sampleWaypoints("circle")),
        LetterItem("en_p", "P", "p", "P", "Pee", "Panda", "Gentle black and white bear", "🐼", LetterType.ENGLISH_ALPHABET, sampleWaypoints("B")),
        LetterItem("en_q", "Q", "q", "Q", "Queue", "Queen", "Wearing a glittering golden crown", "👑", LetterType.ENGLISH_ALPHABET, sampleWaypoints("circle")),
        LetterItem("en_r", "R", "r", "R", "Ar", "Rainbow", "Seven colors in the sky", "🌈", LetterType.ENGLISH_ALPHABET, sampleWaypoints("B")),
        LetterItem("en_s", "S", "s", "S", "Ess", "Sun", "Shining warm and bright", "☀️", LetterType.ENGLISH_ALPHABET, sampleWaypoints("C")),
        LetterItem("en_t", "T", "t", "T", "Tee", "Tiger", "Striped brave wild cat", "🐅", LetterType.ENGLISH_ALPHABET, sampleWaypoints("bangla_matra")),
        LetterItem("en_u", "U", "u", "U", "You", "Umbrella", "Keeps us dry in the rain", "☂️", LetterType.ENGLISH_ALPHABET, sampleWaypoints("C")),
        LetterItem("en_v", "V", "v", "V", "Vee", "Van", "A shiny colorful mini-bus", "🚐", LetterType.ENGLISH_ALPHABET, sampleWaypoints("A")),
        LetterItem("en_w", "W", "w", "W", "Double-U", "Watch", "Tick-tock counting time", "⌚", LetterType.ENGLISH_ALPHABET, sampleWaypoints("A")),
        LetterItem("en_x", "X", "x", "X", "Ex", "Xylophone", "Musical bells and bars", "🎼", LetterType.ENGLISH_ALPHABET, sampleWaypoints("A")),
        LetterItem("en_y", "Y", "y", "Y", "Wye", "Yacht", "Sailing on the waves", "⛵", LetterType.ENGLISH_ALPHABET, sampleWaypoints("A")),
        LetterItem("en_z", "Z", "z", "Z", "Zed", "Zebra", "Striped savanna friend", "🦓", LetterType.ENGLISH_ALPHABET, sampleWaypoints("bangla_matra"))
    )
}
