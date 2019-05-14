package com.carreseller.ui.search.builtdate

import com.carreseller.domain.BuiltDate
import com.carreseller.domain.MainCarType
import com.carreseller.domain.Manufacturer
import com.carreseller.ui.BasePresenter
import com.carreseller.ui.BaseView

interface BuiltDateSearchContract {

    interface Presenter: BasePresenter {
        fun loadData()

        fun setManufacturer(manufacturer: Manufacturer)

        fun setMainCarType(mainCarType: MainCarType)

        fun openSummary(builtDate: BuiltDate)
    }

    interface View: BaseView {
        fun addBuiltDates(list: List<BuiltDate>)

        fun openSummaryScreen(manufacturer: Manufacturer, mainCarType: MainCarType, builtDate: BuiltDate)

        fun showRetryError(retry: () -> Unit)
    }
}
