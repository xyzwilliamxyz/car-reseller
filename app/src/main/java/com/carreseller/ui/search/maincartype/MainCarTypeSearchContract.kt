package com.carreseller.ui.search.maincartype

import com.carreseller.domain.MainCarType
import com.carreseller.domain.Manufacturer
import com.carreseller.ui.BasePresenter
import com.carreseller.ui.BaseView

interface MainCarTypeSearchContract {

    interface Presenter: BasePresenter {
        fun loadPage()

        fun setManufacturer(manufacturer: Manufacturer)

        fun openBuiltDate(mainCarType: MainCarType)
    }

    interface View: BaseView {
        fun addMainCarTypes(list: List<MainCarType>)

        fun openBuiltDateScreen(manufacturer: Manufacturer, mainCarType: MainCarType)

        fun showRetryError(retry: () -> Unit)
    }
}
