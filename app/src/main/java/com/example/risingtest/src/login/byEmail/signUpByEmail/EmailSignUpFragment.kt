package com.example.risingtest.src.login.byEmail.signUpByEmail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentEmailSignUpBinding
import com.example.risingtest.src.login.LoginActivity
import java.util.regex.Pattern


class EmailSignUpFragment : BaseFragment<FragmentEmailSignUpBinding>(
    FragmentEmailSignUpBinding::bind, R.layout.fragment_email_sign_up
),
    LoginActivity.onBackPressedListener {

    lateinit var emailSignUpAdapter: EmailSignUpAdapter
    val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    val pwPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 약관 내용 리사이클러뷰
        emailSignUpAdapter = EmailSignUpAdapter(requireContext())
        emailSignUpAdapter.getListFromView(setRvList())
        binding.rvTermsAndConditions.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTermsAndConditions.adapter = emailSignUpAdapter


        setOnEditTextListener()




        binding.btnBack.setOnClickListener { backFragment() }
    }

    override fun onBackPressed() {
        backFragment()
    }

    fun setRvList(): MutableList<EmailSignUpItemData> {
        var list = mutableListOf<EmailSignUpItemData>()
        list.add(
            EmailSignUpItemData(
                false,
                getString(R.string.email_signup_over_age_14),
                true,
                false
            )
        )
        list.add(
            EmailSignUpItemData(
                false,
                getString(R.string.email_signup_terms_and_conditions),
                true,
                true
            )
        )
        list.add(
            EmailSignUpItemData(
                false,
                getString(R.string.email_signup_agree_collect_and_use),
                true,
                true
            )
        )
        list.add(
            EmailSignUpItemData(
                false,
                getString(R.string.email_signup_agree_use_marketing),
                false,
                true
            )
        )
        list.add(
            EmailSignUpItemData(
                false,
                getString(R.string.email_signup_receive_notification),
                false,
                false
            )
        )
        return list
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
                var email = binding.edtEmail.text.toString()
                if (p0 != null && !email.equals("")) { // 공백이 아닐경우
                    if (!emailPattern.matcher(email).matches()) {// 이메일 형식이 아니라면
                        isEnabledEmailBtn(
                            false,
                            R.string.email_signup_warning_email_format_invalied
                        )
                    } else if (emailPattern.matcher(email).matches()) { // 이메일 형식이 맞다면
                        isEnabledEmailBtn(true)
                    } else {
                        // 이미 카카오톡 간편가입으로 가입된 이메일이라면
                        // sharedPreference
                    }
                } else {

                }
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
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                var inputPassword = binding.edtPassword.text.toString()
                if (p0 != null && !inputPassword.equals("")) { // 공백이 아닐경우
                    if (pwPattern.matcher(inputPassword).find()) { //  inputPassword.matches(pwPattern.toRegex())
                        setEdgeColor(binding.tvWarningPassword, binding.layoutPassword, true)
                    } else {
                        setEdgeColor(binding.tvWarningPassword, binding.layoutPassword, false)
                    }

                    if (!binding.edtPasswordConfirm.text.toString().equals("")
                        && !inputPassword.equals(binding.edtPasswordConfirm.text.toString())) {
                        setEdgeColor(binding.tvWarningPasswordConfirm, binding.layoutPasswordConfirm, false)
                    }
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
                    } else {
                        binding.tvWarningPasswordConfirm.setText(R.string.email_signup_warning_password_confirm_mismatch)
                        setEdgeColor(binding.tvWarningPasswordConfirm, binding.layoutPasswordConfirm, false)
                    }
                }
            }
        })

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
                    resources.getDrawable(R.drawable.shape_blue_edge_rounded)
                binding.btnEmailAuthenticate.setTextColor(resources.getColor(R.color.colorAccent))
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