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
import com.example.studyadmin.Internet.InternetCheck
import com.example.studyadmin.Model.Assignment
import com.example.studyadmin.R
import com.example.studyadmin.databinding.FragmentAssignmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AssignmentHomeFragment : Fragment() {
    lateinit var binding : FragmentAssignmentHomeBinding
    private lateinit var assignmentList: ArrayList<Assignment>
    private lateinit var adapter: AssignmentAdapter
    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAssignmentHomeBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("Assignment")

        showAllAssignment()


        binding.fab.setOnClickListener{
            it.findNavController().navigate(R.id.action_assignmentHomeFragment_to_assignmentAddFragment)
        }







        return binding.root
    }

    private fun showAllAssignment() {
        binding.assignmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.assignmentRecyclerView.setHasFixedSize(true)

        assignmentList = ArrayList()
        adapter = AssignmentAdapter(
            assignmentList,
            onDeleteClick = { assignment ->
                if(InternetCheck().isInternetAvailable(requireContext())){
                    deleteAssignment(assignment)
                }else{
                    Toast.makeText(requireContext(),"Internet not available", Toast.LENGTH_LONG).show()
                }

            },
            onItemClick = { assignment ->
                // Navigate to the update fragment with classTest ID
                val action = AssignmentHomeFragmentDirections
                    .actionAssignmentHomeFragmentToAssignmentUpdateFragment(assignment.id)

                view?.findNavController()?.navigate(action)
            }
        )
        binding.assignmentRecyclerView.adapter = adapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                assignmentList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(Assignment::class.java)
                    if (user != null) {
                        assignmentList.add(user)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun deleteAssignment(assignment: Assignment) {
        assignment.id?.let { id ->
            database.child(id).removeValue().addOnSuccessListener {
                Toast.makeText(context,"Assignment deleted successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"Failed to delete Assignment",Toast.LENGTH_SHORT).show()
            }
        }

    }


}