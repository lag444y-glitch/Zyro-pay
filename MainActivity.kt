package com.zyropay.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF050811)
private val Panel = Color(0xFF10182A)
private val Violet = Color(0xFF7C3AED)
private val Neon = Color(0xFFB58CFF)
private val TextMain = Color(0xFFF5F5FF)
private val TextMuted = Color(0xFFA8B0C5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ZyroPayApp() }
    }
}

@Composable
fun ZyroPayApp() {
    var page by remember { mutableStateOf("Home") }
    var message by remember { mutableStateOf("") }
    var recipient by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    MaterialTheme(colorScheme = darkColorScheme(
        primary = Violet, background = Ink, surface = Panel,
        onPrimary = Color.White, onBackground = TextMain, onSurface = TextMain
    )) {
        Scaffold(
            containerColor = Ink,
            bottomBar = {
                NavigationBar(containerColor = Panel, contentColor = TextMain) {
                    listOf(
                        "Home" to Icons.Default.Home,
                        "History" to Icons.Default.History,
                        "Bank" to Icons.Default.AccountBalance,
                        "Profile" to Icons.Default.Person
                    ).forEach { (label, icon) ->
                        NavigationBarItem(
                            selected = page == label,
                            onClick = { page = label; message = "" },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Neon,
                                selectedTextColor = Neon,
                                indicatorColor = Violet.copy(alpha = .22f),
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Column(Modifier.fillMaxSize().padding(padding).padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(42.dp).background(Violet, RoundedCornerShape(13.dp)),
                        contentAlignment = Alignment.Center
                    ) { Text("Z", fontSize = 27.sp, fontWeight = FontWeight.Black, color = Color.White) }
                    Spacer(Modifier.width(10.dp))
                    Text("ZYRO ", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextMain)
                    Text("PAY", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Neon)
                    Spacer(Modifier.weight(1f))
                    Text("DEMO", color = Neon, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(18.dp))

                when (page) {
                    "Home" -> {
                        Text("Good morning 👋", color = TextMuted, fontSize = 14.sp)
                        Text("Welcome to ZYRO PAY", color = TextMain, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(18.dp))
                        Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(22.dp)) {
                            Column(Modifier.fillMaxWidth().padding(20.dp)) {
                                Text("Demo balance", color = TextMuted)
                                Spacer(Modifier.height(5.dp))
                                Text("₹ 0.00", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                                Text("No real bank balance is connected", color = TextMuted, fontSize = 12.sp)
                            }
                        }
                        Spacer(Modifier.height(18.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ActionTile("Send Money", Icons.Default.Send, Modifier.weight(1f)) { page = "Send Money"; message = "" }
                            ActionTile("Scan & Pay", Icons.Default.QrCodeScanner, Modifier.weight(1f)) { page = "Scan & Pay"; message = "" }
                            ActionTile("Receive", Icons.Default.CallReceived, Modifier.weight(1f)) { page = "Receive"; message = "" }
                        }
                        Spacer(Modifier.height(22.dp))
                        Text("Recent activity", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                                Text("No transactions yet", color = TextMuted)
                                Text("Demo activity will appear here.", color = TextMuted, fontSize = 12.sp)
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        Text("Prototype only • No money is moved", color = Neon, fontSize = 12.sp)
                    }
                    "Send Money" -> {
                        PageTitle("Send Money", "This is a UI demo; no payment will be sent.")
                        OutlinedTextField(
                            value = recipient, onValueChange = { recipient = it },
                            label = { Text("UPI ID or recipient (demo)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = amount, onValueChange = { amount = it.filter { c -> c.isDigit() || c == '.' } },
                            label = { Text("Amount in ₹") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(), singleLine = true
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = { message = if (recipient.isBlank() || (amount.toDoubleOrNull() ?: 0.0) <= 0) "Enter a recipient and valid amount." else "Demo only: payment NOT sent. Connect an authorized UPI partner for real payments." },
                            modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Violet)
                        ) { Text("Continue (Demo)") }
                        if (message.isNotBlank()) { Spacer(Modifier.height(12.dp)); Text(message, color = Neon) }
                    }
                    "Scan & Pay" -> {
                        PageTitle("Scan & Pay", "QR scanning is not connected in this starter project.")
                        Box(Modifier.fillMaxWidth().height(210.dp).background(Panel, RoundedCornerShape(24.dp)), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.QrCodeScanner, null, tint = Neon, modifier = Modifier.size(64.dp))
                                Text("QR scanner placeholder", color = TextMuted)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("A real QR payment flow must validate the UPI QR and confirm the payment through an authorized provider.", color = TextMuted)
                    }
                    "Receive" -> {
                        PageTitle("Receive Money", "Your personal UPI ID is not set up.")
                        Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                                Text("Your demo QR", color = TextMuted)
                                Spacer(Modifier.height(12.dp))
                                Box(Modifier.size(150.dp).background(Color.White, RoundedCornerShape(12.dp)).align(Alignment.CenterHorizontally), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.QrCodeScanner, null, tint = Ink, modifier = Modifier.size(90.dp))
                                }
                                Spacer(Modifier.height(12.dp))
                                Text("Demo QR only — not payable", color = Neon)
                            }
                        }
                    }
                    "History" -> {
                        PageTitle("Transaction History", "Only verified provider transactions should appear here.")
                        Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                                Text("No transactions", fontWeight = FontWeight.Bold)
                                Text("This demo does not create real transactions.", color = TextMuted)
                            }
                        }
                    }
                    "Bank" -> {
                        PageTitle("Bank Accounts", "Bank linking is not enabled in this prototype.")
                        Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                                Text("No bank account linked")
                                Spacer(Modifier.height(6.dp))
                                Text("Real account linking requires an authorized integration and secure provider flow.", color = TextMuted)
                            }
                        }
                    }
                    "Profile" -> {
                        PageTitle("Profile & Security", "Demo profile")
                        listOf("Personal information", "UPI settings", "Security & privacy", "Help & support").forEach {
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Panel)
                            ) { Text(it, Modifier.padding(16.dp), color = TextMain) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionTile(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(42.dp).background(Violet.copy(alpha = .22f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = Neon)
            }
            Spacer(Modifier.height(8.dp))
            Text(title, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun PageTitle(title: String, subtitle: String) {
    Text(title, fontSize = 25.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(6.dp))
    Text(subtitle, color = TextMuted, fontSize = 13.sp)
    Spacer(Modifier.height(18.dp))
}
