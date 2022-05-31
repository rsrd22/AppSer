package com.example.appser.ui.register.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appser.core.BaseViewHolder
import com.example.appser.data.model.CuestionarioEntity
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.PersonaList
import com.example.appser.data.model.relations.PersonaAndUsuario
import com.example.appser.data.model.relations.PersonaWithCuestionario
import com.example.appser.databinding.ItemRegisterBinding

class RegisterListAdapter(private val personawithCuestionario: PersonaWithCuestionario,
                          private val itemClickListener: OnRegisterListClickListener
                          ): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnRegisterListClickListener{
        fun onRegisterListClick(personasAndUsuario: PersonaAndUsuario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemRegisterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = RegisterListViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION}
                ?: return@setOnClickListener
            itemClickListener.onRegisterListClick(personawithCuestionario[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is RegisterListViewHolder -> holder.bind(personawithCuestionario.cuestionario[position])
        }
    }

    override fun getItemCount(): Int = personawithCuestionario.cuestionario.size

    private inner class RegisterListViewHolder(
        val binding: ItemRegisterBinding,
        val context: Context
    ):BaseViewHolder<CuestionarioEntity>(binding.root){
        override fun bind(item: CuestionarioEntity) {
            binding.regDateText.text = item.fecha
            binding.regEmotionText.text = "${item.actividadAsignadaId}"
            binding.regActivityText.text = "${item.actividadAsignadaId}"

        }

        override fun bind(item: CuestionarioEntity, position: Int) {
            TODO("Not yet implemented")
        }
    }
}