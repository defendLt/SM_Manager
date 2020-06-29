package com.platdmit.simplecloudmanager.app.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.platdmit.simplecloudmanager.domain.models.Domain
import com.platdmit.simplecloudmanager.domain.repo.DomainBaseRepo
import io.reactivex.rxjava3.processors.BehaviorProcessor

class DomainViewModel(private val mDomainRepo: DomainBaseRepo, id: Long) : BaseViewModel() {
    val domainLiveData: LiveData<Domain>
    private val mContentProvider = BehaviorProcessor.create<Domain>()

    companion object {
        private val TAG = DomainViewModel::class.java.simpleName
    }

    init {
        //Fast fix for prevent overSubscription after resize
        mCompositeDisposable.add(mDomainRepo.getDomain(id).subscribe { mContentProvider.onNext(it) })
        domainLiveData = LiveDataReactiveStreams.fromPublisher(mContentProvider)
    }

    override fun onCleared() {
        super.onCleared()
        mContentProvider.onComplete()
    }
}