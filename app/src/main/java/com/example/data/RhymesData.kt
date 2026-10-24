package com.example.data

object RhymesData {

    // Music note frequencies (Hz)
    private const val C4 = 261.63
    private const val D4 = 293.66
    private const val E4 = 329.63
    private const val F4 = 349.23
    private const val G4 = 392.00
    private const val A4 = 440.00
    private const val B4 = 493.88
    private const val C5 = 523.25
    private const val D5 = 587.33

    val banglaRhymes = listOf(
        RhymeItem(
            id = "rhyme_bn_1",
            title = "হাট্টিমা টিম টিম",
            subtitle = "Hattimatim Tim - Popular Bengali Rhyme",
            language = "bn",
            emoji = "🦕",
            colorHex = 0xFFFF6B6B,
            lines = listOf(
                RhymeLine("হাট্টিমা টিম টিম,", "Hattimatim tim,", 2600L),
                RhymeLine("তারা মাঠে পাড়ে ডিম।", "Tara mathe pare dim.", 2600L),
                RhymeLine("তাদের খাড়া দুটো শিং,", "Tader khara duto shing,", 2600L),
                RhymeLine("তারা হাট্টিমা টিম টিম!", "Tara hattimatim tim!", 2800L)
            ),
            melodyNotes = listOf(
                Pair(C4, 300), Pair(E4, 300), Pair(G4, 300), Pair(G4, 400),
                Pair(A4, 300), Pair(G4, 300), Pair(F4, 300), Pair(E4, 450),
                Pair(D4, 300), Pair(F4, 300), Pair(E4, 300), Pair(D4, 400),
                Pair(C4, 300), Pair(E4, 300), Pair(D4, 300), Pair(C4, 600)
            )
        ),
        RhymeItem(
            id = "rhyme_bn_2",
            title = "খোকন খোকন ডাক পাড়ি",
            subtitle = "Khokon Khokon Dak Pari - Lullaby",
            language = "bn",
            emoji = "👶",
            colorHex = 0xFF4ECDC4,
            lines = listOf(
                RhymeLine("খোকন খোকন ডাক পাড়ি,", "Khokon khokon dak pari,", 2500L),
                RhymeLine("খোকন মোদের কার বাড়ি?", "Khokon moder kar bari?", 2500L),
                RhymeLine("আয় রে খোকন ঘরে আয়,", "Aay re khokon ghore aay,", 2500L),
                RhymeLine("দুধ মেখে ভাত কাকে খায়!", "Dudh mekhe bhat kake khay!", 2800L)
            ),
            melodyNotes = listOf(
                Pair(E4, 350), Pair(G4, 350), Pair(E4, 350), Pair(C4, 450),
                Pair(D4, 350), Pair(F4, 350), Pair(E4, 350), Pair(D4, 450),
                Pair(E4, 350), Pair(G4, 350), Pair(A4, 350), Pair(G4, 450),
                Pair(F4, 350), Pair(E4, 350), Pair(D4, 350), Pair(C4, 600)
            )
        ),
        RhymeItem(
            id = "rhyme_bn_3",
            title = "আম পাতা জোড়া জোড়া",
            subtitle = "Aam Pata Jora Jora - Play Rhyme",
            language = "bn",
            emoji = "🥭",
            colorHex = 0xFFFFD166,
            lines = listOf(
                RhymeLine("আম পাতা জোড়া জোড়া,", "Aam pata jora jora,", 2400L),
                RhymeLine("মারবো চাবুক চড়বো ঘোড়া।", "Marbo chabuk chorbo ghora.", 2600L),
                RhymeLine("ওরে বুবু সরে দাঁড়া,", "Ore bubu sore dara,", 2400L),
                RhymeLine("আসছে আমার পাগলা ঘোড়া!", "Asche amar pagla ghora!", 2800L)
            ),
            melodyNotes = listOf(
                Pair(G4, 300), Pair(G4, 300), Pair(A4, 300), Pair(G4, 400),
                Pair(C5, 300), Pair(B4, 300), Pair(A4, 300), Pair(G4, 450),
                Pair(F4, 300), Pair(E4, 300), Pair(D4, 300), Pair(G4, 400),
                Pair(E4, 300), Pair(D4, 300), Pair(C4, 500)
            )
        ),
        RhymeItem(
            id = "rhyme_bn_4",
            title = "চাঁদ উঠেছে ফুল ফুটেছে",
            subtitle = "Chand Utheche Phul Phuteche - Sweet Night Song",
            language = "bn",
            emoji = "🌙",
            colorHex = 0xFF6C5CE7,
            lines = listOf(
                RhymeLine("চাঁদ উঠেছে ফুল ফুটেছে,", "Chand utheche phul phuteche,", 2500L),
                RhymeLine("কদম তলায় কে?", "Kodom tolay ke?", 2200L),
                RhymeLine("হাতি নাচছে ঘোড়া নাচছে,", "Hati nachche ghora nachche,", 2500L),
                RhymeLine("সোনামণির বে!", "Sonamonir be!", 2600L)
            ),
            melodyNotes = listOf(
                Pair(C4, 350), Pair(D4, 350), Pair(E4, 350), Pair(G4, 450),
                Pair(A4, 350), Pair(G4, 350), Pair(E4, 500),
                Pair(G4, 350), Pair(A4, 350), Pair(C5, 350), Pair(A4, 450),
                Pair(G4, 350), Pair(E4, 350), Pair(C4, 600)
            )
        ),
        RhymeItem(
            id = "rhyme_bn_5",
            title = "ভোর হলো দোর খোলো",
            subtitle = "Bhor Holo Dor Kholo - Morning Rhyme by Kazi Nazrul Islam",
            language = "bn",
            emoji = "🌅",
            colorHex = 0xFFFA8231,
            lines = listOf(
                RhymeLine("ভোর হলো দোর খোলো,", "Bhor holo dor kholo,", 2400L),
                RhymeLine("খুকুমণি ওঠো রে!", "Khukumoni otho re!", 2400L),
                RhymeLine("ঐ ডাকে জুঁই-শাখে,", "Oi dake jui-shakhe,", 2400L),
                RhymeLine("ফুল-খুকী ছোট রে!", "Phul-khuki chhoto re!", 2700L)
            ),
            melodyNotes = listOf(
                Pair(E4, 350), Pair(F4, 350), Pair(G4, 400),
                Pair(A4, 350), Pair(G4, 350), Pair(F4, 350), Pair(E4, 450),
                Pair(D4, 350), Pair(E4, 350), Pair(F4, 400),
                Pair(G4, 350), Pair(F4, 350), Pair(E4, 350), Pair(C4, 600)
            )
        ),
        RhymeItem(
            id = "rhyme_bn_6",
            title = "বাক বাকুম পায়রা",
            subtitle = "Bak Bakum Payra - Pigeon Play Song",
            language = "bn",
            emoji = "🕊️",
            colorHex = 0xFF20BF6B,
            lines = listOf(
                RhymeLine("বাক বাকুম পায়রা,", "Bak bakum payra,", 2400L),
                RhymeLine("মাথায় দিয়ে টায়রা।", "Mathay diye tayra.", 2500L),
                RhymeLine("বউ সাজবে কাল কি,", "Bou sajbe kal ki,", 2400L),
                RhymeLine("চড়বে সোনার পালকি!", "Chorbe sonar palki!", 2800L)
            ),
            melodyNotes = listOf(
                Pair(C4, 300), Pair(E4, 300), Pair(G4, 450),
                Pair(A4, 300), Pair(G4, 300), Pair(E4, 450),
                Pair(D4, 300), Pair(F4, 300), Pair(A4, 450),
                Pair(G4, 300), Pair(E4, 300), Pair(C4, 600)
            )
        )
    )

    val englishRhymes = listOf(
        RhymeItem(
            id = "rhyme_en_1",
            title = "Twinkle, Twinkle, Little Star",
            subtitle = "Classic Lullaby Sing-Along",
            language = "en",
            emoji = "⭐",
            colorHex = 0xFFFFD166,
            lines = listOf(
                RhymeLine("টুইংকল টুইংকল লিটল স্টার,", "Twinkle, twinkle, little star,", 2600L),
                RhymeLine("হাউ আই ওয়ান্ডার হোয়াট ইউ আর!", "How I wonder what you are!", 2600L),
                RhymeLine("আপ অ্যাবাভ দ্য ওয়ার্ল্ড সো হাই,", "Up above the world so high,", 2600L),
                RhymeLine("লাইক আ ডায়মন্ড ইন দ্য স্কাই!", "Like a diamond in the sky!", 2800L)
            ),
            melodyNotes = listOf(
                Pair(C4, 350), Pair(C4, 350), Pair(G4, 350), Pair(G4, 350), Pair(A4, 350), Pair(A4, 350), Pair(G4, 600),
                Pair(F4, 350), Pair(F4, 350), Pair(E4, 350), Pair(E4, 350), Pair(D4, 350), Pair(D4, 350), Pair(C4, 600),
                Pair(G4, 350), Pair(G4, 350), Pair(F4, 350), Pair(F4, 350), Pair(E4, 350), Pair(E4, 350), Pair(D4, 600),
                Pair(C4, 350), Pair(C4, 350), Pair(G4, 350), Pair(G4, 350), Pair(A4, 350), Pair(A4, 350), Pair(G4, 600)
            )
        ),
        RhymeItem(
            id = "rhyme_en_2",
            title = "Baa, Baa, Black Sheep",
            subtitle = "Friendly Nursery Song",
            language = "en",
            emoji = "🐑",
            colorHex = 0xFF45AAF2,
            lines = listOf(
                RhymeLine("বা বা ব্ল্যাক শিপ হ্যাভ ইউ এনি উল?", "Baa, baa, black sheep, have you any wool?", 3000L),
                RhymeLine("ইয়েস স্যার, ইয়েস স্যার, থ্রি ব্যাগস ফুল!", "Yes sir, yes sir, three bags full!", 2800L),
                RhymeLine("ওয়ান ফর দ্য মাস্টার, ওয়ান ফর দ্য ডেম,", "One for the master, and one for the dame,", 2900L),
                RhymeLine("অ্যান্ড ওয়ান ফর দ্য লিটল বয় হু লিভস ডাউন দ্য লেন!", "And one for the little boy who lives down the lane!", 3200L)
            ),
            melodyNotes = listOf(
                Pair(C4, 300), Pair(C4, 300), Pair(G4, 300), Pair(G4, 300), Pair(A4, 250), Pair(B4, 250), Pair(C5, 250), Pair(A4, 250), Pair(G4, 500),
                Pair(F4, 300), Pair(F4, 300), Pair(E4, 300), Pair(E4, 300), Pair(D4, 300), Pair(D4, 300), Pair(C4, 600)
            )
        ),
        RhymeItem(
            id = "rhyme_en_3",
            title = "The Wheels on the Bus",
            subtitle = "Fun Action Rhyme",
            language = "en",
            emoji = "🚌",
            colorHex = 0xFFFF7644,
            lines = listOf(
                RhymeLine("দ্য হুইলস অন দ্য বাস গো রাউন্ড অ্যান্ড রাউন্ড,", "The wheels on the bus go round and round,", 3000L),
                RhymeLine("রাউন্ড অ্যান্ড রাউন্ড, রাউন্ড অ্যান্ড রাউন্ড।", "Round and round, round and round.", 2800L),
                RhymeLine("দ্য হুইলস অন দ্য বাস গো রাউন্ড অ্যান্ড রাউন্ড,", "The wheels on the bus go round and round,", 3000L),
                RhymeLine("অল থ্রু দ্য টাউন!", "All through the town!", 2600L)
            ),
            melodyNotes = listOf(
                Pair(C4, 300), Pair(F4, 300), Pair(F4, 250), Pair(F4, 250), Pair(F4, 300), Pair(A4, 300), Pair(C5, 300), Pair(A4, 300), Pair(F4, 450),
                Pair(G4, 300), Pair(E4, 300), Pair(C4, 450),
                Pair(F4, 300), Pair(F4, 250), Pair(F4, 250), Pair(F4, 300), Pair(A4, 300), Pair(C5, 300), Pair(A4, 300), Pair(F4, 450),
                Pair(G4, 300), Pair(C4, 300), Pair(F4, 600)
            )
        ),
        RhymeItem(
            id = "rhyme_en_4",
            title = "Old MacDonald Had a Farm",
            subtitle = "Animal Sounds Fun Song",
            language = "en",
            emoji = "🚜",
            colorHex = 0xFF26DE81,
            lines = listOf(
                RhymeLine("ওল্ড ম্যাকডোনাল্ড হ্যাড আ ফার্ম, ই-আই-ই-আই-ও!", "Old MacDonald had a farm, E-I-E-I-O!", 3200L),
                RhymeLine("অ্যান্ড অন হিজ ফার্ম হি হ্যাড সাম ডাকস, ই-আই-ই-আই-ও!", "And on his farm he had some ducks, E-I-E-I-O!", 3400L),
                RhymeLine("উইথ আ কোয়াক-কোয়াক হিয়ার অ্যান্ড আ কোয়াক-কোয়াক দেয়ার,", "With a quack-quack here and a quack-quack there,", 3200L),
                RhymeLine("ওল্ড ম্যাকডোনাল্ড হ্যাড আ ফার্ম, ই-আই-ই-আই-ও!", "Old MacDonald had a farm, E-I-E-I-O!", 3200L)
            ),
            melodyNotes = listOf(
                Pair(F4, 300), Pair(F4, 300), Pair(F4, 300), Pair(C4, 300), Pair(D4, 300), Pair(D4, 300), Pair(C4, 500),
                Pair(A4, 300), Pair(A4, 300), Pair(G4, 300), Pair(G4, 300), Pair(F4, 600)
            )
        ),
        RhymeItem(
            id = "rhyme_en_5",
            title = "Humpty Dumpty",
            subtitle = "Classic Nursery Rhyme",
            language = "en",
            emoji = "🥚",
            colorHex = 0xFFA55EEA,
            lines = listOf(
                RhymeLine("হাম্পটি ডাম্পটি স্যাট অন আ ওয়াল,", "Humpty Dumpty sat on a wall,", 2700L),
                RhymeLine("হাম্পটি ডাম্পটি হ্যাড আ গ্রেট ফল।", "Humpty Dumpty had a great fall.", 2800L),
                RhymeLine("অল দ্য কিংস হর্সেস অ্যান্ড অল দ্য কিংস মেন,", "All the king's horses and all the king's men,", 3000L),
                RhymeLine("কুডন্ট পুট হাম্পটি টুগেদার আগেইন!", "Couldn't put Humpty together again!", 3100L)
            ),
            melodyNotes = listOf(
                Pair(G4, 300), Pair(E4, 300), Pair(G4, 300), Pair(E4, 300), Pair(G4, 300), Pair(E4, 300), Pair(C4, 500),
                Pair(A4, 300), Pair(F4, 300), Pair(A4, 300), Pair(F4, 300), Pair(D4, 500)
            )
        ),
        RhymeItem(
            id = "rhyme_en_6",
            title = "Row, Row, Row Your Boat",
            subtitle = "Gentle River Song",
            language = "en",
            emoji = "🛶",
            colorHex = 0xFF0FB9B1,
            lines = listOf(
                RhymeLine("রো, রো, রো ইয়োর বোট,", "Row, row, row your boat,", 2500L),
                RhymeLine("জেন্টলি ডাউন দ্য স্ট্রিম।", "Gently down the stream.", 2500L),
                RhymeLine("মেরিলি, মেরিলি, মেরিলি, মেরিলি,", "Merrily, merrily, merrily, merrily,", 2700L),
                RhymeLine("লাইফ ইজ বাট আ ড্রিম!", "Life is but a dream!", 2700L)
            ),
            melodyNotes = listOf(
                Pair(C4, 450), Pair(C4, 450), Pair(C4, 350), Pair(D4, 250), Pair(E4, 450),
                Pair(E4, 350), Pair(D4, 250), Pair(E4, 350), Pair(F4, 250), Pair(G4, 700),
                Pair(C5, 200), Pair(C5, 200), Pair(C5, 200), Pair(G4, 200), Pair(G4, 200), Pair(G4, 200), Pair(E4, 200), Pair(E4, 200), Pair(E4, 200), Pair(C4, 200), Pair(C4, 200), Pair(C4, 200),
                Pair(G4, 350), Pair(F4, 250), Pair(E4, 350), Pair(D4, 250), Pair(C4, 700)
            )
        )
    )
}
