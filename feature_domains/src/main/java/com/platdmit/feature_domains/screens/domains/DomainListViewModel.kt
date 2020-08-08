package com.platdmit.feature_domains.screens.domains

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.SavedStateHandle
import com.platdmit.feature_base.vm.BaseViewModel
import com.platdmit.feature_domains.domain.repositories.DomainBaseRepo

class DomainListViewModel
@ViewModelInject
constructor(
        private val domainBaseRepo: DomainBaseRepo,
        @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<DomainListState>() {
    val domainsStateLiveData = LiveDataReactiveStreams.fromPublisher(stateProvider)
    val messageLiveData = LiveDataReactiveStreams.fromPublisher(messageProvider)

    init {
        stateProvider.onNext(DomainListState.Loading)
        //Fast fix for prevent overSubscription after resize
        compositeDisposable.add(domainBaseRepo.getDomains()
                .subscribe({
                    stateProvider.onNext(DomainListState.Success(it))
                }, {
                    stateProvider.onNext(DomainListState.Error)
                })
        )
    }

    fun setStateIntent(stateIntent: StateIntent){
        when(stateIntent){
            is StateIntent.RefreshResult -> {
                reloadDomainList()
            }
        }
    }

    private fun reloadDomainList() {
        domainBaseRepo.nextUpdate()
    }

    sealed class StateIntent {
        object RefreshResult : StateIntent()
    }
}