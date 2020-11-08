package es.iessaladillo.pedrojoya.intents.ui.battle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.intents.data.local.DataSource


@Suppress("UNCHECKED_CAST")
class BattleViewModelFactory(private val repository:DataSource):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        BattleViewModel(repository) as T
}