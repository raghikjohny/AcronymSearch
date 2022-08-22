package com.raghi.acronymsearch.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.raghi.acronymsearch.R
import com.raghi.acronymsearch.databinding.AcronymListItemBinding
import com.raghi.acronymsearch.model.Lfs

class AcronymListAdapter(var acronymList: ArrayList<Lfs>, val context: Context) :
    RecyclerView.Adapter<AcronymListAdapter.AcronymViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcronymViewHolder {
        val view: AcronymListItemBinding = DataBindingUtil.inflate(
            LayoutInflater
                .from(parent.context), R.layout.acronym_list_item, parent, false
        )
        return AcronymViewHolder(view)
    }

    override fun onBindViewHolder(holder: AcronymViewHolder, position: Int) {
        val item = acronymList[position].lf
        holder.bindingView.dataModel = item
    }

    override fun getItemCount(): Int {
        return acronymList.size
    }

    //update the RecyclerVeiw with the new api result
    fun updateAcroList(acroListResult: List<Lfs>) {
        acronymList.clear()
        acronymList.addAll(acroListResult)
        notifyDataSetChanged()
    }

    fun clearList() {
        acronymList.clear()
    }

    inner class AcronymViewHolder(val bindingView: AcronymListItemBinding) :
        RecyclerView.ViewHolder(bindingView.root) {
    }

}