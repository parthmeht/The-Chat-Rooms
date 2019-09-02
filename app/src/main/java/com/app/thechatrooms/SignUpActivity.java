package com.app.thechatrooms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.thechatrooms.utilities.Parameters;
import com.app.thechatrooms.utilities.TextValidator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    @BindView (R.id.signUp_firstNameEditText) EditText firstNameEditText;
    @BindView (R.id.signUp_lastNameEditText) EditText lastNameEditText;
    @BindView (R.id.signUp_emailEditText) EditText emailIdEditText;
    @BindView (R.id.signUp_passwordEditText) EditText passwordEditText;
    @BindView (R.id.signUp_confirmPasswordEditText) EditText confirmPasswordEditText;
    @BindView (R.id.signUp_cityEditText) EditText cityEditText;
    private RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        firstNameEditText.addTextChangedListener(new TextValidator(firstNameEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (Parameters.EMPTY.equalsIgnoreCase(text))
                    textView.setError(Parameters.EMPTY_ERROR_MESSAGE);
            }
        });

        lastNameEditText.addTextChangedListener(new TextValidator(lastNameEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (Parameters.EMPTY.equalsIgnoreCase(text))
                    textView.setError(Parameters.EMPTY_ERROR_MESSAGE);
            }
        });

        emailIdEditText.addTextChangedListener(new TextValidator(emailIdEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (Parameters.EMPTY.equalsIgnoreCase(text))
                    textView.setError(Parameters.EMPTY_ERROR_MESSAGE);
            }
        });

        passwordEditText.addTextChangedListener(new TextValidator(passwordEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (Parameters.EMPTY.equalsIgnoreCase(text))
                    textView.setError(Parameters.EMPTY_ERROR_MESSAGE);
            }
        });

        confirmPasswordEditText.addTextChangedListener(new TextValidator(confirmPasswordEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (Parameters.EMPTY.equalsIgnoreCase(text))
                    textView.setError(Parameters.EMPTY_ERROR_MESSAGE);
            }
        });

        cityEditText.addTextChangedListener(new TextValidator(cityEditText) {
            @Override
            public void validate(TextView textView, String text) {
                if (Parameters.EMPTY.equalsIgnoreCase(text))
                    textView.setError(Parameters.EMPTY_ERROR_MESSAGE);
            }
        });

    }
}
