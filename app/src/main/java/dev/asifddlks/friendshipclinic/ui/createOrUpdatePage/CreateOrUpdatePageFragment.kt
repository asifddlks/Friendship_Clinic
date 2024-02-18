package dev.asifddlks.friendshipclinic.ui.createOrUpdatePage

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dev.asifddlks.bhaibhaiclinicApp.ui.createOrUpdatePage.CreateOrUpdatePageViewModel
import dev.asifddlks.friendshipclinic.utils.CustomDialog
import dev.asifddlks.bhaibhaiclinicApp.utils.enums.GenderEnum
import dev.asifddlks.bhaibhaiclinicApp.utils.enums.UserStatusEnum
import dev.asifddlks.bhaibhaiclinicApp.utils.extensions.fromJson
import dev.asifddlks.bhaibhaiclinicApp.utils.extensions.showToast
import dev.asifddlks.friendshipclinic.R
import dev.asifddlks.friendshipclinic.model.UserModel
import dev.asifddlks.friendshipclinic.databinding.FragmentCreateOrUpdatePageBinding

class CreateOrUpdatePageFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentCreateOrUpdatePageBinding? = null
    private val viewModel: CreateOrUpdatePageViewModel by viewModels()
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            val jsonData = bundle.getString("userItem")
            jsonData?.let { viewModel.userItem = Gson().fromJson(jsonData) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreateOrUpdatePageBinding.inflate(inflater, container, false)

        prepareViews()
        initListeners()


        return binding.root
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonSaveOrUpdate.setOnClickListener {

            if (viewModel.userItem != null) {
                val tempUser = UserModel()
                tempUser.name = binding.editName.text.toString()
                tempUser.email = binding.editEmail.text.toString()
                tempUser.gender = GenderEnum.entries.toTypedArray()[binding.spinner.selectedItemPosition]
                tempUser.status =
                    if (binding.switchStatus.isChecked) UserStatusEnum.ACTIVE else UserStatusEnum.INACTIVE
                viewModel.updateUser(tempUser)
            } else {
                val tempUser = UserModel()
                tempUser.name = binding.editName.text.toString()
                tempUser.email = binding.editEmail.text.toString()
                tempUser.gender = GenderEnum.entries.toTypedArray()[binding.spinner.selectedItemPosition]
                tempUser.status =
                    if (binding.switchStatus.isChecked) UserStatusEnum.ACTIVE else UserStatusEnum.INACTIVE
                viewModel.createUser(tempUser)
            }
        }
    }

    private fun prepareViews() {

        viewModel.showToast.observe(viewLifecycleOwner) {
            requireContext().showToast(it)
        }

        progressDialog = CustomDialog(requireContext()).progressDialog()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            GenderEnum.values().map { it.value }
        )
        binding.spinner.adapter = adapter

        viewModel.userItem?.let { user ->
            binding.toolbar.title = getString(R.string.update)
            binding.buttonSaveOrUpdate.text = getString(R.string.update)

            binding.editName.setText(user.name)
            binding.editEmail.setText(user.email)

            binding.switchStatus.isChecked = user.status == UserStatusEnum.ACTIVE
            binding.spinner.setSelection(GenderEnum.values().indexOf(user.gender))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}