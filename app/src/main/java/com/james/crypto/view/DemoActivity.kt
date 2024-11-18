package com.james.crypto.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.james.crypto.ui.IndexPage
import com.james.crypto.ui.NavigationConfig
import com.james.crypto.ui.SearchableCurrencyList
import com.james.crypto.ui.theme.CryptoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavigationConfig(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        indexPage = {
                            IndexPage(navController)
                        },
                        currencyListPage = { currencyList ->
                            SearchableCurrencyList(
                                currencyList = currencyList,
                                navController = navController
                            )
                        }
                    )
                }
            }
        }
    }
}

