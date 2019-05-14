package com.carreseller.infrascructure.di


import com.carreseller.ui.search.builtdate.BuiltDateSearchContract
import com.carreseller.ui.search.builtdate.BuiltDateSearchPresenter
import com.carreseller.ui.search.maincartype.MainCarTypeSearchContract
import com.carreseller.ui.search.maincartype.MainCarTypeSearchPresenter
import com.carreseller.ui.search.manufacturer.ManufacturerSearchContract
import com.carreseller.ui.search.manufacturer.ManufacturerSearchPresenter
import org.koin.dsl.module

val presenterModule = module {
    factory<ManufacturerSearchContract.Presenter> { (view: ManufacturerSearchContract.View) ->
        ManufacturerSearchPresenter(view, get())
    }

    factory<MainCarTypeSearchContract.Presenter> { (view: MainCarTypeSearchContract.View) ->
        MainCarTypeSearchPresenter(view, get())
    }

    factory<BuiltDateSearchContract.Presenter> { (view: BuiltDateSearchContract.View) ->
        BuiltDateSearchPresenter(view, get())
    }
}
