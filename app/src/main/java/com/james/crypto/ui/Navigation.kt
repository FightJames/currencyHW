package com.james.crypto.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable


@Composable
fun NavigationConfig(
    navController: NavHostController,
    modifier: Modifier,
    indexPage: @Composable () -> Unit,
    currencyListPage: @Composable (CurrencyList) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Index,
        modifier = modifier
    ) {
        composable<Index> {
            indexPage()
        }
        composable<CurrencyList> { backStackEntry ->
            currencyListPage(backStackEntry.toRoute<CurrencyList>())
        }
    }

}

@Serializable
object Index

@Serializable
data class CurrencyList(val currencyType: CurrencyType)

@Serializable
enum class CurrencyType{
    CRYPTO, FIAT, CRYPTO_FIAT
}
