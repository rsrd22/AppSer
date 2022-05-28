package com.example.appser.ui.emotion.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appser.core.BaseViewHolder
import com.example.appser.data.model.ActividadesEntity
import com.example.appser.databinding.ItemActivitiesBinding

class ActivitiesListAdapter(private val actividades: List<ActividadesEntity>,
                            private val itemClickListener: OnActivitiesListClickListener
                            ): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface  OnActivitiesListClickListener{
        fun onActivitiesListClick(actividades: ActividadesEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemActivitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ActivitiesListViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener{
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onActivitiesListClick(actividades[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ActivitiesListViewHolder -> holder.bind(actividades[position])
        }
    }

    override fun getItemCount(): Int = actividades.size

    private inner class ActivitiesListViewHolder(val binding: ItemActivitiesBinding, val context: Context): BaseViewHolder<ActividadesEntity>(binding.root){
        override fun bind(item: ActividadesEntity) {
            binding.actTitleText.text = item.titulo
        }
    }
}