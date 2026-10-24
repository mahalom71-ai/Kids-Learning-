package com.example.data

enum class RewardType {
    MEDAL,
    BADGE
}

enum class MedalTier {
    BRONZE,
    SILVER,
    GOLD,
    DIAMOND
}

data class BadgeReward(
    val id: String,
    val titleBangla: String,
    val titleEnglish: String,
    val descriptionBangla: String,
    val descriptionEnglish: String,
    val iconEmoji: String,
    val colorHex: Long,
    val rewardType: RewardType,
    val medalTier: MedalTier? = null,
    val targetCount: Int,
    val bonusStars: Int = 5
)

data class KidLevel(
    val levelNumber: Int,
    val titleBangla: String,
    val titleEnglish: String,
    val iconEmoji: String,
    val minStars: Int,
    val maxStars: Int
)

object RewardData {
    val medals = listOf(
        BadgeReward(
            id = "medal_bronze",
            titleBangla = "ব্রোঞ্জ পদক",
            titleEnglish = "Bronze Tracing Medal",
            descriptionBangla = "৩টি বর্ণ সফলভাবে আঁকো",
            descriptionEnglish = "Trace your first 3 letters successfully",
            iconEmoji = "🥉",
            colorHex = 0xFFCD7F32,
            rewardType = RewardType.MEDAL,
            medalTier = MedalTier.BRONZE,
            targetCount = 3,
            bonusStars = 5
        ),
        BadgeReward(
            id = "medal_silver",
            titleBangla = "সিলভার পদক",
            titleEnglish = "Silver Tracing Medal",
            descriptionBangla = "১০টি বর্ণ নিখুঁতভাবে আঁকো",
            descriptionEnglish = "Trace 10 letters with high precision",
            iconEmoji = "🥈",
            colorHex = 0xFFC0C0C0,
            rewardType = RewardType.MEDAL,
            medalTier = MedalTier.SILVER,
            targetCount = 10,
            bonusStars = 10
        ),
        BadgeReward(
            id = "medal_gold",
            titleBangla = "স্বর্ণ পদক",
            titleEnglish = "Gold Tracing Medal",
            descriptionBangla = "২৫টি বর্ণ ৩-স্টার পেয়ে সম্পন্ন করো",
            descriptionEnglish = "Trace 25 letters with 3 stars",
            iconEmoji = "🥇",
            colorHex = 0xFFFFD700,
            rewardType = RewardType.MEDAL,
            medalTier = MedalTier.GOLD,
            targetCount = 25,
            bonusStars = 15
        ),
        BadgeReward(
            id = "medal_diamond",
            titleBangla = "হীরক পদক",
            titleEnglish = "Diamond Master Medal",
            descriptionBangla = "৪৫টি বর্ণ সফলভাবে অনুশীলন করো",
            descriptionEnglish = "Trace 45+ letters across Bengali & English",
            iconEmoji = "💎",
            colorHex = 0xFF00D2D3,
            rewardType = RewardType.MEDAL,
            medalTier = MedalTier.DIAMOND,
            targetCount = 45,
            bonusStars = 25
        )
    )

    val achievementBadges = listOf(
        BadgeReward(
            id = "badge_vowel_master",
            titleBangla = "স্বরবর্ণের জাদুকর",
            titleEnglish = "Vowel Wizard",
            descriptionBangla = "সবগুলো ১১টি স্বরবর্ণ (অ-ঔ) সম্পন্ন করো",
            descriptionEnglish = "Master all 11 Bengali Vowels",
            iconEmoji = "🧙‍♂️",
            colorHex = 0xFF5C52E5,
            rewardType = RewardType.BADGE,
            targetCount = 11,
            bonusStars = 10
        ),
        BadgeReward(
            id = "badge_tiger_scribe",
            titleBangla = "বাঘের মতো সাহসী",
            titleEnglish = "Tiger Scribe",
            descriptionBangla = "প্রথম ৫টি ব্যঞ্জনবর্ণ অনুশীলন করো",
            descriptionEnglish = "Trace your first 5 Bengali Consonants",
            iconEmoji = "🐯",
            colorHex = 0xFFFF7644,
            rewardType = RewardType.BADGE,
            targetCount = 5,
            bonusStars = 8
        ),
        BadgeReward(
            id = "badge_abc_explorer",
            titleBangla = "ইংরেজি অভিযাত্রী",
            titleEnglish = "ABC Explorer",
            descriptionBangla = "১০টি ইংরেজি বর্ণ আঁকো",
            descriptionEnglish = "Trace 10 English alphabet letters",
            iconEmoji = "🔤",
            colorHex = 0xFF3A86FF,
            rewardType = RewardType.BADGE,
            targetCount = 10,
            bonusStars = 8
        ),
        BadgeReward(
            id = "badge_star_collector",
            titleBangla = "তারা সংগ্রাহক",
            titleEnglish = "Star Collector",
            descriptionBangla = "২৫টি তারা সংগ্রহ করো",
            descriptionEnglish = "Collect 25 total stars",
            iconEmoji = "⭐",
            colorHex = 0xFFFFB800,
            rewardType = RewardType.BADGE,
            targetCount = 25,
            bonusStars = 10
        ),
        BadgeReward(
            id = "badge_streak_champion",
            titleBangla = "নিয়মিত শিক্ষার্থী",
            titleEnglish = "Streak Champion",
            descriptionBangla = "ধারাবাহিক ৩ দিন অনুশীলন করো",
            descriptionEnglish = "Maintain a 3-day practice streak",
            iconEmoji = "🔥",
            colorHex = 0xFFFF5E7E,
            rewardType = RewardType.BADGE,
            targetCount = 3,
            bonusStars = 10
        ),
        BadgeReward(
            id = "badge_grand_scholar",
            titleBangla = "মহা পণ্ডিত",
            titleEnglish = "Grand Scholar",
            descriptionBangla = "৫০টি তারা অর্জন করে পণ্ডিত হও",
            descriptionEnglish = "Collect 50 total stars across all games",
            iconEmoji = "🎓",
            colorHex = 0xFF00BFA6,
            rewardType = RewardType.BADGE,
            targetCount = 50,
            bonusStars = 20
        ),
        BadgeReward(
            id = "badge_quiz_champion",
            titleBangla = "কুইজ চ্যাম্পিয়ন",
            titleEnglish = "Quiz Champion",
            descriptionBangla = "দৈনিক কুইজে অংশ নিয়ে পদক জেতো",
            descriptionEnglish = "Complete the Daily Quick Quiz successfully",
            iconEmoji = "⚡",
            colorHex = 0xFFFF7644,
            rewardType = RewardType.BADGE,
            targetCount = 1,
            bonusStars = 10
        )
    )

    val allRewards = medals + achievementBadges

    val levels = listOf(
        KidLevel(1, "নবীন শিক্ষার্থী", "Novice Doodler", "🐣", 0, 15),
        KidLevel(2, "ছোট লেখক", "Junior Scribe", "🐰", 16, 30),
        KidLevel(3, "হাতেখড়ি ওস্তাদ", "Letter Wizard", "🦊", 31, 55),
        KidLevel(4, "মাস্টার ক্যালিগ্রাফার", "Master Calligrapher", "🦁", 56, 85),
        KidLevel(5, "বর্ণমালার রাজা", "Bornomala Champion", "👑", 86, 999)
    )

    fun getLevelForStars(stars: Int): KidLevel {
        return levels.lastOrNull { stars >= it.minStars } ?: levels.first()
    }
}
