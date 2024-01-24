package com.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.applicationtest.R
import com.example.applicationtest.databinding.FragmentToastyTestBinding
import es.dmoral.toasty.Toasty

/**
 * @time :2024/1/24 23:11 04
 * @className :ToastyTestFragment
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class ToastyTestFragment : Fragment() {

    private lateinit var binding: FragmentToastyTestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToastyTestBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.fragmentToastyError.setOnClickListener {
            Toasty.error(
                requireContext(),
                "This is an error toast.",
                Toast.LENGTH_SHORT, true
            ).show();
        }
        binding.fragmentToastySuccess.setOnClickListener {
            Toasty.success(
                requireContext(),
                "Success!",
                Toast.LENGTH_SHORT, true
            ).show();
        }
        binding.fragmentToastyInfo.setOnClickListener {
            Toasty.info(requireContext(), "Here is some info for you.", Toast.LENGTH_SHORT, true)
                .show();
        }
        binding.fragmentToastyWarning.setOnClickListener {
            Toasty.warning(requireContext(), "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
        }
        binding.fragmentToastyNormal.setOnClickListener {
            Toasty.normal(requireContext(), "Normal toast w/o icon").show();
        }
        binding.fragmentToastyCustom.setOnClickListener {
            Toasty.custom(
                requireContext(),
                "I'm a custom Toast",
                R.drawable.img_example,
                splitties.material.colors.R.color.red_50,
                Toast.LENGTH_SHORT,
                true,
                true
            ).show();

        }
    }
}