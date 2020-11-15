package es.iessaladillo.pedrojoya.intents.ui.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.intents.data.local.DataSource

@Suppress("UNCHECKED_CAST")
class SelectionViewModelFactory(private val respository:DataSource):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = SelectionViewModel(respository) as T
}