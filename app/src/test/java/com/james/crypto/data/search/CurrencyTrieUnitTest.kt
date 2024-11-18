package com.james.crypto.data.search

import com.james.crypto.CurrencyFakeDataHelper
import com.james.crypto.data.source.model.Currency
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CurrencyTrieUnitTest {

    val cryptoTrie: CurrencyTrie = CurrencyTrie()
    val fiatTrie: CurrencyTrie = CurrencyTrie()
    lateinit var cryptoCurrencyList: List<Currency>
    lateinit var fiatCurrencyList: List<Currency>

    @Before
    fun setup() {
        cryptoCurrencyList = CurrencyFakeDataHelper.cryptoCurrencyFakeData()
        fiatCurrencyList = CurrencyFakeDataHelper.fiatCurrencyFakeData()
        cryptoCurrencyList.forEach {
            cryptoTrie.insert(it)
        }
        fiatCurrencyList.forEach {
            fiatTrie.insert(it)
        }
    }

    @Test
    fun testSearchCryptoWithSingleWord() {
        val keyword = "B"
        val expected = cryptoCurrencyList.filter { it.code.startsWith(keyword, ignoreCase = true) }
        val actual = cryptoTrie.search(keyword)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testSearchCryptoWithTwoWord() {
        val keyword = "CR"
        val expected = cryptoCurrencyList.filter { it.code.startsWith(keyword, ignoreCase = true) }
        val actual = cryptoTrie.search(keyword)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testSearchCryptoAfterReset() {
        val keyword = "B"
        val originalResult =
            cryptoCurrencyList.filter { it.code.startsWith(keyword, ignoreCase = true) }
        cryptoTrie.reset()
        val emptyResult = cryptoTrie.search(keyword)
        Assert.assertTrue(originalResult.isNotEmpty())
        Assert.assertTrue(emptyResult.isEmpty())
    }

    @Test
    fun testSearchFiatWithSingleWord() {
        val keyword = "S"
        val expected = fiatCurrencyList.filter { it.code.startsWith(keyword, ignoreCase = true) }
        val actual = fiatTrie.search(keyword)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testSearchFiatWithTwoWord() {
        val keyword = "US"
        val expected = fiatCurrencyList.filter { it.code.startsWith(keyword, ignoreCase = true) }
        val actual = fiatTrie.search(keyword)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testSearchFiatAfterReset() {
        val keyword = "US"
        val originalResult =
            fiatCurrencyList.filter { it.code.startsWith(keyword, ignoreCase = true) }
        fiatTrie.reset()
        val emptyResult = fiatTrie.search(keyword)
        Assert.assertTrue(originalResult.isNotEmpty())
        Assert.assertTrue(emptyResult.isEmpty())
    }

    @Test
    fun testIsEmpty() {
        cryptoTrie.reset()
        fiatTrie.reset()
        Assert.assertTrue(cryptoTrie.isEmpty())
        Assert.assertTrue(fiatTrie.isEmpty())
    }

    @After
    fun clear() {
        cryptoTrie.reset()
        fiatTrie.reset()
    }
}