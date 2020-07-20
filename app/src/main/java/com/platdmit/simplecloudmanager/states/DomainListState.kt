package com.platdmit.simplecloudmanager.states

import com.platdmit.domain.models.Domain

sealed class DomainListState {
    data class Success(val domains: List<Domain>) : DomainListState()
    object Empty : DomainListState()
    object Loading : DomainListState()
    object Error : DomainListState()
}