package es.iessaladillo.pedrojoya.intents.ui.battle

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity
import es.iessaladillo.pedrojoya.intents.ui.winner.WinnerActivity
import es.iessaladillo.pedrojoya.intents.utils.EXTRA_POKEMON
import es.iessaladillo.pedrojoya.intents.utils.observeEvent
import es.iessaladillo.pedrojoya.intents.utils.requireParcelableExtra

class BattleActivity : AppCompatActivity() {

    private val viewModel: BattleViewModel by viewModels {
        BattleViewModelFactory(Database, this)
    }

    private val binding: BattleActivityBinding by lazy {
        BattleActivityBinding.inflate(layoutInflater)
    }

    /**
     * represents the opponent to be updated
     */
    private var pokemonToUpdate: Int = 0

    private val pokemonSelectionCall: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val intentResult = result.data
            if (result.resultCode == RESULT_OK && intentResult != null) {
               updatePokemon(intentResult.requireParcelableExtra(EXTRA_POKEMON) as Pokemon)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeLiveData()
        observeEvents()
        setListeners()
    }

    private fun observeLiveData() {
        observePokemonLiveData()
    }

    private fun observeEvents() {
        viewModel.onNavigateSelection.observeEvent(this) { pokemon ->
            pokemonSelectionCall.launch(SelectionActivity.newIntent(this, pokemon))
        }

        viewModel.onStartFight.observeEvent(this) { pokemon ->
            startActivity(WinnerActivity.newIntent(this, pokemon))
        }
    }

    private fun setListeners() {
        binding.run {
            battleBtnStartFight.setOnClickListener { viewModel.startFight() }
            battleLayoutOpponentOne.setOnClickListener {
                pokemonToUpdate = 1
                viewModel.navigateToPokemonSelection(pokemonToUpdate)
            }
            battleLayoutOpponentTwo.setOnClickListener {
                pokemonToUpdate = 2
                viewModel.navigateToPokemonSelection(pokemonToUpdate)
            }
        }
    }

    private fun updatePokemon(pokemon: Pokemon) {
        viewModel.updateOpponents(pokemon, pokemonToUpdate)
    }

    private fun observePokemonLiveData() {
        viewModel.opponentOne.observe(this) { pokemon ->
            showPokemon(
                binding.battleImgOpponentOne,
                binding.battleLblOpponentOne,
                pokemon,
            )
        }

        viewModel.opponentTwo.observe(this) { pokemon ->
            showPokemon(
                binding.battleImgOpponentTwo,
                binding.battleLblOpponentTwo,
                pokemon,
            )
        }
    }

    private fun showPokemon(
        pokemonImg: ImageView,
        pokemonName: TextView,
        pokemon: Pokemon,
    ) {
        pokemonImg.setImageResource(pokemon.imageResId)
        pokemonName.text = pokemon.name
    }


}