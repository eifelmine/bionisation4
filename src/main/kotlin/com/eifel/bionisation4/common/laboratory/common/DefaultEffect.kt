package com.eifel.bionisation4.common.laboratory.common

import com.eifel.bionisation4.api.laboratory.species.AbstractEffect

class DefaultEffect() : AbstractEffect(0) {
    override fun getCopy() = DefaultEffect()
}

class DefaultStateEffect(id: Int) : AbstractEffect(id, "Default State Effect") {
    constructor() : this(0)
    override fun getCopy() = DefaultStateEffect(0)
}
