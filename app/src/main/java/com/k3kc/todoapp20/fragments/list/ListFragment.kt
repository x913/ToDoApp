package com.k3kc.todoapp20.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.k3kc.todoapp20.R
import com.k3kc.todoapp20.data.viewmodel.ToDoViewModel
import com.k3kc.todoapp20.databinding.FragmentAddBinding
import com.k3kc.todoapp20.databinding.FragmentListBinding
import com.k3kc.todoapp20.fragments.SharedViewModel


class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    private lateinit var _binding: FragmentListBinding
    private val binding
        get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)


        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mToDoViewModel.getAllData.observe(viewLifecycleOwner) { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        }

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner) { isEmpty ->
            showEmptyDatabaseViews(isEmpty)

        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if(emptyDatabase) {
            binding.noDataImageView.isVisible = true
            binding.noDataTextView.isVisible = true
        } else {
            binding.noDataImageView.isVisible = false
            binding.noDataTextView.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_all) {
            confirmDeleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteAll() {
        val confirmationDialog = AlertDialog.Builder(requireContext())
        confirmationDialog.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAllData()
        }
        confirmationDialog.setNegativeButton("No") { _, _ -> }
        confirmationDialog.setTitle("Confirm deletion of all items")
        confirmationDialog.setMessage("Do you really want to delete all todo items?")
        confirmationDialog.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

}