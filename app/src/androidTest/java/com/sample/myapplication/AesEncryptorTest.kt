package com.sample.myapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sample.myapplication.data.encryptor.aesCbc.AesCbcEncryptor
import com.sample.myapplication.data.encryptor.aesCbc.AesSecretProvider
import com.sample.myapplication.data.encryptor.keystore.KeyStoreSecretProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.security.SecureRandom


@RunWith(AndroidJUnit4::class)
class AesEncryptorTest {

    private val password="12345"

    /**
     * Scenario:Encrypt and Decrypt a text
     *
     * Given a Password and encrypted text
     *
     * When [AesCbcEncryptor] decrypt() is executed
     *
     * Then the text is  decrypted  successfully
     *
     */
    @Test
    fun testEncryptAndDecryptForAes() = runBlocking {

        //Given
        val encryptor = AesCbcEncryptor(AesSecretProvider())
        val text = "This text is going to be encrypted"
        val encryptedText = encryptor.encrypt(text, password)

        //When
        val decryptedText = encryptor.decrypt(encryptedText, password)

        //Then
        assertEquals(text, decryptedText)
    }

    /**
     * Scenario: Encryptor generastes a secret key
     *
     * Given a random salt
     *
     * When [KeyStoreSecretProvider] generateSecret() is called twice with the same Salt
     *
     * Then both secrets are equal
     *
     */
    @Test
    fun tesAesSecretProvider() = runBlocking {
        //Given
        val secretProvider = AesSecretProvider()
        val salt = ByteArray(256)
        SecureRandom().nextBytes(salt)

        //When
        val key1 = secretProvider.generateSecret(password, salt)
        val key2 = secretProvider.generateSecret(password, salt)

        //Then
        assertEquals(key1, key2)
    }
}