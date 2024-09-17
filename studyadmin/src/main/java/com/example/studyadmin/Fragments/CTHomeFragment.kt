package com.example.studyadmin.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyadmin.Adapters.ClassTestAdapter
import com.example.studyadmin.Internet.InternetCheck
import com.example.studyadmin.Model.ClassTest
import com.example.studyadmin.R
import com.example.studyadmin.databinding.FragmentCTHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CTHomeFragment : Fragment() {
     lateinit var binding : FragmentCTHomeBinding
    private lateinit var classTestList: ArrayList<ClassTest>
    private lateinit var adapter: ClassTestAdapter
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCTHomeBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("Class_Test")


        binding.pb.visibility = View.VISIBLE


        showAllClassTest()







        binding.fab.setOnClickListener{
            it.findNavController().navigate(R.id.action_CTHomeFragment_to_CTAddFragment)
        }



        return binding.root
    }

    private fun showAllClassTest() {
        binding.ctRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.ctRecyclerView.setHasFixedSize(true)

        classTestList = ArrayList()
        adapter = ClassTestAdapter(
            classTestList,
            onDeleteClick = { classTest ->
                if(InternetCheck().isInternetAvailable(requireContext())){
                    deleteClassTest(classTest)
                }else{
                    Toast.makeText(requireContext(),"Internet not available",Toast.LENGTH_LONG).show()
                }

            },
            onItemClick = { classTest ->
                // Navigate to the update fragment with classTest ID
                val action = CTHomeFragmentDirections
                    .actionCTHomeFragmentToCTUpdateFragment(classTest.id)

                view?.findNavController()?.navigate(action)
            }
        )
        binding.ctRecyclerView.adapter = adapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                classTestList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(ClassTest::class.java)
                    if (user != null) {
                        classTestList.add(user)
                    }
                }
                adapter.notifyDataSetChanged()

                binding.pb.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                binding.pb.visibility = View.GONE
            }
        })
    }













    private fun deleteClassTest(classTest: ClassTest) {
        classTest.id?.let { id ->
            database.child(id).removeValue().addOnSuccessListener {
                Toast.makeText(context,"Class Test deleted successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"Failed to delete Class Test",Toast.LENGTH_SHORT).show()
            }
        }


    }
}