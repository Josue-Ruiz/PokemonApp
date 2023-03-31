package com.example.josue.pokeapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.josue.pokeapp.core.PicassoUtil
import com.example.josue.pokeapp.databinding.ActivityMainBinding
import com.example.josue.pokeapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var picassoloader: PicassoUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get()

        viewModel.pokemon.observe(this) {
            binding.pkemon.text = "#${it.id} ${it.name} ${it.weight}"
            picassoloader.loadImage(it.urlImage, binding.ivPokemon)
        }

        binding.button.setOnClickListener {
            viewModel.getRandomPokemon()
        }
    }
}