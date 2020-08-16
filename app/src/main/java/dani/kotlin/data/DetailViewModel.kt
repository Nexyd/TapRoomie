package dani.kotlin.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DetailViewModel: ViewModel() {
    companion object {
        var info = MutableLiveData<BeerInfo>()
    }
}