package com.dataStorage.dataStore.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.codelab.android.datastore.UserList
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/**
 * @time :2024/1/4 17:12 45
 * @className :UserPreferencesSerializer
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
object UserListPreferencesSerializer : Serializer<UserList> {
    override val defaultValue: UserList = UserList.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): UserList {
        try {
            return UserList.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserList, output: OutputStream) = t.writeTo(output)
}
