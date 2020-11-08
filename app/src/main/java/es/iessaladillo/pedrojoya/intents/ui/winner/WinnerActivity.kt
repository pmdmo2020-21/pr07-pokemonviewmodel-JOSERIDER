package es.iessaladillo.pedrojoya.intents.ui.winner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.DataSource
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.WinnerActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.EXTRA_POKEMON_ID

class WinnerActivity : AppCompatActivity() {

    private val pokemonDB: DataSource = Database

    private val winnerOpponent: Pokemon by lazy {
        getIntentData()
    }
    private val binding: WinnerActivityBinding by lazy {
        WinnerActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }

    private fun getIntentData(): Pokemon {
        if (intent == null || !intent.hasExtra(EXTRA_POKEMON_ID)) throw RuntimeException("Intent is not valid, must have  EXTRA_POKEMON_ID")
        val pokemonId = intent.getLongExtra(EXTRA_POKEMON_ID, -1)
        return pokemonDB.getPokemonById(pokemonId)!!
    }

    private fun setupViews() {
        showWinner()
    }

    private fun showWinner() {
       with(binding){
            winnerImgPokemon.setImageResource(winnerOpponent.imageResId)
            winnerLblPokemonName.text = winnerOpponent.name
        }
    }

    companion object {
        fun newIntent(context: Context, pokemonId: Long): Intent =
                Intent(context, WinnerActivity::class.java).putExtra(EXTRA_POKEMON_ID, pokemonId)
    }
}