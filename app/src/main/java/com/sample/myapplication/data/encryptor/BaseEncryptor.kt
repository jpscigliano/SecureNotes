package com.sample.myapplication.data.encryptor

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.SecureRandom
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher
import javax.crypto.SecretKey

abstract class BaseEncryptor(generator: SecretGenerator) : Encryptor, SecretGenerator by generator {

    protected abstract val cipherAlgorithm: String

    protected abstract val saltSize: Int
    protected abstract val ivSize: Int

    protected abstract fun getAlgorithmParameterSpec(iv: ByteArray): AlgorithmParameterSpec

    override suspend fun encrypt(plainText: String, password: String): String =
        withContext(Dispatchers.IO) {
            val salt = ByteArray(saltSize)
            SecureRandom().nextBytes(salt)

            val secret: SecretKey = generateSecret(password, salt)

            val cipher = Cipher.getInstance(cipherAlgorithm)
            cipher.init(Cipher.ENCRYPT_MODE, secret)
            val iv = cipher.iv

            val encrypted = cipher.doFinal(plainText.toByteArray())
            val output = salt + iv + encrypted
            Base64.encodeToString(output, Base64.DEFAULT)
        }

    override suspend fun decrypt(encryptedText: String, password: String): String =
        withContext(Dispatchers.IO) {
            val base64Decoded = Base64.decode(encryptedText, Base64.DEFAULT)

            val salt = base64Decoded.take(saltSize).toByteArray()
            val iv = base64Decoded.copyOfRange(saltSize, saltSize + ivSize)
            val encryptedData = base64Decoded.drop(saltSize + ivSize).toByteArray()
            val secret = generateSecret(password, salt)

            val cipher = Cipher.getInstance(cipherAlgorithm)
            cipher.init(Cipher.DECRYPT_MODE, secret, getAlgorithmParameterSpec(iv))
            val decryptedBytes = cipher.doFinal(encryptedData)
            String(decryptedBytes)
        }


}