package com.james.crypto.usecase

import com.james.crypto.data.CurrencyRepository
import com.james.crypto.data.source.model.Currency
import javax.inject.Inject

class SearchAllCurrencyUseCase @Inject constructor(
    private val repo: CurrencyRepository
) {
    suspend operator fun invoke(keyword: String): List<Currency> {
        return repo.searchFiat(keyword) + repo.searchCrypto(keyword)
    }
}
