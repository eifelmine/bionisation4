package com.eifel.bionisation4.common.storage.capability.entity

import java.util.concurrent.Callable

class BioStatFactory(): Callable<IBioStat> {

    override fun call(): IBioStat = BioStat()
}