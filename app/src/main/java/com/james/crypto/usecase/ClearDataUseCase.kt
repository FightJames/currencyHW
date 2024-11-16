package com.james.crypto.usecase

import com.james.crypto.data.CurrencyRepository
import javax.inject.Inject

class ClearDataUseCase @Inject constructor(
    private val repo: CurrencyRepository
) {

    suspend operator fun invoke() {
        repo.clearDB()
    }
}