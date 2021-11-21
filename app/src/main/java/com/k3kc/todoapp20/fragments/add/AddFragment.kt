package com.k3kc.todoapp20.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.k3kc.todoapp20.R
import com.k3kc.todoapp20.data.models.Priority
import com.k3kc.todoapp20.data.models.ToDoData
import com.k3kc.todoapp20.data.viewmodel.ToDoViewModel
import com.k3kc.todoapp20.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()

    private lateinit var _binding: FragmentAddBinding
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.prioritiesSpinner.selectedItem.toString()
        val mDescription = binding.descriptionEt.text.toString()

        val validation = verifyDataFromUser(mTitle, mDescription)
        if(validation) {
            // insert data to database
            val newData = ToDoData(
                0,
                mTitle,
                parsePriority(mPriority),
                mDescription
            )
            mToDoViewModel.insertData(newData)
        }
    }

    private fun verifyDataFromUser(title: String, description: String): Boolean {
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    private fun parsePriority(priority: String): Priority {
        return when(priority) {
            "High Priority" -> { Priority.HIGH }
            "Low Priority" -> { Priority.LOW }
            "Medium Priority" -> { Priority.MEDIUM }
            else -> Priority.LOW
        }
    }

}