package com.example.risingtest.src.login.byEmail.signUpByEmail

import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CheckBox
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentEmailSignUpBinding
import com.example.risingtest.src.login.LoginActivity
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.EmailSignUpResponse
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.PostEmailSignUpRequest
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.UserResponse
import com.example.risingtest.src.main.MainActivity
import java.util.*
import java.util.regex.Pattern


class EmailSignUpFragment : BaseFragment<FragmentEmailSignUpBinding>(
    FragmentEmailSignUpBinding::bind, R.layout.fragment_email_sign_up
),
    LoginActivity.onBackPressedListener, EmailSignUpFragmentInterface {

    val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    val pwPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$")

    var checkEmail = false
    var checkPassword = false
    var checkPasswordConfirm = false
    var checkNickname = false
    var checkAgreement = false

    var recommendName = ""

    var emailList = mutableListOf<String>() // for 이메일 중복 확인
    var nicknameList = mutableListOf<String>() // for 닉네임 중복 확인

    var checkBoxList = mutableListOf<CheckBox>()
    var checkBoxEssentialList = mutableListOf<CheckBox>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setOnEditTextListener()

        setOnCheckBoxListener()


        // 모든 유저 조회하기
        showLoadingDialog(requireContext())
        EmailSignUpService(this).tryGetUser()


        binding.btnBack.setOnClickListener { backFragment() }

        binding.btnSignupComplete.setOnClickListener {
            if(checkAllInput()){
                // 회원가입
                val postRequest = PostEmailSignUpRequest(
                    email = binding.edtEmail.text.toString(),
                    password = binding.edtPassword.text.toString(),
                    nickname = binding.edtNickname.text.toString(),
                    isMarketing = if(binding.cbAgreeUseMarketing.isChecked){"T"} else "F",
                    isReceive = if(binding.cbReceiveNotification.isChecked){"T"} else "F"
                )
                showTextLoadingDialog(requireContext(), "가입중입니다.")
                EmailSignUpService(this).tryPostSignUp(postRequest)


            }else{
                showCustomToast("필수 사항을 확인해주세요.")
            }
        }
    }


    // 모든 유저 조회 요청
    override fun onGetUserSuccess(response: UserResponse) {
        for(i in 0 until response.result.size){
            emailList.add(response.result[i].email)
            nicknameList.add(response.result[i].nickname)
        }
        dismissLoadingDialog()
    }

    override fun onGetUserFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("모든 유저 조회 실패")
    }



    // 회원 가입 요청
    override fun onPostSignUpSuccess(response: EmailSignUpResponse) {
        dismissTextLoadingDialog()

        // result 값 sp에 저장
        var jwt = response.result.jwt
        var userIdx = response.result.userIdx.toString()
        var userNickname = binding.edtNickname.text.toString()
        var editor = sSharedPreferences.edit()
        editor.putString("X-ACCESS-TOKEN", jwt)
        editor.putString("userIdx", userIdx)
        editor.putString("userNickName", userNickname)
        editor.commit()

        var intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("preActivity", "login")
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

    override fun onPostSignUpFailure(message: String) {
        dismissTextLoadingDialog()
        showCustomToast("회원 가입 실패")
    }




    override fun onBackPressed() {
        backFragment()
    }


    fun setOnEditTextListener() {

        // 이메일 edit text
        binding.edtEmail.setOnFocusChangeListener { view, b ->
            if (!b) { // focus가 아니라면
                if (binding.edtEmail.text.toString().equals("")) {
                    isEnabledEmailBtn(false, R.string.email_signup_warning_email_empty)
                } else if (!emailPattern.matcher(binding.edtEmail.text.toString()).matches()) {
                    isEnabledEmailBtn(false, R.string.email_signup_warning_email_format_invalied)
                }
            }
        }
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                var inputEmail = binding.edtEmail.text.toString()
                if (p0 != null && !inputEmail.equals("")) { // 공백이 아닐경우
                    if (!emailPattern.matcher(inputEmail).matches()) {// 이메일 형식이 아니라면
                        isEnabledEmailBtn(
                            false,
                            R.string.email_signup_warning_email_format_invalied
                        )
                        checkEmail = false
                    } else if (emailPattern.matcher(inputEmail).matches()) { // 이메일 형식이 맞다면

                        if(emailList.contains(inputEmail)){
                            isEnabledEmailBtn(
                                false,
                                R.string.email_signup_warning_email_already_subscribed_by_email
                            )
                            return
                        }

                        isEnabledEmailBtn(true)

                    }
                }
                checkAllInput()

            }
        })

        // 비밀번호 edit text
        binding.edtPassword.setOnFocusChangeListener { view, b ->
            if (!b) { // focus가 아니라면
                if (binding.edtPassword.text.toString().equals("")) {
                    setEdgeColor(binding.tvWarningPassword, binding.layoutPassword, false)
                }
            }
        }
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                var inputPassword = binding.edtPassword.text.toString()
                if (p0 != null && !inputPassword.equals("")) { // 공백이 아닐경우
                    if (pwPattern.matcher(inputPassword).find()) { //  inputPassword.matches(pwPattern.toRegex())
                        setEdgeColor(binding.tvWarningPassword, binding.layoutPassword, true)
                        checkPassword = true

                    } else {
                        setEdgeColor(binding.tvWarningPassword, binding.layoutPassword, false)
                        checkPassword = false

                    }

                    if (!binding.edtPasswordConfirm.text.toString().equals("")){
                        if(!inputPassword.equals(binding.edtPasswordConfirm.text.toString())){
                            setEdgeColor(binding.tvWarningPasswordConfirm, binding.layoutPasswordConfirm, false)
                            checkPasswordConfirm = false
                        }else{
                            setEdgeColor(binding.tvWarningPasswordConfirm, binding.layoutPasswordConfirm, true)
                            checkPasswordConfirm = true
                        }
                    }
                    checkAllInput()

                }
            }
        })

        // 비밀번호 확인 edit text
        binding.edtPasswordConfirm.setOnFocusChangeListener { view, b ->
            if (!b) { // focus가 아니라면
                if(binding.edtPasswordConfirm.text.toString().equals("")){
                    binding.tvWarningPasswordConfirm.setText(R.string.email_signup_warning_password_confirm_empty)
                    setEdgeColor(binding.tvWarningPasswordConfirm, binding.layoutPasswordConfirm, false)
                }
                else if (!binding.edtPasswordConfirm.text.toString().equals(binding.edtPassword.text.toString())) { // 비밀번호가 불일치라면
                    binding.tvWarningPasswordConfirm.setText(R.string.email_signup_warning_password_confirm_mismatch)
                    setEdgeColor(binding.tvWarningPasswordConfirm, binding.layoutPasswordConfirm, false)
                }

            }
        }
        binding.edtPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                var inputConfirmPassword = binding.edtPasswordConfirm.text.toString()
                if (p0 != null && !inputConfirmPassword.equals("")) { // 공백이 아닐경우
                    if (inputConfirmPassword.equals(binding.edtPassword.text.toString())) { // 비밀번호가 일치한다면
                        setEdgeColor(binding.tvWarningPasswordConfirm, binding.layoutPasswordConfirm, true)
                        checkPasswordConfirm = true

                    } else {
                        binding.tvWarningPasswordConfirm.setText(R.string.email_signup_warning_password_confirm_mismatch)
                        setEdgeColor(binding.tvWarningPasswordConfirm, binding.layoutPasswordConfirm, false)
                        checkPasswordConfirm = false

                    }
                    checkAllInput()

                }
            }
        })

        binding.edtNickname.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                var inputNickname = binding.edtNickname.text.toString()
                if(p0 != null && !inputNickname.equals("")){ // 공백이 아닐경우
                    if(inputNickname.length < 2 || inputNickname.length > 15){ // 2~15자가 아닐경우
                        binding.tvWarningNickname.setText(R.string.email_signup_warning_nickname_format_invalied)
                        setEdgeColor(binding.tvWarningNickname, binding.layoutNickname, false)
                        checkNickname = false
                        checkAllInput()
                        return
                    }
                    if(nicknameList.contains(inputNickname)){ // 이미 존재하는 닉네임 이라면
                        binding.tvWarningNickname.setText(R.string.email_signup_warning_nickname_in_use)
                        setEdgeColor(binding.tvWarningNickname, binding.layoutNickname, false)

                        binding.ivRemoveNickname.visibility = View.VISIBLE
                        binding.ivRemoveNickname.setOnClickListener {
                            binding.ivRemoveNickname.visibility = View.GONE
                            binding.layoutNicknameRecommend.visibility = View.GONE
                            setEdgeColor(binding.tvWarningNickname, binding.layoutNickname, true)
                            binding.edtNickname.setText("")
                        }

                        binding.layoutNicknameRecommend.visibility = View.VISIBLE
                        var rand = Random().nextInt(99) + 1 // 1 ~ 99
                        recommendName = binding.edtNickname.text.toString() + rand.toString()
                        binding.tvNicknameRecommend.text = "'"+ recommendName + "'"

                        binding.btnRecommendedNicknameUse.setOnClickListener {
                            binding.edtNickname.setText(recommendName)
                            if(nicknameList.contains(recommendName)){
                                var rand = Random().nextInt(99) + 1
                                recommendName = binding.edtNickname.text.toString() + rand.toString()
                                binding.tvNicknameRecommend.text = "'" + recommendName + "'"
                            }
                        }
                        checkNickname = false
                        checkAllInput()
                        return
                    }else{
                        binding.ivRemoveNickname.visibility = View.GONE
                        binding.layoutNicknameRecommend.visibility = View.GONE
                        setEdgeColor(binding.tvWarningNickname, binding.layoutNickname, true)
                        checkNickname = true
                        checkAllInput()
                        return
                    }
                }
            }

        })

    }


    fun setOnCheckBoxListener(){

        checkBoxList.add(binding.cbFullAgreement)
        checkBoxList.add(binding.cbOverAge14)
        checkBoxList.add(binding.cbTermsAndConditions)
        checkBoxList.add(binding.cbAgreeCollectAndUse)
        checkBoxList.add(binding.cbAgreeUseMarketing)
        checkBoxList.add(binding.cbReceiveNotification)
        checkBoxEssentialList.add(binding.cbOverAge14)
        checkBoxEssentialList.add(binding.cbTermsAndConditions)
        checkBoxEssentialList.add(binding.cbAgreeCollectAndUse)

        binding.cbFullAgreement.setOnClickListener {
            if(binding.cbFullAgreement.isChecked){
                for(cb in checkBoxList){
                    cb.isChecked = true
                }
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, true)
                checkAgreement = true

            }else{
                for(cb in checkBoxList){
                    cb.isChecked = false
                }
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, false)
                checkAgreement = false
            }
            checkAllInput()
        }

        binding.cbOverAge14.setOnClickListener {
            if(binding.cbOverAge14.isChecked){
                for(cb in checkBoxEssentialList){
                    if(!cb.isChecked){
                      return@setOnClickListener
                    }
                }
                binding.cbFullAgreement.isChecked = true
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, true)
                checkAgreement = true

            }else{
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, false)
                checkAgreement = false
            }
            checkAllInput()
        }
        binding.cbTermsAndConditions.setOnClickListener {
            if(binding.cbTermsAndConditions.isChecked){
                for(cb in checkBoxEssentialList){
                    if(!cb.isChecked){
                        return@setOnClickListener
                    }
                }
                binding.cbFullAgreement.isChecked = true
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, true)
                checkAgreement = true

            }else{
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, false)
                checkAgreement = false
            }
            checkAllInput()
        }
        binding.cbAgreeCollectAndUse.setOnClickListener {
            if(binding.cbAgreeCollectAndUse.isChecked){
                for(cb in checkBoxEssentialList){
                    if(!cb.isChecked){
                        return@setOnClickListener
                    }
                }
                binding.cbFullAgreement.isChecked = true
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, true)
                checkAgreement = true
            }else{
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, false)
                checkAgreement = false
            }
            checkAllInput()
        }
        binding.cbAgreeUseMarketing.setOnClickListener {
            if(binding.cbAgreeUseMarketing.isChecked){
                binding.cbReceiveNotification.isChecked = true
                for(cb in checkBoxEssentialList){
                    if(!cb.isChecked){
                        return@setOnClickListener
                    }
                }
                binding.cbFullAgreement.isChecked = true
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, true)
            }else{
                binding.cbFullAgreement.isChecked = false
                binding.cbReceiveNotification.isChecked = false
            }
        }
        binding.cbReceiveNotification.setOnClickListener {
            if(binding.cbReceiveNotification.isChecked){
                binding.cbAgreeUseMarketing.isChecked = true

                for(cb in checkBoxEssentialList){
                    if(!cb.isChecked){
                        return@setOnClickListener
                    }
                }
                binding.cbFullAgreement.isChecked = true
                setEdgeColor(binding.tvWarningTermsAndConditions, binding.layoutAgreementTermsAndConditons, true)
            }else{
                binding.cbFullAgreement.isChecked = false
            }
        }


    }

    fun checkAllInput(): Boolean{
        Log.d("EmailSignUpFragment", checkEmail.toString() +" " + checkPassword.toString() +" "+
                checkPasswordConfirm.toString() +" "+ checkNickname.toString() +" "+ checkAgreement.toString())

        if(checkEmail && checkPassword && checkPasswordConfirm && checkNickname && checkAgreement){
            binding.btnSignupComplete.background = resources.getDrawable(R.drawable.shape_blue_btn_enabled)
            return true
        }else{
            binding.btnSignupComplete.background = resources.getDrawable(R.drawable.shape_blue_btn_disabled)
            return false
        }
    }


    fun setEdgeColor(textView: View, layoutView: View, b: Boolean) {
        if (b) {
            textView.visibility = View.GONE
            layoutView.background = resources.getDrawable(R.drawable.shape_gray_edge)
        } else {
            textView.visibility = View.VISIBLE
            layoutView.background = resources.getDrawable(R.drawable.shape_red_edge)
        }
    }

    fun isEnabledEmailBtn(isEnabled: Boolean, warning: Int? = null) {
        when (isEnabled) {
            true -> {
                binding.btnEmailAuthenticate.isEnabled = true

                setEdgeColor(binding.tvWarningEmail, binding.layoutEmail, true)

                binding.btnEmailAuthenticate.background =
                    resources.getDrawable(R.drawable.shape_blue_edge_rounded_3)
                binding.btnEmailAuthenticate.setTextColor(resources.getColor(R.color.colorAccent))

                binding.btnEmailAuthenticate.setOnClickListener {
                    binding.btnEmailAuthenticate.background = resources.getDrawable(R.drawable.shape_signup_gray_btn_disabled)
                    binding.btnEmailAuthenticate.setTextColor(resources.getColor(R.color.login_signup_gray_text_2))
                    binding.btnEmailAuthenticate.setText("이메일 인증 완료")
                    binding.btnEmailAuthenticate.isEnabled = false
                    binding.edtEmail.isEnabled = false
                    binding.edtEmail.background = resources.getDrawable(R.drawable.shape_gray1_view_rounded_3)
                    binding.layoutEmail.background = resources.getDrawable(R.drawable.shape_gray1_view_rounded_3)
                    checkEmail = true
                    checkAllInput()
                }
            }
            false -> {
                binding.btnEmailAuthenticate.isEnabled = false

                binding.tvWarningEmail.setText(warning!!)
                setEdgeColor(binding.tvWarningEmail, binding.layoutEmail, false)

                binding.btnEmailAuthenticate.background =
                    resources.getDrawable(R.drawable.shape_signup_gray_btn_disabled)
                binding.btnEmailAuthenticate.setTextColor(resources.getColor(R.color.login_signup_gray_text_2))

            }
        }
    }



}