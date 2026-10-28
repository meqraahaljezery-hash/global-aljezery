package com.example.data

object AppStrings {
    fun appTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "مَقْرَأَةُ الإِمَامِ الجَزَرِيِّ لِلإِقْرَاءِ وَالإِجَازَةِ"
        AppLanguage.AMHARIC -> "የኢማም አል-ጀዘሪ ቁርኣን መቅረዓ ለንባብ እና ለኢጃዛህ"
        AppLanguage.ENGLISH -> "Imam Al-Jazari Quranic Maqraah"
    }

    fun tabHome(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "الرئيسية"
        AppLanguage.AMHARIC -> "መነሻ"
        AppLanguage.ENGLISH -> "Home"
    }

    fun tabChats(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "محادثات"
        AppLanguage.AMHARIC -> "ውይይቶች"
        AppLanguage.ENGLISH -> "Chats"
    }

    fun tabGroups(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "مجموعات"
        AppLanguage.AMHARIC -> "ቡድኖች"
        AppLanguage.ENGLISH -> "Groups"
    }

    fun tabMyAccount(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "حسابي"
        AppLanguage.AMHARIC -> "መለያዬ"
        AppLanguage.ENGLISH -> "My Account"
    }

    fun tabSettings(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "إعدادات"
        AppLanguage.AMHARIC -> "ቅንብሮች"
        AppLanguage.ENGLISH -> "Settings"
    }

    fun tabLiveRooms(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "المجالس المباشرة"
        AppLanguage.AMHARIC -> "ቀጥታ ክፍሎች"
        AppLanguage.ENGLISH -> "Live Rooms"
    }

    fun tabPackages(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "الباقات والدقائق"
        AppLanguage.AMHARIC -> "ፓኬጆች እና ደቂቃዎች"
        AppLanguage.ENGLISH -> "Packages & Minutes"
    }

    fun tabTeacherStudio(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "استوديو الشيخ"
        AppLanguage.AMHARIC -> "የሸይኽ ስቱዲዮ"
        AppLanguage.ENGLISH -> "Sheikh Studio"
    }

    fun tabAdmin(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "الإدارة والتدقيق"
        AppLanguage.AMHARIC -> "አስተዳደር"
        AppLanguage.ENGLISH -> "Admin Panel"
    }

    fun storiesTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "الحلقات والفرائد"
        AppLanguage.AMHARIC -> "የቀን ማስታወሻዎች"
        AppLanguage.ENGLISH -> "Academy Stories"
    }

    fun categoryAll(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "الكل"
        AppLanguage.AMHARIC -> "ሁሉም"
        AppLanguage.ENGLISH -> "All"
    }

    fun categoryPrivate(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "خاص"
        AppLanguage.AMHARIC -> "የግል"
        AppLanguage.ENGLISH -> "Private"
    }

    fun categoryGroups(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "الحلقات"
        AppLanguage.AMHARIC -> "ቡድኖች"
        AppLanguage.ENGLISH -> "Halaqat"
    }

    fun categoryChannels(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "القنوات"
        AppLanguage.AMHARIC -> "ቻናሎች"
        AppLanguage.ENGLISH -> "Channels"
    }

    fun categoryUnread(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "غير مقروء"
        AppLanguage.AMHARIC -> "ያልተነበቡ"
        AppLanguage.ENGLISH -> "Unread"
    }

    fun joinLiveRoom(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "انضم للمجلس الصوتي المباشر"
        AppLanguage.AMHARIC -> "ወደ ቀጥታ ድምፅ ክፍል ግባ"
        AppLanguage.ENGLISH -> "Join Live Audio Room"
    }

    fun enterMessageHint(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "اكتب رسالة أو ابدأ تسجيلاً صوتياً..."
        AppLanguage.AMHARIC -> "መልእክት ይጻፉ ወይም ድምጽ ይቅረጹ..."
        AppLanguage.ENGLISH -> "Type a message or record audio..."
    }

    fun minuteBalance(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "رصيد الدقائق المتاح"
        AppLanguage.AMHARIC -> "ያሉዎት ደቂቃዎች"
        AppLanguage.ENGLISH -> "Available Minutes"
    }

    fun minutesUnit(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "دقيقة"
        AppLanguage.AMHARIC -> "ደቂቃ"
        AppLanguage.ENGLISH -> "Minutes"
    }

    fun buyPackage(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "طلب باقة دقائق"
        AppLanguage.AMHARIC -> "ደቂቃዎችን ይግዙ"
        AppLanguage.ENGLISH -> "Purchase Package"
    }

    fun requestToRecite(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "طلب دور للتلاوة (رفع اليد)"
        AppLanguage.AMHARIC -> "የንባብ ተራ መጠየቅ"
        AppLanguage.ENGLISH -> "Request Recitation Turn"
    }

    fun livekitConnecting(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "متصل بخادم LiveKit الصوتي المباشر"
        AppLanguage.AMHARIC -> "ከቀጥታ LiveKit ሰርቨር ጋር ተገናኝቷል"
        AppLanguage.ENGLISH -> "Connected to LiveKit Real-Time Media"
    }

    fun tajweedFeedbackTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "ملاحظات التجويد المباشرة"
        AppLanguage.AMHARIC -> "የተጅዊድ ማረሚያ"
        AppLanguage.ENGLISH -> "Live Tajweed Corrections"
    }

    fun queueTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "قائمة انتظار الطلاب"
        AppLanguage.AMHARIC -> "የተማሪዎች ወረፋ"
        AppLanguage.ENGLISH -> "Student Recitation Queue"
    }

    fun callNextStudent(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "استدعاء الطالب التالي للتسميع"
        AppLanguage.AMHARIC -> "ቀጣዩን ተማሪ ጥራ"
        AppLanguage.ENGLISH -> "Call Next Student"
    }

    fun endRecitation(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "إنهاء التسميع وخصم الدقائق"
        AppLanguage.AMHARIC -> "ንባብ ጨርስ እና ደቂቃዎችን ቀንስ"
        AppLanguage.ENGLISH -> "Finish Recitation & Deduct"
    }

    fun approvePayment(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "اعتماد الشحن وإيداع الدقائق"
        AppLanguage.AMHARIC -> "ክፍያ አጽድቅ እና ደቂቃ ጨምር"
        AppLanguage.ENGLISH -> "Approve & Credit Minutes"
    }

    fun rejectPayment(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "رفض الإيصال"
        AppLanguage.AMHARIC -> "ክፍያ ውድቅ አድርግ"
        AppLanguage.ENGLISH -> "Reject Request"
    }

    fun sanadLineageTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "سلسلة الإسناد القرآني المتصل"
        AppLanguage.AMHARIC -> "የቁርኣን ሰነድ ሰንሰለት"
        AppLanguage.ENGLISH -> "Connected Quranic Sanad Lineage"
    }

    fun startBroadcastTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "بدء البث الصوتي والمرئي (المعلم والمشرف فقط)"
        AppLanguage.AMHARIC -> "ቀጥታ ስርጭት ጀምር (ለአስተማሪ ብቻ)"
        AppLanguage.ENGLISH -> "Start Live Broadcast (Teacher/Admin only)"
    }

    fun broadcastPermissionDenied(lang: AppLanguage): String = when (lang) {
        AppLanguage.ARABIC -> "عذراً، صلاحية بدء البث والمكالمة مخصصة للمعلم والمشرف فقط. يمكنك كطالب الاستماع والمشاركة ورفع اليد."
        AppLanguage.AMHARIC -> "ይቅርታ፣ ስርጭት የመጀመር ፈቃድ ለአስተማሪ እና አስተዳዳሪ ብቻ የተፈቀደ ነው።"
        AppLanguage.ENGLISH -> "Permission denied: Only Teachers and Supervisors can initiate live streams and group calls."
    }
}
