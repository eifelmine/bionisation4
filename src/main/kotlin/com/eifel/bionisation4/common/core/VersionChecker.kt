package com.eifel.bionisation4.common.core

import com.eifel.bionisation4.Info
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class VersionChecker : Runnable {

    companion object {
        var checker: VersionChecker? = null
        var wasWarned = false
    }

    var newVersionURL = ""
    val changes = mutableListOf<String>()
    var isLatestVersion = false
    var latestVersion = ""

    override fun run() {
        try {
            URL("").openStream()
                .use { ins ->
                    val version = IOUtils.readLines(ins)[0].split("_").toTypedArray()
                    latestVersion = version[0]
                    newVersionURL = version[1]
                    changes.addAll(version.drop(2))
                }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        isLatestVersion = Info.MOD_VERSION == latestVersion
    }
}