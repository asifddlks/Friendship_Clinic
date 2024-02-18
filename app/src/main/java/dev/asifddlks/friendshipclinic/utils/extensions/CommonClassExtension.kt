package dev.asifddlks.bhaibhaiclinicApp.utils.extensions

fun Any.getCurrentClassName(): String? {
    return Thread.currentThread().stackTrace[3].className
}

fun Any.getCurrentMethodName(): String? {
    return Thread.currentThread().stackTrace[3].methodName
    //return object{}.javaClass.enclosingMethod?.name
}
