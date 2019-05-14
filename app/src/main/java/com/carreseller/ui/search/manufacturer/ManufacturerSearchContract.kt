package com.carreseller.ui.search.manufacturer

import com.carreseller.domain.Manufacturer
import com.carreseller.gateways.remote.callback.ApiCallback
import com.carreseller.ui.BasePresenter
import com.carreseller.ui.BaseView

interface ManufacturerSearchContract {

    interface Presenter: BasePresenter {
        fun loadPage()

        fun openMainCarTypeSearch(manufacturer: Manufacturer)
    }

    interface View: BaseView {
        fun addManufacturers(list: List<Manufacturer>)

        fun openMainCarTypeSearchScreen(manufacturer: Manufacturer)

        fun showRetryError(retry: () -> Unit)
    }
}
