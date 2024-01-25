package com.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.applicationtest.databinding.FragmentDataStorageBinding
import com.viewModel.DataStorageFragmentViewModel

/**
 * @time :2024/1/25 8:52 59
 * @className :DataStorageFragment
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class DataStorageFragment : Fragment() {
    private lateinit var binding: FragmentDataStorageBinding
    private val viewModel by viewModels<DataStorageFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataStorageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // DataStorePref
        binding.DataStorePrefUpdate.setOnClickListener {
            viewModel.dataStorePrefUpdate()
        }
        binding.DataStorePrefSaveData.setOnClickListener {
            viewModel.dataStorePrefSaveData()
        }

        // dataStoreProto
        binding.DataStoreProtoUpdate.setOnClickListener {
            viewModel.dataStoreProtoUpdate()
        }
        binding.DataStoreProtoSaveData.setOnClickListener {
            viewModel.dataStoreProtoSaveData()
        }

        binding.DataStoreProtoAdd.setOnClickListener {
            viewModel.dataStoreProtoAdd()
        }
        binding.DataStoreProtoGetList.setOnClickListener {
            viewModel.dataStoreProtoGetList()
        }
        binding.DataStoreProtoUpdateForList.setOnClickListener {
            viewModel.dataStoreProtoUpdateForList()
        }

        // ShareP数据操作
        binding.sharePUpdata.setOnClickListener {
            viewModel.sharePUpdata()
        }
        binding.sharePGetData.setOnClickListener {
            viewModel.sharePGetData()
        }

        // RoomDatabase 数据操作
        binding.RoomDataInsert.setOnClickListener {
            viewModel.roomDataInsert()
        }
        binding.RoomDataFind.setOnClickListener {
            viewModel.roomDataFind()
        }


    }
}