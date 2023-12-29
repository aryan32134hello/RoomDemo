package com.example.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ItemUpdateBinding

class itemAdapter(private val items:ArrayList<EmployeeEntity>,
    private val updateListener:(id:Int)->Unit,
    private val deleteListener:(id:Int)->Unit) :RecyclerView.Adapter<itemAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemUpdateBinding):RecyclerView.ViewHolder(binding.root){
        val llmain = binding.llMain
        val tvName = binding.tvName
        val tvEmail = binding.tvEmail
        val ivEdit = binding.ivEdit
        val ivDelete = binding.ivDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUpdateBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvName.text = item.name
        holder.tvEmail.text = item.email

        if(position%2==0){
            holder.llmain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.colorLightGray))
        }else{
            holder.llmain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
        }

        holder.ivEdit.setOnClickListener{
            updateListener.invoke(item.id)
        }

        holder.ivDelete.setOnClickListener {
            deleteListener.invoke(item.id)
        }
    }
}