package com.ziyad.zradio.di

import com.ziyad.core.domain.usecase.RadioInteractor
import com.ziyad.core.domain.usecase.RadioUseCase
import com.ziyad.zradio.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<RadioUseCase> { RadioInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}