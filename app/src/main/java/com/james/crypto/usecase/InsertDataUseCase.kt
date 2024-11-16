package com.james.crypto.usecase

import com.james.crypto.data.CurrencyRepository
import javax.inject.Inject

class InsertDataUseCase @Inject constructor(
    private val repo: CurrencyRepository
) {

    suspend operator fun invoke() {
        repo.insertCoinToDB()
    }
}