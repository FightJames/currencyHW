package com.james.crypto.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.james.crypto.ui.theme.CryptoTheme

class DemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DemoUI(
                        Modifier.padding(innerPadding),
                        onClearData = {},
                        onInsertData = {},
                        onSwitchToCrypto = {},
                        onSwitchToFiat = {},
                        onDisplayPurchasable = {}
                    )
                }
            }
        }
    }
}

@Composable
fun DemoUI(
    modifier: Modifier = Modifier,
    onClearData: () -> Unit,
    onInsertData: () -> Unit,
    onSwitchToCrypto: () -> Unit,
    onSwitchToFiat: () -> Unit,
    onDisplayPurchasable: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = onClearData, modifier = Modifier.fillMaxWidth()) {
            Text("Clear Local Database")
        }
        Button(onClick = onInsertData, modifier = Modifier.fillMaxWidth()) {
            Text("Insert Data into Local Database")
        }
        Button(onClick = onSwitchToCrypto, modifier = Modifier.fillMaxWidth()) {
            Text("Switch to Currency List A - Crypto")
        }
        Button(onClick = onSwitchToFiat, modifier = Modifier.fillMaxWidth()) {
            Text("Switch to Currency List B - Fiat")
        }
        Button(onClick = onDisplayPurchasable, modifier = Modifier.fillMaxWidth()) {
            Text("Display Purchasable Currency Info")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDemoUI() {
    DemoUI(
        onClearData = {},
        onInsertData = {},
        onSwitchToCrypto = {},
        onSwitchToFiat = {},
        onDisplayPurchasable = {}
    )
}
