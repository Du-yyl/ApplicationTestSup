package com.utils

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.constant.ConstName
import com.constant.ConstName.APP_DEFAULT_KEY_ALIAS
import com.dataStorage.roomData.RoomDatabaseHelper
import com.dataStorage.sharedPref.SharePrefHelper
import com.example.applicationtest.R
import splitties.init.appCtx
import java.security.KeyPairGenerator
import javax.crypto.Cipher


/**
 * @time :2024/1/15 21:14 43
 * @className :KeystoreUtil
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */

// todo: 2024/1/15 21:20 数据加密 【密码，软件，自定义加密和自定义解密】
class KeystoreUtil {

    companion object {
        private var INSTANCE: KeyPairGenerator? = null

        fun getInstance(): KeyPairGenerator {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_RSA, R.string.app_name.toString()
                ).also {
                    INSTANCE = it
                }
            }
        }

        /**
         * 获取默认的参数指定
         */
        fun getDefaultKeyGenParameterSpec() = KeyGenParameterSpec.Builder(
            APP_DEFAULT_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setKeySize(2048)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .build()
    }

    fun func() {
        val keyPairGenerator = getInstance()
        val keyGenParameterSpec = getDefaultKeyGenParameterSpec()
        keyPairGenerator.initialize(keyGenParameterSpec)

        val keyPair = keyPairGenerator.generateKeyPair()

        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.public)

        val encryptedData = cipher.doFinal(byteArrayOf(11))


//        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
//        cipher.init(Cipher.DECRYPT_MODE, keyPair.private)
//
//        val decryptedData = cipher.doFinal(encryptedData)
//        val decryptedText = kotlin.String(decryptedData)

    }
}