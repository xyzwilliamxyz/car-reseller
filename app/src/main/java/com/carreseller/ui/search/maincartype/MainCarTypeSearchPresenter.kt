package com.carreseller.ui.search.maincartype

import com.carreseller.domain.MainCarType
import com.carreseller.domain.MainCarTypeList
import com.carreseller.domain.Manufacturer
import com.carreseller.gateways.remote.callback.ApiCallback
import com.carreseller.interactors.GetMainCarTypes
import com.carreseller.utils.Constants

class MainCarTypeSearchPresenter(private val view: MainCarTypeSearchContract.View,
                                 private val getMainCarTypes: GetMainCarTypes) :
    MainCarTypeSearchContract.Presenter {

    private var pageCount = Constants.NO_PAGE_COUNT_DEFINED
    private var currentPage = Constants.FIRST_PAGE

    private lateinit var manufacturer: Manufacturer

    override fun setManufacturer(manufacturer: Manufacturer) {
        this.manufacturer = manufacturer
    }

    override fun onStart() {
        loadPage()
    }

    override fun onFinish() {
        getMainCarTypes.dispose()
    }

    override fun loadPage() {
        if (pageCount == Constants.NO_PAGE_COUNT_DEFINED || currentPage < pageCount) {
            view.showLoading(true)
            getMainCarTypes.execute(currentPage, Constants.PAGE_SIZE, manufacturer.manufacturerId,
                GetMainCarTypesApiCallback())
        }
    }

    override fun openBuiltDate(mainCarType: MainCarType) {
        view.openBuiltDateScreen(manufacturer, mainCarType)
    }

    inner class GetMainCarTypesApiCallback: ApiCallback<MainCarTypeList> {
        override fun onSuccess(result: MainCarTypeList) {
            currentPage++
            if (pageCount == Constants.NO_PAGE_COUNT_DEFINED) {
                pageCount = result.totalPageCount
            }

            view.addMainCarTypes(result.list)
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
