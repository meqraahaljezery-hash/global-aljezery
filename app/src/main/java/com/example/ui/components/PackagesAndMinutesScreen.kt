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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.AppStrings
import com.example.data.MinutePackage
import com.example.data.PaymentRequest
import com.example.data.PaymentRequestStatus
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
fun PackagesAndMinutesScreen(
    viewModel: MaqraahViewModel,
    packages: List<MinutePackage>,
    payments: List<PaymentRequest>,
    minutesBalance: Int,
    currentLang: AppLanguage
) {
    var selectedPackageToBuy by remember { mutableStateOf<MinutePackage?>(null) }
    var showSuccessToast by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicMidnight)
            .padding(14.dp)
    ) {
        // Balance Hero Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(IslamicRoyalBlue, IslamicSurfaceDark, Color(0xFF142C60))
                        )
                    )
                    .border(1.5.dp, IslamicGold, RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = AppStrings.minuteBalance(currentLang),
                            color = IslamicGoldLight,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "Minutes",
                            tint = IslamicGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = minutesBalance.toString(),
                            color = Color.White,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = AppStrings.minutesUnit(currentLang),
                            color = IslamicGold,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "يتم خصم الدقائق تلقائياً وبدقة بالثانية أثناء التسميع المباشر مع مشايخ المقرأة.",
                        color = Color.LightGray.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "باقات الدقائق المتاحة للاشتراك والتسميع:",
                color = IslamicGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Package Cards
        items(packages) { pkg ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicSurfaceDark),
                border = androidx.compose.foundation.BorderStroke(
                    if (pkg.isPopular) 1.5.dp else 1.dp,
                    if (pkg.isPopular) IslamicGold else IslamicSurfaceCard
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = when (currentLang) {
                                    AppLanguage.ARABIC -> pkg.nameAr
                                    AppLanguage.AMHARIC -> pkg.nameAm
                                    AppLanguage.ENGLISH -> pkg.nameEn
                                },
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            if (pkg.isPopular) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(IslamicGold)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "الأكثر طلباً",
                                        color = IslamicMidnight,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Text(
                            text = "$${pkg.priceUsd.toInt()}",
                            color = IslamicGold,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = when (currentLang) {
                            AppLanguage.ARABIC -> pkg.descAr
                            AppLanguage.AMHARIC -> pkg.descAm
                            AppLanguage.ENGLISH -> pkg.descEn
                        },
                        color = Color.LightGray.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = TelegramBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${pkg.minutes} دقيقة تسميع معتمد",
                                color = TelegramBlue,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Button(
                            onClick = { selectedPackageToBuy = pkg },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (pkg.isPopular) IslamicGold else IslamicRoyalBlue
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("buy_package_${pkg.id}")
                        ) {
                            Text(
                                text = "طلب شحن الباقة",
                                color = if (pkg.isPopular) IslamicMidnight else Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Transaction History / Payment Requests Section
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = null,
                    tint = IslamicGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "سجل طلبات التحويل وشحن الدقائق:",
                    color = IslamicGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        items(payments) { req ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(IslamicSurfaceDark)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = req.packageName,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${req.paymentMethod} • رمز العملية: ${req.transactionId}",
                        color = Color.LightGray.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )
                    Text(
                        text = "${req.date} • الهاتف: ${req.senderPhone}",
                        color = Color.LightGray.copy(alpha = 0.5f),
                        fontSize = 10.sp
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "+${req.minutes} دقيقة",
                        color = IslamicGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    val statusColor = when (req.status) {
                        PaymentRequestStatus.APPROVED -> IslamicEmerald
                        PaymentRequestStatus.PENDING -> TelegramBlue
                        PaymentRequestStatus.REJECTED -> RubyRed
                    }
                    val statusLabel = when (req.status) {
                        PaymentRequestStatus.APPROVED -> "معتمد ومضاف"
                        PaymentRequestStatus.PENDING -> "قيد المراجعة"
                        PaymentRequestStatus.REJECTED -> "مرفوض"
                    }
                    Text(
                        text = statusLabel,
                        color = statusColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }

    // Manual Payment Submission Dialog
    selectedPackageToBuy?.let { pkg ->
        var senderName by remember { mutableStateOf("") }
        var senderPhone by remember { mutableStateOf("") }
        var transactionId by remember { mutableStateOf("") }
        var selectedMethod by remember { mutableStateOf("بنك الراجحي (السعودية)") }

        val paymentMethods = listOf(
            "بنك الراجحي (السعودية)",
            "Telebirr (إثيوبيا)",
            "Commercial Bank of Ethiopia (CBE)",
            "فودافون كاش (مصر)",
            "PayPal / بطاقة بنكية"
        )

        AlertDialog(
            onDismissRequest = { selectedPackageToBuy = null },
            title = {
                Text(
                    text = "تأكيد طلب شحن: ${pkg.nameAr}",
                    color = IslamicGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "القيمة: $${pkg.priceUsd.toInt()} • الرصيد: ${pkg.minutes} دقيقة تسميع",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text("اختر وسيلة التحويل:", color = IslamicGoldLight, fontSize = 12.sp)
                    paymentMethods.forEach { method ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedMethod = method }
                                .padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(if (selectedMethod == method) IslamicGold else Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = method, color = Color.White, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = senderName,
                        onValueChange = { senderName = it },
                        label = { Text("اسم صاحب الحساب المحول") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = senderPhone,
                        onValueChange = { senderPhone = it },
                        label = { Text("رقم الهاتف أو الواتساب") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = transactionId,
                        onValueChange = { transactionId = it },
                        label = { Text("رقم الإيصال / معرف العملية (Transaction ID)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.submitPayment(
                            packageName = pkg.nameAr,
                            minutes = pkg.minutes,
                            amount = pkg.priceUsd,
                            method = selectedMethod,
                            txId = transactionId,
                            phone = senderPhone,
                            name = senderName
                        )
                        selectedPackageToBuy = null
                        showSuccessToast = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold)
                ) {
                    Text("إرسال إشعار التحويل", color = IslamicMidnight, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedPackageToBuy = null }) {
                    Text("إلغاء", color = Color.LightGray)
                }
            },
            containerColor = IslamicSurfaceDark
        )
    }

    if (showSuccessToast) {
        AlertDialog(
            onDismissRequest = { showSuccessToast = false },
            title = {
                Text("تم إرسال طلب الشحن بنجاح", color = IslamicEmerald, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    text = "تم إرسال إيصال التحويل لإدارة المقرأة. سيتم تدقيق المعاملة وإيداع الدقائق في رصيدك فوراً.",
                    color = Color.White
                )
            },
            confirmButton = {
                Button(
                    onClick = { showSuccessToast = false },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold)
                ) {
                    Text("حسناً", color = IslamicMidnight)
                }
            },
            containerColor = IslamicSurfaceDark
        )
    }
}
