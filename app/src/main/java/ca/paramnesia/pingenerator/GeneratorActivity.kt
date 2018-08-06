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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generator)
        viewModel = ViewModelProviders.of(this).get(GeneratorViewModel::class.java)

        lengthText.text = lengthSeekBar.progress.toString()
        lengthSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(bar: SeekBar?, value: Int, fromUser: Boolean) {
                lengthText.text = value.toString()
            }

            override fun onStartTrackingTouch(bar: SeekBar?) {}
            override fun onStopTrackingTouch(bar: SeekBar?) {}
        })

        generateButton.setOnClickListener { viewModel.requestNewPin(lengthSeekBar.progress) }

        viewModel.pin.observe(this, Observer { pin ->
            pinText.text = pin?.toString()
        })
    }
}
