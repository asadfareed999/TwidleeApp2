package com.example.asadfareed.twidlee2.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.asadfareed.twidlee2.R
import com.example.asadfareed.twidlee2.model.User
import com.example.asadfareed.twidlee2.model.VerifyCode
import com.example.asadfareed.twidlee2.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_verification.view.*
import kotlinx.android.synthetic.main.fragment_verification.view.code_Field_2
import java.text.SimpleDateFormat
import java.util.*


class CodeVerificationFragment(body: User?) : Fragment() {

    private lateinit var viewModel: ViewModel
    private var user: User= body!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_verification, container, false)
        initData()
        startTimer(view)
        clickHandlers(view)
        EditTextAutoFocus(view)
        return view
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
    }

    private fun clickHandlers(view: View) {
        view.fragment_button_done.setOnClickListener {
            val code: String = fetchFieldsData(view)
            dataValidation(code)
        }
        view.fragment_button_resend.setOnClickListener {
            viewModel.resendCode(activity,user)
        }
    }

    private fun EditTextAutoFocus(view: View) {
        requestFocus(view, view.code_Field_1)
        requestFocus(view, view.code_Field_2)
        requestFocus(view, view.code_Field_3)
        requestFocus(view, view.code_Field_4)
    }

    private fun fetchFieldsData(view: View): String {
        val codeDigit1: String = view.code_Field_1.text.toString()
        val codeDigit2: String = view.code_Field_2.text.toString()
        val codeDigit3: String = view.code_Field_3.text.toString()
        val codeDigit4: String = view.code_Field_4.text.toString()
        val stringBuilder: StringBuilder = java.lang.StringBuilder()
        stringBuilder.append(codeDigit1).append(codeDigit2).append(codeDigit3).append(codeDigit4)
        val code: String = stringBuilder.toString()
        return code
    }

    private fun dataValidation(code: String) {
        if (code.length != 4) {
            Toast.makeText(activity, "Invalid code", Toast.LENGTH_LONG).show()
        } else {
            val verifyCode: VerifyCode = VerifyCode(code)
            viewModel.verifyRegistration(activity, verifyCode,user)
        }
    }

    private fun requestFocus(view: View, codeField: EditText) {
        codeField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (codeField.text.length == 1) //size as per your requirement
                {
                    if (R.id.code_Field_1.equals(codeField.id)) {
                        view.code_Field_2.requestFocus()
                    }else if (R.id.code_Field_2.equals(codeField.id)) {
                        view.code_Field_3.requestFocus()
                    }else if (R.id.code_Field_3.equals(codeField.id)) {
                        view.code_Field_4.requestFocus()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
    }

    private fun startTimer(view: View) {
        object : CountDownTimer(3 * 60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                view.fragment_textView_countDownTimer.setText(
                    "Resend code in " +
                            SimpleDateFormat("mm:ss").format(Date(millisUntilFinished)))
            }
            override fun onFinish() {
                view.fragment_button_resend.setBackgroundResource(R.drawable.button_background)
                view.fragment_button_resend.isEnabled=true
                view.fragment_textView_countDownTimer.setText("Resend Code Now")
            }
        }.start()
    }
}