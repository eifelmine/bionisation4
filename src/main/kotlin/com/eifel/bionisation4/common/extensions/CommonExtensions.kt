package com.eifel.bionisation4.common.extensions

inline fun <reified E : Any> Collection<*>.toTypedList()  = this as List<E>