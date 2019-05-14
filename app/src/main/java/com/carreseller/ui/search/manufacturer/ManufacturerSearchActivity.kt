package com.carreseller.ui.search.manufacturer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carreseller.R
import com.carreseller.domain.Manufacturer
import com.carreseller.ui.search.PaginationRecyclerViewAdapter
import com.carreseller.ui.search.maincartype.MainCarTypeSearchActivity
import com.carreseller.utils.EndlessRecyclerOnScrollListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search_manufacturer.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class ManufacturerSearchActivity : AppCompatActivity(), ManufacturerSearchContract.View {

    private val presenter: ManufacturerSearchContract.Presenter by inject { parametersOf(this) }

    private lateinit var recyclerView: RecyclerView

    private val adapter = PaginationRecyclerViewAdapter(this, ::onManufacturerClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_manufacturer)
        supportActionBar?.title = getString(R.string.title_manufacturer)

        initView()
        presenter.onStart()
    }

    private fun initView() {
        recyclerView = rv_manufacturers
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener(recyclerView.layoutManager
                as LinearLayoutManager) {
            override fun onLoadMore(currentPage: Int) {
                presenter.loadPage()
            }
        })
    }

    private fun onManufacturerClicked(manufacturer: Any) {
        presenter.openMainCarTypeSearch(manufacturer as Manufacturer)
    }

    override fun addManufacturers(list: List<Manufacturer>) {
        adapter.addData(list)
    }

    override fun openMainCarTypeSearchScreen(manufacturer: Manufacturer) {
        startActivity(MainCarTypeSearchActivity.getIntent(this, manufacturer))
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
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

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ManufacturerSearchActivity::class.java)
        }
    }
}
