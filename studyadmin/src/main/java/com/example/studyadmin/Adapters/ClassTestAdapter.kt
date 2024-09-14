package com.example.studyadmin.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyadmin.Model.ClassTest
import com.example.studyadmin.R

class ClassTestAdapter(
    private val classTestList: List<ClassTest>,
    private val onDeleteClick: (ClassTest) -> Unit,
    private val onItemClick: (ClassTest) -> Unit // Add the callback for item click
) : RecyclerView.Adapter<ClassTestAdapter.ClassTestViewHolder>() {

    class ClassTestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coursecode: TextView = itemView.findViewById(R.id.coursecode)
        val date: TextView = itemView.findViewById(R.id.date)
        val ctdelete: ImageButton = itemView.findViewById(R.id.ctdelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassTestViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_class_test, parent, false)
        return ClassTestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClassTestViewHolder, position: Int) {
        val currentClassTest = classTestList[position]
        holder.coursecode.text = currentClassTest.courseCode
        holder.date.text = currentClassTest.date

        // Delete button functionality
        holder.ctdelete.setOnClickListener {
            onDeleteClick(currentClassTest)
        }

        // Set itemView click listener for updating
        holder.itemView.setOnClickListener {
            onItemClick(currentClassTest) // Trigger the callback when itemView is clicked
        }
    }

    override fun getItemCount(): Int {
        return classTestList.size
    }
}
