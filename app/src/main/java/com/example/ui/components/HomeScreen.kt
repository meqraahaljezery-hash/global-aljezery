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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.data.QuranProgram
import com.example.data.UserRole
import com.example.ui.MaqraahViewModel
import com.example.ui.theme.DayBg
import com.example.ui.theme.DayCardBg
import com.example.ui.theme.DayCardBorder
import com.example.ui.theme.DayTextPrimary
import com.example.ui.theme.DayTextSecondary
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.ImperialGold
import com.example.ui.theme.ImperialGoldLight
import com.example.ui.theme.LightBluePrimary
import com.example.ui.theme.LightSkyBlue
import com.example.ui.theme.LightSkyBlueSoft
import com.example.ui.theme.NightBg
import com.example.ui.theme.NightCardBg
import com.example.ui.theme.NightCardBorder
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TelegramBlue

@Composable
fun HomeScreen(
    viewModel: MaqraahViewModel,
    currentLang: AppLanguage,
    currentRole: UserRole,
    onNavigateToGroups: () -> Unit,
    onOpenLiveRoom: () -> Unit
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val bannerText by viewModel.academyBannerText.collectAsState()
    val aboutText by viewModel.aboutMaqraahText.collectAsState()
    val programs by viewModel.programs.collectAsState()

    var showEditBannerDialog by remember { mutableStateOf(false) }
    var showEditAboutDialog by remember { mutableStateOf(false) }
    var showAddProgramDialog by remember { mutableStateOf(false) }
    var selectedProgramForRegistration by remember { mutableStateOf<QuranProgram?>(null) }
    var registrationSuccessToast by remember { mutableStateOf<String?>(null) }

    // Dynamic colors based on Day / Night mode
    val screenBg = if (isDarkMode) NightBg else DayBg
    val cardBg = if (isDarkMode) NightCardBg else DayCardBg
    val cardBorder = if (isDarkMode) NightCardBorder else DayCardBorder
    val textPrimary = if (isDarkMode) PureWhite else DayTextPrimary
    val textSecondary = if (isDarkMode) Color.LightGray else DayTextSecondary

    // Permissions:
    // Only Director/Admin can edit the site banner, about text, and add programs
    val canEditSiteContent = currentRole == UserRole.ADMIN

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBg)
            .padding(14.dp)
    ) {
        // Urgent Alerts Bar (إشارات المقرأة العاجلة)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isDarkMode) NightCardBg else LightSkyBlueSoft)
                    .border(1.dp, if (isDarkMode) TelegramBlue.copy(alpha = 0.5f) else DayCardBorder, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 9.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = "Alerts",
                        tint = ImperialGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (currentRole) {
                            UserRole.ADMIN -> "تنبيه إداري للمدير: يمكنك تعديل لوحة الإعلانات، برامج المقرأة، والحسابات البنكية فوراً من هذه الشاشة والإعدادات."
                            UserRole.TEACHER -> "تنبيه المعلم: الحلقات السبع جاهزة لبدء البث والمكالمات، وقائمة التسميع بانتظار استدعاء الطلاب."
                            UserRole.STUDENT -> "إشارة عاجلة للطالب: مجالس التسميع المباشرة منعقدة الآن في الحلقات السبع، والتسجيل في المسارات متاح."
                        },
                        color = textPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Large Announcement Banner (لوحة إعلانات رسمية كبيرة)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, ImperialGold)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(ImperialGold)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "لوحة إعلانات المقرأة الرسمية",
                                color = ImperialGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        // Visible ONLY if Manager / Admin
                        if (canEditSiteContent) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isDarkMode) NightBg else LightSkyBlueSoft)
                                    .clickable { showEditBannerDialog = true }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = ImperialGold, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("تعديل الإعلان (المدير)", color = if (isDarkMode) ImperialGold else LightBluePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = bannerText,
                        color = textPrimary,
                        fontSize = 13.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onOpenLiveRoom,
                            colors = ButtonDefaults.buttonColors(containerColor = ImperialGold),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.PlayCircle, contentDescription = null, tint = NightBg, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("دخول المجلس المباشر", color = NightBg, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }

                        Button(
                            onClick = onNavigateToGroups,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDarkMode) LightSkyBlueSoft.copy(alpha = 0.2f) else LightBluePrimary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "الحلقات الدراسية (٧)",
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // About Maqraah Section (عالمقرأة)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "عن مقرأة الإمام الجزري",
                                color = if (isDarkMode) ImperialGold else LightBluePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        // Visible ONLY if Manager / Admin
                        if (canEditSiteContent) {
                            IconButton(onClick = { showEditAboutDialog = true }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = ImperialGold, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = aboutText,
                        color = textSecondary,
                        fontSize = 12.sp,
                        lineHeight = 19.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Programs Section Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.School, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "برامج ومسارات المقرأة المعتمدة (${programs.size}):",
                        color = if (isDarkMode) ImperialGold else LightBluePrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                // Add Program Button - ONLY for Manager / Admin
                if (canEditSiteContent) {
                    Button(
                        onClick = { showAddProgramDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("+ مسار جديد (المدير)", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // List of Quranic Programs (التلقين، التصحيح، الحفظ، الإجازة بالسند، القراءات السبع)
        items(programs) { prog ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isDarkMode) NightBg else LightSkyBlueSoft)
                                .border(0.8.dp, ImperialGold, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = prog.category,
                                color = if (isDarkMode) ImperialGoldLight else LightBluePrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "${prog.enrolledCount} طالب مسجل",
                            color = textSecondary,
                            fontSize = 10.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = prog.title,
                        color = textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = prog.description,
                        color = textSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // How to Use section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isDarkMode) NightBg else LightSkyBlueSoft.copy(alpha = 0.5f))
                            .padding(8.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.HelpOutline, contentDescription = null, tint = LightBluePrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "كيفية الاستخدام وطريقة الحضور:",
                                    color = LightBluePrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = prog.howToUse,
                                color = textPrimary,
                                fontSize = 10.sp,
                                lineHeight = 15.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "شروط الانضمام: ${prog.requirements}",
                                color = ImperialGold,
                                fontSize = 9.sp,
                                lineHeight = 13.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "المدة: ${prog.durationText}",
                            color = textSecondary,
                            fontSize = 10.sp
                        )

                        // Registration Button for Students, or status for Teachers/Admins
                        if (currentRole == UserRole.STUDENT) {
                            Button(
                                onClick = { selectedProgramForRegistration = prog },
                                colors = ButtonDefaults.buttonColors(containerColor = ImperialGold),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp).testTag("register_program_${prog.id}")
                            ) {
                                Text(
                                    text = "التسجيل في هذا المسار",
                                    color = NightBg,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(EmeraldGreen.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (currentRole == UserRole.ADMIN) "مسار نشط (إشرافك)" else "مسار تدريسك المعتمد",
                                    color = EmeraldGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Edit Banner Dialog (Admin only)
    if (showEditBannerDialog && canEditSiteContent) {
        var newBanner by remember { mutableStateOf(bannerText) }
        AlertDialog(
            onDismissRequest = { showEditBannerDialog = false },
            title = { Text("تعديل لوحة الإعلانات الرسمية (المدير)", color = ImperialGold, fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = newBanner,
                    onValueChange = { newBanner = it },
                    label = { Text("نص الإعلان الرسمي") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateAcademyBanner(newBanner)
                        showEditBannerDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)
                ) {
                    Text("حفظ ونشر فوراً", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditBannerDialog = false }) { Text("إلغاء", color = Color.Gray) }
            },
            containerColor = cardBg
        )
    }

    // Edit About Dialog (Admin only)
    if (showEditAboutDialog && canEditSiteContent) {
        var newAbout by remember { mutableStateOf(aboutText) }
        AlertDialog(
            onDismissRequest = { showEditAboutDialog = false },
            title = { Text("تعديل نبذة 'عن المقرأة' (المدير)", color = ImperialGold, fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = newAbout,
                    onValueChange = { newAbout = it },
                    label = { Text("نص عن المقرأة") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateAboutMaqraah(newAbout)
                        showEditAboutDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)
                ) {
                    Text("حفظ ونشر", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditAboutDialog = false }) { Text("إلغاء", color = Color.Gray) }
            },
            containerColor = cardBg
        )
    }

    // Add Program Dialog (Admin only)
    if (showAddProgramDialog && canEditSiteContent) {
        var title by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("حفظ وتلقين") }
        var desc by remember { mutableStateOf("") }
        var howTo by remember { mutableStateOf("") }
        var reqs by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddProgramDialog = false },
            title = { Text("إضافة مسار قرآني جديد (المدير)", color = ImperialGold, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان البرنامج") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("التصنيف") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("الوصف العام") }, modifier = Modifier.fillMaxWidth(), maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = howTo, onValueChange = { howTo = it }, label = { Text("كيفية الاستخدام") }, modifier = Modifier.fillMaxWidth(), maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = reqs, onValueChange = { reqs = it }, label = { Text("شروط الانضمام") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            viewModel.addProgram(
                                QuranProgram(
                                    id = "prog_${System.currentTimeMillis()}",
                                    title = title,
                                    category = category,
                                    description = desc,
                                    howToUse = howTo,
                                    requirements = reqs,
                                    durationText = "حسب الجدول المعتمد"
                                )
                            )
                            showAddProgramDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)
                ) {
                    Text("نشر البرنامج الآن", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddProgramDialog = false }) { Text("إلغاء", color = Color.Gray) }
            },
            containerColor = cardBg
        )
    }

    // Program Registration Confirmation Dialog
    selectedProgramForRegistration?.let { prog ->
        AlertDialog(
            onDismissRequest = { selectedProgramForRegistration = null },
            title = { Text("تأكيد التسجيل في: ${prog.title}", color = ImperialGold, fontWeight = FontWeight.Bold, fontSize = 15.sp) },
            text = {
                Column {
                    Text(text = "التصنيف: ${prog.category}", color = LightBluePrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = prog.description, color = textPrimary, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "طريقة الحضور: ${prog.howToUse}", color = textSecondary, fontSize = 10.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        registrationSuccessToast = "تم تسجيلك بنجاح في ${prog.title}! يمكنك متابعة جدول الحلقة في تبويب المجموعات."
                        selectedProgramForRegistration = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)
                ) {
                    Text("تأكيد الانضمام", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedProgramForRegistration = null }) { Text("إلغاء", color = Color.Gray) }
            },
            containerColor = cardBg
        )
    }

    // Success Toast Dialog
    registrationSuccessToast?.let { msg ->
        AlertDialog(
            onDismissRequest = { registrationSuccessToast = null },
            title = { Text("تم التسجيل بنجاح ✓", color = EmeraldGreen, fontWeight = FontWeight.Bold) },
            text = { Text(text = msg, color = textPrimary) },
            confirmButton = {
                Button(onClick = { registrationSuccessToast = null }, colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)) {
                    Text("تم", color = NightBg)
                }
            },
            containerColor = cardBg
        )
    }
}
