package com.example.pickup_pic.presentation.authentication

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.Coil
import com.example.pickup_pic.R
import com.example.pickup_pic.databinding.FragmentAuthenticationBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import timber.log.Timber

@AndroidEntryPoint
class AuthenticationFragment : Fragment() {


    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthenticationViewModel by viewModels()

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val resultCode = result.resultCode
        val intent = result.data
        if (resultCode == Activity.RESULT_OK && intent != null) {
            /**
             * Given a successful authorization response carrying an authorization code.
             * A token request can be made to exchange the code for a refresh token.
             */
            val tokenRequest =
                AuthorizationResponse.fromIntent(intent)?.createTokenExchangeRequest()
            val exception = AuthorizationException.fromIntent(intent)

            if (tokenRequest != null) {
                viewModel.getToken(tokenRequest)
            } else {
                binding.progressBar2.isVisible = false
                binding.signUpViewGroup.isVisible = true
                exception?.message?.let {
                    showSnackBar(it)
                }
                Timber.d(exception)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationIntent()
        observeAuth()
        setUpListener()
    }

    private fun observeAuth() {
        viewLifecycleOwner.lifecycleScope.launch {
            //почитать
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authState.collect { state ->
                    when (state) {
                        is AuthenticationState.Loading -> {
                            binding.progressBar2.isVisible = true
                            binding.signUpViewGroup.isVisible = false
                        }
                        is AuthenticationState.LoggedIn -> {
                            binding.progressBar2.isVisible = false
                            binding.signUpViewGroup.isVisible = false
                            showSnackBar(getString(R.string.succes_auth))
                            findNavController().navigate(AuthenticationFragmentDirections.actionAuthenticationFragment2ToLoggedFragment())
                        }
                        is AuthenticationState.Error -> {
                            binding.progressBar2.isVisible = false
                            binding.signUpViewGroup.isVisible = true
                            showSnackBar(state.errorMsg)
                            Timber.d(state.errorMsg)
                        }
                        is AuthenticationState.NotLoggedIn -> {
                            binding.progressBar2.isVisible = false
                            Timber.d("not logged in")
                        }
                    }
                }
            }

        }
    }

    private fun setUpListener() {
        binding.signUpButton.setOnClickListener {
            viewModel.authenticateUser()
        }
    }

    private fun observeAuthenticationIntent() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.authenticatingPageIntent.collect() { authIntent ->
                launcher.launch(authIntent)
            }
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar
            .make(
                requireView(),
                msg,
                Snackbar.LENGTH_LONG
            )
            .setAction(getString(R.string.retry)) {
                viewModel.authenticateUser()
            }
            .show()
    }

}