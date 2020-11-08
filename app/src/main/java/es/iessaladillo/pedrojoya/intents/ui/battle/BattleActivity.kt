package es.iessaladillo.pedrojoya.intents.ui.battle

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.Fight
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.EXTRA_POKEMON_ID
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity
import es.iessaladillo.pedrojoya.intents.ui.winner.WinnerActivity

const val RC_POKEMON_SELECTION: Int = 1;

class BattleActivity : AppCompatActivity() {


    private val viewModel: BattleViewModel by viewModels {
        BattleViewModelFactory(Database)
    }

    private lateinit var opponentOne: Pokemon
    private lateinit var opponentTwo: Pokemon

    private val binding: BattleActivityBinding by lazy {
        BattleActivityBinding.inflate(layoutInflater)
    }

    /**
     * represents the opponent to be updated
     */
    private var pokemonToUpdate: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeLiveData()
        setupListeners()
    }

    private fun observeLiveData() {
        viewModel.opponentOne.observe(this) {
            opponentOne = it
            showPokemon(
                binding.battleImgOpponentOne,
                binding.battleLblOpponentOne,
                opponentOne,
            )
        }

        viewModel.opponentTwo.observe(this) {
            opponentTwo = it
            showPokemon(
                binding.battleImgOpponentTwo,
                binding.battleLblOpponentTwo,
                opponentTwo,
            )
        }
    }

    private fun setupListeners() {
        binding.run {
            battleBtnStartFight.setOnClickListener { startFight() }
            battleLayoutOpponentOne.setOnClickListener {
                navigateToPokemonSelection(
                    opponentOne.id,
                    1
                )
            }
            battleLayoutOpponentTwo.setOnClickListener {
                navigateToPokemonSelection(
                    opponentTwo.id,
                    2
                )
            }
        }
    }

    private fun navigateToPokemonSelection(id: Long, pokemonToSelect: Int) {
        this.pokemonToUpdate = pokemonToSelect
        val intent = SelectionActivity.newIntent(this, id)
        startActivityForResult(intent, RC_POKEMON_SELECTION)
    }

    private fun startFight() {
        val fight = Fight(opponentOne, opponentTwo)
        val winningOpponent = fight.startFight()
        startActivity(WinnerActivity.newIntent(this, winningOpponent.id))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == RC_POKEMON_SELECTION && data != null) {
            getResult(data)
        }
    }

    private fun getResult(intent: Intent) {
        if (!intent.hasExtra(EXTRA_POKEMON_ID)) throw RuntimeException("Intent is not valid, must have  EXTRA_POKEMON_ID")
        updatePokemon(intent.getLongExtra(EXTRA_POKEMON_ID, 0))
    }

    private fun updatePokemon(pokemonId: Long) {
        viewModel.changeOpponentOne(pokemonId, pokemonToUpdate)
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