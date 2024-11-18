package com.james.crypto.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.james.crypto.databinding.FragmentCurrencyListBinding
import com.james.crypto.ui.CurrencyList
import com.james.crypto.ui.SearchableCurrencyList
import com.james.crypto.ui.theme.CryptoTheme
import dagger.hilt.android.AndroidEntryPoint

const val CURRENCY_LIST = "CURRENCY_LIST"

@AndroidEntryPoint
class CurrencyListFragment : Fragment() {
    private var currencyList: CurrencyList? = null
    lateinit var binding: FragmentCurrencyListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currencyList = it.getSerializable(CURRENCY_LIST) as? CurrencyList
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCurrencyListBinding.inflate(inflater, container, false).let {
            binding = it
            binding.composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            it.root
        }
    }

    override fun onStart() {
        super.onStart()
        currencyList?.let { currencyList ->
            binding.composeView.setContent {
                CryptoTheme {
                    SearchableCurrencyList(
                        currencyList = currencyList,
                        navController = findNavController()
                    )
                }
            }
        }
    }

    companion object {
        fun newInstance(currencyList: CurrencyList) =
            CurrencyListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(CURRENCY_LIST, currencyList)
                }
            }
    }
}