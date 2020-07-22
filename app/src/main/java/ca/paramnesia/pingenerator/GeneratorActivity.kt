package ca.paramnesia.pingenerator

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import ca.paramnesia.pingenerator.viewmodel.GeneratorViewModel
import kotlinx.android.synthetic.main.activity_generator.*

class GeneratorActivity : AppCompatActivity() {
    private lateinit var viewModel: GeneratorViewModel

    private val minimumLength: Int = 3
    private val SeekBar.adjustedProgress: Int
        get() = progress + minimumLength

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generator)
        viewModel = ViewModelProviders.of(this).get(GeneratorViewModel::class.java)

        lengthText.text = lengthSeekBar.adjustedProgress.toString()
        lengthSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(bar: SeekBar?, value: Int, fromUser: Boolean) {
                lengthText.text = (value + minimumLength).toString()
            }

            override fun onStartTrackingTouch(bar: SeekBar?) {}
            override fun onStopTrackingTouch(bar: SeekBar?) {}
        })

        generateButton.setOnClickListener { viewModel.requestNewPin(lengthSeekBar.adjustedProgress) }

        viewModel.pin.observe(this, Observer { pin ->
            pinText.text = pin?.toString()
        })
    }
}
