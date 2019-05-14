package com.carreseller.ui.search.maincartype

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carreseller.R
import com.carreseller.domain.MainCarType
import com.carreseller.domain.Manufacturer
import com.carreseller.ui.search.PaginationRecyclerViewAdapter
import com.carreseller.ui.search.builtdate.BuiltDateSearchActivity
import com.carreseller.utils.EndlessRecyclerOnScrollListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search_main_car_type.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class MainCarTypeSearchActivity : AppCompatActivity(),
    MainCarTypeSearchContract.View {

    private val presenter: MainCarTypeSearchContract.Presenter by inject { parametersOf(this) }

    private lateinit var recyclerView: RecyclerView

    private val adapter = PaginationRecyclerViewAdapter(this, ::onMainCarTypeClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_main_car_type)
        supportActionBar?.title = getString(R.string.title_main_car_type)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val manufacturer: Manufacturer = intent.getParcelableExtra(ARGUMENT_MANUFACTURER)
        initView(manufacturer)
        presenter.setManufacturer(manufacturer)
        presenter.onStart()
    }

    private fun initView(manufacturer: Manufacturer) {
        tv_breadcumb_manufacturer.text = manufacturer.name
        recyclerView = rv_main_car_type
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener(
            recyclerView.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(currentPage: Int) {
                presenter.loadPage()
            }
        })
    }

    override fun addMainCarTypes(list: List<MainCarType>) {
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

    override fun openBuiltDateScreen(manufacturer: Manufacturer, mainCarType: MainCarType) {
        startActivity(BuiltDateSearchActivity.getIntent(this, manufacturer, mainCarType))
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    private fun onMainCarTypeClicked(mainCarType: Any) {
        presenter.openBuiltDate(mainCarType as MainCarType)
    }

    companion object {
        private const val ARGUMENT_MANUFACTURER = "ARGUMENT_MANUFACTURER"

        fun getIntent(context: Context, manufacturer: Manufacturer): Intent {
            return Intent(context, MainCarTypeSearchActivity::class.java)
                .putExtra(ARGUMENT_MANUFACTURER, manufacturer)
        }
    }
}
