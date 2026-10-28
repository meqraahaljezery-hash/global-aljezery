package com.example.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.AppStrings
import com.example.ui.MainTab
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.IslamicMidnight
import com.example.ui.theme.IslamicSurfaceDark
import com.example.ui.theme.TelegramBlue

@Composable
fun MaqraahBottomBar(
    currentTab: MainTab,
    currentLang: AppLanguage,
    unreadMessagesCount: Int,
    onTabSelected: (MainTab) -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = IslamicSurfaceDark,
        tonalElevation = 8.dp
    ) {
        // Tab 1: الرئيسية (Home)
        NavigationItem(
            selected = currentTab == MainTab.HOME,
            onClick = { onTabSelected(MainTab.HOME) },
            icon = Icons.Default.Home,
            label = AppStrings.tabHome(currentLang)
        )

        // Tab 2: محادثات (Chats)
        NavigationItem(
            selected = currentTab == MainTab.CHATS,
            onClick = { onTabSelected(MainTab.CHATS) },
            icon = Icons.Default.Chat,
            label = AppStrings.tabChats(currentLang),
            badgeCount = unreadMessagesCount
        )

        // Tab 3: مجموعات (Groups)
        NavigationItem(
            selected = currentTab == MainTab.GROUPS,
            onClick = { onTabSelected(MainTab.GROUPS) },
            icon = Icons.Default.Group,
            label = AppStrings.tabGroups(currentLang)
        )

        // Tab 4: حسابي (My Account)
        NavigationItem(
            selected = currentTab == MainTab.MY_ACCOUNT,
            onClick = { onTabSelected(MainTab.MY_ACCOUNT) },
            icon = Icons.Default.AccountCircle,
            label = AppStrings.tabMyAccount(currentLang)
        )

        // Tab 5: إعدادات (Settings)
        NavigationItem(
            selected = currentTab == MainTab.SETTINGS,
            onClick = { onTabSelected(MainTab.SETTINGS) },
            icon = Icons.Default.Settings,
            label = AppStrings.tabSettings(currentLang)
        )
    }
}

@Composable
private fun RowScope.NavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    badgeCount: Int = 0
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            if (badgeCount > 0) {
                BadgedBox(
                    badge = {
                        Badge(containerColor = TelegramBlue) {
                            Text(text = badgeCount.toString(), color = Color.White, fontSize = 10.sp)
                        }
                    }
                ) {
                    Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(22.dp))
                }
            } else {
                Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(22.dp))
            }
        },
        label = {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = IslamicMidnight,
            selectedTextColor = IslamicGold,
            indicatorColor = IslamicGold,
            unselectedIconColor = Color.LightGray.copy(alpha = 0.7f),
            unselectedTextColor = Color.LightGray.copy(alpha = 0.7f)
        )
    )
}
