package com.platdmit.simplecloudmanager.screens.server.tabactions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.platdmit.domain.models.Action
import com.platdmit.simplecloudmanager.R
import com.platdmit.simplecloudmanager.screens.server.ServerTabFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_server_tab_actions.*

@AndroidEntryPoint
class ServerTabActionsFragment(
        private val mTitle: String = "empty"
) : Fragment(R.layout.fragment_server_tab_actions), ServerTabFragment<ServerTabActionsFragment> {
    private val actionViewModel: ActionViewModel by viewModels()
    private val actionListAdapter = ActionListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        server_actions_list.layoutManager = LinearLayoutManager(context)

        actionViewModel.actionStateLiveData.observe(viewLifecycleOwner, Observer { stateHandler(it) })
    }

    private fun stateHandler(actionState: ActionState){
        when(actionState){
            is ActionState.Loading -> {}
            is ActionState.Success -> {
                updateServerActionData(actionState.actions)
            }
            is ActionState.Empty -> {}
            is ActionState.Error -> {}
        }
    }

    private fun setStateInstance(stateInstance: ActionViewModel.StateIntent){
        actionViewModel.setStateIntent(stateInstance)
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getInstance(): ServerTabActionsFragment {
        return ServerTabActionsFragment()
    }

    private fun updateServerActionData(actions: List<Action>) {
        actionListAdapter.setContentData(actions)
        if (server_actions_list.adapter == null) {
            server_actions_list.adapter = actionListAdapter
        } else {
            actionListAdapter.notifyDataSetChanged()
        }
    }
}