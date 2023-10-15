package com.example.josue.pokeapp.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.josue.pokeapp.R
import com.example.josue.pokeapp.core.Extensions.setNameReplace
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
    lateinit var picassoLoader: PicassoUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dialog = FoundPokemon()

        viewModel = ViewModelProvider(this).get()

        viewModel.pokemon.observe(this) {
            val id = getString(R.string.id_pokemon, it.id)
            val weight = getString(R.string.weight_pokemon, it.weight)
            binding.tvPkemonId.text = id
            binding.tvPkemonWeight.text = weight
            binding.tvPkemonName.text = it.name.setNameReplace()
            picassoLoader.loadImage(it.urlImage, binding.ivPokemon)
        }

        viewModel.dialogState.observe(this) {
            showDialog(it)
        }

        binding.button.setOnClickListener {
            viewModel.getRandomPokemon()
        }

        getCurrentLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (checkPermissions()) {

            if (isLocationEnabled()) {
                startLocationUpdate()
            } else {
                // setting open here
                Toast.makeText(this, "GPS turn off", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        } else {
            requestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Concede Los permisos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startLocationUpdate() {
        viewModel.getLocationData().observe(this) {
            viewModel.setLocationData(it.latitude, it.longitude)
        }
    }

    private fun showDialog(state: Boolean) {
        if (!dialog.isAdded) {
            if (state) {
                dialog.show(supportFragmentManager, "New Pokemon")
                return
            }
            dialog.dismiss()
        }else{

            dialog.dismiss()
        }
    }

}
