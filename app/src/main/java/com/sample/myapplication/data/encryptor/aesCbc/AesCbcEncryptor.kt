package com.sample.myapplication.data.encryptor.aesCbc

import com.sample.myapplication.data.encryptor.BaseEncryptor
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.spec.IvParameterSpec

class AesCbcEncryptor(aesSecretProvider: AesSecretProvider) : BaseEncryptor(aesSecretProvider) {

    override val cipherAlgorithm: String = "AES/CBC/PKCS7Padding"
    override val saltSize: Int = 256
    override val ivSize: Int = 16

    override fun getAlgorithmParameterSpec(iv: ByteArray): AlgorithmParameterSpec {
        return IvParameterSpec(iv)
    }
}