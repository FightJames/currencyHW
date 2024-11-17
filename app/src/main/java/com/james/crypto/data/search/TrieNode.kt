package com.james.crypto.data.search

import com.james.crypto.data.source.model.Currency

class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    val currencies: MutableList<Currency> = mutableListOf()
    var isEndOfWord: Boolean = false
}