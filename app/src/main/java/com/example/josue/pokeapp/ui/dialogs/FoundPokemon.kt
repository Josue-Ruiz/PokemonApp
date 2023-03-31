package com.example.josue.pokeapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.josue.pokeapp.R
import com.example.josue.pokeapp.databinding.DialogFoundPokemonBinding

class FoundPokemon() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.dialog_found_pokemon,null)
        builder.setView(view)

        val dialog = builder.create()
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
         return  dialog
    }
}
