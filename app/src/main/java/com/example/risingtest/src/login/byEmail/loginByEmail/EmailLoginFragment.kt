package com.example.risingtest.src.login.byEmail.loginByEmail

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentEmailLoginBinding
import com.example.risingtest.src.login.LoginActivity
import com.example.risingtest.src.login.byEmail.loginByEmail.models.EmailLogInResponse
import com.example.risingtest.src.login.byEmail.loginByEmail.models.PostEmailLogInRequest
import com.example.risingtest.src.main.MainActivity


class EmailLoginFragment : BaseFragment<FragmentEmailLoginBinding>(
    FragmentEmailLoginBinding::bind, R.layout.fragment_email_login),
    LoginActivity.onBackPressedListener, EmailLoginFragmentInterface {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.edtEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0 != null && !binding.edtEmail.text.toString().equals("")){
                    if(!binding.edtPassword.text.toString().equals("")){
                        binding.btnLogin.background = resources.getDrawable(R.drawable.shape_blue_btn_enabled)
                    }else{
                        binding.btnLogin.background = resources.getDrawable(R.drawable.shape_blue_btn_disabled)
                    }
                }else{
                    binding.btnLogin.background = resources.getDrawable(R.drawable.shape_blue_btn_disabled)
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
                        binding.btnLogin.background = resources.getDrawable(R.drawable.shape_blue_btn_enabled)
                    }else{
                        binding.btnLogin.background = resources.getDrawable(R.drawable.shape_blue_btn_disabled)
                    }
                }else{
                    binding.btnLogin.background = resources.getDrawable(R.drawable.shape_blue_btn_disabled)
                }
            }
        })


        binding.btnLogin.setOnClickListener {

            if(binding.edtEmail.text.toString().equals("")){
                showCustomToast("이메일을 입력해주세요")
            }else if(binding.edtPassword.text.toString().equals("")){
                showCustomToast("비밀번호를 입력해주세요.")

            }else{
                var email = binding.edtEmail.text.toString()
                var password = binding.edtPassword.text.toString()

                val postRequest = PostEmailLogInRequest(email = email, password = password)
                showTextLoadingDialog(requireContext(), "로그인 중입니다.")
                EmailLoginService(this).tryPostLogIn(postRequest)

            }
        }

        binding.btnBack.setOnClickListener {
            backFragment()
        }

    }
    override fun onBackPressed(){
        backFragment()
    }


    override fun onPostLogInSuccess(response: EmailLogInResponse) {

        // result 값 sp에 저장
        var jwt = response.result.jwt
        var userIdx = response.result.userIdx.toString()
        var editor = ApplicationClass.sSharedPreferences.edit()
        editor.putString("X-ACCESS-TOKEN", jwt)
        editor.putString("userIdx", userIdx)
        editor.commit()

        dismissTextLoadingDialog()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    override fun onPostLogInFailure(message: String) {
        dismissTextLoadingDialog()
        showCustomToast(message)
    }


}