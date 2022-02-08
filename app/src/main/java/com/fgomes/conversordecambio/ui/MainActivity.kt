package com.fgomes.conversordecambio.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import com.fgomes.conversordecambio.R
import com.fgomes.conversordecambio.core.extensions.*
import com.fgomes.conversordecambio.data.model.Coin
import com.fgomes.conversordecambio.databinding.ActivityMainBinding
import com.fgomes.conversordecambio.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private val dialog by lazy { createProgressDialog() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindAdapters()
        bindListiners()
        bindObserve()

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_history) {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindListiners() {
        binding.tilValor.editText?.doAfterTextChanged {
            binding.btnCalc.isEnabled = it != null && it.toString().isNotEmpty()
            binding.btnSave.isEnabled = false
        }
        binding.btnCalc.setOnClickListener {
            it.hideSoftKeyboard()
            val search = "${binding.tilFrom.text}-${binding.tilTo.text}"

            viewModel.getExchangeValue(search)
        }

        binding.btnSave.setOnClickListener {
            val value = viewModel.state.value
            (value as MainViewModel.State.Success)?.let {
                val exchange = it.exchange.copy(bid = it.exchange.bid * binding.tilValor.text.toDouble())
                viewModel.saveExchange(exchange)
            }
        }
    }

    private fun bindAdapters() {
        val list = Coin.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        binding.tvFrom.setAdapter(adapter)
        binding.tvTo.setAdapter(adapter)

        binding.tvFrom.setText(Coin.BRL.name, false)
        binding.tvTo.setText(Coin.USD.name, false)
    }

    private fun bindObserve() {
        viewModel.state.observe(this) {
            when (it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog { setMessage(it.throwable.message) }.show()
                }
                is MainViewModel.State.Success -> stateSuccess(it)
                MainViewModel.State.Saved -> {
                    dialog.dismiss()
                    createDialog { setMessage("Item salvo") }.show()
                }
            }
        }
    }

    private fun stateSuccess(it: MainViewModel.State.Success) {
        dialog.dismiss()
        binding.btnSave.isEnabled = true

        val selectCoin = binding.tilTo.text
        val coin = Coin.values().find { it.name == selectCoin } ?: Coin.BRL
        val result = it.exchange.bid * binding.tilValor.text.toDouble()
        binding.tvResult.text = result.formatCurrency(coin.locale)
    }

}