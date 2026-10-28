package com.example.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class MaqraahRepository : ViewModel() {
    private val _currentLanguage = MutableStateFlow(AppLanguage.ARABIC)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    // Day Mode (false) or Night Mode (true) - default Day Mode (Light blue, white, gold)
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    val availableAccounts = listOf(
        AcademyAccount(
            id = "acc_admin",
            email = "khdahd241@gmail.com",
            name = "فضيلة الشيخ خ. د. أحمد",
            role = UserRole.ADMIN,
            description = "المدير والمشرف العام (صلاحيات المدير والمعلم والتحكم الكامل)"
        ),
        AcademyAccount(
            id = "acc_teacher",
            email = "sheikh.abdullah@jazari.org",
            name = "الشيخ عبد الله الأثيوبي",
            role = UserRole.TEACHER,
            description = "مقرئ مجاز (صلاحيات المعلم وبدء البث والمكالمات وإدارة التسميع)"
        ),
        AcademyAccount(
            id = "acc_student",
            email = "student.abdurrahman@jazari.org",
            name = "عبد الرحمن السعيد",
            role = UserRole.STUDENT,
            description = "طالب مقرأة (صلاحيات الطالب، طلب التسميع، الشحن، والصداقات)"
        )
    )

    private val _currentAccount = MutableStateFlow(availableAccounts[0])
    val currentAccount: StateFlow<AcademyAccount> = _currentAccount.asStateFlow()

    private val _currentUserEmail = MutableStateFlow<String?>(availableAccounts[0].email)
    val currentUserEmail: StateFlow<String?> = _currentUserEmail.asStateFlow()

    private val _currentRole = MutableStateFlow(availableAccounts[0].role)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    private val _studentMinutesBalance = MutableStateFlow(120)
    val studentMinutesBalance: StateFlow<Int> = _studentMinutesBalance.asStateFlow()

    private val _currentTab = MutableStateFlow(com.example.ui.MainTab.HOME)
    val currentTab: StateFlow<com.example.ui.MainTab> = _currentTab.asStateFlow()

    private val _selectedConversationId = MutableStateFlow<String?>(null)
    val selectedConversationId: StateFlow<String?> = _selectedConversationId.asStateFlow()

    private val _selectedGroup = MutableStateFlow<StudyGroup?>(null)
    val selectedGroup: StateFlow<StudyGroup?> = _selectedGroup.asStateFlow()

    private val _selectedLiveRoom = MutableStateFlow<LiveRoomInfo?>(null)
    val selectedLiveRoom: StateFlow<LiveRoomInfo?> = _selectedLiveRoom.asStateFlow()

    private val _permissionWarningMessage = MutableStateFlow<String?>(null)
    val permissionWarningMessage: StateFlow<String?> = _permissionWarningMessage.asStateFlow()

    private val _showGroupProfileDialog = MutableStateFlow<StudyGroup?>(null)
    val showGroupProfileDialog: StateFlow<StudyGroup?> = _showGroupProfileDialog.asStateFlow()

    private val _isMicMuted = MutableStateFlow(false)
    val isMicMuted: StateFlow<Boolean> = _isMicMuted.asStateFlow()

    private val _isCameraOn = MutableStateFlow(true)
    val isCameraOn: StateFlow<Boolean> = _isCameraOn.asStateFlow()

    private val _isHandRaised = MutableStateFlow(false)
    val isHandRaised: StateFlow<Boolean> = _isHandRaised.asStateFlow()

    private val _liveRecitationSeconds = MutableStateFlow(0)
    val liveRecitationSeconds: StateFlow<Int> = _liveRecitationSeconds.asStateFlow()

    private val _liveKitServerUrl = MutableStateFlow("wss://aljazari-livekit.maqraah.org")
    val liveKitServerUrl: StateFlow<String> = _liveKitServerUrl.asStateFlow()

    private val _liveKitToken = MutableStateFlow("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val liveKitToken: StateFlow<String> = _liveKitToken.asStateFlow()

    // Editable by Admin in Settings
    private val _academyBannerText = MutableStateFlow(
        "مرحباً بكم في مقرأة الإمام الجزري للإقراء والإجازة بالسند المتصل. التسجيل مفتوح الآن في مسارات الإجازة والتلقين للفترات الصباحية والمسائية والليلية مع كبار مشايخ الإقراء."
    )
    val academyBannerText: StateFlow<String> = _academyBannerText.asStateFlow()

    private val _aboutMaqraahText = MutableStateFlow(
        "مقرأة الإمام الجزري صرح قرآني عالمي متخصص في تعليم التلاوة، والتلقين، وتصحيح القراءة، وضبط المتون، وإقراء القراءات السبع والعشر، ومنح الإجازات القرآنية بالسند المتصل إلى رسول الله ﷺ، بإشراف الشيخ خ. د. أحمد ونخبة من علماء الإقراء المعتمدين."
    )
    val aboutMaqraahText: StateFlow<String> = _aboutMaqraahText.asStateFlow()

    // Programs list editable by Admin in Settings
    private val _programs = MutableStateFlow(
        listOf(
            QuranProgram(
                id = "prog_talqin",
                title = "برنامج التلقين وتصحيح التلاوة (قصار السور وجزء عم)",
                category = "التلقين والتصحيح",
                description = "جلسات تلقين مباشرة كلمة بكلمة وآية بآية مع مراعاة مخارج الحروف، موجه للمبتدئين والناشئة والأعاجم لضبط الفاتحة وقصار السور.",
                howToUse = "يحضر الطالب في الموعد المحدد، يستمع لقراءة الشيخ المعلم، ثم يعيد خلفه ويصحح الشيخ اللحن الجلي والخفي مباشرة.",
                requirements = "لا يشترط معرفة مسبقة بالتجويد، متاح لجميع الأعمار والمستويات.",
                durationText = "30 - 60 دقيقة أسبوعياً",
                isRegistrationOpen = true,
                enrolledCount = 58
            ),
            QuranProgram(
                id = "prog_hifz",
                title = "برنامج الحفظ والمراجعة وضبط المتشابهات القرآنية",
                category = "الحفظ والمراجعة",
                description = "مسار منهجي لحفظ القرآن كاملاً أو أجزاء منه مع جدول مراجعة يومي وتسميع مباشر واختبارات دورية في المتشابهات.",
                howToUse = "يحفظ الطالب الورد اليومي المقرر (صفحة إلى صفحتين)، ويدخل قائمة انتظار التسميع اليومية ليسمع ورده للشيخ.",
                requirements = "إتقان القراءة النظرية من المصحف برسم عثماني والتزام الحضور المنتظم.",
                durationText = "فترات صباحية ومسائية",
                isRegistrationOpen = true,
                enrolledCount = 112
            ),
            QuranProgram(
                id = "prog_ijazah",
                title = "مسار ختمة الإجازة بالسند المتصل إلى رسول الله ﷺ",
                category = "الإجازة بالسند المتصل",
                description = "قراءة القرآن كاملاً من أول الفاتحة إلى سورة الناس غيباً أو نظراً مع التحريرات وإتقان أوجه الرواية لنيل السند المتصل العالي.",
                howToUse = "يخصص للطالب وقت تسميع معتبر في مجلس الشيخ خ. د. أحمد، ويسجل الطالب في دفتر الختمات المعتمد مع متابعة دقيقة لكل ربع.",
                requirements = "أن يكون الطالب حافظاً للقرآن أو متقناً لأحكام التجويد متناً وتطبيقاً مع اجتياز المقابلة التأهيلية.",
                durationText = "ختمة كاملة (600+ دقيقة)",
                isRegistrationOpen = true,
                enrolledCount = 34
            ),
            QuranProgram(
                id = "prog_qiraat",
                title = "برنامج دراسة وإقراء القراءات السبع من طريق الشاطبية",
                category = "القراءات السبع",
                description = "دراسة أصول وفرش القراء السبعة (نافع، ابن كثير، أبو عمرو، ابن عامر، عاصم، حمزة، الكسائي) وتطبيق الجمع بالإفراد أو الجمع بالوقف.",
                howToUse = "حضور المجلس التخصصي الأسبوعي، مدارسة أبيات الشاطبية، وقراءة الأرباع المقررة على فضيلة الشيخ.",
                requirements = "الحصول على إجازة سابقة برواية حفص أو ورش، وحفظ منظومة حرز الأماني ووجه التهاني.",
                durationText = "مسار أكاديمي متقدم",
                isRegistrationOpen = true,
                enrolledCount = 19
            ),
            QuranProgram(
                id = "prog_matn",
                title = "مجلس ضبط وحفظ المنظومة الجزرية وتحفة الأطفال بالسند",
                category = "متون التجويد",
                description = "شرح متني المقدمة الجزرية للإمام ابن الجزري وتحفة الأطفال للإمام الجمزوري وتسميعهما ومنح الإجازة في المتن بالسند.",
                howToUse = "حفظ الأبيات المقررة أسبوعياً، الاستماع لشرح فضيلة الشيخ للعلل الصوتية، ثم اختبار الحفظ غيباً.",
                requirements = "مفتوح لكافة الطلاب والمدرسين الراغبين في ضبط قواعد الأداء والتجويد النظري.",
                durationText = "شهرين (جلسات أسبوعية)",
                isRegistrationOpen = true,
                enrolledCount = 76
            )
        )
    )
    val programs: StateFlow<List<QuranProgram>> = _programs.asStateFlow()

    // Bank and Payment details editable by Admin in Settings
    private val _bankDetails = MutableStateFlow(
        listOf(
            BankPaymentInfo(
                id = "bank_rajhi",
                bankName = "مصرف الراجحي (المملكة العربية السعودية)",
                accountHolder = "مقرأة الإمام الجزري الدولية",
                accountNumber = "SA4480000293608010992811",
                ibanOrIdentifier = "RJHI-SA-293608010",
                currency = "ريال سعودي (SAR) / دولار (USD)",
                instructions = "يرجى كتابة اسم الطالب في خانة الغرض من الحوالة وإرفاق رقم العملية."
            ),
            BankPaymentInfo(
                id = "bank_cbe",
                bankName = "Commercial Bank of Ethiopia (CBE) - البنك التجاري الإثيوبي",
                accountHolder = "Imam Al-Jazari Quranic Academy",
                accountNumber = "1000382910492",
                ibanOrIdentifier = "CBETETAA",
                currency = "ETB / USD",
                instructions = "ክፍያውን ካጠናቀቁ በኋላ የግብይት ቁጥሩን (Transaction ID) ያስገቡ።"
            ),
            BankPaymentInfo(
                id = "bank_telebirr",
                bankName = "Telebirr (ኢትዮጵያ) - الدفع السريع",
                accountHolder = "Al-Jazari Academy Ethiopia",
                accountNumber = "+251911223344",
                ibanOrIdentifier = "TELE-99214",
                currency = "ETB",
                instructions = "الدفع الفوري عبر تطبيق تليبير برقم الهاتف مع إرسال رقم إشعار التحويل."
            ),
            BankPaymentInfo(
                id = "bank_vodafone",
                bankName = "فودافون كاش (مصر)",
                accountHolder = "إدارة المقرأة - الفرع الإقليمي",
                accountNumber = "01099887766",
                ibanOrIdentifier = "VODACASH-EG",
                currency = "EGP",
                instructions = "التحويل عبر محفظة فودافون كاش وكتابة رقم الهاتف المحول منه."
            )
        )
    )
    val bankDetails: StateFlow<List<BankPaymentInfo>> = _bankDetails.asStateFlow()

    // 7 Study Groups (Telegram-Style Halaqat)
    private val _studyGroups = MutableStateFlow(
        listOf(
            StudyGroup(
                id = "grp_m_morning",
                name = "ذكور صباحي",
                timing = GroupTiming.MORNING,
                gender = GroupGender.MALE,
                description = "حلقة مخصصة للرجال والشباب في الفترة الصباحية بعد صلاة الفجر لقراءة الختمات والتسميع اليومي.",
                sheikhSupervisor = "فضيلة الشيخ خ. د. أحمد",
                membersCount = 42,
                isLiveBroadcastActive = true,
                liveRoomId = "room_1"
            ),
            StudyGroup(
                id = "grp_m_evening",
                name = "ذكور مسائي",
                timing = GroupTiming.EVENING,
                gender = GroupGender.MALE,
                description = "حلقة الفترة المسائية للذكور بعد العصر لتصحيح التلاوة والتلقين وضبط أحكام التجويد.",
                sheikhSupervisor = "الشيخ عبد الله الأثيوبي",
                membersCount = 38,
                isLiveBroadcastActive = false,
                liveRoomId = "room_2"
            ),
            StudyGroup(
                id = "grp_m_night",
                name = "ذكور ليلي",
                timing = GroupTiming.NIGHT,
                gender = GroupGender.MALE,
                description = "حلقة ليلية مكثفة للذكور بعد العشاء لقراءة أجزاء الحفظ والمراجعة ومسارات الإجازة.",
                sheikhSupervisor = "فضيلة الشيخ خ. د. أحمد",
                membersCount = 55,
                isLiveBroadcastActive = true,
                liveRoomId = "room_1"
            ),
            StudyGroup(
                id = "grp_f_morning",
                name = "إناث صباحي",
                timing = GroupTiming.MORNING,
                gender = GroupGender.FEMALE,
                description = "حلقة خاصة بالأخوات في الفترة الصباحية لحفظ القرآن الكريم وتصحيح مخارج الحروف تحت إشراف شيخة مجازة بالسند.",
                sheikhSupervisor = "الشيخة أم عبد الرحمن (المجازة بالعشر)",
                membersCount = 64,
                isLiveBroadcastActive = false,
                liveRoomId = "room_4"
            ),
            StudyGroup(
                id = "grp_f_evening",
                name = "إناث مسائي",
                timing = GroupTiming.EVENING,
                gender = GroupGender.FEMALE,
                description = "حلقة مسائية للأخوات بعد العصر لتلقين جزء عم وقصار السور وتثبيت الحفظ والمراجعة الأسبوعية.",
                sheikhSupervisor = "الشيخة فاطمة الزهراء",
                membersCount = 49,
                isLiveBroadcastActive = true,
                liveRoomId = "room_2"
            ),
            StudyGroup(
                id = "grp_f_night",
                name = "إناث ليلي",
                timing = GroupTiming.NIGHT,
                gender = GroupGender.FEMALE,
                description = "حلقة ليلية للأخوات بعد العشاء في مسار ختمة الإجازة بالسند وقراءة أصول الشاطبية والدرة.",
                sheikhSupervisor = "الشيخة أم عبد الرحمن (المجازة بالعشر)",
                membersCount = 31,
                isLiveBroadcastActive = false,
                liveRoomId = "room_3"
            ),
            StudyGroup(
                id = "grp_general",
                name = "الملتقى العام",
                timing = GroupTiming.GENERAL,
                gender = GroupGender.ALL,
                description = "ملتقى مفتوح لجميع طلاب وطالبات ومشايخ المقرأة لنشر الفوائد القرآنية، المتشابهات، ومواعيد الندوات العامة.",
                sheikhSupervisor = "إدارة المقرأة العامة",
                membersCount = 280,
                isLiveBroadcastActive = false
            )
        )
    )
    val studyGroups: StateFlow<List<StudyGroup>> = _studyGroups.asStateFlow()

    // Group members for each group
    private val _groupMembers = MutableStateFlow(
        listOf(
            GroupMember("gm_sheikh", "فضيلة الشيخ خ. د. أحمد", UserRole.TEACHER, isOnline = true, qiraah = "القراءات العشر الكبرى بالسند"),
            GroupMember("gm_admin", "المشرف العام (الإدارة)", UserRole.ADMIN, isOnline = true, qiraah = "إدارة المقرأة"),
            GroupMember("gm_std_1", "عبد الرحمن السعيد", UserRole.STUDENT, isOnline = true, qiraah = "حفص عن عاصم", friendStatus = FriendStatus.FRIENDS),
            GroupMember("gm_std_2", "بلال حسن الإثيوبي", UserRole.STUDENT, isOnline = true, qiraah = "ورش عن نافع", friendStatus = FriendStatus.NONE),
            GroupMember("gm_std_3", "عمر الفاروق الصومالي", UserRole.STUDENT, isOnline = false, qiraah = "قالون عن نافع", friendStatus = FriendStatus.NONE),
            GroupMember("gm_std_4", "زيد بن ثابت الجزائري", UserRole.STUDENT, isOnline = true, qiraah = "الدوري عن أبي عمرو", friendStatus = FriendStatus.NONE),
            GroupMember("gm_std_5", "محمد الأمين السوداني", UserRole.STUDENT, isOnline = false, qiraah = "حفص عن عاصم", friendStatus = FriendStatus.REQUEST_SENT),
            GroupMember("gm_std_6", "إبراهيم خليل التركي", UserRole.STUDENT, isOnline = true, qiraah = "شعبة عن عاصم", friendStatus = FriendStatus.NONE)
        )
    )
    val groupMembers: StateFlow<List<GroupMember>> = _groupMembers.asStateFlow()

    // Minute loan requests
    private val _loanRequests = MutableStateFlow(
        listOf(
            MinuteLoanRequest("loan_1", "عبد الرحمن السعيد", 30, "إكمال ربع سورة النساء في جلسة التسميع اليومية", "27 أكتوبر 2026", PaymentRequestStatus.PENDING)
        )
    )
    val loanRequests: StateFlow<List<MinuteLoanRequest>> = _loanRequests.asStateFlow()

    // Stories
    private val _stories = MutableStateFlow(
        listOf(
            StoryItem("1", "فضيلة الشيخ خ. د. أحمد", "مجلس الإجازة المباشر الآن", "منذ 10 د", isLiveNow = true),
            StoryItem("2", "مقرأة الجزري", "شرح مخرج الضاد والظاء من الجزرية", "منذ ساعة", isLiveNow = false),
            StoryItem("3", "إدارة المقرأة", "باقات شهر رمضان المبارك مع خصم 20%", "منذ ساعتين", isLiveNow = false),
            StoryItem("4", "حلقة قالون", "تنبيه: اختبار أصول قالون غداً", "منذ 4 س", isLiveNow = false)
        )
    )
    val stories: StateFlow<List<StoryItem>> = _stories.asStateFlow()

    // Conversations
    private val _conversations = MutableStateFlow(
        listOf(
            ChatConversation(
                id = "conv_sheikh",
                title = "فضيلة الشيخ خ. د. أحمد (المقرئ المجاز)",
                subtitle = "أحسنت في تحقيق مخرج القاف، استمع للتصحيح الصوتي المرفق...",
                lastMessageTime = "10:45 ص",
                unreadCount = 2,
                isGroup = false,
                isVerified = true,
                isOnline = true,
                hasAudioLastMessage = true,
                liveRoomId = "room_1"
            ),
            ChatConversation(
                id = "conv_halaqah",
                title = "حلقة الإتقان والإجازة بالسند المتصل",
                subtitle = "الشيخ: تم فتح الغرفة الصوتية المباشرة لتسميع سورة الكهف",
                lastMessageTime = "10:30 ص",
                unreadCount = 5,
                isGroup = true,
                isVerified = true,
                liveRoomId = "room_1"
            ),
            ChatConversation(
                id = "conv_announcements",
                title = "قناة إعلانات المقرأة الرسمية",
                subtitle = "جدول مواعيد شيوخ الإقراء لهذا الأسبوع بالروايات العشر",
                lastMessageTime = "أمس",
                unreadCount = 0,
                isChannel = true,
                isVerified = true
            ),
            ChatConversation(
                id = "conv_support",
                title = "الدعم الفني وشحن الدقائق والباقات",
                subtitle = "تم استلام إيصال التحويل، وسيتم الشحن فور التدقيق",
                lastMessageTime = "26 أكتوبر",
                unreadCount = 0
            )
        )
    )
    val conversations: StateFlow<List<ChatConversation>> = _conversations.asStateFlow()

    private val _messagesMap = MutableStateFlow(
        mutableMapOf(
            "conv_sheikh" to mutableListOf(
                ChatMessage("m1", "conv_sheikh", "طالب مقرأة", "السلام عليكم ورحمة الله وبركاته يا شيخنا، هل يمكن مراجعة تلاوتي في سورة الفاتحة والآيات الأولى من البقرة؟", "10:20 ص", isMine = true, isRead = true),
                ChatMessage("m2", "conv_sheikh", "فضيلة الشيخ خ. د. أحمد", "وعليكم السلام ورحمة الله وبركاته، مرحباً بك يا بني. تفضل بإرسال تسجيلك الصوتي أو انضم إلى مجلس الإقراء المباشر الآن.", "10:25 ص", isMine = false, isRead = true),
                ChatMessage("m3", "conv_sheikh", "طالب مقرأة", "تلاوة صوتية: سورة الفاتحة برواية حفص عن عاصم", "10:35 ص", isMine = true, isAudio = true, audioDurationSec = 42, isRead = true),
                ChatMessage("m4", "conv_sheikh", "فضيلة الشيخ خ. د. أحمد", "ما شاء الله، قراءة طيبة. لكن انتبه لترقيق الراء في (غير المغضوب) وتخليص المفخم من المرقق.", "10:44 ص", isMine = false, isRead = false)
            ),
            "grp_m_morning" to mutableListOf(
                ChatMessage("gm1", "grp_m_morning", "فضيلة الشيخ خ. د. أحمد", "السلام عليكم ورحمة الله وبركاته يا أهل القرآن. مرحباً بكم في حلقة الفترة الصباحية.", "05:30 ص", isMine = false),
                ChatMessage("gm2", "grp_m_morning", "عبد الرحمن السعيد", "وعليكم السلام شيخنا الحبيب، جاهز للتسميع في سورة النساء إن شاء الله.", "05:35 ص", isMine = false),
                ChatMessage("gm3", "grp_m_morning", "المشرف العام", "تذكير: البث المباشر والمكالمة الصوتية تبدأ بعد قليل مع فضيلة الشيخ.", "05:40 ص", isMine = false)
            )
        )
    )
    val messagesMap: StateFlow<Map<String, List<ChatMessage>>> = _messagesMap.asStateFlow()

    private val _liveRooms = MutableStateFlow(
        listOf(
            LiveRoomInfo(
                id = "room_1",
                nameAr = "مجلس الإقراء والإجازة بالسند المتصل",
                nameAm = "የኢጃዛህ እና የሰነድ ቀጥታ ንባብ መድረክ",
                nameEn = "Recitation & Connected Sanad Ijazah Hall",
                teacherName = "فضيلة الشيخ خ. د. أحمد",
                riwayah = "حفص عن عاصم / قالون عن نافع",
                currentSurah = "سورة الفاتحة والنساء",
                participantsCount = 18,
                isLive = true,
                activeReciter = "عبد الرحمن السعيد"
            ),
            LiveRoomInfo(
                id = "room_2",
                nameAr = "حلقة تصحيح التلاوة وتلقين جزء عم",
                nameAm = "የተጅዊድ ማረሚያ እና የተልቂን ክፍል (ጁዝእ ዐማ)",
                nameEn = "Recitation Correction & Talqin Juz Amma",
                teacherName = "الشيخ عبد الله الأثيوبي",
                riwayah = "حفص عن عاصم",
                currentSurah = "سورة النبأ والنازعات",
                participantsCount = 26,
                isLive = true,
                activeReciter = "بلال حسن"
            ),
            LiveRoomInfo(
                id = "room_3",
                nameAr = "مقرأة القراءات العشر الكبرى والصغرى",
                nameAm = "የአስሩ ቂራኣቶች ከፍተኛ መቅረዓ",
                nameEn = "Ten Qira'at Recitation & Comparative Study",
                teacherName = "د. عمر المقرئ",
                riwayah = "ورش عن نافع طريق الأزرق",
                currentSurah = "سورة البقرة (الربع الثالث)",
                participantsCount = 14,
                isLive = true
            ),
            LiveRoomInfo(
                id = "room_4",
                nameAr = "حلقة الحفظ والمراجعة وضبط المتشابهات",
                nameAm = "የቁርኣን ሂፍዝ እና ክለሳ ክፍል",
                nameEn = "Intensive Memorization & Mutashabihat",
                teacherName = "الشيخ محمود القاضي",
                riwayah = "حفص عن عاصم",
                currentSurah = "سورة آل عمران",
                participantsCount = 19,
                isLive = true
            )
        )
    )
    val liveRooms: StateFlow<List<LiveRoomInfo>> = _liveRooms.asStateFlow()

    private val _studentQueue = MutableStateFlow(
        listOf(
            StudentQueueItem("q1", "عبد الرحمن السعيد", "حفص عن عاصم", "سورة النساء: الآيات 1-10", 45, QueueStatus.RECITING),
            StudentQueueItem("q2", "بلال الإثيوبي", "ورش عن نافع", "سورة الحجرات: الآيات 1-18", 60, QueueStatus.WAITING),
            StudentQueueItem("q3", "عمر الفاروق الصومالي", "قالون عن نافع", "سورة الكهف: الآيات 1-20", 30, QueueStatus.WAITING)
        )
    )
    val studentQueue: StateFlow<List<StudentQueueItem>> = _studentQueue.asStateFlow()

    private val _packages = MutableStateFlow(
        listOf(
            MinutePackage("pkg_30", "باقة التقييم والقراءة السريعة", "ፈጣን የንባብ ግምገማ ፓኬጅ", "Quick Recitation", 30, 10.0, "30 دقيقة تسميع مباشر وتحديد المستوى", "", ""),
            MinutePackage("pkg_60", "باقة تصحيح التلاوة والتجويد", "የተጅዊድ ማረሚያ ፓኬጅ", "Tajweed Correction", 60, 18.0, "60 دقيقة موزعة على جلسات إتقان لمخارج الحروف", "", ""),
            MinutePackage("pkg_180", "باقة التلقين والحفظ الشهري المعتمدة", "ወርሃዊ የሂፍዝ ፓኬጅ", "Monthly Memorization", 180, 45.0, "180 دقيقة لحفظ ومراجعة الأجزاء شهرياً", "", "", isPopular = true),
            MinutePackage("pkg_600", "مسار ختمة الإجازة بالسند المتصل", "የሙሉ ቁርኣን ኢጃዛህ በሰነድ", "Complete Quran Ijazah with Sanad", 600, 135.0, "600 دقيقة لقراءة الختمة كاملة لنيل السند", "", "")
        )
    )
    val packages: StateFlow<List<MinutePackage>> = _packages.asStateFlow()

    private val _payments = MutableStateFlow(
        listOf(
            PaymentRequest("p1", "عبد الرحمن السعيد", "باقة التلقين والحفظ الشهري", 180, 45.0, "TRX-948271029", "+966501234567", "مصرف الراجحي", "27 أكتوبر 2026", PaymentRequestStatus.PENDING),
            PaymentRequest("p2", "بلال حسن الإثيوبي", "باقة تصحيح التلاوة والتجويد", 60, 18.0, "ET-TELEBIRR-88319", "+251911223344", "Telebirr (ኢትዮጵያ)", "26 أكتوبر 2026", PaymentRequestStatus.APPROVED),
            PaymentRequest("p3", "عمر الشريف", "مسار ختمة الإجازة بالسند", 600, 135.0, "CBE-99481729", "+251933445566", "Commercial Bank of Ethiopia (CBE)", "25 أكتوبر 2026", PaymentRequestStatus.APPROVED)
        )
    )
    val payments: StateFlow<List<PaymentRequest>> = _payments.asStateFlow()

    val sanadNodes = listOf(
        SanadNode("s1", "رسول الله محمد ﷺ", "عن جبريل عليه السلام", "الوحي الشريف", "المصدر والمنبع للنور والقرآن العظيم"),
        SanadNode("s2", "الصحابة الكرام (عثمان، علي، زيد، ابن مسعود، أبي)", "العصر النبوي", "العرضة الأخيرة", "نقلوا القرآن غضاً طرياً كما أُنزل"),
        SanadNode("s3", "أئمة القراء السبعة والعشرة (نافع، عاصم، ابن كثير، أبو عمرو...)", "عصر التابعين", "الروايات المعتمدة", "ضبطوا القراءات بالسند الصحيح المتواتر"),
        SanadNode("s4", "الإمام شمس الدين محمد بن محمد بن الجزري (ت 833 هـ)", "عصر التدوين والإتقان", "صاحب النشر والمنظومة الجزرية", "إمام أهل الإقراء ومحرر الفنون القرآنية"),
        SanadNode("s5", "فضيلة الشيخ خ. د. أحمد (مشرف ومعلم المقرأة)", "العصر الحاضر", "بالسند المتصل إلى ابن الجزري", "مجاز بالقراءات العشر الصغرى والكبرى بسند متصل إلى النبي ﷺ")
    )

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }

    fun setRole(role: UserRole) {
        _currentRole.value = role
    }

    fun switchAccount(account: AcademyAccount) {
        _currentAccount.value = account
        _currentUserEmail.value = account.email
        _currentRole.value = account.role
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    fun loginGoogle(email: String = "khdahd241@gmail.com") {
        val target = availableAccounts.find { it.email == email } ?: availableAccounts[0]
        switchAccount(target)
    }

    fun logout() {
        switchAccount(availableAccounts[2]) // Switch to Student account
    }

    // Admin direct content editing
    fun updateAcademyBanner(newText: String) {
        _academyBannerText.value = newText
    }

    fun updateAboutMaqraah(newText: String) {
        _aboutMaqraahText.value = newText
    }

    fun addProgram(program: QuranProgram) {
        _programs.value = _programs.value + program
    }

    fun updateProgram(updated: QuranProgram) {
        _programs.value = _programs.value.map { if (it.id == updated.id) updated else it }
    }

    fun deleteProgram(programId: String) {
        _programs.value = _programs.value.filter { it.id != programId }
    }

    fun addOrUpdateBankDetail(bank: BankPaymentInfo) {
        val exists = _bankDetails.value.any { it.id == bank.id }
        if (exists) {
            _bankDetails.value = _bankDetails.value.map { if (it.id == bank.id) bank else it }
        } else {
            _bankDetails.value = _bankDetails.value + bank
        }
    }

    fun saveBankDetail(bank: BankPaymentInfo) {
        addOrUpdateBankDetail(bank)
    }

    fun deleteBankDetail(bankId: String) {
        _bankDetails.value = _bankDetails.value.filter { it.id != bankId }
    }

    // Friend requests & member direct chats
    fun sendFriendRequest(targetMemberId: String) {
        _groupMembers.value = _groupMembers.value.map { member ->
            if (member.id == targetMemberId) member.copy(friendStatus = FriendStatus.REQUEST_SENT) else member
        }
    }

    fun acceptFriendRequest(targetMemberId: String) {
        _groupMembers.value = _groupMembers.value.map { member ->
            if (member.id == targetMemberId) member.copy(friendStatus = FriendStatus.FRIENDS) else member
        }
    }

    fun startDirectChat(member: GroupMember): String {
        val convId = "conv_${member.id}"
        val exists = _conversations.value.any { it.id == convId }
        if (!exists) {
            val newConv = ChatConversation(
                id = convId,
                title = member.name,
                subtitle = "محادثة خاصة جديدة مع ${member.name}",
                lastMessageTime = "الآن",
                unreadCount = 0,
                isGroup = false,
                isOnline = member.isOnline
            )
            _conversations.value = listOf(newConv) + _conversations.value
        }
        return convId
    }

    // Minute loan requests
    fun requestMinuteLoan(studentName: String, minutes: Int, reason: String) {
        val newLoan = MinuteLoanRequest(
            id = "loan_${System.currentTimeMillis()}",
            studentName = studentName,
            requestedMinutes = minutes,
            reason = reason,
            requestDate = "اليوم",
            status = PaymentRequestStatus.PENDING
        )
        _loanRequests.value = listOf(newLoan) + _loanRequests.value
    }

    fun approveMinuteLoan(loanId: String) {
        var addedMinutes = 0
        val updated = _loanRequests.value.map { loan ->
            if (loan.id == loanId && loan.status == PaymentRequestStatus.PENDING) {
                addedMinutes = loan.requestedMinutes
                loan.copy(status = PaymentRequestStatus.APPROVED)
            } else loan
        }
        _loanRequests.value = updated
        if (addedMinutes > 0) {
            _studentMinutesBalance.value += addedMinutes
        }
    }

    fun rejectMinuteLoan(loanId: String) {
        _loanRequests.value = _loanRequests.value.map { loan ->
            if (loan.id == loanId) loan.copy(status = PaymentRequestStatus.REJECTED) else loan
        }
    }

    // Messages
    fun sendMessage(conversationId: String, text: String, isAudio: Boolean = false, durationSec: Int = 0) {
        val current = _messagesMap.value.toMutableMap()
        val list = current[conversationId]?.toMutableList() ?: mutableListOf()
        val timeNow = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
        val newMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            conversationId = conversationId,
            senderName = if (_currentRole.value == UserRole.TEACHER) "فضيلة الشيخ خ. د. أحمد" else "طالب مقرأة",
            text = text,
            timestamp = timeNow,
            isMine = true,
            isAudio = isAudio,
            audioDurationSec = durationSec,
            isRead = false
        )
        list.add(newMsg)
        current[conversationId] = list
        _messagesMap.value = current

        val convList = _conversations.value.map { conv ->
            if (conv.id == conversationId) {
                conv.copy(
                    subtitle = if (isAudio) "تسجيل صوتي ($durationSec ث)" else text,
                    lastMessageTime = timeNow,
                    hasAudioLastMessage = isAudio
                )
            } else conv
        }
        _conversations.value = convList
    }

    fun submitPaymentRequest(packageName: String, minutes: Int, amount: Double, method: String, txId: String, phone: String, studentName: String) {
        val newReq = PaymentRequest(
            id = "pay_${System.currentTimeMillis()}",
            studentName = studentName.ifBlank { "طالب المقرأة" },
            packageName = packageName,
            minutes = minutes,
            amount = amount,
            transactionId = txId.ifBlank { "TXN-${System.currentTimeMillis().toString().takeLast(6)}" },
            senderPhone = phone.ifBlank { "+966500000000" },
            paymentMethod = method,
            date = "اليوم",
            status = PaymentRequestStatus.PENDING
        )
        _payments.value = listOf(newReq) + _payments.value
    }

    fun approvePayment(id: String) {
        var addedMinutes = 0
        val updated = _payments.value.map { req ->
            if (req.id == id && req.status == PaymentRequestStatus.PENDING) {
                addedMinutes = req.minutes
                req.copy(status = PaymentRequestStatus.APPROVED)
            } else req
        }
        _payments.value = updated
        if (addedMinutes > 0) {
            _studentMinutesBalance.value += addedMinutes
        }
    }

    fun rejectPayment(id: String) {
        _payments.value = _payments.value.map { req ->
            if (req.id == id) req.copy(status = PaymentRequestStatus.REJECTED) else req
        }
    }

    fun deductMinutes(amount: Int) {
        if (_studentMinutesBalance.value >= amount) {
            _studentMinutesBalance.value -= amount
        } else {
            _studentMinutesBalance.value = 0
        }
    }

    fun callStudentInQueue(studentId: String) {
        _studentQueue.value = _studentQueue.value.map { item ->
            when (item.id) {
                studentId -> item.copy(status = QueueStatus.RECITING)
                else -> if (item.status == QueueStatus.RECITING) item.copy(status = QueueStatus.COMPLETED) else item
            }
        }
    }

    fun completeCurrentRecitation(deductMinutesAmount: Int = 15) {
        _studentQueue.value = _studentQueue.value.map { item ->
            if (item.status == QueueStatus.RECITING) item.copy(status = QueueStatus.COMPLETED) else item
        }
        deductMinutes(deductMinutesAmount)
    }

    fun addStudentToQueue(name: String, surah: String, riwayah: String) {
        val newItem = StudentQueueItem(
            id = "q_${System.currentTimeMillis()}",
            studentName = name,
            riwayah = riwayah,
            surah = surah,
            minutesBalance = _studentMinutesBalance.value,
            status = QueueStatus.WAITING
        )
        _studentQueue.value = _studentQueue.value + newItem
    }

    fun enrollStudentInGroup(
        studentName: String,
        gender: GroupGender,
        timing: GroupTiming,
        qiraah: String,
        program: QuranProgram
    ): StudyGroup {
        val targetGroupId = when (gender) {
            GroupGender.MALE -> when (timing) {
                GroupTiming.MORNING -> "grp_m_morning"
                GroupTiming.EVENING -> "grp_m_evening"
                GroupTiming.NIGHT -> "grp_m_night"
                GroupTiming.GENERAL -> "grp_general"
            }
            GroupGender.FEMALE -> when (timing) {
                GroupTiming.MORNING -> "grp_f_morning"
                GroupTiming.EVENING -> "grp_f_evening"
                GroupTiming.NIGHT -> "grp_f_night"
                GroupTiming.GENERAL -> "grp_general"
            }
            GroupGender.ALL -> "grp_general"
        }

        // 1. Increment members count for target group and grp_general
        _studyGroups.value = _studyGroups.value.map { group ->
            if (group.id == targetGroupId || (group.id == "grp_general" && targetGroupId != "grp_general")) {
                group.copy(membersCount = group.membersCount + 1)
            } else group
        }

        // 2. Increment program enrolledCount
        _programs.value = _programs.value.map { p ->
            if (p.id == program.id) p.copy(enrolledCount = p.enrolledCount + 1) else p
        }

        // 3. Grant +30 welcome minutes
        _studentMinutesBalance.value += 30

        // 4. Add student to group members
        val memberId = "gm_std_${System.currentTimeMillis()}"
        val newMember = GroupMember(
            id = memberId,
            name = studentName.ifBlank { "طالب المقرأة الجديد" },
            role = UserRole.STUDENT,
            isOnline = true,
            qiraah = qiraah,
            friendStatus = FriendStatus.NONE
        )
        _groupMembers.value = _groupMembers.value + newMember

        // 5. Add welcome message into target group messages
        val currentMessages = _messagesMap.value.toMutableMap()
        val groupMsgList = currentMessages[targetGroupId]?.toMutableList() ?: mutableListOf()
        val timeNow = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
        val welcomeMsg = ChatMessage(
            id = "wmsg_${System.currentTimeMillis()}",
            conversationId = targetGroupId,
            senderName = "بوت القبول والتسجيل الآلي",
            text = "تهانينا! نرحب بانضمام الطالب المبارك: ${studentName.ifBlank { "طالب علم" }} إلى ${ _studyGroups.value.find { it.id == targetGroupId }?.name ?: "الحلقة" } في ${program.title} برواية ($qiraah). نسأل الله له التوفيق والفتح.",
            timestamp = timeNow,
            isMine = false,
            isRead = true
        )
        groupMsgList.add(welcomeMsg)
        currentMessages[targetGroupId] = groupMsgList
        _messagesMap.value = currentMessages

        return _studyGroups.value.find { it.id == targetGroupId } ?: _studyGroups.value.first()
    }

    fun selectTab(tab: com.example.ui.MainTab) {
        _currentTab.value = tab
        _selectedConversationId.value = null
        _selectedGroup.value = null
    }

    fun openConversation(convId: String) {
        _selectedConversationId.value = convId
    }

    fun closeConversation() {
        _selectedConversationId.value = null
    }

    fun openGroupChat(group: StudyGroup) {
        _selectedGroup.value = group
    }

    fun closeGroupChat() {
        _selectedGroup.value = null
    }

    fun openLiveRoom(room: LiveRoomInfo) {
        _selectedLiveRoom.value = room
    }

    fun leaveLiveRoom() {
        _selectedLiveRoom.value = null
    }

    fun dismissPermissionWarning() {
        _permissionWarningMessage.value = null
    }

    fun openGroupProfile(group: StudyGroup) {
        _showGroupProfileDialog.value = group
    }

    fun closeGroupProfile() {
        _showGroupProfileDialog.value = null
    }

    fun tryStartBroadcastOrCall(group: StudyGroup, type: String) {
        if (_currentRole.value == UserRole.STUDENT) {
            _permissionWarningMessage.value = "عذراً، صلاحيات بدء البث والمكالمات في المجموعات مخصصة للمعلمين والمشرفين فقط."
            return
        }
        val room = _liveRooms.value.first()
        openLiveRoom(room)
    }

    fun getMessagesForConversation(convId: String): List<ChatMessage> {
        return _messagesMap.value[convId] ?: emptyList()
    }

    fun sendVoiceNote(convId: String) {
        sendMessage(convId, "🎤 [تسجيل صوتي للقراءة]", true, 14)
    }

    fun submitPayment(
        packageName: String,
        minutes: Int,
        amount: Double,
        method: String,
        txId: String,
        phone: String,
        name: String = "عبد الرحمن السعيد"
    ) {
        submitPaymentRequest(packageName, minutes, amount, method, txId, phone, name)
    }

    fun callStudent(studentId: String) {
        if (_currentRole.value == UserRole.STUDENT) {
            _permissionWarningMessage.value = "المكالمات الصوتية والمرئية المباشرة متاحة بين المعلمين والمشرفين والطلاب."
            return
        }
        val room = _liveRooms.value.first()
        openLiveRoom(room)
    }

    fun toggleMic() {
        _isMicMuted.value = !_isMicMuted.value
    }

    fun toggleCamera() {
        _isCameraOn.value = !_isCameraOn.value
    }

    fun toggleHandRaise() {
        _isHandRaised.value = !_isHandRaised.value
    }

    fun finishRecitation() {
        completeCurrentRecitation(15)
        leaveLiveRoom()
    }

    fun finishRecitation(minutesDeducted: Int) {
        completeCurrentRecitation(minutesDeducted)
        leaveLiveRoom()
    }

    fun updateLiveKitConfig(url: String, token: String) {
        _liveKitServerUrl.value = url
        _liveKitToken.value = token
    }
}

