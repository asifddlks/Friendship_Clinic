package dev.asifddlks.friendshipclinic.ui.mainPage

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.asifddlks.friendshipclinic.model.UserModel
import dev.asifddlks.bhaibhaiclinicApp.utils.enums.UserStatusEnum
import dev.asifddlks.friendshipclinic.databinding.ItemUserActiveBinding
import dev.asifddlks.friendshipclinic.databinding.ItemUserInactiveBinding

/**
 * Created by Asif Ahmed on 18/02/24.
 */

class UserItemAdapter(private val itemInterface: ItemInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: MutableList<UserModel> = mutableListOf()

    companion object {
        private const val VIEW_TYPE_ACTIVE = 1
        private const val VIEW_TYPE_INACTIVE = 2
    }

    override fun getItemViewType(position: Int): Int {
        // Determine the view type based on the data
        val item = dataList[position]
        return if (item.status == UserStatusEnum.ACTIVE) VIEW_TYPE_ACTIVE else VIEW_TYPE_INACTIVE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            VIEW_TYPE_ACTIVE -> {
                val binding = ItemUserActiveBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewHolderActiveUser(binding, itemInterface)
            }

            VIEW_TYPE_INACTIVE -> {
                val binding = ItemUserInactiveBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewHolderInactiveUser(binding, itemInterface)
            }

            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]

        //when (holder.itemViewType) {
        when (item.status) {
            UserStatusEnum.ACTIVE -> {
                val viewHolder = holder as ViewHolderActiveUser
                val indexBGColor = if (position % 4 == 0) {
                    "#2171AB"
                } else if (position % 4 == 1) {
                    "#EF5E50"
                } else if (position % 4 == 2) {
                    "#18988B"
                }
                //else if(position % 4 == 3){ "#FFC811" }
                else {
                    "#FFC811"
                }
                viewHolder.bind(item, indexBGColor)
            }

            UserStatusEnum.INACTIVE -> {
                val viewHolder = holder as ViewHolderInactiveUser
                viewHolder.bind(item)
            }

            else -> {}
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(updatedDataList: List<UserModel>) {
        val diffCallback = DiffUtilItem(dataList, updatedDataList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList.clear()
        dataList.addAll(updatedDataList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolderActiveUser(
        private val binding: ItemUserActiveBinding,
        private val itemInterface: ItemInterface
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserModel, indexBGColor: String) {
            binding.apply {
                cardIndex.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        Color.parseColor(
                            indexBGColor
                        )
                    )
                )
                textIndex.text = item.name.first().toString()
                textName.text = item.name
                textGender.text = item.gender.value
                textEmail.text = item.email

                buttonEdit.setOnClickListener {
                    itemInterface.onEditClick(item)
                }
            }
        }
    }

    class ViewHolderInactiveUser(
        private val binding: ItemUserInactiveBinding,
        private val itemInterface: ItemInterface
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserModel) {
            binding.apply {
                textIndex.text = item.name.first().toString()
                textName.text = item.name
                textGender.text = item.gender.value
                textEmail.text = item.email

                buttonEdit.setOnClickListener {
                    itemInterface.onEditClick(item)
                }
            }
        }
    }

    interface ItemInterface {
        fun onItemClick(item: UserModel)
        fun onEditClick(item: UserModel)
    }

    class DiffUtilItem(
        private val oldList: List<UserModel>,
        private val newList: List<UserModel>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }
    }
}