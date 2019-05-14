package com.carreseller.interactors

import com.carreseller.gateways.converters.MainCarTypeConverter
import com.carreseller.gateways.remote.CarTypesApi
import com.carreseller.ui.search.maincartype.MainCarTypeSearchPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GetMainCarTypes constructor(private val carTypesApi: CarTypesApi) {

    private val disposables = CompositeDisposable()

    fun execute(page: Int, pageSize: Int, manufacturerId: String,
                callback: MainCarTypeSearchPresenter.GetMainCarTypesApiCallback) {
        disposables.add(
            carTypesApi.getMainTypes(page, pageSize, manufacturerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { callback.onTerminate() }
                .map { result -> MainCarTypeConverter.fromResponse(result) }
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
