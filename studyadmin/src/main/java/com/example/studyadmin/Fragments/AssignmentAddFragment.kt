package com.example.studyadmin.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.studyadmin.Internet.InternetCheck
import com.example.studyadmin.Model.Assignment
import com.example.studyadmin.Model.ClassTest
import com.example.studyadmin.R
import com.example.studyadmin.databinding.FragmentAssignmentAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar


class AssignmentAddFragment : Fragment() {

    lateinit var binding: FragmentAssignmentAddBinding
    lateinit var courseCodeList : ArrayList<String>
    lateinit var assignmentNoList : ArrayList<String>
    lateinit var courseNameList : ArrayList<String>
    var selectedDate : String?=null
    lateinit var selectedCourseCode : String
    lateinit var selectedCourseName : String
    lateinit var topic : String
    lateinit var additional : String
    lateinit var selectedAssignmentNo: String
    lateinit var database: DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAssignmentAddBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("Assignment")

        initVariable()
        showCourseList()
        showAssignmentNoList()


        binding.date.setOnClickListener{
            showDateDialog()
        }


        binding.add.setOnClickListener{

            if(InternetCheck().isInternetAvailable(requireContext())){
                saveData()
            }else{
                Toast.makeText(requireContext(),"Internet not available", Toast.LENGTH_LONG).show()
            }


        }





        return binding.root
    }

    private fun saveData() {
        if(selectedDate==null){
            Toast.makeText(context,"Select Date",Toast.LENGTH_LONG).show()
        }
        else{
            var id = database.push()!!.key!!
            topic = binding.topic.text.toString()
            additional = binding.additional.text.toString()
            var assignment = Assignment(id,selectedCourseCode,selectedCourseName,selectedAssignmentNo,selectedDate,topic,additional)

            database.child(id).setValue(assignment).addOnSuccessListener {
                Toast.makeText(context,"Assignment added successfully",Toast.LENGTH_SHORT).show()

                findNavController().navigateUp()
            }.addOnFailureListener{
                Toast.makeText(context,"Failed to add Assignment",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun showDateDialog() {
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Format the date (Month starts from 0, so add 1)
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.date.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showAssignmentNoList() {
        val dataAdapter2 = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            assignmentNoList
        )
        binding.assignmentno.adapter = dataAdapter2

        binding.assignmentno.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedAssignmentNo = assignmentNoList[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun showCourseList() {
        val dataAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            courseCodeList
        )
        binding.courseCode.adapter = dataAdapter

        binding.courseCode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedCourseCode = courseCodeList[p2]
                selectedCourseName = courseNameList[p2]
                binding.coursename.text = selectedCourseName
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun initVariable() {
        //init courseNameList
        courseNameList = arrayListOf()
        courseNameList.add("Software Engineering")
        courseNameList.add("Microprocessors, Microcontrollers and Embedded Systems ")
        courseNameList.add("Computer Architecture ")
        courseNameList.add("Data Communication ")
        courseNameList.add("Compiler")
        courseNameList.add("Basic Mechanical Engineering ")

        courseNameList.add("Software Development Project II")
        courseNameList.add("Software Engineering Sessional")
        courseNameList.add("Microprocessors, Microcontrollers and Embedded Systems Sessional")
        courseNameList.add("Compiler Sessional")




        //init courseName
        courseCodeList = arrayListOf()
        courseCodeList.add("CSE 3101")
        courseCodeList.add("CSE 3103")
        courseCodeList.add("CSE 3105")
        courseCodeList.add("CSE 3107")
        courseCodeList.add("CSE 3109")
        courseCodeList.add("ME 3181")


        courseCodeList.add("CSE 3100")
        courseCodeList.add("CSE 3102")
        courseCodeList.add("CSE 3104")
        courseCodeList.add("CSE 3110")


        //init ctno
        assignmentNoList = arrayListOf()
        assignmentNoList.add("Assignment-1")
        assignmentNoList.add("Assignment-2")

    }


}