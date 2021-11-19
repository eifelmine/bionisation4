package com.eifel.bionisation4.common.storage.capability.player

import java.util.concurrent.Callable

class BioPlayerFactory(): Callable<IBioPlayer> {

    override fun call(): IBioPlayer = BioPlayer()
}