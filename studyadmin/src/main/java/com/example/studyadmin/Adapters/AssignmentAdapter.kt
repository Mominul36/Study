package com.example.studyadmin.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyadmin.Adapters.ClassTestAdapter.ClassTestViewHolder
import com.example.studyadmin.Model.Assignment
import com.example.studyadmin.Model.ClassTest
import com.example.studyadmin.R

class AssignmentAdapter(
    private val assignmentList : List<Assignment>,
    private val onDeleteClick: (Assignment) -> Unit,
    private val onItemClick: (Assignment) -> Unit
) : RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>() {

    class AssignmentViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
        val coursecode: TextView = itemView.findViewById(R.id.coursecode)
        val date: TextView = itemView.findViewById(R.id.date)
        val assignmentdelete: ImageButton = itemView.findViewById(R.id.assignmentdelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_assignment, parent, false)
        return AssignmentViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return assignmentList.size
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val currentAssignment = assignmentList[position]
        holder.coursecode.text = "Course Code: "+currentAssignment.courseCode
        holder.date.text = "Submission Date: "+currentAssignment.date

        // Delete button functionality
        holder.assignmentdelete.setOnClickListener {
            onDeleteClick(currentAssignment)
        }

        // Set itemView click listener for updating
        holder.itemView.setOnClickListener {
            onItemClick(currentAssignment) // Trigger the callback when itemView is clicked
        }



    }
}