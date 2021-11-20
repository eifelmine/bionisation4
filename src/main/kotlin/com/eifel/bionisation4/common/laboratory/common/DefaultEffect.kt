package com.eifel.bionisation4.common.laboratory.common

import com.eifel.bionisation4.api.laboratory.species.AbstractEffect

class DefaultEffect() : AbstractEffect(0)

class DefaultStateEffect(id: Int) : AbstractEffect(id) {
    constructor() : this(0)
}
