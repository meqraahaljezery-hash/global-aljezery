package com.example.data

enum class AppLanguage(val code: String, val displayName: String, val isRtl: Boolean) {
    ARABIC("ar", "العربية", true),
    AMHARIC("am", "አማርኛ", false),
    ENGLISH("en", "English", false)
}

enum class UserRole(val titleAr: String, val titleAm: String, val titleEn: String) {
    STUDENT("طالب مقرأة", "የቁርኣን ተማሪ", "Quran Student"),
    TEACHER("الشيخ المقرئ", "የቁርኣን ሸይኽ / መምህር", "Master Reciter / Sheikh"),
    ADMIN("المشرف العام", "ዋና አስተዳዳሪ", "Academy Admin")
}

data class AcademyAccount(
    val id: String,
    val email: String,
    val name: String,
    val role: UserRole,
    val description: String,
    val isOnline: Boolean = true
)

data class ChatConversation(
    val id: String,
    val title: String,
    val subtitle: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0,
    val isGroup: Boolean = false,
    val isChannel: Boolean = false,
    val isVerified: Boolean = false,
    val isOnline: Boolean = false,
    val hasAudioLastMessage: Boolean = false,
    val liveRoomId: String? = null
)

data class ChatMessage(
    val id: String,
    val conversationId: String,
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isMine: Boolean,
    val isAudio: Boolean = false,
    val audioDurationSec: Int = 0,
    val isRead: Boolean = true
)

data class StoryItem(
    val id: String,
    val authorName: String,
    val title: String,
    val timeAgo: String,
    val isLiveNow: Boolean = false,
    val hasUnseen: Boolean = true
)

data class LiveRoomInfo(
    val id: String,
    val nameAr: String,
    val nameAm: String,
    val nameEn: String,
    val teacherName: String,
    val riwayah: String,
    val currentSurah: String,
    val participantsCount: Int,
    val isLive: Boolean = true,
    val activeReciter: String? = null
)

enum class QueueStatus {
    WAITING,
    RECITING,
    COMPLETED
}

data class StudentQueueItem(
    val id: String,
    val studentName: String,
    val riwayah: String,
    val surah: String,
    val minutesBalance: Int,
    val status: QueueStatus
)

data class MinutePackage(
    val id: String,
    val nameAr: String,
    val nameAm: String,
    val nameEn: String,
    val minutes: Int,
    val priceUsd: Double,
    val descAr: String,
    val descAm: String,
    val descEn: String,
    val isPopular: Boolean = false
)

enum class PaymentRequestStatus {
    PENDING,
    APPROVED,
    REJECTED
}

data class PaymentRequest(
    val id: String,
    val studentName: String,
    val packageName: String,
    val minutes: Int,
    val amount: Double,
    val transactionId: String,
    val senderPhone: String,
    val paymentMethod: String,
    val date: String,
    val status: PaymentRequestStatus
)

data class SanadNode(
    val id: String,
    val name: String,
    val era: String,
    val riwayah: String,
    val description: String
)

data class QuranProgram(
    val id: String,
    val title: String,
    val category: String, // "تلقين", "تصحيح", "حفظ", "إجازة بالسند", "القراءات السبع"
    val description: String,
    val howToUse: String,
    val requirements: String,
    val durationText: String,
    val isRegistrationOpen: Boolean = true,
    val enrolledCount: Int = 24
)

data class BankPaymentInfo(
    val id: String,
    val bankName: String,
    val accountHolder: String,
    val accountNumber: String,
    val ibanOrIdentifier: String,
    val currency: String,
    val instructions: String,
    val isPrimary: Boolean = true
)

enum class GroupTiming(val labelAr: String) {
    MORNING("الفترة الصباحية (بعد الفجر)"),
    EVENING("الفترة المسائية (بعد العصر)"),
    NIGHT("الفترة الليلية (بعد العشاء)"),
    GENERAL("ملتقى عام مستمر")
}

enum class GroupGender(val labelAr: String) {
    MALE("حلقة الذكور (رجال)"),
    FEMALE("حلقة الإناث (أخوات)"),
    ALL("ملتقى عام للجميع")
}

data class StudyGroup(
    val id: String,
    val name: String,
    val timing: GroupTiming,
    val gender: GroupGender,
    val description: String,
    val sheikhSupervisor: String,
    val membersCount: Int,
    val isLiveBroadcastActive: Boolean = false,
    val liveRoomId: String? = null
)

enum class FriendStatus {
    NONE,
    REQUEST_SENT,
    FRIENDS
}

data class GroupMember(
    val id: String,
    val name: String,
    val role: UserRole,
    val isOnline: Boolean,
    val friendStatus: FriendStatus = FriendStatus.NONE,
    val minutesBalance: Int = 45,
    val qiraah: String = "حفص عن عاصم"
)

data class MinuteLoanRequest(
    val id: String,
    val studentName: String,
    val requestedMinutes: Int,
    val reason: String,
    val requestDate: String,
    val status: PaymentRequestStatus = PaymentRequestStatus.PENDING
)
