package com.eifel.bionisation4.common.extensions

import net.minecraftforge.items.ItemStackHandler

inline fun <reified E : Any> Collection<*>.toTypedList()  = this as List<E>
inline fun ItemStackHandler.getAllItems()  = Array(slots){ getStackInSlot(it) }.toMutableList()