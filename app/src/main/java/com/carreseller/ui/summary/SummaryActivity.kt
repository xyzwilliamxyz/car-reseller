package com.carreseller.ui.summary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.carreseller.R
import com.carreseller.domain.BuiltDate
import com.carreseller.domain.MainCarType
import com.carreseller.domain.Manufacturer
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initView(intent.getParcelableExtra(ARGUMENT_MANUFACTURER), intent.getParcelableExtra(ARGUMENT_MAIN_CAR_TYPE),
            intent.getParcelableExtra(ARGUMENT_BUILT_DATE))
    }

    private fun initView(manufacturer: Manufacturer, mainCarType: MainCarType, builtDate: BuiltDate) {
        tv_manufacturer.text = manufacturer.name
        tv_main_car_type.text = mainCarType.name
        tv_built_date.text = builtDate.date
        aib_back.setOnClickListener {
            onSupportNavigateUp()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        return true
    }

    companion object {
        private const val ARGUMENT_MANUFACTURER = "ARGUMENT_MANUFACTURER"
        private const val ARGUMENT_MAIN_CAR_TYPE = "ARGUMENT_MAIN_CAR_TYPE"
        private const val ARGUMENT_BUILT_DATE = "ARGUMENT_BUILT_DATE"

        fun getIntent(context: Context, manufacturer: Manufacturer, mainCarType: MainCarType, builtDate: BuiltDate)
                : Intent {
            return Intent(context, SummaryActivity::class.java)
                .putExtra(ARGUMENT_MANUFACTURER, manufacturer)
                .putExtra(ARGUMENT_MAIN_CAR_TYPE, mainCarType)
                .putExtra(ARGUMENT_BUILT_DATE, builtDate)
        }
    }
}
