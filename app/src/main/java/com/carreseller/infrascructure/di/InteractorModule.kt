package com.carreseller.infrascructure.di

import com.carreseller.interactors.GetBuiltDates
import com.carreseller.interactors.GetMainCarTypes
import com.carreseller.interactors.GetManufacturers
import org.koin.dsl.module

val interactorModule = module {
    factory { GetManufacturers(get()) }

    factory { GetMainCarTypes(get()) }

    factory { GetBuiltDates(get()) }
}
