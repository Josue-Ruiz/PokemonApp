package com.example.josue.pokeapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.josue.pokeapp.R
import com.example.josue.pokeapp.core.PicassoUtil
import com.example.josue.pokeapp.databinding.ActivityMainBinding
import com.example.josue.pokeapp.ui.dialogs.FoundPokemon
import com.example.josue.pokeapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
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
            val id = getString(R.string.id_pokemon,it.id)
            val weight = getString(R.string.weight_pokemon,it.weight)
            binding.tvPkemonId.text = id
            binding.tvPkemonWeight.text = weight
            binding.tvPkemonName.text = it.name
            picassoloader.loadImage(it.urlImage, binding.ivPokemon)
        }

        viewModel.dialogState.observe(this) {
                showDialog(it)
        }

        binding.button.setOnClickListener {
            viewModel.getRandomPokemon()
        }
    }

    private fun showDialog(state : Boolean){
        if (state) {
            if (!dialog.isAdded){
                dialog.show(supportFragmentManager, "New Pokemon")
                return
            }
            return
        }
        dialog.dismiss()
    }

}