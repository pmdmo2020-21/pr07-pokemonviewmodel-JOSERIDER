package es.iessaladillo.pedrojoya.intents.ui.battle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.intents.data.local.DataSource
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.utils.Event
import kotlin.math.max

private const val STATE_OPPONENT_ONE = "STATE_OPPONENT_ONE"
private const val STATE_OPPONENT_TWO = "STATE_OPPONENT_TWO"

class BattleViewModel(
    repository: DataSource,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _opponentOne: MutableLiveData<Pokemon> = savedStateHandle.getLiveData(
        STATE_OPPONENT_ONE,repository.getRandomPokemon()
    )
    val opponentOne: LiveData<Pokemon>
        get() = _opponentOne


    private val _opponentTwo: MutableLiveData<Pokemon> = savedStateHandle.getLiveData(
        STATE_OPPONENT_TWO, repository.getRandomPokemon()
    )
    val opponentTwo: LiveData<Pokemon>
        get() = _opponentTwo

    //Events
    private val _onNavigateSelection: MutableLiveData<Event<Pokemon>> = MutableLiveData()
    val onNavigateSelection: LiveData<Event<Pokemon>>
        get() = _onNavigateSelection

    private val _onStartFight: MutableLiveData<Event<Pokemon>> = MutableLiveData()
    val onStartFight: LiveData<Event<Pokemon>>
        get() = _onStartFight

    fun updateOpponents(pokemon: Pokemon, pokemonToUpdate: Int) {
        if (pokemonToUpdate == 1) {
            _opponentOne.value = pokemon
        } else {
            _opponentTwo.value = pokemon
        }
    }

    fun navigateToPokemonSelection(pokemonToUpdate: Int) {
        _onNavigateSelection.value =
            Event(if (pokemonToUpdate == 1) opponentOne.value!! else opponentTwo.value!!)
    }

    fun startFight() {
        val maxPower = max(opponentOne.value?.power!!, opponentTwo.value?.power!!)
        val pokemonWinner =
            (if (opponentOne.value?.power!! == maxPower) opponentOne.value else opponentTwo.value) as Pokemon
        _onStartFight.value = Event(pokemonWinner)
    }


}