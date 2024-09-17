package com.example.studyadmin.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyadmin.Model.Assignment
import com.example.studyadmin.Model.LabReport
import com.example.studyadmin.R

class LabReportAdapter(
    private val lrList : List<LabReport>,
    private val onDeleteClick: (LabReport) -> Unit,
    private val onItemClick: (LabReport) -> Unit
) : RecyclerView.Adapter<LabReportAdapter.LabReportViewHolder>() {

    class LabReportViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
        val coursecode: TextView = itemView.findViewById(R.id.coursecode)
        val date: TextView = itemView.findViewById(R.id.date)
        val lrdelete: ImageButton = itemView.findViewById(R.id.lrdelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabReportViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lab_report, parent, false)
        return LabReportViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lrList.size
    }

    override fun onBindViewHolder(holder: LabReportViewHolder, position: Int) {
        val currentLR = lrList[position]
        holder.coursecode.text = "Course Code: "+currentLR.courseCode
        holder.date.text = "Submission Date: "+currentLR.date

        // Delete button functionality
        holder.lrdelete.setOnClickListener {
            onDeleteClick(currentLR)
        }

        // Set itemView click listener for updating
        holder.itemView.setOnClickListener {
            onItemClick(currentLR) // Trigger the callback when itemView is clicked
        }



    }
}