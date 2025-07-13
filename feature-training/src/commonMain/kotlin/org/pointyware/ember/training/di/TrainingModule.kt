package org.pointyware.ember.training.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.ember.training.interactors.TrainingController
import org.pointyware.ember.training.interactors.TrainingControllerImpl
import org.pointyware.ember.training.viewmodels.TrainingViewModel

val trainingQualifier = named("training")

fun trainingModule() = module {

    single<CoroutineScope>(qualifier = trainingQualifier) {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    includes(
        trainingDataModule(),
        trainingViewModelModule(),
    )
}

fun trainingDataModule() = module {
    factory<TrainingController> {
        TrainingControllerImpl(
            trainingScope = get(qualifier = trainingQualifier)
        )
    }
}

fun trainingViewModelModule() = module {
    // Define your ViewModel bindings here
    // viewModel { YourViewModel(get()) }
    factoryOf(::TrainingViewModel)
}
