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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.AppStrings
import com.example.data.BankPaymentInfo
import com.example.data.MinutePackage
import com.example.data.PaymentRequest
import com.example.data.PaymentRequestStatus
import com.example.data.UserRole
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
fun MyAccountScreen(
    viewModel: MaqraahViewModel,
    minutesBalance: Int,
    currentLang: AppLanguage,
    currentRole: UserRole,
    userEmail: String?
) {
    val packages by viewModel.packages.collectAsState()
    val bankDetails by viewModel.bankDetails.collectAsState()
    val payments by viewModel.payments.collectAsState()
    val loanRequests by viewModel.loanRequests.collectAsState()

    var showLoanDialog by remember { mutableStateOf(false) }
    var selectedPackageToBuy by remember { mutableStateOf<MinutePackage?>(null) }
    var successToast by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicMidnight)
            .padding(14.dp)
    ) {
        // Student Profile and Balance Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(IslamicRoyalBlue, IslamicSurfaceDark, Color(0xFF142B5B))
                        )
                    )
                    .border(1.5.dp, IslamicGold, RoundedCornerShape(20.dp))
                    .padding(18.dp)
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
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(IslamicMidnight)
                                    .border(1.dp, IslamicGold, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = IslamicGold, modifier = Modifier.size(26.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (userEmail != null) "طالب المقرأة ($userEmail)" else "طالب مقرأة الجزري",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "الرواية: حفص عن عاصم من الشاطبية",
                                    color = IslamicGoldLight,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Loan Minute Button (طلب سلفة دقائق)
                        Button(
                            onClick = { showLoanDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = IslamicMidnight, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("سلفة دقائق", color = IslamicMidnight, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(text = "رصيد الدقائق الفعلي المتاح:", color = IslamicGoldLight, fontSize = 12.sp)
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = minutesBalance.toString(),
                                    color = Color.White,
                                    fontSize = 38.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "دقيقة تسميع",
                                    color = IslamicGold,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(IslamicMidnight)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = "خصم بالثانية عبر LiveKit", color = IslamicEmerald, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Bank Accounts Configured by Supervisor
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccountBalance, contentDescription = null, tint = IslamicGold, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "الحسابات البنكية ووسائل الدفع المعتمدة للمقرأة:",
                    color = IslamicGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "يتم تعديل هذه الحسابات وتحديثها من قِبل المشرف عبر الإعدادات.",
                color = Color.LightGray.copy(alpha = 0.7f),
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Bank Cards List
        items(bankDetails) { bank ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicSurfaceDark),
                border = androidx.compose.foundation.BorderStroke(1.dp, IslamicSurfaceCard)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = bank.bankName, color = IslamicGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(text = bank.currency, color = TelegramBlue, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "المستفيد: ${bank.accountHolder}", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Text(text = "رقم الحساب / المحفظة: ${bank.accountNumber}", color = IslamicGoldLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(text = "المعرف / الآيبان: ${bank.ibanOrIdentifier}", color = Color.LightGray.copy(alpha = 0.8f), fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = bank.instructions, color = Color.Gray, fontSize = 10.sp)
                }
            }
        }

        // Minute Packages List
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Payment, contentDescription = null, tint = IslamicGold, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "باقات الدقائق المتاحة للشحن اليدوي:",
                    color = IslamicGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(packages) { pkg ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = IslamicSurfaceDark),
                border = androidx.compose.foundation.BorderStroke(1.dp, if (pkg.isPopular) IslamicGold else IslamicSurfaceCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = pkg.nameAr, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            if (pkg.isPopular) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(IslamicGold)
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text("الأكثر طلباً", color = IslamicMidnight, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Text(text = "${pkg.minutes} دقيقة • $${pkg.priceUsd.toInt()}", color = IslamicGoldLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = pkg.descAr, color = Color.LightGray.copy(alpha = 0.7f), fontSize = 10.sp, maxLines = 1)
                    }

                    Button(
                        onClick = { selectedPackageToBuy = pkg },
                        colors = ButtonDefaults.buttonColors(containerColor = IslamicGold),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("شحن يدوي", color = IslamicMidnight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Loan Requests and Payment Status History
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.History, contentDescription = null, tint = IslamicGold, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "سجل طلبات السلفة والتحويلات:",
                    color = IslamicGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }

        items(loanRequests) { loan ->
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
                    Text(text = "سلفة دقائق: ${loan.requestedMinutes} دقيقة", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(text = "السبب: ${loan.reason} • ${loan.requestDate}", color = Color.LightGray, fontSize = 10.sp)
                }
                Text(
                    text = if (loan.status == PaymentRequestStatus.APPROVED) "معتمدة ومضافة ✓" else "قيد مراجعة المشرف",
                    color = if (loan.status == PaymentRequestStatus.APPROVED) IslamicEmerald else TelegramBlue,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        items(payments) { pay ->
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
                    Text(text = "${pay.packageName} (${pay.paymentMethod})", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    Text(text = "العملية: ${pay.transactionId} • ${pay.date}", color = Color.LightGray, fontSize = 10.sp)
                }
                Text(
                    text = when (pay.status) {
                        PaymentRequestStatus.APPROVED -> "معتمد ✓"
                        PaymentRequestStatus.PENDING -> "قيد المراجعة"
                        PaymentRequestStatus.REJECTED -> "مرفوض"
                    },
                    color = when (pay.status) {
                        PaymentRequestStatus.APPROVED -> IslamicEmerald
                        PaymentRequestStatus.PENDING -> TelegramBlue
                        PaymentRequestStatus.REJECTED -> RubyRed
                    },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Minute Loan Request Dialog (طلب سلفة دقائق)
    if (showLoanDialog) {
        var requestedMinutes by remember { mutableStateOf("15") }
        var reason by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showLoanDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = IslamicGold, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("طلب سلفة دقائق طارئة", color = IslamicGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            },
            text = {
                Column {
                    Text(
                        text = "تتيح المقرأة للطالب استلاف 15 إلى 30 دقيقة لمواصلة التسميع في حال نفاد الرصيد، وتسدد لاحقاً عند شحن أي باقة.",
                        color = Color.White,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = requestedMinutes,
                        onValueChange = { requestedMinutes = it },
                        label = { Text("عدد الدقائق المطلوبة (15 - 30 د)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reason,
                        onValueChange = { reason = it },
                        label = { Text("سبب الطلب والسورة المراد تسميعها") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val mins = requestedMinutes.toIntOrNull() ?: 15
                        viewModel.requestMinuteLoan("طالب المقرأة", mins, reason.ifBlank { "مواصلة تسميع الورد اليومي" })
                        showLoanDialog = false
                        successToast = "تم رفع طلب سلفة $mins دقيقة للمشرف بنجاح وسيتلقى إشعاراً للموافقة الفورية."
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold)
                ) {
                    Text("إرسال طلب السلفة", color = IslamicMidnight, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLoanDialog = false }) { Text("إلغاء", color = Color.LightGray) }
            },
            containerColor = IslamicSurfaceDark
        )
    }

    // Manual Payment Submission Dialog (طريقة الدفع بإرسال يدوي)
    selectedPackageToBuy?.let { pkg ->
        var senderName by remember { mutableStateOf("") }
        var senderPhone by remember { mutableStateOf("") }
        var txId by remember { mutableStateOf("") }
        var chosenBank by remember { mutableStateOf(bankDetails.firstOrNull()?.bankName ?: "مصرف الراجحي") }

        AlertDialog(
            onDismissRequest = { selectedPackageToBuy = null },
            title = { Text("إشعار تحويل يدوي: ${pkg.nameAr}", color = IslamicGold, fontWeight = FontWeight.Bold, fontSize = 15.sp) },
            text = {
                Column {
                    Text(text = "القيمة: $${pkg.priceUsd.toInt()} • الرصيد: ${pkg.minutes} دقيقة", color = IslamicGoldLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "اختر وسيلة الدفع التي حولت إليها:", color = Color.LightGray, fontSize = 11.sp)

                    bankDetails.forEach { b ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { chosenBank = b.bankName }
                                .padding(vertical = 3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(if (chosenBank == b.bankName) IslamicGold else Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = b.bankName, color = Color.White, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = senderName, onValueChange = { senderName = it }, label = { Text("اسم صاحب الحساب المرسل") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = senderPhone, onValueChange = { senderPhone = it }, label = { Text("رقم الهاتف أو الواتساب") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = txId, onValueChange = { txId = it }, label = { Text("رقم العملية / الحوالة (Transaction ID)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.submitPayment(
                            packageName = pkg.nameAr,
                            minutes = pkg.minutes,
                            amount = pkg.priceUsd,
                            method = chosenBank,
                            txId = txId,
                            phone = senderPhone,
                            name = senderName
                        )
                        selectedPackageToBuy = null
                        successToast = "تم إرسال إشعار التحويل اليدوي بنجاح. سيقوم المشرف بمراجعته واعتماد الرصيد فوراً."
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IslamicGold)
                ) {
                    Text("تأكيد وإرسال للمشرف", color = IslamicMidnight, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedPackageToBuy = null }) { Text("إلغاء", color = Color.LightGray) }
            },
            containerColor = IslamicSurfaceDark
        )
    }

    successToast?.let { msg ->
        AlertDialog(
            onDismissRequest = { successToast = null },
            title = { Text("تم بنجاح ✓", color = IslamicEmerald, fontWeight = FontWeight.Bold) },
            text = { Text(msg, color = Color.White) },
            confirmButton = {
                Button(onClick = { successToast = null }, colors = ButtonDefaults.buttonColors(containerColor = IslamicGold)) {
                    Text("تم", color = IslamicMidnight)
                }
            },
            containerColor = IslamicSurfaceDark
        )
    }
}
