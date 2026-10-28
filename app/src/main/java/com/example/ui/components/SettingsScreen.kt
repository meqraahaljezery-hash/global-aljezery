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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AcademyAccount
import com.example.data.AppLanguage
import com.example.data.BankPaymentInfo
import com.example.data.PaymentRequestStatus
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
import com.example.ui.theme.RubyRed
import com.example.ui.theme.TelegramBlue

@Composable
fun SettingsScreen(
    viewModel: MaqraahViewModel,
    currentLang: AppLanguage,
    currentRole: UserRole,
    userEmail: String?
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val bannerText by viewModel.academyBannerText.collectAsState()
    val aboutText by viewModel.aboutMaqraahText.collectAsState()
    val programs by viewModel.programs.collectAsState()
    val bankDetails by viewModel.bankDetails.collectAsState()
    val loanRequests by viewModel.loanRequests.collectAsState()
    val currentAccount by viewModel.currentAccount.collectAsState()
    val availableAccounts = viewModel.availableAccounts

    var showEditBannerDialog by remember { mutableStateOf(false) }
    var showEditAboutDialog by remember { mutableStateOf(false) }
    var showAddBankDialog by remember { mutableStateOf(false) }
    var showAddProgramDialog by remember { mutableStateOf(false) }
    var editingProgram by remember { mutableStateOf<QuranProgram?>(null) }
    var showAccountDialog by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var statusFeedback by remember { mutableStateOf<String?>(null) }

    val screenBg = if (isDarkMode) NightBg else DayBg
    val cardBg = if (isDarkMode) NightCardBg else DayCardBg
    val cardBorder = if (isDarkMode) NightCardBorder else DayCardBorder
    val textPrimary = if (isDarkMode) PureWhite else DayTextPrimary
    val textSecondary = if (isDarkMode) Color.LightGray else DayTextSecondary

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBg)
            .padding(14.dp)
    ) {
        // User Profile & Status Card (Dynamically displayed according to Role)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, ImperialGold)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(
                                when (currentRole) {
                                    UserRole.ADMIN -> ImperialGold.copy(alpha = 0.2f)
                                    UserRole.TEACHER -> LightSkyBlue.copy(alpha = 0.2f)
                                    UserRole.STUDENT -> EmeraldGreen.copy(alpha = 0.15f)
                                }
                            )
                            .border(1.5.dp, ImperialGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (currentRole) {
                                UserRole.ADMIN -> Icons.Default.Security
                                UserRole.TEACHER -> Icons.Default.CheckCircle
                                UserRole.STUDENT -> Icons.Default.Person
                            },
                            contentDescription = null,
                            tint = when (currentRole) {
                                UserRole.ADMIN -> ImperialGold
                                UserRole.TEACHER -> LightBluePrimary
                                UserRole.STUDENT -> EmeraldGreen
                            },
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = currentAccount.name,
                                color = textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ImperialGold)
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = currentRole.titleAr,
                                    color = NightBg,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Text(
                            text = currentAccount.email,
                            color = LightBluePrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = currentAccount.description,
                            color = textSecondary,
                            fontSize = 10.sp
                        )
                    }

                    Button(
                        onClick = { showAccountDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ImperialGold),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text("تبديل", color = NightBg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // ==========================================
        // 1. IF ROLE IS DIRECTOR / ADMIN (المدير والمشرف العام)
        // Shows CMS controls to edit site/banner/programs/banks without database
        // ==========================================
        if (currentRole == UserRole.ADMIN) {
            item {
                Text(
                    text = "لوحة تحكم المدير والمشرف (تعديل التطبيق دون الحاجة للداتابيس):",
                    color = if (isDarkMode) ImperialGold else LightBluePrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Edit Banner
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { showEditBannerDialog = true }.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Campaign, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("تعديل لوحة الإعلانات الكبيرة", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(bannerText.take(40) + "...", color = textSecondary, fontSize = 10.sp)
                            }
                        }
                        Icon(Icons.Default.Edit, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(16.dp))
                    }
                }

                // Edit About
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { showEditAboutDialog = true }.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("تعديل نبذة 'عن المقرأة'", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(aboutText.take(40) + "...", color = textSecondary, fontSize = 10.sp)
                            }
                        }
                        Icon(Icons.Default.Edit, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Manage Programs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.School, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("إدارة برامج ومسارات المقرأة (${programs.size}):", color = if (isDarkMode) ImperialGold else LightBluePrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Button(
                        onClick = { showAddProgramDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text("+ مسار جديد", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            items(programs) { prog ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(cardBg)
                        .border(1.dp, cardBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(prog.title, color = textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, maxLines = 1)
                        Text("${prog.category} • ${prog.durationText}", color = LightBluePrimary, fontSize = 10.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { editingProgram = prog }, modifier = Modifier.size(26.dp)) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = ImperialGold, modifier = Modifier.size(15.dp))
                        }
                        IconButton(onClick = { viewModel.deleteProgram(prog.id) }, modifier = Modifier.size(26.dp)) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = RubyRed, modifier = Modifier.size(15.dp))
                        }
                    }
                }
            }

            // Manage Bank Details
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountBalance, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("إدارة الحسابات البنكية للمقرأة (${bankDetails.size}):", color = if (isDarkMode) ImperialGold else LightBluePrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Button(
                        onClick = { showAddBankDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ImperialGold),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text("+ بنك جديد", color = NightBg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            items(bankDetails) { bank ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(cardBg)
                        .border(1.dp, cardBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(bank.bankName, color = ImperialGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("المستفيد: ${bank.accountHolder} | رقم الحساب: ${bank.accountNumber}", color = textPrimary, fontSize = 10.sp)
                    }
                    IconButton(onClick = { viewModel.deleteBankDetail(bank.id) }, modifier = Modifier.size(26.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = RubyRed, modifier = Modifier.size(15.dp))
                    }
                }
            }

            // Loan Requests for Admin Review
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("طلبات سلفة الدقائق المعلقة للمراجعة والاعتماد:", color = if (isDarkMode) ImperialGold else LightBluePrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            val pendingLoans = loanRequests.filter { it.status == PaymentRequestStatus.PENDING }
            if (pendingLoans.isEmpty()) {
                item {
                    Text("لا توجد طلبات سلفة معلقة حالياً.", color = textSecondary, fontSize = 11.sp, modifier = Modifier.padding(start = 6.dp))
                }
            }

            items(pendingLoans) { loan ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isDarkMode) NightBg else LightSkyBlueSoft)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("${loan.studentName} يطلب سلفة ${loan.requestedMinutes} دقيقة", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("السبب: ${loan.reason}", color = ImperialGold, fontSize = 10.sp)
                    }
                    Row {
                        Button(
                            onClick = {
                                viewModel.approveMinuteLoan(loan.id)
                                statusFeedback = "تمت الموافقة وإيداع ${loan.requestedMinutes} دقيقة للطالب."
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text("اعتماد", fontSize = 10.sp)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = cardBorder)
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        // ==========================================
        // 2. IF ROLE IS TEACHER (المعلم)
        // Shows teacher live broadcasting & queue options
        // ==========================================
        if (currentRole == UserRole.TEACHER) {
            item {
                Text(
                    text = "صلاحيات الشيخ المقرئ والمعلم:",
                    color = if (isDarkMode) ImperialGold else LightBluePrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("صلاحية بث مباشر ومكالمات جماعية في الحلقات", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "يمكنك الدخول لأي حلقة من الحلقات السبع وبدء بث مرئي أو مكالمة صوتية ودعوة الطلاب للتسميع.",
                            color = textSecondary,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = cardBorder)
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        // ==========================================
        // 3. COMMON SETTINGS (للجميع: لغة، وضع ليلي ونهاري، إشعارات)
        // ==========================================
        item {
            Text(
                text = "المظهر واللغة والإشعارات:",
                color = if (isDarkMode) ImperialGold else LightBluePrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Day / Night Mode Toggle (وضع ليلي ونهاري)
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.WbSunny else Icons.Default.DarkMode,
                            contentDescription = null,
                            tint = ImperialGold,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isDarkMode) "الوضع الليلي (أزرق داكن وذهب)" else "الوضع النهاري (أزرق فاتح وأبيض وذهب)",
                                color = textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = if (isDarkMode) "مفعل: مظهر ليلي هادئ مريح للعين" else "مفعل: مظهر نهاري مشرق ونقي بأزرق فاتح وأبيض",
                                color = textSecondary,
                                fontSize = 10.sp
                            )
                        }
                    }

                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { viewModel.setDarkMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ImperialGold,
                            checkedTrackColor = NightCardBg,
                            uncheckedThumbColor = LightBluePrimary,
                            uncheckedTrackColor = LightSkyBlueSoft
                        )
                    )
                }
            }

            // Language Selector
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("لغة التطبيق:", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { viewModel.setLanguage(AppLanguage.ARABIC) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (currentLang == AppLanguage.ARABIC) ImperialGold else if (isDarkMode) NightBg else DayBg
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("العربية", color = if (currentLang == AppLanguage.ARABIC) NightBg else textPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { viewModel.setLanguage(AppLanguage.AMHARIC) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (currentLang == AppLanguage.AMHARIC) ImperialGold else if (isDarkMode) NightBg else DayBg
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("አማርኛ", color = if (currentLang == AppLanguage.AMHARIC) NightBg else textPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { viewModel.setLanguage(AppLanguage.ENGLISH) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (currentLang == AppLanguage.ENGLISH) ImperialGold else if (isDarkMode) NightBg else DayBg
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("English", color = if (currentLang == AppLanguage.ENGLISH) NightBg else textPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Notifications
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("إشعارات الحلقات والمجالس الحية", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("تنبيه عند فتح حلقة مباشرة", color = textSecondary, fontSize = 10.sp)
                        }
                    }
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = ImperialGold,
                            checkedTrackColor = NightCardBg
                        )
                    )
                }
            }

            // Switch to Google Director Account Quick Button
            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = { viewModel.loginGoogle("khdahd241@gmail.com") },
                colors = ButtonDefaults.buttonColors(containerColor = LightBluePrimary),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(46.dp)
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = PureWhite)
                Spacer(modifier = Modifier.width(8.dp))
                Text("الدخول السريع بحساب المدير (khdahd241@gmail.com)", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }

    // Edit Banner Dialog (Admin only)
    if (showEditBannerDialog) {
        var bannerInput by remember { mutableStateOf(bannerText) }
        AlertDialog(
            onDismissRequest = { showEditBannerDialog = false },
            title = { Text("تعديل لوحة الإعلانات الكبيرة (المدير)", color = ImperialGold, fontWeight = FontWeight.Bold, fontSize = 15.sp) },
            text = {
                OutlinedTextField(
                    value = bannerInput,
                    onValueChange = { bannerInput = it },
                    label = { Text("نص الإعلان الرسمي") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateAcademyBanner(bannerInput)
                        showEditBannerDialog = false
                        statusFeedback = "تم تحديث لوحة الإعلانات في الصفحة الرئيسية بنجاح!"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)
                ) {
                    Text("حفظ ونشر التعديل", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditBannerDialog = false }) { Text("إلغاء", color = Color.Gray) }
            },
            containerColor = cardBg
        )
    }

    // Edit About Dialog (Admin only)
    if (showEditAboutDialog) {
        var aboutInput by remember { mutableStateOf(aboutText) }
        AlertDialog(
            onDismissRequest = { showEditAboutDialog = false },
            title = { Text("تعديل نبذة 'عن المقرأة' (المدير)", color = ImperialGold, fontWeight = FontWeight.Bold, fontSize = 15.sp) },
            text = {
                OutlinedTextField(
                    value = aboutInput,
                    onValueChange = { aboutInput = it },
                    label = { Text("نص عن المقرأة") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateAboutMaqraah(aboutInput)
                        showEditAboutDialog = false
                        statusFeedback = "تم تحديث نبذة المقرأة بنجاح!"
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

    // Add Bank Account Dialog (Admin only)
    if (showAddBankDialog) {
        var bankName by remember { mutableStateOf("") }
        var holder by remember { mutableStateOf("مقرأة الإمام الجزري") }
        var accNum by remember { mutableStateOf("") }
        var iban by remember { mutableStateOf("") }
        var currency by remember { mutableStateOf("USD / SAR / ETB") }

        AlertDialog(
            onDismissRequest = { showAddBankDialog = false },
            title = { Text("إضافة وسيلة دفع جديدة (المدير)", color = ImperialGold, fontWeight = FontWeight.Bold, fontSize = 15.sp) },
            text = {
                Column {
                    OutlinedTextField(value = bankName, onValueChange = { bankName = it }, label = { Text("اسم البنك أو المحفظة") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = holder, onValueChange = { holder = it }, label = { Text("اسم المستفيد الرسمي") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = accNum, onValueChange = { accNum = it }, label = { Text("رقم الحساب أو المحفظة") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = iban, onValueChange = { iban = it }, label = { Text("الآيبان / رمز المعرف") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = currency, onValueChange = { currency = it }, label = { Text("العملة") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (bankName.isNotBlank() && accNum.isNotBlank()) {
                            viewModel.saveBankDetail(
                                BankPaymentInfo(
                                    id = "bank_${System.currentTimeMillis()}",
                                    bankName = bankName,
                                    accountHolder = holder,
                                    accountNumber = accNum,
                                    ibanOrIdentifier = iban,
                                    currency = currency,
                                    instructions = "يرجى كتابة رقم العملية."
                                )
                            )
                            showAddBankDialog = false
                            statusFeedback = "تمت إضافة البنك الجديد فوراً دون الحاجة للداتابيس!"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)
                ) {
                    Text("حفظ البنك", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddBankDialog = false }) { Text("إلغاء", color = Color.Gray) }
            },
            containerColor = cardBg
        )
    }

    // Add Program Dialog (Admin only)
    if (showAddProgramDialog) {
        var title by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("القراءات السبع") }
        var desc by remember { mutableStateOf("") }
        var howTo by remember { mutableStateOf("") }
        var reqs by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddProgramDialog = false },
            title = { Text("إضافة مسار قرآني جديد (المدير)", color = ImperialGold, fontWeight = FontWeight.Bold, fontSize = 15.sp) },
            text = {
                Column {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان البرنامج") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("التصنيف (تلقين، تصحيح، حفظ، إجازة بالسند)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("الوصف العام") }, modifier = Modifier.fillMaxWidth(), maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = howTo, onValueChange = { howTo = it }, label = { Text("طريقة الاستخدام") }, modifier = Modifier.fillMaxWidth(), maxLines = 2)
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
                                    durationText = "حسب الخطة المعتمدة"
                                )
                            )
                            showAddProgramDialog = false
                            statusFeedback = "تم نشر البرنامج الجديد في الصفحة الرئيسية بنجاح!"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)
                ) {
                    Text("نشر البرنامج", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddProgramDialog = false }) { Text("إلغاء", color = Color.Gray) }
            },
            containerColor = cardBg
        )
    }

    // Edit Program Dialog (Admin only)
    editingProgram?.let { prog ->
        var title by remember { mutableStateOf(prog.title) }
        var category by remember { mutableStateOf(prog.category) }
        var desc by remember { mutableStateOf(prog.description) }
        var howTo by remember { mutableStateOf(prog.howToUse) }
        var reqs by remember { mutableStateOf(prog.requirements) }
        var duration by remember { mutableStateOf(prog.durationText) }

        AlertDialog(
            onDismissRequest = { editingProgram = null },
            title = { Text("تعديل مسار المقرأة (التلقين، الإجازة، الحفظ...)", color = ImperialGold, fontWeight = FontWeight.Bold, fontSize = 15.sp) },
            text = {
                Column {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان البرنامج") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("التصنيف (تلقين، تصحيح، حفظ، إجازة، قراءات)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("الوصف") }, modifier = Modifier.fillMaxWidth(), maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = howTo, onValueChange = { howTo = it }, label = { Text("طريقة الاستخدام") }, modifier = Modifier.fillMaxWidth(), maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = reqs, onValueChange = { reqs = it }, label = { Text("الشروط") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = duration, onValueChange = { duration = it }, label = { Text("المدة") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            viewModel.updateProgram(
                                prog.copy(
                                    title = title,
                                    category = category,
                                    description = desc,
                                    howToUse = howTo,
                                    requirements = reqs,
                                    durationText = duration
                                )
                            )
                            editingProgram = null
                            statusFeedback = "تم تحديث برنامج ($title) بنجاح عبر الذاكرة مباشرة!"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)
                ) {
                    Text("حفظ التعديل", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { editingProgram = null }) { Text("إلغاء", color = Color.Gray) }
            },
            containerColor = cardBg
        )
    }

    // Account Switcher Dialog
    if (showAccountDialog) {
        AlertDialog(
            onDismissRequest = { showAccountDialog = false },
            title = { Text("تبديل الحساب", color = ImperialGold, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    availableAccounts.forEach { acc ->
                        val isSel = currentAccount.id == acc.id
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) ImperialGold.copy(alpha = 0.2f) else if (isDarkMode) NightBg else DayBg)
                                .clickable {
                                    viewModel.switchAccount(acc)
                                    showAccountDialog = false
                                }
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(acc.name, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("${acc.role.titleAr} (${acc.email})", color = LightBluePrimary, fontSize = 11.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showAccountDialog = false }, colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)) {
                    Text("إغلاق", color = NightBg)
                }
            },
            containerColor = cardBg
        )
    }

    statusFeedback?.let { msg ->
        AlertDialog(
            onDismissRequest = { statusFeedback = null },
            title = { Text("تم التحديث بنجاح ✓", color = EmeraldGreen, fontWeight = FontWeight.Bold) },
            text = { Text(msg, color = textPrimary) },
            confirmButton = {
                Button(onClick = { statusFeedback = null }, colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)) {
                    Text("تم", color = NightBg)
                }
            },
            containerColor = cardBg
        )
    }
}
