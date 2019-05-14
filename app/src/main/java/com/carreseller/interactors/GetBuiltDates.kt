package com.carreseller.interactors

import com.carreseller.gateways.converters.BuiltDateConverter
import com.carreseller.gateways.remote.CarTypesApi
import com.carreseller.ui.search.builtdate.BuiltDateSearchPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GetBuiltDates constructor(private val carTypesApi: CarTypesApi) {

    private val disposables = CompositeDisposable()

    fun execute(manufacturerId: String, mainType: String,
                callback: BuiltDateSearchPresenter.GetBuiltDatesApiCallback) {
        disposables.add(
            carTypesApi.getBuiltDates(manufacturerId, mainType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { callback.onTerminate() }
                .map { result -> BuiltDateConverter.fromResponse(result) }
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
