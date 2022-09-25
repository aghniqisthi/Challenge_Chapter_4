package com.example.challengechapter4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.challengechapter4.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    lateinit var sharedPref : SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireActivity().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        var dbUser = sharedPref.getString("username", "")

        if(dbUser != "") {
            var dbUsername = sharedPref.getString("username", "You!")
            var bundle = Bundle()
            bundle.putString("username", dbUsername)

            Handler().postDelayed({
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment, bundle)
            }, 3000)
        }
        else {
            Handler().postDelayed({
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
            }, 3000)
        }
    }
}