package es.iessaladillo.pedrojoya.intents.ui.battle

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import es.iessaladillo.pedrojoya.intents.data.local.DataSource


@Suppress("UNCHECKED_CAST")
class BattleViewModelFactory(private val repository:DataSource,
                             owner: SavedStateRegistryOwner,
                             defaultArgs: Bundle? = null):
    AbstractSavedStateViewModelFactory(owner,defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return BattleViewModel(repository,handle) as T
    }

}