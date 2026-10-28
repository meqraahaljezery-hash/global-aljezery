package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.AcademyAccount
import com.example.data.AppLanguage
import com.example.data.AppStrings
import com.example.data.UserRole
import com.example.ui.MaqraahViewModel
import com.example.ui.theme.DayBg
import com.example.ui.theme.DayCardBorder
import com.example.ui.theme.DayTextPrimary
import com.example.ui.theme.DayTextSecondary
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.ImperialGold
import com.example.ui.theme.ImperialGoldDark
import com.example.ui.theme.ImperialGoldLight
import com.example.ui.theme.LightBluePrimary
import com.example.ui.theme.LightSkyBlue
import com.example.ui.theme.LightSkyBlueSoft
import com.example.ui.theme.NightBg
import com.example.ui.theme.NightCardBg
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TelegramBlue

@Composable
fun TopBarAndHeader(
    viewModel: MaqraahViewModel,
    currentLang: AppLanguage,
    currentRole: UserRole,
    userEmail: String?,
    onOpenLiveKitSettings: () -> Unit
) {
    var showLanguageMenu by remember { mutableStateOf(false) }
    var showAccountSwitcherDialog by remember { mutableStateOf(false) }

    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val currentAccount by viewModel.currentAccount.collectAsState()
    val availableAccounts = viewModel.availableAccounts

    // Day & Night styling tokens
    val barBackground = if (isDarkMode) NightBg else PureWhite
    val titleColor = if (isDarkMode) ImperialGold else LightBluePrimary
    val subTextColor = if (isDarkMode) ImperialGoldLight else DayTextPrimary.copy(alpha = 0.7f)
    val buttonBg = if (isDarkMode) NightCardBg else LightSkyBlueSoft
    val buttonBorder = if (isDarkMode) ImperialGold.copy(alpha = 0.5f) else DayCardBorder

    Surface(
        color = barBackground,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = if (isDarkMode) 2.dp else 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            // Row 1: Academy Emblem, Title, Theme Toggle, Account Switcher, Language
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Emblem + Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, ImperialGold, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.jazari_icon),
                            contentDescription = "Emblem",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = AppStrings.appTitle(currentLang),
                            color = titleColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldGreen)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "LiveKit • أزرق سماوي وذهب",
                                color = subTextColor,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                // Header Controls: Day/Night Toggle + Account Switcher + Language
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // 1. Day / Night Toggle (وضع ليلي ونهاري)
                    IconButton(
                        onClick = { viewModel.toggleDarkMode() },
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(buttonBg)
                            .border(1.dp, buttonBorder, CircleShape)
                            .testTag("theme_mode_toggle")
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.WbSunny else Icons.Default.DarkMode,
                            contentDescription = if (isDarkMode) "Switch to Day Mode" else "Switch to Night Mode",
                            tint = if (isDarkMode) ImperialGold else LightBluePrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    // 2. Dynamic Account Persona Chip (حساب طالب / معلم / مدير)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                when (currentRole) {
                                    UserRole.ADMIN -> ImperialGold.copy(alpha = 0.2f)
                                    UserRole.TEACHER -> LightSkyBlue.copy(alpha = 0.2f)
                                    UserRole.STUDENT -> EmeraldGreen.copy(alpha = 0.15f)
                                }
                            )
                            .border(
                                1.dp,
                                when (currentRole) {
                                    UserRole.ADMIN -> ImperialGold
                                    UserRole.TEACHER -> LightBluePrimary
                                    UserRole.STUDENT -> EmeraldGreen
                                },
                                RoundedCornerShape(16.dp)
                            )
                            .clickable { showAccountSwitcherDialog = true }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("account_persona_chip"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = when (currentRole) {
                                    UserRole.ADMIN -> Icons.Default.Security
                                    UserRole.TEACHER -> Icons.Default.CheckCircle
                                    UserRole.STUDENT -> Icons.Default.Person
                                },
                                contentDescription = "Role",
                                tint = when (currentRole) {
                                    UserRole.ADMIN -> ImperialGold
                                    UserRole.TEACHER -> if (isDarkMode) LightSkyBlue else LightBluePrimary
                                    UserRole.STUDENT -> EmeraldGreen
                                },
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = when (currentRole) {
                                    UserRole.ADMIN -> "المدير: خ. د. أحمد"
                                    UserRole.TEACHER -> "المعلم: د. عبد الله"
                                    UserRole.STUDENT -> "طالب: عبد الرحمن"
                                },
                                color = if (isDarkMode) PureWhite else DayTextPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    // 3. Language Switcher Dropdown
                    Box {
                        IconButton(
                            onClick = { showLanguageMenu = true },
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(buttonBg)
                                .border(1.dp, buttonBorder, CircleShape)
                                .testTag("language_switch_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Switch Language",
                                tint = if (isDarkMode) ImperialGold else LightBluePrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showLanguageMenu,
                            onDismissRequest = { showLanguageMenu = false },
                            modifier = Modifier.background(if (isDarkMode) NightCardBg else PureWhite)
                        ) {
                            DropdownMenuItem(
                                text = { Text("العربية (Arabic • RTL)", color = if (isDarkMode) PureWhite else DayTextPrimary) },
                                onClick = {
                                    viewModel.setLanguage(AppLanguage.ARABIC)
                                    showLanguageMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("አማርኛ (Amharic • LTR)", color = if (isDarkMode) PureWhite else DayTextPrimary) },
                                onClick = {
                                    viewModel.setLanguage(AppLanguage.AMHARIC)
                                    showLanguageMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("English (LTR)", color = if (isDarkMode) PureWhite else DayTextPrimary) },
                                onClick = {
                                    viewModel.setLanguage(AppLanguage.ENGLISH)
                                    showLanguageMenu = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Sub-bar: Active Role indicator & Quick LiveKit Server Connect
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Role description indicator
                Text(
                    text = when (currentRole) {
                        UserRole.ADMIN -> "الصلاحيات النشطة: المدير العام والمشرف (التحكم بالبرامج، الإعلانات، البنوك، والبث)"
                        UserRole.TEACHER -> "الصلاحيات النشطة: الشيخ المقرئ (بدء البث والمكالمات، إدارة طابور التسميع، والتقييم)"
                        UserRole.STUDENT -> "الصلاحيات النشطة: طالب مقرأة (الاستماع، طلب التسميع، طلب سلفة، والشحن)"
                    },
                    color = if (isDarkMode) ImperialGoldLight else DayTextSecondary,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isDarkMode) NightCardBg else LightSkyBlueSoft)
                        .clickable { onOpenLiveKitSettings() }
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "LiveKit Hall",
                        color = if (isDarkMode) ImperialGold else LightBluePrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Role & Account Switcher Dialog (للتبديل الفوري بين حساب الطالب، المعلم، والمدير)
    if (showAccountSwitcherDialog) {
        AlertDialog(
            onDismissRequest = { showAccountSwitcherDialog = false },
            title = {
                Text(
                    text = "تبديل الحساب وتجربة الصلاحيات",
                    color = if (isDarkMode) ImperialGold else LightBluePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "اختر الحساب الذي تريد عرضه والتطبيق يتكيف كلياً معه:",
                        color = if (isDarkMode) PureWhite else DayTextPrimary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    availableAccounts.forEach { acc ->
                        val isSelected = currentAccount.id == acc.id
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isSelected) {
                                        if (isDarkMode) ImperialGold.copy(alpha = 0.25f) else LightSkyBlueSoft
                                    } else {
                                        if (isDarkMode) NightCardBg else DayBg
                                    }
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) ImperialGold else Color.Transparent,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    viewModel.switchAccount(acc)
                                    showAccountSwitcherDialog = false
                                }
                                .padding(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when (acc.role) {
                                                UserRole.ADMIN -> ImperialGold
                                                UserRole.TEACHER -> LightSkyBlue
                                                UserRole.STUDENT -> EmeraldGreen
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = when (acc.role) {
                                            UserRole.ADMIN -> Icons.Default.Security
                                            UserRole.TEACHER -> Icons.Default.CheckCircle
                                            UserRole.STUDENT -> Icons.Default.Person
                                        },
                                        contentDescription = null,
                                        tint = if (acc.role == UserRole.ADMIN) NightBg else PureWhite,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Text(
                                        text = acc.name,
                                        color = if (isDarkMode) PureWhite else DayTextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = "${acc.role.titleAr} (${acc.email})",
                                        color = if (isDarkMode) ImperialGoldLight else LightBluePrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = acc.description,
                                        color = if (isDarkMode) Color.LightGray else DayTextSecondary,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showAccountSwitcherDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)
                ) {
                    Text("إغلاق", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = if (isDarkMode) NightCardBg else PureWhite
        )
    }
}
