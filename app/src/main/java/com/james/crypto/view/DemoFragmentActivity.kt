package com.james.crypto.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.james.crypto.databinding.ActivityDemoFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoFragmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}