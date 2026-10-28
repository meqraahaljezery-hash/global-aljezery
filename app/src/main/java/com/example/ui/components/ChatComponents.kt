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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.AppStrings
import com.example.data.ChatConversation
import com.example.data.ChatMessage
import com.example.ui.MaqraahViewModel
import com.example.ui.theme.BubbleIncomingDark
import com.example.ui.theme.BubbleOutgoingDark
import com.example.ui.theme.IslamicEmerald
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.IslamicGoldLight
import com.example.ui.theme.IslamicMidnight
import com.example.ui.theme.IslamicRoyalBlue
import com.example.ui.theme.IslamicSurfaceCard
import com.example.ui.theme.IslamicSurfaceDark
import com.example.ui.theme.TelegramBlue
import com.example.ui.theme.WhatsAppGreen

@Composable
fun ChatListScreen(
    viewModel: MaqraahViewModel,
    conversations: List<ChatConversation>,
    currentLang: AppLanguage,
    onSelectConversation: (ChatConversation) -> Unit,
    onJoinLiveRoom: (String) -> Unit
) {
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf(
        AppStrings.categoryAll(currentLang),
        AppStrings.categoryPrivate(currentLang),
        AppStrings.categoryGroups(currentLang),
        AppStrings.categoryChannels(currentLang),
        AppStrings.categoryUnread(currentLang)
    )

    val filteredConversations = conversations.filter { conv ->
        val matchesCategory = when (selectedCategoryIndex) {
            1 -> !conv.isGroup && !conv.isChannel
            2 -> conv.isGroup
            3 -> conv.isChannel
            4 -> conv.unreadCount > 0
            else -> true
        }
        val matchesSearch = searchQuery.isBlank() || conv.title.contains(searchQuery, ignoreCase = true) || conv.subtitle.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicMidnight)
    ) {
        // Search Bar (Telegram Style)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = when (currentLang) {
                            AppLanguage.ARABIC -> "البحث في الحلقات والمشايخ والرسائل..."
                            AppLanguage.AMHARIC -> "ውይይቶችን እና ሸይኾችን ይፈልጉ..."
                            AppLanguage.ENGLISH -> "Search chats, halaqat, and reciters..."
                        },
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = IslamicGold.copy(alpha = 0.8f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("chat_search_input"),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = IslamicSurfaceDark,
                    unfocusedContainerColor = IslamicSurfaceDark,
                    focusedBorderColor = IslamicGold,
                    unfocusedBorderColor = IslamicSurfaceCard,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true
            )
        }

        // Horizontal Category Tabs (Telegram style)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            items(categories.size) { index ->
                val isSelected = selectedCategoryIndex == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) IslamicGold else IslamicSurfaceDark)
                        .clickable { selectedCategoryIndex = index }
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = categories[index],
                        color = if (isSelected) IslamicMidnight else Color.White.copy(alpha = 0.8f),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Divider(color = IslamicSurfaceCard, thickness = 0.8.dp)

        // Conversation List
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredConversations) { conv ->
                ChatListItem(
                    conversation = conv,
                    onClick = { onSelectConversation(conv) },
                    onJoinLive = { conv.liveRoomId?.let { onJoinLiveRoom(it) } }
                )
                Divider(
                    color = IslamicSurfaceDark.copy(alpha = 0.6f),
                    thickness = 0.6.dp,
                    modifier = Modifier.padding(start = 76.dp)
                )
            }
        }
    }
}

@Composable
fun ChatListItem(
    conversation: ChatConversation,
    onClick: () -> Unit,
    onJoinLive: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with Online dot
        Box(
            modifier = Modifier.size(52.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (conversation.isGroup) IslamicRoyalBlue else IslamicSurfaceCard)
                    .border(1.dp, if (conversation.isVerified) IslamicGold else Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (conversation.isGroup) Icons.Default.Group else Icons.Default.GraphicEq,
                    contentDescription = conversation.title,
                    tint = if (conversation.isVerified) IslamicGold else Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(26.dp)
                )
            }

            if (conversation.isOnline) {
                Box(
                    modifier = Modifier
                        .size(13.dp)
                        .clip(CircleShape)
                        .background(IslamicEmerald)
                        .border(2.dp, IslamicMidnight, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Title and Last Message
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = conversation.title,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (conversation.isVerified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified",
                            tint = IslamicGold,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }

                Text(
                    text = conversation.lastMessageTime,
                    color = if (conversation.unreadCount > 0) IslamicGold else Color.LightGray.copy(alpha = 0.6f),
                    fontSize = 11.sp,
                    fontWeight = if (conversation.unreadCount > 0) FontWeight.Bold else FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (conversation.hasAudioLastMessage) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Voice note",
                            tint = TelegramBlue,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    Text(
                        text = conversation.subtitle,
                        color = Color.White.copy(alpha = 0.65f),
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (conversation.liveRoomId != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(IslamicEmerald.copy(alpha = 0.2f))
                                .border(0.8.dp, IslamicEmerald, RoundedCornerShape(12.dp))
                                .clickable { onJoinLive() }
                                .padding(horizontal = 7.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "مباشر",
                                color = IslamicEmerald,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    if (conversation.unreadCount > 0) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(TelegramBlue)
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = conversation.unreadCount.toString(),
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatDetailScreen(
    conversation: ChatConversation,
    messages: List<ChatMessage>,
    currentLang: AppLanguage,
    onBack: () -> Unit,
    onSendMessage: (String) -> Unit,
    onSendVoiceNote: () -> Unit,
    onJoinLiveRoom: (String) -> Unit
) {
    var textInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicMidnight)
    ) {
        // Chat Header
        Surface(
            color = IslamicSurfaceDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = IslamicGold
                    )
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(IslamicRoyalBlue)
                        .border(1.dp, IslamicGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (conversation.isGroup) Icons.Default.Group else Icons.Default.GraphicEq,
                        contentDescription = conversation.title,
                        tint = IslamicGold,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = conversation.title,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (conversation.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified",
                                tint = IslamicGold,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                    Text(
                        text = if (conversation.isOnline) "متصل الآن بالمقرأة" else "آخر ظهور حديثاً",
                        color = if (conversation.isOnline) IslamicEmerald else Color.LightGray.copy(alpha = 0.6f),
                        fontSize = 11.sp
                    )
                }

                // If this chat has an active live room, show "Join Live" button
                if (conversation.liveRoomId != null) {
                    Button(
                        onClick = { onJoinLiveRoom(conversation.liveRoomId) },
                        colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VideoCall,
                            contentDescription = "Join Live",
                            tint = IslamicMidnight,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "المجلس المباشر",
                            color = IslamicMidnight,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Live Room Quick Banner if available
        if (conversation.liveRoomId != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IslamicRoyalBlue)
                    .clickable { onJoinLiveRoom(conversation.liveRoomId) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
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
                                .background(IslamicEmerald)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = AppStrings.joinLiveRoom(currentLang),
                            color = IslamicGoldLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "18 طالباً متصلاً الآن",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Message List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            reverseLayout = false
        ) {
            items(messages) { msg ->
                ChatMessageBubble(msg = msg)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Bottom Input Area (Telegram & WhatsApp Style)
        Surface(
            color = IslamicSurfaceDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Attachment sheet */ }) {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = "Attach",
                        tint = IslamicGold.copy(alpha = 0.8f)
                    )
                }

                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    placeholder = {
                        Text(
                            text = AppStrings.enterMessageHint(currentLang),
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .testTag("chat_text_input"),
                    shape = RoundedCornerShape(22.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = IslamicMidnight,
                        unfocusedContainerColor = IslamicMidnight,
                        focusedBorderColor = IslamicGold,
                        unfocusedBorderColor = IslamicSurfaceCard,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    maxLines = 4
                )

                if (textInput.isNotBlank()) {
                    IconButton(
                        onClick = {
                            onSendMessage(textInput)
                            textInput = ""
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(IslamicGold)
                            .testTag("send_message_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = IslamicMidnight,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    // Voice Recitation Record Button (WhatsApp / Telegram style)
                    IconButton(
                        onClick = onSendVoiceNote,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(TelegramBlue)
                            .testTag("record_voice_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Record Recitation",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(msg: ChatMessage) {
    val bubbleColor = if (msg.isMine) BubbleOutgoingDark else BubbleIncomingDark
    val alignment = if (msg.isMine) Alignment.End else Alignment.Start

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (msg.isMine) 16.dp else 4.dp,
                        bottomEnd = if (msg.isMine) 4.dp else 16.dp
                    )
                )
                .background(bubbleColor)
                .border(
                    0.6.dp,
                    if (msg.isMine) TelegramBlue.copy(alpha = 0.4f) else IslamicGold.copy(alpha = 0.2f),
                    RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Column {
                if (!msg.isMine) {
                    Text(
                        text = msg.senderName,
                        color = IslamicGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }

                if (msg.isAudio) {
                    // Audio Message Waveform Player (Quran Recitation)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(if (msg.isMine) IslamicGold else TelegramBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = if (msg.isMine) IslamicMidnight else Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            // Waveform bars simulation
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                listOf(8, 16, 24, 12, 20, 28, 14, 22, 10, 18, 26, 12, 16, 8, 20).forEach { heightDp ->
                                    Box(
                                        modifier = Modifier
                                            .width(3.dp)
                                            .height(heightDp.dp)
                                            .clip(RoundedCornerShape(2.dp))
                                            .background(if (msg.isMine) IslamicGoldLight else TelegramBlue.copy(alpha = 0.8f))
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "تلاوة صوتية • 0:${msg.audioDurationSec} د",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                        }
                    }
                } else {
                    Text(
                        text = msg.text,
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = msg.timestamp,
                        color = Color.LightGray.copy(alpha = 0.6f),
                        fontSize = 10.sp
                    )

                    if (msg.isMine) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = if (msg.isRead) Icons.Default.DoneAll else Icons.Default.Check,
                            contentDescription = "Delivered",
                            tint = if (msg.isRead) WhatsAppGreen else Color.LightGray.copy(alpha = 0.6f),
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }
        }
    }
}
