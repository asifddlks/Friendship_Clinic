package dev.asifddlks.friendshipclinic.model

import dev.asifddlks.bhaibhaiclinicApp.utils.constants.ApiConstants
import dev.asifddlks.bhaibhaiclinicApp.utils.enums.GenderEnum
import dev.asifddlks.bhaibhaiclinicApp.utils.enums.UserStatusEnum
import dev.asifddlks.friendshipclinic.utils.extensions.parseInt
import dev.asifddlks.friendshipclinic.utils.extensions.parseString
import dev.asifddlks.friendshipclinic.network.NetworkManager
import dev.asifddlks.friendshipclinic.network.RequestListener
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException

/**
 * Created by Asif Ahmed on 18/02/24.
 */
/*"id": 5366063,
"name": "Brahmdev Deshpande",
"email": "brahmdev_deshpande@rogahn.test",
"gender": "female",
"status": "inactive"*/
data class UserModel(
    var id: Int = -1,
    var name: String = "",
    var email: String = "",
    var gender: GenderEnum = GenderEnum.OTHER,
    var status: UserStatusEnum = UserStatusEnum.UNKNOWN,
) {

    fun getUsersApi(
        callback: (Boolean, MutableList<UserModel>?, String?) -> Unit
    ) {
        try {
            val url = ApiConstants.USERS
            NetworkManager().getRequest(
                url = url,
                listener = object :
                    RequestListener {
                    override fun onSuccess(response: Any?) {
                        val responseObject = (response as? Response<*>)
                        val responseBody = responseObject?.body().toString()
                        val body = JSONArray(responseBody)

                        val dataList = mutableListOf<UserModel>()

                        for (i in 0 until body.length()) {
                            val itemJsonObject = body.getJSONObject(i)

                            val userModel = UserModel()
                            itemJsonObject.parseInt("id")?.let {
                                userModel.id = it
                            }
                            itemJsonObject.parseString("name")?.let {
                                userModel.name = it
                            }
                            itemJsonObject.parseString("email")?.let {
                                userModel.email = it
                            }
                            itemJsonObject.parseString("gender")?.let {
                                if (it.equals(GenderEnum.MALE.name, ignoreCase = true)) {
                                    userModel.gender = GenderEnum.MALE
                                } else if (it.equals(GenderEnum.FEMALE.name, ignoreCase = true)) {
                                    userModel.gender = GenderEnum.FEMALE
                                } else {
                                    userModel.gender = GenderEnum.OTHER
                                }
                            }
                            itemJsonObject.parseString("status")?.let {
                                if (it.equals(UserStatusEnum.ACTIVE.name, ignoreCase = true)) {
                                    userModel.status = UserStatusEnum.ACTIVE
                                } else {
                                    userModel.status = UserStatusEnum.INACTIVE
                                }
                            }
                            dataList.add(userModel)
                        }
                        callback(true, dataList, null)
                    }

                    override fun onError(error: String) {
                        Timber.e("error $error")
                        callback(false, null, error)
                    }

                })
        } catch (conEx: ConnectException) {
            callback(false, null, "Connection refused")
        } catch (ex: Exception) {
            callback(false, null, ex.cause.toString())
        }
    }

    fun updateUserApi(
        tempUser: UserModel, callback: (Boolean, String?) -> Unit
    ) {
        try {
            val url = "${ApiConstants.USERS}/${this.id}"

            val params = HashMap<String, @JvmSuppressWildcards Any?>()
            params["name"] = tempUser.name
            params["email"] = tempUser.email
            params["gender"] = tempUser.gender.value.lowercase()
            params["status"] = tempUser.status.value.lowercase()

            NetworkManager().putRequest(
                url = url,
                params = params,
                listener = object :
                    RequestListener {
                    override fun onSuccess(response: Any?) {
                        val responseObject = (response as? Response<*>)
                        val responseBody = responseObject?.body().toString()
                        val body = JSONObject(responseBody)

                        body.parseInt("id")?.let {
                            this@UserModel.id = it
                        }
                        body.parseString("name")?.let {
                            this@UserModel.name = it
                        }
                        body.parseString("email")?.let {
                            this@UserModel.email = it
                        }
                        body.parseString("gender")?.let {
                            if (it.equals(GenderEnum.MALE.name, ignoreCase = true)) {
                                this@UserModel.gender = GenderEnum.MALE
                            } else if (it.equals(GenderEnum.FEMALE.name, ignoreCase = true)) {
                                this@UserModel.gender = GenderEnum.FEMALE
                            } else {
                                this@UserModel.gender = GenderEnum.OTHER
                            }
                        }
                        body.parseString("status")?.let {
                            if (it.equals(UserStatusEnum.ACTIVE.name, ignoreCase = true)) {
                                this@UserModel.status = UserStatusEnum.ACTIVE
                            } else {
                                this@UserModel.status = UserStatusEnum.INACTIVE
                            }
                        }
                        callback(true, null)
                    }

                    override fun onError(error: String) {
                        Timber.e("error $error")
                        callback(false, error)
                    }

                })
        } catch (conEx: ConnectException) {
            callback(false, "Connection refused")
        } catch (ex: Exception) {
            callback(false, ex.cause.toString())
        }
    }

    fun createUserApi(
        callback: (Boolean, String?) -> Unit
    ) {
        try {
            val url = ApiConstants.USERS

            val params = HashMap<String, @JvmSuppressWildcards Any?>()
            params["name"] = this.name
            params["email"] = this.email
            params["gender"] = this.gender.value.lowercase()
            params["status"] = this.status.value.lowercase()

            NetworkManager().postRequest(
                url = url,
                params = params,
                listener = object :
                    RequestListener {
                    override fun onSuccess(response: Any?) {
                        val responseObject = (response as? Response<*>)
                        val responseBody = responseObject?.body().toString()
                        val body = JSONObject(responseBody)

                        body.parseInt("id")?.let {
                            this@UserModel.id = it
                        }
                        body.parseString("name")?.let {
                            this@UserModel.name = it
                        }
                        body.parseString("email")?.let {
                            this@UserModel.email = it
                        }
                        body.parseString("gender")?.let {
                            if (it.equals(GenderEnum.MALE.name, ignoreCase = true)) {
                                this@UserModel.gender = GenderEnum.MALE
                            } else if (it.equals(GenderEnum.FEMALE.name, ignoreCase = true)) {
                                this@UserModel.gender = GenderEnum.FEMALE
                            } else {
                                this@UserModel.gender = GenderEnum.OTHER
                            }
                        }
                        body.parseString("status")?.let {
                            if (it.equals(UserStatusEnum.ACTIVE.name, ignoreCase = true)) {
                                this@UserModel.status = UserStatusEnum.ACTIVE
                            } else {
                                this@UserModel.status = UserStatusEnum.INACTIVE
                            }
                        }
                        callback(true, null)
                    }

                    override fun onError(error: String) {
                        Timber.e("error $error")
                        callback(false, error)
                    }

                })
        } catch (conEx: ConnectException) {
            callback(false, "Connection refused")
        } catch (ex: Exception) {
            callback(false, ex.cause.toString())
        }
    }
}
