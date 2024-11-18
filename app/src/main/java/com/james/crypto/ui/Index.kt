package com.james.crypto.ui

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.james.crypto.view.IndexUiState
import com.james.crypto.view.IndexViewModel
import com.james.crypto.R
import com.james.crypto.view.fragment.CURRENCY_LIST

@Composable
fun IndexUI(
    onClearData: () -> Unit,
    onInsertData: () -> Unit,
    onSwitchToCrypto: () -> Unit,
    onSwitchToFiat: () -> Unit,
    onDisplayPurchasable: () -> Unit
) {
    Column(
        modifier = Modifier
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
    IndexUI(
        onClearData = {},
        onInsertData = {},
        onSwitchToCrypto = {},
        onSwitchToFiat = {},
        onDisplayPurchasable = {}
    )
}

@Composable
fun IndexPage(navController: NavController) {
    val indexViewModel: IndexViewModel = hiltViewModel()
    val indexUiState: IndexUiState by indexViewModel.uiState.collectAsStateWithLifecycle(initialValue = IndexUiState.Idle)

    when (indexUiState) {
        is IndexUiState.Success -> {
            val msg = (indexUiState as IndexUiState.Success).message
            Toast.makeText(LocalContext.current, msg, Toast.LENGTH_SHORT).show()
        }

        is IndexUiState.Error -> {
            val msg = (indexUiState as IndexUiState.Error).error
            Toast.makeText(LocalContext.current, msg, Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }

    IndexUI(
        onClearData = {
            indexViewModel.clearData()
        },
        onInsertData = {
            indexViewModel.insertData()
        },
        onSwitchToCrypto = {
            val bundle = Bundle().apply {
                putSerializable(CURRENCY_LIST, CurrencyList(CurrencyType.CRYPTO))
            }
            navController.navigate(R.id.action_IndexFragment_to_CurrencyListFragment, bundle)
        },
        onSwitchToFiat = {
            val bundle = Bundle().apply {
                putSerializable(CURRENCY_LIST, CurrencyList(CurrencyType.FIAT))
            }
            navController.navigate(R.id.action_IndexFragment_to_CurrencyListFragment, bundle)
        },
        onDisplayPurchasable = {
            val bundle = Bundle().apply {
                putSerializable(CURRENCY_LIST, CurrencyList(CurrencyType.CRYPTO_FIAT))
            }
            navController.navigate(R.id.action_IndexFragment_to_CurrencyListFragment, bundle)
        }
    )
}