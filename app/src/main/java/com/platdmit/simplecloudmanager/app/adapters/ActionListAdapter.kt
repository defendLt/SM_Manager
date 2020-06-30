package com.platdmit.simplecloudmanager.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.platdmit.simplecloudmanager.R
import com.platdmit.simplecloudmanager.app.adapters.ActionListAdapter.ActionListHolder
import com.platdmit.simplecloudmanager.domain.models.Action

class ActionListAdapter : RecyclerView.Adapter<ActionListHolder>() {
    private lateinit var elementList: List<Action>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutType = R.layout.fragment_actions_item
        return ActionListHolder(layoutInflater, parent, layoutType)
    }

    override fun onBindViewHolder(holder: ActionListHolder, position: Int) {
        holder.bindData(elementList[position])
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    fun setContentData(elements: List<Action>) {
        elementList = elements
    }

    inner class ActionListHolder(inflater: LayoutInflater, parent: ViewGroup?, layoutType: Int) : RecyclerView.ViewHolder(inflater.inflate(layoutType, parent, false)) {
        private val mId = itemView.findViewById<TextView>(R.id.action_id)
        private val mStartedAt = itemView.findViewById<TextView>(R.id.action_start)
        private val mCompletedAt = itemView.findViewById<TextView>(R.id.action_complete)
        private val mType = itemView.findViewById<TextView>(R.id.action_type)
        private val mInitiator = itemView.findViewById<TextView>(R.id.action_initiator)
        private val mStatus = itemView.findViewById<TextView>(R.id.action_status)

        @SuppressLint("SetTextI18n")
        fun bindData(data: Action) {
            mId.text = data.id.toString()
            mStartedAt.text = data.startedAt
            mCompletedAt.text = " - ${data.completedAt}"
            mType.text = data.type
            mInitiator.text = data.initiator
            mStatus.text = data.status
        }
    }
}