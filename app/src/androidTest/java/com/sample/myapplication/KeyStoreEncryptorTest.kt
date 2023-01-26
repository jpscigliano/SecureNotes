package com.sample.myapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sample.myapplication.data.encryptor.keystore.KeyStoreEncryptor
import com.sample.myapplication.data.encryptor.keystore.KeyStoreSecretProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.security.SecureRandom


@RunWith(AndroidJUnit4::class)
class KeyStoreEncryptorTest {
    private val password = "12345"

    /**
     * Scenario:Encrypt and Decrypt a text
     *
     * Given a Password and encrypted text
     *
     * When [KeyStoreEncryptor] decrypt() is called
     *
     * Then the text is decrypted  successfully
     *
     */
    @Test
    fun testEncryptDecryptForKeyStore() = runBlocking {
        //Given
        val encryptor = KeyStoreEncryptor(KeyStoreSecretProvider())
        val text = "This text is going to be encrypted"

        //When
        val encryptedText = encryptor.encrypt(text, password)
        val decryptedText = encryptor.decrypt(encryptedText, password)

        //Then
        assertEquals(text, decryptedText)
    }

    /**
     * Scenario: Encryptor generastes a secret key
     *
     * Given a random salt
     *
     * When [KeyStoreSecretProvider] generateSecret() is called
     *
     * Then both secrets are equals
     *
     */
    @Test
    fun tesKeyStoreSecretProvider() = runBlocking {
        //Given
        val secretProvider = KeyStoreSecretProvider()
        val salt = ByteArray(256)
        SecureRandom().nextBytes(salt)

        //When
        val key1 = secretProvider.generateSecret(password, salt)
        val key2 = secretProvider.generateSecret(password, salt)

        //Then
        assertEquals(key1, key2)
    }

}