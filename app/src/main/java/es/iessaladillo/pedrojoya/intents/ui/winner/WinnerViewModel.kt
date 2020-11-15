package es.iessaladillo.pedrojoya.intents.ui.winner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon

class WinnerViewModel:ViewModel() {

    private val _pokemonWinner:MutableLiveData<Pokemon> = MutableLiveData()
    val pokemonWinner:LiveData<Pokemon>
    get() = _pokemonWinner


    fun setPokemonWinner(pokemon: Pokemon){
        _pokemonWinner.value = pokemon
    }
}