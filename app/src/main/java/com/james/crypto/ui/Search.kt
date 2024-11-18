package com.james.crypto.ui

import CurrencySelectionScreen
import EmptyScreen
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.james.crypto.data.source.model.Currency
import com.james.crypto.view.CurrencyUiState
import com.james.crypto.view.CurrencyViewModel

val currencies = listOf(
    Currency("Bitcoin", "BTC"),
    Currency("Binance Coin", "BNB"),
    Currency("Bitcoin Cash", "BCH"),
    Currency("Basic Attention Token", "BAT"),
)

@Composable
fun SearchableCurrencyList(
    currencyList: CurrencyList,
    navController: NavController
) {

    val currencyViewModel: CurrencyViewModel = hiltViewModel()
    var query by remember { mutableStateOf("") }
    fun getCurrency() {
        when (currencyList.currencyType) {
            CurrencyType.CRYPTO -> currencyViewModel.getCrypto()
            CurrencyType.FIAT -> currencyViewModel.getFiat()
            CurrencyType.CRYPTO_FIAT -> currencyViewModel.getAllCurrency()
        }
    }

    fun searchCurrency(keyword: String) {
        when (currencyList.currencyType) {
            CurrencyType.CRYPTO -> currencyViewModel.searchCrypto(keyword)
            CurrencyType.FIAT -> currencyViewModel.searchFiat(keyword)
            CurrencyType.CRYPTO_FIAT -> currencyViewModel.searchAllCurrency(keyword)
        }
    }
    BackHandler {
        if (currencyViewModel.isSearchState()) {
            currencyViewModel.cancelSearch()
            getCurrency()
        } else {
            currencyViewModel.cancelSearch()
            navController.popBackStack()
        }
    }
    getCurrency()
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                IconButton(onClick = {
                    query = ""
                    if (currencyViewModel.isSearchState()) {
                        currencyViewModel.cancelSearch()
                        keyboardController?.hide()
                        getCurrency()
                    } else {
                        navController.popBackStack()
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                        searchCurrency(it)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    placeholder = { Text("Search...") },
                    singleLine = true
                )

                IconButton(onClick = {
                    if (!currencyViewModel.isSearchState()) return@IconButton
                    query = ""
                    currencyViewModel.clearSearch()
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                }
            }
        }
    ) { paddingValues ->
        val currencyUiState: CurrencyUiState by currencyViewModel.uiState.collectAsStateWithLifecycle()
        when (currencyUiState) {

            is CurrencyUiState.SearchError,
            is CurrencyUiState.LoadingError -> {
                val msg = (currencyUiState as CurrencyUiState.LoadingError).error
                Toast.makeText(LocalContext.current, msg, Toast.LENGTH_SHORT).show()
            }

            is CurrencyUiState.LoadCurrencySuccess -> {
                CurrencySelectionScreen(
                    currencyCategories = (currencyUiState as CurrencyUiState.LoadCurrencySuccess).currencyList,
                    paddingValues
                )
            }

            is CurrencyUiState.SearchEmpty,
            is CurrencyUiState.LoadingEmpty -> {
                EmptyScreen(paddingValues)
            }

            is CurrencyUiState.SearchResult -> {
                val data = (currencyUiState as CurrencyUiState.SearchResult).currencyList
                SearchResult(paddingValues = paddingValues, data = data)
            }
            CurrencyUiState.Idle -> EmptyScreen(paddingValues)
        }
    }
}

@Composable
fun SearchResult(
    paddingValues: PaddingValues,
    data: List<Currency>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        items(data) { currency ->
            ListItem(
                headlineContent = { Text(currency.name) },
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currency.name.first().toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                trailingContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            currency.code,
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Normal
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearch() {
    SearchResult(paddingValues = PaddingValues(0.dp), data = currencies)
}
