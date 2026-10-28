package com.example.ui.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.AppStrings
import com.example.data.PaymentRequest
import com.example.data.PaymentRequestStatus
import com.example.data.QueueStatus
import com.example.data.StudentQueueItem
import com.example.ui.MaqraahViewModel
import com.example.ui.theme.IslamicEmerald
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.IslamicGoldLight
import com.example.ui.theme.IslamicMidnight
import com.example.ui.theme.IslamicRoyalBlue
import com.example.ui.theme.IslamicSurfaceCard
import com.example.ui.theme.IslamicSurfaceDark
import com.example.ui.theme.RubyRed
import com.example.ui.theme.TelegramBlue

@Composable
fun TeacherStudioScreen(
    viewModel: MaqraahViewModel,
    queue: List<StudentQueueItem>,
    currentLang: AppLanguage,
    onOpenLiveRoom: () -> Unit
) {
    val sanadNodes = viewModel.sanadNodes

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicMidnight)
            .padding(14.dp)
    ) {
        // Sheikh Profile Badge
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicSurfaceDark),
                border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(IslamicRoyalBlue)
                            .border(1.5.dp, IslamicGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Sheikh",
                            tint = IslamicGold,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "فضيلة الشيخ خ. د. أحمد",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "الحساب المعتمد: khdahd241@gmail.com",
                            color = TelegramBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "المشرف العام ومقرئ القراءات العشر بالسند المتصل",
                            color = IslamicGoldLight,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action: Enter Live Recitation Hall
            Button(
                onClick = onOpenLiveRoom,
                colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("teacher_enter_live_room_button")
            ) {
                Icon(
                    imageVector = Icons.Default.RecordVoiceOver,
                    contentDescription = null,
                    tint = IslamicMidnight,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "فتح مجلس الإقراء الصوتي المباشر (LiveKit)",
                    color = IslamicMidnight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Queue Management Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppStrings.queueTitle(currentLang),
                    color = IslamicGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${queue.size} طلاب في الطابور",
                    color = Color.LightGray.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Student Queue Items with 1-Click Call & Finish
        items(queue) { student ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (student.status == QueueStatus.RECITING) IslamicRoyalBlue else IslamicSurfaceDark
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (student.status == QueueStatus.RECITING) IslamicGold else IslamicSurfaceCard
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = student.studentName,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
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
                            color = IslamicGoldLight,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "رصيد الطالب: ${student.minutesBalance} دقيقة",
                            color = Color.LightGray.copy(alpha = 0.6f),
                            fontSize = 10.sp
                        )
                    }

                    if (student.status == QueueStatus.WAITING) {
                        Button(
                            onClick = { viewModel.callStudent(student.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(
                                text = "استدعاء",
                                color = IslamicMidnight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    } else if (student.status == QueueStatus.RECITING) {
                        Button(
                            onClick = { viewModel.finishRecitation(15) },
                            colors = ButtonDefaults.buttonColors(containerColor = RubyRed),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(
                                text = "إنهاء وخصم 15 د",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    } else {
                        Text(
                            text = "أتم التسميع ✓",
                            color = IslamicEmerald,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Sanad Lineage Section
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountTree,
                    contentDescription = null,
                    tint = IslamicGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = AppStrings.sanadLineageTitle(currentLang),
                    color = IslamicGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        items(sanadNodes) { node ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(IslamicSurfaceDark)
                    .border(0.8.dp, IslamicGold.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = node.name,
                            color = IslamicGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = node.era,
                            color = Color.LightGray.copy(alpha = 0.6f),
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = node.riwayah,
                        color = TelegramBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = node.description,
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AdminPanelScreen(
    viewModel: MaqraahViewModel,
    payments: List<PaymentRequest>,
    currentLang: AppLanguage
) {
    var broadcastText by remember { mutableStateOf("") }
    var showBroadcastSuccess by remember { mutableStateOf(false) }

    val pendingPayments = payments.filter { it.status == PaymentRequestStatus.PENDING }
    val reviewedPayments = payments.filter { it.status != PaymentRequestStatus.PENDING }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicMidnight)
            .padding(14.dp)
    ) {
        // Admin Supervisor Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicRoyalBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, IslamicGold)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Admin",
                            tint = IslamicGold,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "لوحة تحكم المشرف العام (الإدارة والتدقيق)",
                            color = IslamicGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "الحساب الإداري المعتمد: khdahd241@gmail.com",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "تدقيق إيصالات الشحن اليدوي (Telebirr, CBE, الراجحي)، واعتماد الدقائق للطلاب فورياً.",
                        color = Color.LightGray.copy(alpha = 0.8f),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Broadcast Announcement Tool
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicSurfaceDark)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = null,
                            tint = TelegramBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "إرسال إشعار عام لجميع الطلاب والمشايخ:",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = broadcastText,
                        onValueChange = { broadcastText = it },
                        placeholder = {
                            Text("اكتب نص الإعلان العام أو موعد المجالس القادمة...", color = Color.Gray, fontSize = 12.sp)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            if (broadcastText.isNotBlank()) {
                                viewModel.sendMessage("conv_announcements", broadcastText)
                                broadcastText = ""
                                showBroadcastSuccess = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TelegramBlue),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("بث الإعلان الآن", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Pending Payments List
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "طلبات الشحن المعلقة التي تحتاج تدقيقاً (${pendingPayments.size}):",
                    color = IslamicGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (pendingPayments.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(IslamicSurfaceDark)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "لا توجد طلبات شحن معلقة حالياً. جميع المعاملات مدققة ومعتمدة.",
                        color = Color.LightGray.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        items(pendingPayments) { req ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicSurfaceDark),
                border = androidx.compose.foundation.BorderStroke(1.dp, TelegramBlue)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = req.studentName,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "الهاتف: ${req.senderPhone} • التاريخ: ${req.date}",
                                color = Color.LightGray.copy(alpha = 0.7f),
                                fontSize = 11.sp
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "$${req.amount.toInt()}",
                                color = IslamicGold,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "+${req.minutes} دقيقة",
                                color = IslamicEmerald,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(IslamicMidnight)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "الباقة: ${req.packageName}\nوسيلة الدفع: ${req.paymentMethod}\nرقم العملية (Transaction ID): ${req.transactionId}",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Approve / Reject Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.approvePayment(req.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = IslamicEmerald),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("اعتماد وإيداع الدقائق", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = { viewModel.rejectPayment(req.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = RubyRed),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("رفض", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Previous Audited Payments
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "سجل العمليات السابقة المعتمدة:",
                color = IslamicGold,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(reviewedPayments) { req ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(IslamicSurfaceDark)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "${req.studentName} (${req.packageName})", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "${req.paymentMethod} • ${req.transactionId}", color = Color.Gray, fontSize = 10.sp)
                }
                Text(
                    text = if (req.status == PaymentRequestStatus.APPROVED) "معتمد ✓" else "مرفوض ✗",
                    color = if (req.status == PaymentRequestStatus.APPROVED) IslamicEmerald else RubyRed,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
