package com.carreseller.interactors

import com.carreseller.gateways.converters.ManufacturerConverter
import com.carreseller.gateways.remote.CarTypesApi
import com.carreseller.ui.search.manufacturer.ManufacturerSearchPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GetManufacturers constructor(private val carTypesApi: CarTypesApi) {

    private val disposables = CompositeDisposable()

    fun execute(page: Int, pageSize: Int, callback: ManufacturerSearchPresenter.GetManufacturersApiCallback) {
        disposables.add(
            carTypesApi.getManufacturers(page, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { callback.onTerminate() }
                .map { result -> ManufacturerConverter.fromResponse(result) }
                .subscribe(
                    { result -> callback.onSuccess(result) },
                    { callback.onError() }
                )
        )
    }

    fun dispose() {
        disposables.dispose()
    }
}
