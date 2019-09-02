package com.app.thechatrooms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    @BindView(R.id.main_emailEditText) EditText emailEditText;
    @BindView(R.id.main_passwordEditText) EditText passwordTextBox;
    @BindView(R.id.main_forgotPasswordTextView) TextView forgotPasswordTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.main_signUpTextView).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        forgotPasswordTextView.setOnClickListener(view -> {
            LayoutInflater layoutInflater = getLayoutInflater();
            final View v = layoutInflater.inflate(R.layout.alert_dialog, null);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Enter Email Id :");
            final EditText input = v.findViewById(R.id.etComments);
            alertDialog.setPositiveButton("YES",
                    (dialog, which) -> {
                        email = input.getText().toString();
                        Log.e(TAG,"Email = "+email);
                        mAuth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email sent.");
                                        Toast.makeText(view.getContext(),"Email Sent Successfully to "+email,Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(view.getContext(),"Unable to Send Email",Toast.LENGTH_LONG).show();
                                    }
                                });

                    });

            alertDialog.setNegativeButton("NO",
                    (dialog, which) -> dialog.cancel());
            alertDialog.setView(v);
            alertDialog.show();

        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @OnClick(R.id.main_loginButton) public void submit(View view){
        email = emailEditText.getText().toString();
        password = passwordTextBox.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "Display Name = " + user.getDisplayName() + "\n " +
                                "Email Id = " + user.getEmail());
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    public void updateUI(FirebaseUser user){
        if (user!=null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }


}
