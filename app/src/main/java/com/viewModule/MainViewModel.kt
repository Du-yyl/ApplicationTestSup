package com.viewModule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.android.datastore.UserPreferences
import com.dataStorage.dataStore.DataStorePreferencesHelper
import com.dataStorage.dataStore.DataStoreProtoHelper
import com.dataStorage.dataStore.storageServices.UserListRepository
import com.dataStorage.dataStore.storageServices.UserRepository
import com.dataStorage.roomData.RoomDatabaseHelper
import com.dataStorage.roomData.entity.User
import com.dataStorage.roomData.services.UserService
import com.dataStorage.sharedPref.SharePrefHelper
import com.enums.UserTypeEnum
import com.expand.toJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @time :2024/1/3 17:15 57
 * @className :MainViewMoudle
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */


class MainViewModel : ViewModel() {

}