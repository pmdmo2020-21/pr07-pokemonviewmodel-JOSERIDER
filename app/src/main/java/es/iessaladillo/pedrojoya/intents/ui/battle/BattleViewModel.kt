package es.iessaladillo.pedrojoya.intents.ui.battle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.intents.data.local.DataSource
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon

class BattleViewModel(private val repository: DataSource) : ViewModel() {

    private val _opponentOne: MutableLiveData<Pokemon> = MutableLiveData()
    val opponentOne: LiveData<Pokemon>
        get() = _opponentOne

    private val _opponentTwo: MutableLiveData<Pokemon> = MutableLiveData()
    val opponentTwo: LiveData<Pokemon>
        get() = _opponentTwo


    init {
        _opponentOne.value = repository.getRandomPokemon()
        _opponentTwo.value = repository.getRandomPokemon()
    }


    fun changeOpponentOne(pokemonId: Long, pokemonToUpdate: Int) {
        if (pokemonToUpdate == 1) {
            _opponentOne.value = repository.getPokemonById(pokemonId)
        } else {
            _opponentTwo.value = repository.getPokemonById(pokemonId)
        }
    }


    fun changeOpponentTwo(pokemonId: Long) {
        _opponentTwo.value = repository.getPokemonById(pokemonId)
    }

    fun startFight() {
        //TODO:
    }


}