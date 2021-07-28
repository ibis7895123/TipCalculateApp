package com.example.tipcalculateapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculateapp.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {
        // 入力したサービス料金を取得
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        if (cost == null) {
            Log.d("###", "Cost is null.")
            displayTip(0.0)
            return
        }

        // ラジオボタンでチェックしたパーセントを取得
        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // チップの額
        var tip = tipPercentage * cost

        // 切り上げするかどうか
        val isRoundUp = binding.roundUpSwitch.isChecked

        // 切り上げする場合はチップを再計算
        tip = if (isRoundUp) ceil(tip) else tip

        // チップの値を更新
        displayTip(tip)
    }

    private fun displayTip(tip : Double) {
        // システム言語に合わせた通貨表記に変換
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        // UIテキストにチップの値を反映
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

}