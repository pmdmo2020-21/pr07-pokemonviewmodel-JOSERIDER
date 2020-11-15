package es.iessaladillo.pedrojoya.intents.ui.selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.SelectionActivityBinding
import es.iessaladillo.pedrojoya.intents.utils.EXTRA_POKEMON
import es.iessaladillo.pedrojoya.intents.utils.requireParcelableExtra


class SelectionActivity : AppCompatActivity() {

    private val binding: SelectionActivityBinding by lazy {
        SelectionActivityBinding.inflate(layoutInflater)
    }

    private val viewModel: SelectionViewModel by viewModels {
        SelectionViewModelFactory(Database)
    }

    private val radioButtonList: List<RadioButton> by lazy {
        listOf(
            binding.selectionRdbName1,
            binding.selectionRdbName2,
            binding.selectionRdbName3,
            binding.selectionRdbName4,
            binding.selectionRdbName5,
            binding.selectionRdbName6,
        )
    }

    private val imgViewList: List<ImageView> by lazy {
        listOf(
            binding.selectionImg1,
            binding.selectionImg2,
            binding.selectionImg3,
            binding.selectionImg4,
            binding.selectionImg5,
            binding.selectionImg6,
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getIntentData()
        observeLiveData()
    }


    override fun onPause() {
        radioButtonList.forEach {
            it.setOnCheckedChangeListener (null)
        }
        super.onPause()
    }
    private fun getIntentData() {
        viewModel.changePokemon(
            intent.requireParcelableExtra(EXTRA_POKEMON) as Pokemon
        )
    }

    private fun observeLiveData() {
        viewModel.pokemons.observe(this) { pokemonList ->
            setupPokemons(pokemonList)
        }

        viewModel.currentPokemon.observe(this) { pokemon ->
            updatePokemonSelected(pokemon)
        }
    }

    private fun setupPokemons(pokemonList: List<Pokemon>) {
        for ((i, pokemon) in pokemonList.withIndex()) {
            setupImageView(imgViewList[i], pokemon)
            setupRadioButton(radioButtonList[i], pokemon)
        }
    }


    private fun setupImageView(imgView: ImageView, pokemon: Pokemon) {
        with(imgView) {
            setImageResource(pokemon.imageResId)
            tag = pokemon
            setOnClickListener { viewModel.changePokemon(pokemon) }
        }
    }

    private fun setupRadioButton(radioButton: RadioButton, pokemon: Pokemon) {
        with(radioButton) {
            text = pokemon.name
            tag = pokemon
            if (pokemon.id == viewModel.currentPokemon.value?.id) isChecked = true
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && (tag as Pokemon) != viewModel.currentPokemon.value) viewModel.changePokemon(
                    tag as Pokemon
                )
            }
        }
    }

    private fun updatePokemonSelected(pokemon: Pokemon) {
        radioButtonList.forEach { it.isChecked = false }
        radioButtonList.find { rb -> (rb.tag as Pokemon).id == pokemon.id }
            ?.isChecked = true
    }

    override fun onBackPressed() {
        setActivityResult(viewModel.currentPokemon.value!!)
        super.onBackPressed()
    }

    private fun setActivityResult(pokemon: Pokemon) {
        val intent = Intent()
            .putExtra(EXTRA_POKEMON, pokemon)
        setResult(RESULT_OK, intent)
    }


    companion object {

        fun newIntent(context: Context, pokemon: Pokemon): Intent =
            Intent(context, SelectionActivity::class.java)
                .putExtra(EXTRA_POKEMON, pokemon)
    }
}