package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AppLanguage
import com.example.data.UserRole
import com.example.ui.MainTab
import com.example.ui.MaqraahViewModel
import com.example.ui.components.ChatDetailScreen
import com.example.ui.components.ChatListScreen
import com.example.ui.components.GroupChatScreen
import com.example.ui.components.GroupsScreen
import com.example.ui.components.HomeScreen
import com.example.ui.components.LiveRoomScreen
import com.example.ui.components.MaqraahBottomBar
import com.example.ui.components.MyAccountScreen
import com.example.ui.components.SettingsScreen
import com.example.ui.components.StoriesBar
import com.example.ui.components.TopBarAndHeader
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.IslamicMidnight
import com.example.ui.theme.IslamicSurfaceDark
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RubyRed

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MaqraahApp()
            }
        }
    }
}

@Composable
fun MaqraahApp(
    viewModel: MaqraahViewModel = viewModel()
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val currentRole by viewModel.currentRole.collectAsState()
    val userEmail by viewModel.currentUserEmail.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val selectedConvId by viewModel.selectedConversationId.collectAsState()
    val selectedGroup by viewModel.selectedGroup.collectAsState()
    val selectedLiveRoom by viewModel.selectedLiveRoom.collectAsState()
    val permissionWarning by viewModel.permissionWarningMessage.collectAsState()

    val conversations by viewModel.conversations.collectAsState()
    val stories by viewModel.stories.collectAsState()
    val liveRooms by viewModel.liveRooms.collectAsState()
    val studyGroups by viewModel.studyGroups.collectAsState()
    val minutesBalance by viewModel.studentMinutesBalance.collectAsState()

    val layoutDir = if (currentLang.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDir) {
        val totalUnread = conversations.sumOf { it.unreadCount }

        // BackHandler logic
        BackHandler(enabled = selectedLiveRoom != null || selectedGroup != null || selectedConvId != null) {
            if (selectedLiveRoom != null) {
                viewModel.leaveLiveRoom()
            } else if (selectedGroup != null) {
                viewModel.closeGroupChat()
            } else if (selectedConvId != null) {
                viewModel.closeConversation()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(IslamicMidnight)
                .safeDrawingPadding()
        ) {
            if (selectedLiveRoom != null) {
                // Full Screen LiveKit Recitation Room
                LiveRoomScreen(
                    room = selectedLiveRoom!!,
                    viewModel = viewModel,
                    currentLang = currentLang,
                    currentRole = currentRole,
                    onLeave = { viewModel.leaveLiveRoom() }
                )
            } else if (selectedGroup != null) {
                // Group Chat Screen (Telegram Style with live broadcast, call, and profile sheet)
                GroupChatScreen(
                    group = selectedGroup!!,
                    viewModel = viewModel,
                    currentLang = currentLang,
                    currentRole = currentRole,
                    onBack = { viewModel.closeGroupChat() },
                    onOpenLiveRoom = { roomId ->
                        val room = liveRooms.find { it.id == roomId } ?: liveRooms.first()
                        viewModel.openLiveRoom(room)
                    }
                )
            } else if (selectedConvId != null) {
                // Chat Detail Screen (Conversation view)
                val conv = conversations.find { it.id == selectedConvId } ?: conversations.first()
                val messages = viewModel.getMessagesForConversation(conv.id)

                ChatDetailScreen(
                    conversation = conv,
                    messages = messages,
                    currentLang = currentLang,
                    onBack = { viewModel.closeConversation() },
                    onSendMessage = { text -> viewModel.sendMessage(conv.id, text) },
                    onSendVoiceNote = { viewModel.sendVoiceNote(conv.id) },
                    onJoinLiveRoom = { roomId ->
                        val room = liveRooms.find { it.id == roomId } ?: liveRooms.first()
                        viewModel.openLiveRoom(room)
                    }
                )
            } else {
                // Main Multi-tab Layout
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = IslamicMidnight,
                    topBar = {
                        TopBarAndHeader(
                            viewModel = viewModel,
                            currentLang = currentLang,
                            currentRole = currentRole,
                            userEmail = userEmail,
                            onOpenLiveKitSettings = {
                                val defaultRoom = liveRooms.first()
                                viewModel.openLiveRoom(defaultRoom)
                            }
                        )
                    },
                    bottomBar = {
                        MaqraahBottomBar(
                            currentTab = currentTab,
                            currentLang = currentLang,
                            unreadMessagesCount = totalUnread,
                            onTabSelected = { tab -> viewModel.selectTab(tab) }
                        )
                    },
                    floatingActionButton = {
                        if (currentTab == MainTab.CHATS || currentTab == MainTab.GROUPS) {
                            FloatingActionButton(
                                onClick = {
                                    val defaultRoom = liveRooms.first()
                                    viewModel.openLiveRoom(defaultRoom)
                                },
                                containerColor = IslamicGold,
                                contentColor = IslamicMidnight,
                                modifier = Modifier.testTag("quick_live_fab")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VideoCall,
                                    contentDescription = "Quick Live Room"
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentTab) {
                            MainTab.HOME -> {
                                HomeScreen(
                                    viewModel = viewModel,
                                    currentLang = currentLang,
                                    currentRole = currentRole,
                                    onNavigateToGroups = { viewModel.selectTab(MainTab.GROUPS) },
                                    onOpenLiveRoom = {
                                        viewModel.openLiveRoom(liveRooms.first())
                                    }
                                )
                            }
                            MainTab.CHATS -> {
                                Column(modifier = Modifier.fillMaxSize()) {
                                    StoriesBar(
                                        stories = stories,
                                        onStoryClick = { story ->
                                            if (story.isLiveNow) {
                                                viewModel.openLiveRoom(liveRooms.first())
                                            }
                                        },
                                        onAddStoryClick = {
                                            viewModel.sendMessage(
                                                "conv_sheikh",
                                                "تمت مشاركة فائدة قرآنية جديدة في الحالة."
                                            )
                                        }
                                    )
                                    ChatListScreen(
                                        viewModel = viewModel,
                                        conversations = conversations,
                                        currentLang = currentLang,
                                        onSelectConversation = { conv ->
                                            viewModel.openConversation(conv.id)
                                        },
                                        onJoinLiveRoom = { roomId ->
                                            val room = liveRooms.find { it.id == roomId } ?: liveRooms.first()
                                            viewModel.openLiveRoom(room)
                                        }
                                    )
                                }
                            }
                            MainTab.GROUPS -> {
                                GroupsScreen(
                                    viewModel = viewModel,
                                    groups = studyGroups,
                                    currentLang = currentLang,
                                    onSelectGroup = { group ->
                                        viewModel.openGroupChat(group)
                                    }
                                )
                            }
                            MainTab.ACCOUNT, MainTab.MY_ACCOUNT -> {
                                MyAccountScreen(
                                    viewModel = viewModel,
                                    minutesBalance = minutesBalance,
                                    currentLang = currentLang,
                                    currentRole = currentRole,
                                    userEmail = userEmail
                                )
                            }
                            MainTab.SETTINGS -> {
                                SettingsScreen(
                                    viewModel = viewModel,
                                    currentLang = currentLang,
                                    currentRole = currentRole,
                                    userEmail = userEmail
                                )
                            }
                        }
                    }
                }
            }

            // Permission Warning Dialog (when student tries to broadcast/call)
            permissionWarning?.let { message ->
                AlertDialog(
                    onDismissRequest = { viewModel.dismissPermissionWarning() },
                    title = {
                        Text(
                            text = "تنبيه صلاحيات البث والمكالمات",
                            color = RubyRed,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            text = message,
                            color = Color.White
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = { viewModel.dismissPermissionWarning() },
                            colors = ButtonDefaults.buttonColors(containerColor = IslamicGold)
                        ) {
                            Text("حسناً", color = IslamicMidnight, fontWeight = FontWeight.Bold)
                        }
                    },
                    containerColor = IslamicSurfaceDark
                )
            }
        }
    }
}
