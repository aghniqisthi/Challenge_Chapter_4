package com.example.challengechapter4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.challengechapter4.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    lateinit var sharedPref : SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireActivity().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        btnDaftar.setOnClickListener {
            var getUsername = binding.editUsername.text.toString()
            var getEmail = binding.editEmail.text.toString()
            var getPass = binding.editPassword.text.toString()
            var getPassConf = binding.editConfirmPassword.text.toString()

            //seleksi pass dgn confirm pass. kalo sama, ntar add ke sharedpref trus pindah ke login
            if(getPass.equals(getPassConf)){
                var addUser = sharedPref.edit()
                addUser.putString("username", getUsername)
                addUser.putString("email", getEmail)
                addUser.putString("password", getPass)
                addUser.apply()
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
            }
            //kalo salah, toast Invalid Password!
            else Toast.makeText(context, "Password Invalid!", Toast.LENGTH_SHORT).show()
        }
    }

}