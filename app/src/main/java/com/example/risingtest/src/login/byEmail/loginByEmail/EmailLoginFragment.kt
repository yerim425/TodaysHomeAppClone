package com.example.risingtest.src.login.byEmail.loginByEmail

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentEmailLoginBinding
import com.example.risingtest.src.login.LoginActivity
import com.example.risingtest.src.login.byEmail.byEmailModels.LogInResponse
import com.example.risingtest.src.login.byEmail.byEmailModels.PostLogInRequest
import com.example.risingtest.src.main.MainActivity


class EmailLoginFragment : BaseFragment<FragmentEmailLoginBinding>(
    FragmentEmailLoginBinding::bind, R.layout.fragment_email_login),
    LoginActivity.onBackPressedListener, EmailLoginFragmentInterface {

    var isActived = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.edtEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0 != null && !binding.edtEmail.text.toString().equals("")){
                    if(!binding.edtPassword.text.toString().equals("")){
                        isActived = true
                        binding.btnLogin.background = resources.getDrawable(R.drawable.shape_login_signup_blue_btn_enabled)
                    }else{
                        isActived = false
                        binding.btnLogin.background = resources.getDrawable(R.drawable.shape_login_signup_blue_btn_disabled)
                    }
                }else{
                    isActived = false
                    binding.btnLogin.background = resources.getDrawable(R.drawable.shape_login_signup_blue_btn_disabled)
                }

            }
        })
        binding.edtPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0 != null && !binding.edtPassword.text.toString().equals("")){
                    if(!binding.edtEmail.text.toString().equals("")){
                        isActived = true
                        binding.btnLogin.background = resources.getDrawable(R.drawable.shape_login_signup_blue_btn_enabled)
                    }else{
                        isActived = false
                        binding.btnLogin.background = resources.getDrawable(R.drawable.shape_login_signup_blue_btn_disabled)
                    }
                }else{
                    isActived = false
                    binding.btnLogin.background = resources.getDrawable(R.drawable.shape_login_signup_blue_btn_disabled)
                }
            }
        })


        binding.btnLogin.setOnClickListener {

            if(!isActived){
                if(binding.edtEmail.text.toString() == ""){
                    showCustomToast("이메일을 입력해주세요")
                }else {
                    showCustomToast("비밀번호를 입력해주세요.")
                }
            }else{
                var email = binding.edtEmail.text.toString()
                var password = binding.edtPassword.text.toString()

                val postRequest = PostLogInRequest(email = email, password = password)
                //showLoadingDialog(requireContext())
                EmailLoginService(this).tryPostLogIn(postRequest)

            }
        }

        binding.btnBack.setOnClickListener {
            backFragment()
        }

    }
    override fun onBackPressed() {
        backFragment()
    }


    override fun onPostLogInSuccess(response: LogInResponse) {
        //dismissLoadingDialog()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    override fun onPostLogInFailure(message: String) {
        Toast.makeText(requireContext(), getString(R.string.email_login_mismatch), Toast.LENGTH_LONG).show()
    }


}