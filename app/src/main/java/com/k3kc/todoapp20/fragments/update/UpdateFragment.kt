package com.k3kc.todoapp20.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.k3kc.todoapp20.R
import com.k3kc.todoapp20.data.models.Priority
import com.k3kc.todoapp20.data.models.ToDoData
import com.k3kc.todoapp20.data.viewmodel.ToDoViewModel
import com.k3kc.todoapp20.databinding.FragmentUpdateBinding
import com.k3kc.todoapp20.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: ToDoViewModel by viewModels()
    private var _binding: FragmentUpdateBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_save) {
            updateItem()
        } else if (item.itemId == R.id.menu_delete) {
            confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTodoViewModel.deleteItem(args.currentItem)
            Toast
                .makeText(requireContext(), "Item successfully remove", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentItem.title}?")
        builder.setMessage("Are you sure you want to remove ${args.currentItem.title}?")
        builder.create().show()
    }

    private fun updateItem() {
        val title = binding.currentTitleEt.text.toString()
        val description = binding.currentDescriptionEt.text.toString()
        val priority = binding.currentPrioritiesSpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)

        if(!validation) {
            Toast
                .makeText(requireContext(), "Please fill title and description", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val updatedItem = ToDoData(
            args.currentItem.id, title, mSharedViewModel.parsePriority(priority), description
        )
        mTodoViewModel.updateData(updatedItem)
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}