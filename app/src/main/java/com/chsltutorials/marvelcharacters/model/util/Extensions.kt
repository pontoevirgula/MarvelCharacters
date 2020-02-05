package com.chsltutorials.marvelcharacters.model.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.security.NoSuchAlgorithmException

fun ImageView.load(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun String.md5(): String {
    try {
        val digest = java.security.MessageDigest.getInstance("MD5")
        digest.update(toByteArray())
        val messageDigest = digest.digest()
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2)
                h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()

    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}
