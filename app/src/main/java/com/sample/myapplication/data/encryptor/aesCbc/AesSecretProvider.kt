package com.sample.myapplication.data.encryptor.aesCbc

import com.sample.myapplication.data.encryptor.SecretGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class AesSecretProvider: SecretGenerator {
    private val secretKeyAlgo: String = "AES"
    private val keyAlgorithm: String = "PBKDF2WithHmacSHA1"
    private val keyLength: Int = 256

    override fun generateSecret(password: String, salt: ByteArray): SecretKey {
        val factory = SecretKeyFactory.getInstance(keyAlgorithm)
        val pbeKeySpec = PBEKeySpec(password.toCharArray(), salt, 10000,keyLength)
        val tmp = factory.generateSecret(pbeKeySpec)
        return SecretKeySpec(tmp.encoded, secretKeyAlgo)
    }
}