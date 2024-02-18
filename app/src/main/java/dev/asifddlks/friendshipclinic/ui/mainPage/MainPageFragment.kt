package dev.asifddlks.friendshipclinic.ui.mainPage

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dev.asifddlks.friendshipclinic.model.UserModel
import dev.asifddlks.friendshipclinic.utils.CustomDialog
import dev.asifddlks.bhaibhaiclinicApp.utils.enums.UserStatusEnum
import dev.asifddlks.bhaibhaiclinicApp.utils.extensions.showToast
import dev.asifddlks.friendshipclinic.R
import dev.asifddlks.friendshipclinic.databinding.FragmentMainPageBinding

@Suppress("NAME_SHADOWING")
class MainPageFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentMainPageBinding? = null
    private val viewModel: MainPageViewModel by viewModels()

    private lateinit var progressDialog: Dialog
    private lateinit var adapter: UserItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)

        prepareViews()
        initListeners()
        viewModel.loadUsers()

        return binding.root
    }

    private fun initListeners() {

        binding.buttonActive.setOnClickListener {
            viewModel.userStatus.postValue(UserStatusEnum.ACTIVE)
        }

        binding.buttonInactive.setOnClickListener {
            viewModel.userStatus.postValue(UserStatusEnum.INACTIVE)
        }

        binding.buttonAdd.setOnClickListener {
            val bundle = Bundle()
            //bundle.putInt("userId", 13)
            findNavController().navigate(
                R.id.action_mainPageFragment_to_createOrUpdatePageFragment,
                bundle
            )
        }
    }

    private fun prepareViews() {

        viewModel.showToast.observe(viewLifecycleOwner) {
            requireContext().showToast(it)
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { userStatus ->
            userStatus?.let { userStatus ->
                binding.apply {
                    if (userStatus == UserStatusEnum.ACTIVE) {
                        buttonActive.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.colorAccentMain
                            )
                        )
                        buttonActive.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )

                        buttonInactive.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.transparent
                            )
                        )
                        buttonInactive.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_3
                            )
                        )

                        viewModel.userList.value?.let { list -> adapter.updateData(list.filter { it.status == UserStatusEnum.ACTIVE }) }
                    } else {
                        buttonActive.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.transparent
                            )
                        )
                        buttonActive.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_3
                            )
                        )

                        buttonInactive.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.colorAccentMain
                            )
                        )
                        buttonInactive.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )

                        viewModel.userList.value?.let { list -> adapter.updateData(list.filter { it.status == UserStatusEnum.INACTIVE }) }
                    }
                }
            }
        }

        progressDialog = CustomDialog(requireContext()).progressDialog()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }

        adapter = UserItemAdapter(object :
            UserItemAdapter.ItemInterface {
            override fun onItemClick(item: UserModel) {

            }

            override fun onEditClick(item: UserModel) {
                val bundle = Bundle()
                bundle.putSerializable("userItem", Gson().toJson(item))
                findNavController().navigate(
                    R.id.action_mainPageFragment_to_createOrUpdatePageFragment,
                    bundle
                )
            }
        })

        binding.recyclerView.adapter = adapter

        viewModel.userList.observe(viewLifecycleOwner) {
            it?.let {
                if (viewModel.userStatus.value == UserStatusEnum.ACTIVE) {
                    adapter.updateData(it.filter { it.status == UserStatusEnum.ACTIVE })
                } else {
                    adapter.updateData(it.filter { it.status == UserStatusEnum.ACTIVE })
                }

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}