import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.james.crypto.R
import com.james.crypto.data.source.model.Currency
import com.james.crypto.ui.CurrencyList
import com.james.crypto.ui.CurrencyType
import com.james.crypto.ui.theme.CurrencyCapitalColor
import com.james.crypto.ui.theme.CurrencySelectedColor
import com.james.crypto.view.CurrencyUiState
import com.james.crypto.view.CurrencyViewModel

data class CurrencyCategory(
    val name: String,
    val subCurrencies: List<Currency>
)

//fake data
val currencyCategories = listOf(
    CurrencyCategory(
        "Crypto.com Chain",
        listOf(
            Currency("Crypto.com Chain", "CRO Token 1"),
            Currency("Crypto.com Chain", "CRO Token 2")
        )
    ),
    CurrencyCategory(
        "Cucumber",
        listOf(
            Currency("Cucumber", "CUC Token A"),
            Currency("Cucumber", "CUC Token B"),
            Currency("Cucumber", "CUC Token C")
        )
    ),
    CurrencyCategory(
        "USD Coin",
        listOf(Currency("USD Coin", "USDC Mainnet"), Currency("USD Coin", "USDC Polygon"))
    ),
    CurrencyCategory(
        "Singapore Dollar",
        listOf(Currency("Singapore Dollar", "SGD Local Bank"))
    ),
    CurrencyCategory(
        "Euro",
        listOf(Currency("Euro", "EUR Reserve"), Currency("Euro", "EUR Forex"))
    ),
    CurrencyCategory(
        "British Pound",
        listOf(Currency("British Pound", "GBP Central"))
    )
)

@Composable
fun CurrencySelectionScreen(
    currencyCategories: List<CurrencyCategory>,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    var selectedCategory: CurrencyCategory by remember { mutableStateOf(currencyCategories[0]) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 16.dp,
                start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr) + 16.dp,
                end = paddingValues.calculateRightPadding(LayoutDirection.Ltr) + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp
            )

    ) {
        LazyColumn(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
        ) {
            items(currencyCategories.size) { index ->
                CurrencyCategoryItem(
                    category = currencyCategories[index],
                    isSelected = selectedCategory == currencyCategories[index],
                    onClick = { selectedCategory = currencyCategories[index] }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 16.dp)
        ) {
            items(selectedCategory.subCurrencies.size) { index ->
                Text(
                    text = selectedCategory.subCurrencies[index].code,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun CurrencyCategoryItem(
    category: CurrencyCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .background(if (isSelected) CurrencySelectedColor else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = CircleShape,
                color = CurrencyCapitalColor
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(40.dp)
                ) {
                    Text(
                        text = category.name.first().toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
        Text(
            text = category.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EmptyScreen(paddingValues: PaddingValues = PaddingValues(0.dp)) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(50.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_folder_off_24),
                contentDescription = "Empty State Icon",
                tint = Color.Gray,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No Data Available",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCurrencySelectionScreen() {
    CurrencySelectionScreen(currencyCategories)
}

@Composable
fun CurrencyListPage(
    currencyList: CurrencyList,
    navController: NavController
) {
    val currencyViewModel: CurrencyViewModel = hiltViewModel()
    BackHandler {
        navController.popBackStack()
    }
    when (currencyList.currencyType) {
        CurrencyType.CRYPTO -> currencyViewModel.getCrypto()
        CurrencyType.FIAT -> currencyViewModel.getFiat()
        CurrencyType.CRYPTO_FIAT -> currencyViewModel.getAllCurrency()
    }
    val currencyUiState: CurrencyUiState by currencyViewModel.uiState.collectAsStateWithLifecycle()
    when (currencyUiState) {
        is CurrencyUiState.LoadingError -> {
            val msg = (currencyUiState as CurrencyUiState.LoadingError).error
            Toast.makeText(LocalContext.current, msg, Toast.LENGTH_SHORT).show()
        }

        is CurrencyUiState.LoadCurrencySuccess -> {
            CurrencySelectionScreen(
                currencyCategories = (currencyUiState as CurrencyUiState.LoadCurrencySuccess).currencyList,
            )
        }

        is CurrencyUiState.LoadingEmpty -> {
            EmptyScreen()
        }

        else -> {}
    }
}