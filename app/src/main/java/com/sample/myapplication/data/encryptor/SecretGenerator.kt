package com.sample.myapplication.data.encryptor

import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

interface SecretGenerator  {
     fun generateSecret(password: String, salt: ByteArray): SecretKey
}