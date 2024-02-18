package dev.asifddlks.friendshipclinic.ui.mainPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.asifddlks.bhaibhaiclinicApp.utils.enums.UserStatusEnum
import dev.asifddlks.friendshipclinic.model.UserModel

/**
 * Created by Asif Ahmed on 18/02/24.
 */

class MainPageViewModel : ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var showToast: MutableLiveData<String> = MutableLiveData()
    var userStatus: MutableLiveData<UserStatusEnum> = MutableLiveData(UserStatusEnum.ACTIVE)
    var userList: MutableLiveData<MutableList<UserModel>> = MutableLiveData(mutableListOf())
    fun loadUsers() {
        isLoading.postValue(true)
        UserModel().getUsersApi { isSuccess, dataList, error ->
            isLoading.postValue(false)
            if (isSuccess) {
                dataList?.let { userList.postValue(it) }
            } else {
                error?.let { showToast.postValue(it) }
            }
        }

    }
}