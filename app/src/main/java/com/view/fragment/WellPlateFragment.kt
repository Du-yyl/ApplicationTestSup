package com.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.applicationtest.R


/**
 * @time :2024/1/24 11:18 40
 * @className :WellPlateFragment
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class WellPlateFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wellplate, container, false);
    }
}
