package com.carreseller.ui.search.builtdate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.carreseller.R
import com.carreseller.domain.BuiltDate
import com.carreseller.domain.MainCarType
import com.carreseller.domain.Manufacturer
import com.carreseller.ui.search.PaginationRecyclerViewAdapter
import com.carreseller.ui.summary.SummaryActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search_built_date.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class BuiltDateSearchActivity : AppCompatActivity(),
    BuiltDateSearchContract.View {

    private val presenter: BuiltDateSearchContract.Presenter by inject { parametersOf(this) }

    private lateinit var recyclerView: RecyclerView

    private val adapter = PaginationRecyclerViewAdapter(this, ::onBuiltDateClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_built_date)
        supportActionBar?.title = getString(R.string.title_built_date)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val manufacturer: Manufacturer = intent.getParcelableExtra(ARGUMENT_MANUFACTURER)
        val mainCarType: MainCarType = intent.getParcelableExtra(ARGUMENT_MAIN_CAR_TYPE)
        
        initView(manufacturer, mainCarType)
        presenter.setManufacturer(manufacturer)
        presenter.setMainCarType(mainCarType)
        presenter.onStart()
    }

    private fun initView(manufacturer: Manufacturer, mainCarType: MainCarType) {
        tv_breadcumb_manufacturer.text = manufacturer.name
        tv_breadcumb_main_car_type.text = mainCarType.name
        recyclerView = rv_built_date
        recyclerView.adapter = adapter
    }

    override fun addBuiltDates(list: List<BuiltDate>) {
        adapter.addData(list)
    }

    override fun showLoading(showHide: Boolean) {
        recyclerView.post {
            adapter.enableLoading(showHide)
        }
    }

    override fun showRetryError(retry: () -> Unit) {
        Snackbar.make(recyclerView, getString(R.string.error_message), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.error_retry)) { retry() }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onFinish()
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

    override fun openSummaryScreen(manufacturer: Manufacturer, mainCarType: MainCarType, builtDate: BuiltDate) {
        startActivity(SummaryActivity.getIntent(this, manufacturer, mainCarType, builtDate))
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    private fun onBuiltDateClicked(builtDate: Any) {
        presenter.openSummary(builtDate as BuiltDate)
    }

    companion object {
        private const val ARGUMENT_MANUFACTURER = "ARGUMENT_MANUFACTURER"
        private const val ARGUMENT_MAIN_CAR_TYPE = "ARGUMENT_MAIN_CAR_TYPE"

        fun getIntent(context: Context, manufacturer: Manufacturer, mainCarType: MainCarType): Intent {
            return Intent(context, BuiltDateSearchActivity::class.java)
                .putExtra(ARGUMENT_MANUFACTURER, manufacturer)
                .putExtra(ARGUMENT_MAIN_CAR_TYPE, mainCarType)
        }
    }
}
