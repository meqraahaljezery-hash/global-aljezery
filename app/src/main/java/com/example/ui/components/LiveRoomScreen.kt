package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FrontHand
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.AppLanguage
import com.example.data.AppStrings
import com.example.data.LiveRoomInfo
import com.example.data.QueueStatus
import com.example.data.UserRole
import com.example.ui.MaqraahViewModel
import com.example.ui.theme.IslamicEmerald
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.IslamicGoldDark
import com.example.ui.theme.IslamicGoldLight
import com.example.ui.theme.IslamicMidnight
import com.example.ui.theme.IslamicRoyalBlue
import com.example.ui.theme.IslamicSurfaceCard
import com.example.ui.theme.IslamicSurfaceDark
import com.example.ui.theme.RubyRed
import com.example.ui.theme.TelegramBlue

@Composable
fun LiveRoomScreen(
    room: LiveRoomInfo,
    viewModel: MaqraahViewModel,
    currentLang: AppLanguage,
    currentRole: UserRole,
    onLeave: () -> Unit
) {
    val isMicMuted by viewModel.isMicMuted.collectAsState()
    val isCameraOn by viewModel.isCameraOn.collectAsState()
    val isHandRaised by viewModel.isHandRaised.collectAsState()
    val liveRecitationSec by viewModel.liveRecitationSeconds.collectAsState()
    val studentQueue by viewModel.studentQueue.collectAsState()
    val minutesBalance by viewModel.studentMinutesBalance.collectAsState()
    val liveKitServerUrl by viewModel.liveKitServerUrl.collectAsState()
    val liveKitToken by viewModel.liveKitToken.collectAsState()

    var showLiveKitSettings by remember { mutableStateOf(false) }
    var selectedRiwayah by remember { mutableStateOf("حفص عن عاصم من طريق الشاطبية") }
    var tajweedScore by remember { mutableIntStateOf(5) }
    var showEvaluationFeedback by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicMidnight)
    ) {
        // Top Room Header
        Surface(
            color = IslamicSurfaceDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onLeave) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Leave",
                            tint = IslamicGold
                        )
                    }

                    Column {
                        Text(
                            text = room.nameAr,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(IslamicEmerald)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "LiveKit WebRTC: ${room.participantsCount} متصل • ${room.riwayah}",
                                color = IslamicGoldLight.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { showLiveKitSettings = true }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "LiveKit Config",
                            tint = IslamicGold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(RubyRed)
                            .clickable { onLeave() }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CallEnd,
                                contentDescription = "Exit",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "مغادرة",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // LiveKit Connection Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(IslamicRoyalBlue)
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "خادم LiveKit: $liveKitServerUrl",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 11.sp
                )
                Text(
                    text = "رصيد دقائقك: $minutesBalance دقيقة",
                    color = IslamicGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Main Studio Area (Scrollable)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            // Sheikh Podium (Master Broadcaster)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicSurfaceDark),
                border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(IslamicRoyalBlue)
                                    .border(1.5.dp, IslamicGold, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Sheikh",
                                    tint = IslamicGold,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "فضيلة الشيخ خ. د. أحمد (المقرئ المجاز)",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "المشرف العام • صاحب السند المتصل • البث الصوتي المباشر",
                                    color = IslamicGoldLight,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Voice Activity Waveform
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(IslamicMidnight)
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        ) {
                            listOf(6, 14, 22, 16, 26, 12, 20, 8).forEach { h ->
                                Box(
                                    modifier = Modifier
                                        .width(3.dp)
                                        .height(h.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(IslamicEmerald)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sheikh Video / Hero Frame
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(IslamicMidnight)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.jazari_hero),
                            contentDescription = "Academy Hall",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, IslamicMidnight.copy(alpha = 0.85f))
                                    )
                                )
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(RubyRed)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "LIVE",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "مجلس الإقراء المباشر للقرآن الكريم",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Student Recitation Podium with Mushaf View
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicSurfaceDark),
                border = androidx.compose.foundation.BorderStroke(1.dp, TelegramBlue.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "منصة القارئ الحالي:",
                                color = IslamicGoldLight,
                                fontSize = 11.sp
                            )
                            Text(
                                text = "عبد الرحمن السعيد (طالب الإجازة)",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        // Live Recitation Timer
                        val minutes = liveRecitationSec / 60
                        val seconds = liveRecitationSec % 60
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(IslamicRoyalBlue)
                                .border(1.dp, IslamicGold, RoundedCornerShape(10.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = "Timer",
                                    tint = IslamicGold,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = String.format("%02d:%02d", minutes, seconds),
                                    color = IslamicGold,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Mushaf Ayah recitation box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(IslamicMidnight)
                            .border(1.dp, IslamicGold.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "سُورَةُ الفَاتِحَةِ - رِوَايَةُ حَفْصٍ عَنْ عَاصِمٍ",
                                color = IslamicGold,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ﴿١﴾ الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ ﴿٢﴾ الرَّحْمَٰنِ الرَّحِيمِ ﴿٣﴾ مَالِكِ يَوْمِ الدِّينِ ﴿٤﴾ إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ ﴿٥﴾ اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ ﴿٦﴾ صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ ﴿٧﴾",
                                color = Color.White,
                                fontSize = 16.sp,
                                lineHeight = 28.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Live Tajweed Feedback & Notes (Real-Time Scoring)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(IslamicRoyalBlue.copy(alpha = 0.6f))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "توجيهات الشيخ التجويدية الفورية:",
                            color = IslamicGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "• إحكام ضم الشفتين عند الواو في (المغضوب)\n• تمكين المد اللازم الكلمي المثقل في (الضَّالِّينَ) 6 حركات بالتوسط والتسوية\n• تحقيق صفة الجهر والرخاوة في الضاد بدون تحويلها إلى ظاء",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )
                    }

                    // Sheikh evaluation controls if Teacher role
                    if (currentRole == UserRole.TEACHER || currentRole == UserRole.ADMIN) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "تقييم التلاوة:",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Row {
                                for (star in 1..5) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star",
                                        tint = if (star <= tajweedScore) IslamicGold else Color.Gray,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable { tajweedScore = star }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                viewModel.finishRecitation(15)
                                showEvaluationFeedback = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "اعتماد التسميع، خصم 15 دقيقة، واستدعاء التالي",
                                color = IslamicMidnight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Student Queue in this Room
            Text(
                text = "قائمة انتظار الطلاب في هذا المجلس (${studentQueue.size}):",
                color = IslamicGold,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))

            studentQueue.forEach { student ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (student.status == QueueStatus.RECITING) IslamicRoyalBlue else IslamicSurfaceDark)
                        .border(
                            1.dp,
                            if (student.status == QueueStatus.RECITING) IslamicGold else Color.Transparent,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = student.studentName,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (student.status == QueueStatus.RECITING) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(IslamicEmerald)
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "يقرأ الآن",
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Text(
                            text = "${student.riwayah} • ${student.surah}",
                            color = Color.LightGray.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }

                    if ((currentRole == UserRole.TEACHER || currentRole == UserRole.ADMIN) && student.status == QueueStatus.WAITING) {
                        Button(
                            onClick = { viewModel.callStudent(student.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text("استدعاء", color = IslamicMidnight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Text(
                            text = "${student.minutesBalance} دقيقة",
                            color = IslamicGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // LiveKit Bottom Call Controls (Like LiveKit Meet / Telegram Voice Chat)
        Surface(
            color = IslamicSurfaceDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mic Toggle
                IconButton(
                    onClick = { viewModel.toggleMic() },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(if (isMicMuted) RubyRed else IslamicRoyalBlue)
                        .border(1.dp, IslamicGold, CircleShape)
                        .testTag("toggle_mic_button")
                ) {
                    Icon(
                        imageVector = if (isMicMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = "Mic",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Camera Toggle
                IconButton(
                    onClick = { viewModel.toggleCamera() },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(if (isCameraOn) TelegramBlue else IslamicSurfaceCard)
                        .border(1.dp, IslamicGold.copy(alpha = 0.5f), CircleShape)
                        .testTag("toggle_camera_button")
                ) {
                    Icon(
                        imageVector = if (isCameraOn) Icons.Default.Videocam else Icons.Default.VideocamOff,
                        contentDescription = "Camera",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Hand Raise (Request to recite)
                IconButton(
                    onClick = { viewModel.toggleHandRaise() },
                    modifier = Modifier
                        .size(50.dp)
                        .scale(if (isHandRaised) pulseScale else 1f)
                        .clip(CircleShape)
                        .background(if (isHandRaised) IslamicEmerald else IslamicGold)
                        .testTag("raise_hand_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.FrontHand,
                        contentDescription = "Raise Hand",
                        tint = IslamicMidnight,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // End / Leave Call
                IconButton(
                    onClick = onLeave,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(RubyRed)
                        .testTag("leave_room_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.CallEnd,
                        contentDescription = "Leave Call",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }

    // LiveKit Server Configuration Modal
    if (showLiveKitSettings) {
        var urlInput by remember { mutableStateOf(liveKitServerUrl) }
        var tokenInput by remember { mutableStateOf(liveKitToken) }

        AlertDialog(
            onDismissRequest = { showLiveKitSettings = false },
            title = {
                Text(
                    text = "إعدادات اتصال خادم LiveKit المباشر",
                    color = IslamicGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "يمكنك إدخال عنوان خادم LiveKit WebSocket الخاص بالمقرأة ورمز الدخول (Token) للربط الحقيقي:",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = urlInput,
                        onValueChange = { urlInput = it },
                        label = { Text("LiveKit Server URL") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tokenInput,
                        onValueChange = { tokenInput = it },
                        label = { Text("LiveKit Room Token (JWT)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateLiveKitConfig(urlInput, tokenInput)
                        showLiveKitSettings = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold)
                ) {
                    Text("حفظ والاتصال", color = IslamicMidnight, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLiveKitSettings = false }) {
                    Text("إغلاق", color = Color.LightGray)
                }
            },
            containerColor = IslamicSurfaceDark
        )
    }

    // Evaluation feedback dialog
    if (showEvaluationFeedback) {
        AlertDialog(
            onDismissRequest = { showEvaluationFeedback = false },
            title = {
                Text("تم اعتماد التسميع بنجاح", color = IslamicEmerald, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    text = "تم رصد تقييم $tajweedScore نجوم للطالب عبد الرحمن السعيد، وخصم 15 دقيقة من الرصيد، ونقل الدور للطالب التالي في قائمة الانتظار.",
                    color = Color.White,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { showEvaluationFeedback = false },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold)
                ) {
                    Text("تم", color = IslamicMidnight)
                }
            },
            containerColor = IslamicSurfaceDark
        )
    }
}
