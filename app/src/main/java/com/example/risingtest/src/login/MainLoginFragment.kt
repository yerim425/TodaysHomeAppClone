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
import com.example.risingtest.src.login.byKakao.KakaoLoginService
import com.example.risingtest.src.login.byKakao.models.KakaoLoginResponse
import com.example.risingtest.src.login.byKakao.models.PostKakaoLoginRequest
import com.example.risingtest.src.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


class MainLoginFragment : BaseFragment<FragmentMainLoginBinding>(
    FragmentMainLoginBinding::bind, R.layout.fragment_main_login), LoginActivity.onBackPressedListener, MainLoginFragmentInterface {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutLoginKakao.setOnClickListener { kakaoLogin() }

        binding.tvLoginToEmail.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.frm_login, EmailLoginFragment()).commit()
        }
        binding.tvSignUpToEmail.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.frm_login, EmailSignUpFragment()).commit()
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
                UserApiClient.instance.me { user, error ->
                    Log.d("loginActivity", "카카오계정으로 로그인 성공 \n\n " +
                            "token: ${token.accessToken} \n\n " +
                            "me: ${user}")

                    //sSharedPreferences.edit().putString("loginStatus", "true").commit()

                    //val postRequest = PostKakaoLoginRequest(code = "${token.accessToken}")
                    //showLoadingDialog(requireContext())
                    //KakaoLoginService(this).tryPostKakaoLogin(postRequest)

                    // 서버 연동 구현하면 제거하기
                    var intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra("preActivity", "login")
                    requireActivity().startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) { // 카카오톡 앱에 로그인된 계정이 없음
                    Log.d("loginActivity", "카카오톡으로 로그인 실패 : ${error}")
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)

                } else if (token != null) { // 카카오 토큰 받아온 후 서버로 보냄

                    Log.d("loginActivity", "카카오계정으로 로그인 성공, token: ${token.accessToken}")

                    //sSharedPreferences.edit().putString("loginStatus", "true").commit()

                    //val postRequest = PostKakaoLoginRequest(code = "${token.accessToken}")
                    //showLoadingDialog(requireContext())
                    //KakaoLoginService(this).tryPostKakaoLogin(postRequest)

                    // 서버 연동 구현하면 제거하기
                    var intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra("preActivity", "login")
                    requireActivity().startActivity(intent)
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

    override fun onPostKakaoLogInSuccess(response: KakaoLoginResponse) {

        // result 값 sp에 저장
        var jwt = response.result.jwt
        var userIdx = response.result.userIdx.toString()
        var editor = sSharedPreferences.edit()
        editor.putString("X-ACCESS-TOKEN", jwt)
        editor.putString("userIdx", userIdx)
        editor.commit()

        dismissLoadingDialog()
        var intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("preActivity", "login")
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

    override fun onPostKakaoLogInFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("카카오 로그인 실패")
    }

}