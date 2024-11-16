package com.james.crypto.data.source.db

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.Companion.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = CoinDB.CRYPTO_TABLE_NAME)
data class CryptoCurrency(
    @PrimaryKey
    @ColumnInfo(name = "id", typeAffinity = TEXT)
    val id: String,

    @ColumnInfo(name = "name", typeAffinity = TEXT)
    val name: String,

    @ColumnInfo(name = "symbol", typeAffinity = TEXT)
    var symbol: String,
)