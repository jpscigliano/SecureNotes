package com.sample.myapplication.mock

import com.sample.myapplication.data.encryptor.Encryptor

class FakeEncryptor : Encryptor {
    override suspend fun encrypt(plainText: String, password: String): String =
        plainText + password

    override suspend fun decrypt(encryptedText: String, password: String): String =
        encryptedText.replace(password, "")

}