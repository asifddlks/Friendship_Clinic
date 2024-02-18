
package dev.asifddlks.friendshipclinic.network

import java.io.IOException

class NoConnectivityException : IOException() {
    // You can send any message whatever you want from here.
    override val message: String
        get() = "Please check your internet connection then try again."
    // You can send any message whatever you want from here.
}