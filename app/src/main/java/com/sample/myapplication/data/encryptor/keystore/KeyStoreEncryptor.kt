package com.sample.myapplication.data.encryptor.keystore

import com.sample.myapplication.data.encryptor.BaseEncryptor
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.spec.GCMParameterSpec

class KeyStoreEncryptor(keyStoreSecretProvider: KeyStoreSecretProvider) : BaseEncryptor(keyStoreSecretProvider) {

    private val keyAlias="Test"

    override val cipherAlgorithm: String = "AES/GCM/NoPadding"
    override val saltSize: Int = 0
    override val ivSize: Int = 12

    override fun getAlgorithmParameterSpec(iv: ByteArray): AlgorithmParameterSpec {
       return GCMParameterSpec(128, iv)
    }

}