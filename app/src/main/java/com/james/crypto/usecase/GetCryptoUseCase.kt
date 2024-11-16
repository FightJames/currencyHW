package com.james.crypto.usecase

import com.james.crypto.data.CurrencyRepository
import com.james.crypto.data.source.model.Currency
import javax.inject.Inject

class GetCryptoUseCase @Inject constructor(
    private val repo: CurrencyRepository
) {
    suspend operator fun invoke(): List<Currency> {
        return repo.getAllCryptoCurrency()
    }
}