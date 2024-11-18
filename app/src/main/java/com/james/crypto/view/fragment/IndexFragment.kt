package com.james.crypto.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.findNavController
import com.james.crypto.R
import com.james.crypto.databinding.FragmentIndexBinding
import com.james.crypto.ui.IndexPage
import com.james.crypto.ui.theme.CryptoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndexFragment : Fragment() {
    lateinit var binding: FragmentIndexBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentIndexBinding.inflate(inflater, container, false).let {
            binding = it
            binding.composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            binding.root
        }
    }

    override fun onStart() {
        super.onStart()
        binding.composeView.setContent {
            CryptoTheme {
                IndexPage(findNavController())
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = IndexFragment()
    }
}