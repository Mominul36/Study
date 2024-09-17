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
import com.example.studyadmin.databinding.FragmentAssignmentUpdateBinding
import com.example.studyadmin.databinding.FragmentLRUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar


class LRUpdateFragment : Fragment() {
    lateinit var binding: FragmentLRUpdateBinding
    lateinit var courseCodeList : ArrayList<String>
    lateinit var lrNoList : ArrayList<String>
    lateinit var courseNameList : ArrayList<String>
    var selectedDate : String?=null
    lateinit var selectedCourseCode : String
    lateinit var selectedCourseName : String
    lateinit var topic : String
    lateinit var additional : String
    lateinit var selectedLRNo: String
    lateinit var database: DatabaseReference


    private var labReportId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLRUpdateBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().getReference("Lab_Report")
        labReportId = arguments?.getString("labReportId")


        initVariable()

        binding.date.setOnClickListener{
            showDateDialog()
        }

        binding.update.setOnClickListener{
            if(InternetCheck().isInternetAvailable(requireContext())){
                updateData()
            }else{
                Toast.makeText(requireContext(),"Internet not available", Toast.LENGTH_LONG).show()
            }

        }


        database.child(labReportId!!).get().addOnSuccessListener {
            if(it.exists()){
                selectedCourseCode = it.child("courseCode").value.toString()
                selectedCourseName = it.child("courseName").value.toString()
                selectedDate = it.child("date").value.toString()
                additional = it.child("additional").value.toString()
                topic = it.child("topic").value.toString()
                selectedLRNo = it.child("lrNo").value.toString()


                setUpForCourseListSpinner()
                setUpForLRNoListSpinner()
                setAnotherValue()

            }
        }




        return binding.root
    }

    private fun setAnotherValue() {
        binding.date.text = selectedDate
        binding.additional.setText(additional)
        binding.topic.setText(topic)
    }

    private fun setUpForLRNoListSpinner() {
        val dataAdapter2 = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            lrNoList
        )
        binding.lrno.adapter = dataAdapter2

        if(selectedLRNo!=null){
            val spinnerPosition2 = dataAdapter2.getPosition(selectedLRNo)
            binding.lrno.setSelection(spinnerPosition2)
        }

        binding.lrno.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedLRNo = lrNoList[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun setUpForCourseListSpinner() {
        val dataAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            courseCodeList
        )
        binding.courseCode.adapter = dataAdapter

        if(selectedCourseCode!=null){
            val spinnerPosition = dataAdapter.getPosition(selectedCourseCode)
            binding.courseCode.setSelection(spinnerPosition)
        }





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

    private fun updateData() {
        topic = binding.topic.text.toString()
        additional = binding.additional.text.toString()

        val assignment = mapOf<String,String>(
            "courseCode" to selectedCourseCode.toString(),
            "courseName" to selectedCourseName.toString(),
            "date" to selectedDate.toString(),
            "additional" to additional.toString(),
            "topic" to topic.toString(),
            "lrNo" to selectedLRNo.toString()
        )
        database.child(labReportId!!).updateChildren(assignment).addOnSuccessListener {
            Toast.makeText(context,"Lab Report updated successfully",Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to update Lab Report", Toast.LENGTH_SHORT).show()
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

    private fun initVariable() {
        //init courseNameList
        courseNameList = arrayListOf()
        courseNameList.add("Software Development Project II")
        courseNameList.add("Software Engineering Sessional")
        courseNameList.add("Microprocessors, Microcontrollers and Embedded Systems Sessional")
        courseNameList.add("Compiler Sessional")




        //init courseName
        courseCodeList = arrayListOf()
        courseCodeList.add("CSE 3100")
        courseCodeList.add("CSE 3102")
        courseCodeList.add("CSE 3104")
        courseCodeList.add("CSE 3110")


        //init ctno
        lrNoList = arrayListOf()
        lrNoList.add("Lab Report 1")
        lrNoList.add("Lab Report 2")
        lrNoList.add("Lab Report 3")
        lrNoList.add("Lab Report 4")
        lrNoList.add("Lab Report 5")
        lrNoList.add("Lab Report 6")
        lrNoList.add("Lab Report 7")
        lrNoList.add("Lab Report 8")
        lrNoList.add("Lab Report 9")
        lrNoList.add("Lab Report 10")
        lrNoList.add("Lab Report 11")
        lrNoList.add("Lab Report 12")
    }


}