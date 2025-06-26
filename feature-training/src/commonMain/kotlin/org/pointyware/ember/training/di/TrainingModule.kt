package org.pointyware.ember.training.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.pointyware.ember.training.data.TrainingController
import org.pointyware.ember.training.data.TrainingControllerImpl
import org.pointyware.ember.training.viewmodels.TrainingViewModel

fun trainingModule() = module {
    includes(
        trainingDataModule(),
        trainingViewModelModule(),
    )
}

fun trainingDataModule() = module {
    factoryOf(::TrainingControllerImpl) {
        bind<TrainingController>()
    }
}

fun trainingViewModelModule() = module {
    // Define your ViewModel bindings here
    // viewModel { YourViewModel(get()) }
    factoryOf(::TrainingViewModel)
}
