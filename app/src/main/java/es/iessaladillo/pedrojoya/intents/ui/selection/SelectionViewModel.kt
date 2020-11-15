package es.iessaladillo.pedrojoya.intents.ui.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.intents.data.local.DataSource
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon

class SelectionViewModel(repository: DataSource) : ViewModel() {


    private val _pokemons: MutableLiveData<List<Pokemon>> = MutableLiveData(repository.getAllPokemons())
    val pokemons: LiveData<List<Pokemon>>
        get() = _pokemons

    private val _currentPokemon: MutableLiveData<Pokemon> = MutableLiveData()
    val currentPokemon: LiveData<Pokemon>
        get() = _currentPokemon


    fun changePokemon(pokemon: Pokemon){
        _currentPokemon.value = pokemon
    }
}