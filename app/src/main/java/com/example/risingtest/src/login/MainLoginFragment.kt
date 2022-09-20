package com.example.risingtest.src.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentMainLoginBinding
import com.example.risingtest.src.login.byEmail.loginByEmail.EmailLoginFragment
import com.example.risingtest.src.login.byEmail.signUpByEmail.EmailSignUpFragment
import com.example.risingtest.src.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


class MainLoginFragment : BaseFragment<FragmentMainLoginBinding>(
    FragmentMainLoginBinding::bind, R.layout.fragment_main_login), LoginActivity.onBackPressedListener {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutLoginKakao.setOnClickListener { kakaoLogin() }

        binding.tvLoginToEmail.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.frm_login, EmailLoginFragment()).commitAllowingStateLoss()
        }
        binding.tvSignUpToEmail.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.frm_login, EmailSignUpFragment()).commitAllowingStateLoss()
        }

    }

    private fun kakaoLogin() {
        //val keyHash = Utility.getKeyHash(this)
        //Log.d("Hash", keyHash)

        KakaoSdk.init(requireContext(), "d87393a841537bad120450d7b7a92f5a")

        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("loginActivity", "카카오계정으로 로그인 실패 : ${error}")
            } else if (token != null) {
                //TODO: 최종적으로 카카오로그인 및 유저정보 가져온 결과
                UserApiClient.instance.me { user, error ->
                    Log.d("loginActivity", "카카오계정으로 로그인 성공 \n\n " +
                            "token: ${token.accessToken} \n\n " +
                            "me: ${user}")

                    sSharedPreferences.edit().putString("loginStatus", "true").commit()
                    // 서버에 accessToken을 보내고 받은 회원 정보로 로그인 한다...

                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.d("loginActivity", "카카오톡으로 로그인 실패 : ${error}")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {

                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
                } else if (token != null) {
                    Log.d("loginActivity", "카카오톡으로 로그인 성공 ${token.accessToken}")

                    sSharedPreferences.edit().putString("loginStatus", "true").commit()
                    // 서버에 accessToken을 보내고 받은 회원 정보로 로그인 한다...

                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }
    }

    override fun onBackPressed() {
        requireActivity().finish()
    }

}