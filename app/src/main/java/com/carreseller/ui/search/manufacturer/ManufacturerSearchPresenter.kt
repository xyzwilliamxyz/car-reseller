package com.carreseller.ui.search.manufacturer

import com.carreseller.domain.Manufacturer
import com.carreseller.domain.ManufacturerList
import com.carreseller.gateways.remote.callback.ApiCallback
import com.carreseller.interactors.GetManufacturers
import com.carreseller.utils.Constants

class ManufacturerSearchPresenter(private val view: ManufacturerSearchContract.View,
                                  private val getManufacturers: GetManufacturers) :
    ManufacturerSearchContract.Presenter {

    private var pageCount = Constants.NO_PAGE_COUNT_DEFINED
    private var currentPage = Constants.FIRST_PAGE

    override fun onStart() {
        loadPage()
    }

    override fun onFinish() {
        getManufacturers.dispose()
    }

    override fun openMainCarTypeSearch(manufacturer: Manufacturer) {
        view.openMainCarTypeSearchScreen(manufacturer)
    }

    override fun loadPage() {
        if (pageCount == Constants.NO_PAGE_COUNT_DEFINED || currentPage < pageCount) {
            view.showLoading(true)
            getManufacturers.execute(currentPage, Constants.PAGE_SIZE, GetManufacturersApiCallback())
        }
    }

    inner class GetManufacturersApiCallback: ApiCallback<ManufacturerList> {
        override fun onSuccess(result: ManufacturerList) {
            currentPage++
            if (pageCount == Constants.NO_PAGE_COUNT_DEFINED) {
                pageCount = result.totalPageCount
            }

            view.addManufacturers(result.list)
        }

        override fun onError() {
            view.showRetryError {
                loadPage()
            }
        }

        override fun onTerminate() {
            view.showLoading(false)
        }
    }
}
