package com.eifel.bionisation4.common.storage.capability.entity

import java.util.concurrent.Callable

class BioMobFactory(): Callable<IBioMob> {

    override fun call(): IBioMob = BioMob()
}