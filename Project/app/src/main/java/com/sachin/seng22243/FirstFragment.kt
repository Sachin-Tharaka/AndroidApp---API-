package com.sachin.seng22243

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sachin.seng22243.api.UserAPIService
import com.sachin.seng22243.databinding.FragmentFirstBinding
import com.sachin.seng22243.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userAPIService = UserAPIService.create()

    //Only valid between onCreateView and on DestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonFirst.setOnClickListener {
         //   findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            val id = binding.editTextNumber.editableText

            val user = userAPIService.getUser(id.toString())
        user.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val body = response.body()
                body?.let {
                    binding.textviewMail.text = "Email : "+it.email
                    binding.textviewName.text= "Name : "+it.name
                    binding.textviewUsername.text= "User Name : "+it.username

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                binding.textviewMail.text = "An Error Occurred!.Invalid User Id"

            }
        })


         }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null

    }
}