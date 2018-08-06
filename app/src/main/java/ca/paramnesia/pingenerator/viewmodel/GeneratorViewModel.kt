package ca.paramnesia.pingenerator.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ca.paramnesia.pingenerator.pin.PIN
import ca.paramnesia.pingenerator.pin.PinFactory

class GeneratorViewModel : ViewModel() {
    private val pinFactory: PinFactory = PinFactory()
    private val pinMutable: MutableLiveData<PIN> = MutableLiveData()

    val pin: LiveData<PIN>
        get() = pinMutable

    fun requestNewPin(length: Int) {
        pinMutable.value = pinFactory.generatePin(length)
    }
}
