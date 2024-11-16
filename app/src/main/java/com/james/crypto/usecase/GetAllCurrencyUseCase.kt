package com.james.crypto.usecase

import com.james.crypto.data.CurrencyRepository
import com.james.crypto.di.DefaultDispatcher
import com.james.crypto.data.source.model.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllCurrencyUseCase @Inject constructor(
    private val repo: CurrencyRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<Currency> = withContext(dispatcher) {
        return@withContext repo.getAllFiatCurrency() + repo.getAllCryptoCurrency()
    }
}