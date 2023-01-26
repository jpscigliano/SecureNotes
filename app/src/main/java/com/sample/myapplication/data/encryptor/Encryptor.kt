package com.sample.myapplication.data.encryptor

import java.security.InvalidKeyException

interface Encryptor {

    suspend fun encrypt(plainText: String, password: String): String

    @Throws(InvalidKeyException::class)
    suspend fun decrypt(encryptedText: String, password: String): String

}