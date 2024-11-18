package com.james.crypto.data.search

import com.james.crypto.data.source.model.Currency

class CurrencyTrie {
    private val root = TrieNode()

    fun insert(currency: Currency) {
        var currentNode = root
        for (char in currency.code.lowercase()) { // ignore word case
            currentNode = currentNode.children.getOrPut(char) { TrieNode() }
        }
        currentNode.isEndOfWord = true
        currentNode.currencies.add(currency)
    }

    fun search(prefix: String): List<Currency> {
        var currentNode = root
        for (char in prefix.lowercase()) {
            currentNode = currentNode.children[char] ?: return emptyList()
        }
        return collectCurrencies(currentNode)
    }

    fun reset() {
        root.currencies.clear()
        root.isEndOfWord = false
        root.children.clear()
    }

    fun isEmpty() = root.children.isEmpty()

    private fun collectCurrencies(node: TrieNode): List<Currency> {
        val result = mutableListOf<Currency>()
        if (node.isEndOfWord) {
            result.addAll(node.currencies)
        }
        //add match prefix currencies
        for (child in node.children.values) {
            result.addAll(collectCurrencies(child))
        }
        return result
    }
}
