package com.example.appser.ui.register.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appser.core.BaseViewHolder
import com.example.appser.data.model.PersonaAndUsuario
import com.example.appser.data.model.PersonaEntity
import com.example.appser.data.model.PersonaList
import com.example.appser.databinding.ItemRegisterBinding

class RegisterListAdapter(private val personasAndUsuario: List<PersonaAndUsuario>,
                          private val itemClickListener: OnRegisterListClickListener
                          ): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnRegisterListClickListener{
        fun onRegisterListClick(persona: PersonaEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemRegisterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = RegisterListViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION}
                ?: return@setOnClickListener
            itemClickListener.onRegisterListClick(personasAndUsuario[position].persona)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is RegisterListViewHolder -> holder.bind(personasAndUsuario[position].persona)
        }
    }

    override fun getItemCount(): Int = personasAndUsuario.size

    private inner class RegisterListViewHolder(
        val binding: ItemRegisterBinding,
        val context: Context
    ):BaseViewHolder<PersonaEntity>(binding.root){
        override fun bind(item: PersonaEntity) {
            binding.regNameText.text = item.nombre_completo
            binding.regAgeText.text = "${item.edad}"
            binding.regGenderText.text = item.genero

            //binding.executePendingBindings()
        }
    }
}