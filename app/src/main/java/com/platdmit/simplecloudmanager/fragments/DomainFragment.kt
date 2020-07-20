package com.platdmit.simplecloudmanager.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.platdmit.domain.models.Domain
import com.platdmit.simplecloudmanager.R
import com.platdmit.simplecloudmanager.adapters.DomainRecordListAdapter
import com.platdmit.simplecloudmanager.states.DomainState
import com.platdmit.simplecloudmanager.vm.DomainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_domain.*

@AndroidEntryPoint
class DomainFragment : Fragment(R.layout.fragment_domain) {
    private val domainViewModel: DomainViewModel by viewModels()
    private val domainRecordListAdapter = DomainRecordListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            setStateInstance(
                    DomainViewModel.StateIntent.SetDomainId(requireArguments().getLong("ELEMENT_ID"))
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        domain_record_list.layoutManager = LinearLayoutManager(context)
        domainViewModel.domainStateLiveData.observe(viewLifecycleOwner, Observer { stateHandler(it) })
    }

    private fun stateHandler(domainState: DomainState){
        when(domainState){
            is DomainState.Loading -> {}
            is DomainState.Success -> {
                initData(domainState.domain)
            }
            is DomainState.Empty -> {}
            is DomainState.Error -> {}
        }
    }

    private fun setStateInstance(stateInstance: DomainViewModel.StateIntent){
        domainViewModel.setStateIntent(stateInstance)
    }

    private fun initData(domain: Domain) {
        domain_name.text = domain.name
        domain_type.text = domain.type
        domain.domainRecords?.let { domainRecordListAdapter.setContentData(it) }
        if (domain_record_list.adapter == null) {
            domain_record_list.adapter = domainRecordListAdapter
        } else {
            domainRecordListAdapter.notifyDataSetChanged()
        }
    }
}