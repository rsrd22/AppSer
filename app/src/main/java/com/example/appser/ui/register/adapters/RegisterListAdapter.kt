package com.example.appser.ui.register.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appser.core.BaseViewHolder
import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.HistoricoCuestionario
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.PersonaList
import com.example.appser.data.model.relations.PersonaAndUsuario
import com.example.appser.data.model.relations.PersonaWithCuestionario
import com.example.appser.databinding.ItemRegisterBinding

class RegisterListAdapter(private val historicoCuestionarios: List<HistoricoCuestionario>,
                          private val itemClickListener: OnRegisterListClickListener
                          ): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnRegisterListClickListener{
        fun onRegisterListClick(historicoCuestionario: HistoricoCuestionario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemRegisterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = RegisterListViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION}
                ?: return@setOnClickListener
            itemClickListener.onRegisterListClick(historicoCuestionarios[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is RegisterListViewHolder -> holder.bind(historicoCuestionarios[position])
        }
    }

    override fun getItemCount(): Int = historicoCuestionarios.size

    private inner class RegisterListViewHolder(
        val binding: ItemRegisterBinding,
        val context: Context
    ):BaseViewHolder<HistoricoCuestionario>(binding.root){
        override fun bind(item: HistoricoCuestionario) {
            binding.regDateText.text = item.fecha
            binding.regEmotionText.text = "${item.emocion}"
            binding.regActivityText.text = "${item.actividad}"

        }

        override fun bind(item: HistoricoCuestionario, position: Int) {
            TODO("Not yet implemented")
        }
    }
}