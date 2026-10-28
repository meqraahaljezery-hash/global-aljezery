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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.AppLanguage
import com.example.data.AppStrings
import com.example.data.LiveRoomInfo
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
fun LiveRoomsListScreen(
    rooms: List<LiveRoomInfo>,
    currentLang: AppLanguage,
    onSelectRoom: (LiveRoomInfo) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicMidnight)
            .padding(14.dp)
    ) {
        item {
            // LiveKit Banner Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(IslamicRoyalBlue)
                    .border(1.dp, IslamicGold, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(IslamicEmerald)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "بث LiveKit الصوتي والمرئي عالي النقاء",
                                color = IslamicGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(IslamicEmerald.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("WebRTC Ultra-Low Latency", color = IslamicEmerald, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "اختر أحد المجالس القرآنية المباشرة للدخول والاستماع أو رفع اليد لطلب التسميع والإجازة.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "الغرف والمجالس القرآنية الفعالة الآن:",
                color = IslamicGold,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(rooms) { room ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onSelectRoom(room) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicSurfaceDark),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (room.isLive) IslamicGold.copy(alpha = 0.6f) else IslamicSurfaceCard
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(if (room.isLive) IslamicRoyalBlue else IslamicSurfaceCard)
                                    .border(1.dp, if (room.isLive) IslamicGold else Color.Gray, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (room.isLive) Icons.Default.RecordVoiceOver else Icons.Default.GraphicEq,
                                    contentDescription = room.nameAr,
                                    tint = if (room.isLive) IslamicGold else Color.Gray,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = when (currentLang) {
                                        AppLanguage.ARABIC -> room.nameAr
                                        AppLanguage.AMHARIC -> room.nameAm ?: room.nameAr
                                        AppLanguage.ENGLISH -> room.nameEn
                                    },
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = room.teacherName,
                                    color = IslamicGoldLight,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        if (room.isLive) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(RubyRed)
                                    .padding(horizontal = 7.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "مباشر LIVE",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "الرواية: ${room.riwayah}",
                                color = TelegramBlue,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "السورة: ${room.currentSurah}",
                                color = Color.LightGray.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }

                        Button(
                            onClick = { onSelectRoom(room) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (room.isLive) IslamicGold else IslamicSurfaceCard
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .height(36.dp)
                                .testTag("join_room_${room.id}")
                        ) {
                            Text(
                                text = if (room.isLive) "انضم للمجلس" else "مغلق حالياً",
                                color = if (room.isLive) IslamicMidnight else Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }

                    if (room.activeReciter != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(IslamicMidnight)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(IslamicEmerald)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "يقرأ الآن: ${room.activeReciter}",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
