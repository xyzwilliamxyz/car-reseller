package com.carreseller.ui.search.builtdate

import com.carreseller.domain.BuiltDate
import com.carreseller.domain.BuiltDateList
import com.carreseller.domain.MainCarType
import com.carreseller.domain.Manufacturer
import com.carreseller.gateways.remote.callback.ApiCallback
import com.carreseller.interactors.GetBuiltDates

class BuiltDateSearchPresenter(private val view: BuiltDateSearchContract.View,
                               private val getBuiltDates: GetBuiltDates) : BuiltDateSearchContract.Presenter {

    private lateinit var manufacturer: Manufacturer
    private lateinit var mainCarType: MainCarType

    override fun setManufacturer(manufacturer: Manufacturer) {
        this.manufacturer = manufacturer
    }

    override fun setMainCarType(mainCarType: MainCarType) {
        this.mainCarType = mainCarType
    }

    override fun onStart() {
        loadData()
    }

    override fun onFinish() {
        getBuiltDates.dispose()
    }

    override fun loadData() {
        view.showLoading(true)
        getBuiltDates.execute(manufacturer.manufacturerId, mainCarType.name, GetBuiltDatesApiCallback())
    }

    override fun openSummary(builtDate: BuiltDate) {
        view.openSummaryScreen(manufacturer, mainCarType, builtDate)
    }

    inner class GetBuiltDatesApiCallback: ApiCallback<BuiltDateList> {
        override fun onSuccess(result: BuiltDateList) {
            view.addBuiltDates(result.list)
        }

        override fun onError() {
            view.showRetryError {
                loadData()
            }
        }

        override fun onTerminate() {
            view.showLoading(false)
        }
    }
}
