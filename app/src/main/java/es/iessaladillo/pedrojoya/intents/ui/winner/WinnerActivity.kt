package es.iessaladillo.pedrojoya.intents.ui.winner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.WinnerActivityBinding
import es.iessaladillo.pedrojoya.intents.utils.EXTRA_POKEMON
import es.iessaladillo.pedrojoya.intents.utils.requireParcelableExtra

class WinnerActivity : AppCompatActivity() {

    private val viewModel: WinnerViewModel by viewModels()

    private val binding: WinnerActivityBinding by lazy {
        WinnerActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getIntentData()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.pokemonWinner.observe(this){
            showWinner(it)
        }
    }

    private fun getIntentData() {
        val pokemon = intent.requireParcelableExtra(EXTRA_POKEMON)
        viewModel.setPokemonWinner(pokemon as Pokemon)
    }

    private fun showWinner(winner:Pokemon) {
        with(binding) {
            winnerImgPokemon.setImageResource(winner.imageResId)
            winnerLblPokemonName.text = winner.name
        }
    }

    companion object {
        fun newIntent(context: Context, pokemon: Pokemon): Intent =
            Intent(context, WinnerActivity::class.java).putExtra(EXTRA_POKEMON, pokemon)
    }
}