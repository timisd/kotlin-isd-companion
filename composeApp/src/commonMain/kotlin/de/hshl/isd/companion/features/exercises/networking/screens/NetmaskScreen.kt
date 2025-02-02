package de.hshl.isd.companion.features.exercises.networking.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetmaskScreen() {
    val navigator = LocalNavigator.currentOrThrow
    var showSolution by remember { mutableStateOf(false) }
    var ipAddress by remember { mutableStateOf(generateRandomIP()) }
    var netmaskData by remember { mutableStateOf(generateRandomNetmask()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("netmask", currentLanguage)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = Strings.get("go_back", currentLanguage)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = Strings.get("calculate_network_host", currentLanguage),
                style = MaterialTheme.typography.headlineSmall
            )
            
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${Strings.get("ip_address", currentLanguage)}: $ipAddress",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${Strings.get("netmask", currentLanguage)}: /${netmaskData.prefix}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    if (showSolution) {
                        Text(
                            text = "${Strings.get("network_address", currentLanguage)}: ${calculateNetworkAddress(ipAddress, netmaskData.mask)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${Strings.get("host_address", currentLanguage)}: ${calculateHostAddress(ipAddress, netmaskData.mask)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        ipAddress = generateRandomIP()
                        netmaskData = generateRandomNetmask()
                        showSolution = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = Strings.get("new_ip", currentLanguage),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(Strings.get("new_ip", currentLanguage))
                }
                Switch(
                    checked = showSolution,
                    onCheckedChange = { showSolution = it }
                )
            }
            
            if (!showSolution) {
                Text(Strings.get("show_solution_netmask", currentLanguage))
            }
        }
    }
}

private data class NetmaskData(
    val prefix: Int,
    val mask: String
)

private fun generateRandomIP(): String {
    return (0..3).joinToString(".") { Random.nextInt(1, 255).toString() }
}

private fun generateRandomNetmask(): NetmaskData {
    val prefixLengths = listOf(24, 25, 26, 27, 28, 29, 30)
    val prefix = prefixLengths.random()
    val mask = (-1 shl (32 - prefix)).toLong() and 0xFFFFFFFF
    val maskString = listOf(
        (mask shr 24) and 0xFF,
        (mask shr 16) and 0xFF,
        (mask shr 8) and 0xFF,
        mask and 0xFF
    ).joinToString(".")
    return NetmaskData(prefix, maskString)
}

private fun calculateNetworkAddress(ip: String, netmask: String): String {
    val ipParts = ip.split(".").map { it.toInt() }
    val maskParts = netmask.split(".").map { it.toInt() }
    return ipParts.zip(maskParts) { i, m -> i and m }.joinToString(".")
}

private fun calculateHostAddress(ip: String, netmask: String): String {
    val ipParts = ip.split(".").map { it.toInt() }
    val maskParts = netmask.split(".").map { it.toInt() }
    return ipParts.zip(maskParts) { i, m -> i and m.inv() }.joinToString(".")
}