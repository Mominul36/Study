package com.example.studyadmin.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyadmin.Adapters.AssignmentAdapter
import com.example.studyadmin.Adapters.LabReportAdapter
import com.example.studyadmin.Internet.InternetCheck
import com.example.studyadmin.Model.Assignment
import com.example.studyadmin.Model.LabReport
import com.example.studyadmin.R
import com.example.studyadmin.databinding.FragmentLRHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LRHomeFragment : Fragment() {
    lateinit var binding : FragmentLRHomeBinding
    private lateinit var lrList: ArrayList<LabReport>
    private lateinit var adapter: LabReportAdapter
    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLRHomeBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("Lab_Report")

        showAllLabReport()


        binding.fab.setOnClickListener{
            it.findNavController().navigate(R.id.action_LRHomeFragment_to_LRAddFragment)
        }







        return binding.root
    }

    private fun showAllLabReport() {
        binding.lrRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.lrRecyclerView.setHasFixedSize(true)

        lrList = ArrayList()
        adapter = LabReportAdapter(
            lrList,
            onDeleteClick = { labReport ->
                if(InternetCheck().isInternetAvailable(requireContext())){
                    deleteLabReport(labReport)
                }else{
                    Toast.makeText(requireContext(),"Internet not available", Toast.LENGTH_LONG).show()
                }

            },
            onItemClick = { labReport ->

                val action = LRHomeFragmentDirections.
                    actionLRHomeFragmentToLRUpdateFragment(labReport.id)

                view?.findNavController()?.navigate(action)
            }
        )
        binding.lrRecyclerView.adapter = adapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lrList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(LabReport::class.java)
                    if (user != null) {
                        lrList.add(user)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun deleteLabReport(labReport: LabReport) {
        labReport.id?.let { id ->
            database.child(id).removeValue().addOnSuccessListener {
                Toast.makeText(context,"Lab Report deleted successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"Failed to delete Lab Report",Toast.LENGTH_SHORT).show()
            }
        }

    }


}