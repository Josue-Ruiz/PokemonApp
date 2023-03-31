package com.example.josue.pokeapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.josue.pokeapp.core.PicassoUtil
import com.example.josue.pokeapp.databinding.ActivityMainBinding
import com.example.josue.pokeapp.ui.dialogs.FoundPokemon
import com.example.josue.pokeapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialog: FoundPokemon
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var picassoloader: PicassoUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dialog = FoundPokemon()

        viewModel = ViewModelProvider(this).get()

        viewModel.pokemon.observe(this) {
            binding.tvPkemonId.text = "# ${it.id}"
            binding.tvPkemonWeight.text = "${it.weight}"
            binding.tvPkemonName.text = "${it.name}"
            picassoloader.loadImage(it.urlImage, binding.ivPokemon)
        }

        viewModel.dialogState.observe(this) {
            if (it) {
                dialog.show(supportFragmentManager, "New Pokemon")
                return@observe
            }
            dialog.dismiss()
        }

        binding.button.setOnClickListener {
            viewModel.getRandomPokemon()
        }
    }

}