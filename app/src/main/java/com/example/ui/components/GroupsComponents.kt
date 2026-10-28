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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.FriendStatus
import com.example.data.GroupGender
import com.example.data.GroupMember
import com.example.data.StudyGroup
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
fun GroupsScreen(
    viewModel: MaqraahViewModel,
    groups: List<StudyGroup>,
    currentLang: AppLanguage,
    onSelectGroup: (StudyGroup) -> Unit
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
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
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDarkMode) NightCardBg else LightSkyBlueSoft),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, ImperialGold)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Group, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "الحلقات الدراسية السبع (صباحي، مسائي، ليلي)",
                            color = if (isDarkMode) ImperialGold else LightBluePrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "مجموعات تليجرام تفاعلية مقسمة للذكور والإناث حسب الفترات الثلاث، مع إمكانية بدء البث المباشر والمكالمات للمعلم والمشرف.",
                        color = textPrimary,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "قائمة المجموعات الدراسية (${groups.size}):",
                color = if (isDarkMode) ImperialGold else LightBluePrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(groups) { group ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clickable { onSelectGroup(group) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (group.isLiveBroadcastActive) ImperialGold else cardBorder
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                when (group.gender) {
                                    GroupGender.MALE -> if (isDarkMode) NightBg else LightSkyBlueSoft
                                    GroupGender.FEMALE -> if (isDarkMode) Color(0xFF381438) else Color(0xFFFCE7F3)
                                    GroupGender.ALL -> if (isDarkMode) NightBg else LightSkyBlueSoft
                                }
                            )
                            .border(1.5.dp, ImperialGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = group.name,
                            tint = if (group.gender == GroupGender.FEMALE) Color(0xFFD946EF) else LightBluePrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = group.name,
                                color = textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )

                            if (group.isLiveBroadcastActive) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(RubyRed)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("بث مباشر", color = PureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = group.timing.labelAr,
                            color = LightBluePrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "${group.sheikhSupervisor} • ${group.membersCount} عضواً",
                            color = textSecondary,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GroupChatScreen(
    group: StudyGroup,
    viewModel: MaqraahViewModel,
    currentLang: AppLanguage,
    currentRole: UserRole,
    onBack: () -> Unit,
    onOpenLiveRoom: (String) -> Unit
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val screenBg = if (isDarkMode) NightBg else DayBg
    val headerBg = if (isDarkMode) NightCardBg else PureWhite
    val textPrimary = if (isDarkMode) PureWhite else DayTextPrimary
    val textSecondary = if (isDarkMode) Color.LightGray else DayTextSecondary

    var textInput by remember { mutableStateOf("") }
    val messages = viewModel.getMessagesForConversation(group.id)
    val showProfileDialog by viewModel.showGroupProfileDialog.collectAsState()
    val groupMembers by viewModel.groupMembers.collectAsState()

    var selectedMemberForAction by remember { mutableStateOf<GroupMember?>(null) }
    var actionToast by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBg)
    ) {
        // Telegram-Style Group Header
        Surface(
            color = headerBg,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = if (isDarkMode) ImperialGold else LightBluePrimary
                    )
                }

                // Profile Avatar Icon (Clickable -> opens Group Profile & Members)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isDarkMode) NightBg else LightSkyBlueSoft)
                        .border(1.2.dp, ImperialGold, CircleShape)
                        .clickable { viewModel.openGroupProfile(group) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Group, contentDescription = "Group Profile", tint = ImperialGold, modifier = Modifier.size(20.dp))
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Group Title & Members Count
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.openGroupProfile(group) }
                ) {
                    Text(
                        text = group.name,
                        color = textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${group.membersCount} عضواً • انقر لعرض البروفايل والأعضاء",
                        color = LightBluePrimary,
                        fontSize = 10.sp
                    )
                }

                // Call Actions: Voice Call, Video Call, Live Broadcast
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Voice Call Button
                    IconButton(
                        onClick = {
                            viewModel.tryStartBroadcastOrCall(group, "voice")
                            onOpenLiveRoom(group.liveRoomId ?: "room_1")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Voice Call",
                            tint = if (isDarkMode) ImperialGold else LightBluePrimary,
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    // Video Call Button
                    IconButton(
                        onClick = {
                            viewModel.tryStartBroadcastOrCall(group, "video")
                            onOpenLiveRoom(group.liveRoomId ?: "room_1")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = "Video Call",
                            tint = LightBluePrimary,
                            modifier = Modifier.size(21.dp)
                        )
                    }

                    // Live Broadcast Button (علامة البث)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (group.isLiveBroadcastActive) RubyRed else if (isDarkMode) NightBg else LightSkyBlueSoft)
                            .border(1.dp, ImperialGold, RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.tryStartBroadcastOrCall(group, "broadcast")
                                onOpenLiveRoom(group.liveRoomId ?: "room_1")
                            }
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LiveTv,
                                contentDescription = "Broadcast",
                                tint = if (group.isLiveBroadcastActive) PureWhite else LightBluePrimary,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = if (group.isLiveBroadcastActive) "مباشر" else "بث",
                                color = if (group.isLiveBroadcastActive) PureWhite else textPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Live status bar in group
        if (group.isLiveBroadcastActive) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (isDarkMode) NightCardBg else LightSkyBlueSoft)
                    .clickable { onOpenLiveRoom(group.liveRoomId ?: "room_1") }
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(EmeraldGreen))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "البث المباشر فعال الآن مع الشيخ! انقر للانضمام", color = if (isDarkMode) ImperialGoldLight else LightBluePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(text = "دخول المجلس", color = ImperialGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Messages List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            items(messages) { msg ->
                ChatMessageBubble(msg = msg)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Bottom Input Bar
        Surface(
            color = headerBg,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    placeholder = {
                        Text(text = "اكتب رسالة في ${group.name}...", color = textSecondary, fontSize = 12.sp)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(22.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = if (isDarkMode) NightBg else DayBg,
                        unfocusedContainerColor = if (isDarkMode) NightBg else DayBg,
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary
                    ),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(6.dp))

                if (textInput.isNotBlank()) {
                    IconButton(
                        onClick = {
                            viewModel.sendMessage(group.id, textInput)
                            textInput = ""
                        },
                        modifier = Modifier.size(42.dp).clip(CircleShape).background(ImperialGold)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = NightBg, modifier = Modifier.size(19.dp))
                    }
                } else {
                    IconButton(
                        onClick = { viewModel.sendVoiceNote(group.id) },
                        modifier = Modifier.size(42.dp).clip(CircleShape).background(LightBluePrimary)
                    ) {
                        Icon(Icons.Default.Mic, contentDescription = "Voice", tint = PureWhite, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }

    // Group Profile & Members Dialog (شاشة بروفايل ووصف المجموعة والأعضاء)
    if (showProfileDialog != null) {
        AlertDialog(
            onDismissRequest = { viewModel.closeGroupProfile() },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = ImperialGold, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "بيانات ووصف المجموعة والأعضاء", color = if (isDarkMode) ImperialGold else LightBluePrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            },
            text = {
                LazyColumn(modifier = Modifier.height(380.dp)) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isDarkMode) NightBg else LightSkyBlueSoft)
                                .border(1.dp, ImperialGold.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(text = group.name, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(text = "التوقيت: ${group.timing.labelAr}", color = LightBluePrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = "المشرف: ${group.sheikhSupervisor}", color = ImperialGold, fontSize = 11.sp)
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(text = group.description, color = textSecondary, fontSize = 11.sp, lineHeight = 15.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "أعضاء المجموعة (${groupMembers.size}):",
                            color = if (isDarkMode) ImperialGold else LightBluePrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = if (currentRole == UserRole.STUDENT)
                                "حسابك كطالب: انقر على أي زميل لإرسال طلب صداقة لفتح محادثة خاصة."
                            else
                                "حسابك كمعلم/مدير: انقر على أي طالب لبدء محادثة فورية، مكالمة صوتية أو مرئية تلقائياً.",
                            color = textSecondary,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    items(groupMembers) { member ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isDarkMode) NightBg else DayBg)
                                .clickable { selectedMemberForAction = member }
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (member.role == UserRole.TEACHER || member.role == UserRole.ADMIN)
                                                ImperialGold.copy(alpha = 0.2f)
                                            else
                                                LightSkyBlueSoft
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (member.role == UserRole.TEACHER || member.role == UserRole.ADMIN)
                                            Icons.Default.Security
                                        else
                                            Icons.Default.Person,
                                        contentDescription = null,
                                        tint = if (member.role == UserRole.TEACHER || member.role == UserRole.ADMIN)
                                            ImperialGold
                                        else
                                            LightBluePrimary,
                                        modifier = Modifier.size(17.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = member.name, color = textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                    Text(
                                        text = "${member.role.titleAr} • ${member.qiraah}",
                                        color = textSecondary,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            // Dynamic Action according to logged in Role
                            if (currentRole == UserRole.TEACHER || currentRole == UserRole.ADMIN) {
                                Button(
                                    onClick = { selectedMemberForAction = member },
                                    colors = ButtonDefaults.buttonColors(containerColor = ImperialGold),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.height(28.dp)
                                ) {
                                    Text("تواصل تلقائي", color = NightBg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            } else {
                                // Student role
                                when (member.friendStatus) {
                                    FriendStatus.FRIENDS -> {
                                        Text("صديق ✓", color = EmeraldGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                    FriendStatus.REQUEST_SENT -> {
                                        Text("تم الطلب", color = LightBluePrimary, fontSize = 10.sp)
                                    }
                                    FriendStatus.NONE -> {
                                        if (member.role == UserRole.STUDENT) {
                                            IconButton(
                                                onClick = {
                                                    viewModel.sendFriendRequest(member.id)
                                                    actionToast = "تم إرسال طلب صداقة إلى ${member.name} بنجاح!"
                                                },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(Icons.Default.PersonAdd, contentDescription = "Add Friend", tint = ImperialGold, modifier = Modifier.size(18.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { viewModel.closeGroupProfile() }, colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)) {
                    Text("إغلاق", color = NightBg, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = if (isDarkMode) NightCardBg else PureWhite
        )
    }

    // Member Interaction Sheet
    selectedMemberForAction?.let { member ->
        AlertDialog(
            onDismissRequest = { selectedMemberForAction = null },
            title = {
                Text(text = "التواصل مع: ${member.name}", color = ImperialGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            },
            text = {
                Column {
                    Text(text = "الرتبة: ${member.role.titleAr}", color = textPrimary, fontSize = 12.sp)
                    Text(text = "الرواية: ${member.qiraah}", color = ImperialGoldLight, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    if (currentRole == UserRole.TEACHER || currentRole == UserRole.ADMIN) {
                        Text(text = "صلاحيات المعلم والمدير: تواصل تلقائي مباشر مع الطالب:", color = textSecondary, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Button(
                                onClick = {
                                    viewModel.startDirectChat(member)
                                    selectedMemberForAction = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ImperialGold),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(13.dp), tint = NightBg)
                                Spacer(modifier = Modifier.width(3.dp))
                                Text("محادثة", color = NightBg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    selectedMemberForAction = null
                                    onOpenLiveRoom(group.liveRoomId ?: "room_1")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = LightBluePrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(13.dp))
                                Spacer(modifier = Modifier.width(3.dp))
                                Text("صوت", fontSize = 10.sp)
                            }

                            Button(
                                onClick = {
                                    selectedMemberForAction = null
                                    onOpenLiveRoom(group.liveRoomId ?: "room_1")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Videocam, contentDescription = null, modifier = Modifier.size(13.dp))
                                Spacer(modifier = Modifier.width(3.dp))
                                Text("مرئي", fontSize = 10.sp)
                            }
                        }
                    } else {
                        // Student interacting with member
                        if (member.friendStatus == FriendStatus.FRIENDS) {
                            Button(
                                onClick = {
                                    viewModel.startDirectChat(member)
                                    selectedMemberForAction = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ImperialGold),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("فتح محادثة خاصة مع الصديق", color = NightBg, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Button(
                                onClick = {
                                    viewModel.sendFriendRequest(member.id)
                                    actionToast = "تم إرسال طلب صداقة إلى ${member.name} بنجاح!"
                                    selectedMemberForAction = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = LightBluePrimary),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("إرسال طلب صداقة للمحادثة الخاصة", fontSize = 12.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedMemberForAction = null }) { Text("إلغاء", color = Color.Gray) }
            },
            containerColor = if (isDarkMode) NightCardBg else PureWhite
        )
    }

    actionToast?.let { msg ->
        AlertDialog(
            onDismissRequest = { actionToast = null },
            title = { Text("إشعار", color = EmeraldGreen, fontWeight = FontWeight.Bold) },
            text = { Text(msg, color = textPrimary) },
            confirmButton = {
                Button(onClick = { actionToast = null }, colors = ButtonDefaults.buttonColors(containerColor = ImperialGold)) {
                    Text("تم", color = NightBg)
                }
            },
            containerColor = if (isDarkMode) NightCardBg else PureWhite
        )
    }
}
