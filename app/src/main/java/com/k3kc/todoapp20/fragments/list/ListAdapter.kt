package com.k3kc.todoapp20.fragments.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.k3kc.todoapp20.R
import com.k3kc.todoapp20.data.models.Priority
import com.k3kc.todoapp20.data.models.ToDoData
import com.k3kc.todoapp20.databinding.RowLayoutBinding
import java.util.zip.Inflater

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(private val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ToDoData) {
            binding.titleTxt.text = item.title
            binding.descriptionTxt.text = item.description

            binding.rowBackground.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(item)
                binding.root.findNavController().navigate(action)
            }

            val priority = item.priority
            when(priority) {
                Priority.HIGH -> binding.priorityIndication.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.red))
                Priority.MEDIUM -> binding.priorityIndication.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.yellow))
                Priority.LOW -> binding.priorityIndication.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(RowLayoutBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }

}