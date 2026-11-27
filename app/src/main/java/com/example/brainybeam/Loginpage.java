package com.example.brainybeam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.snackbar.Snackbar;

public class Loginpage extends AppCompatActivity {
    Button btnLogin;
    EditText etLoginEmail, etLoginPassword;
    TextView tvForgotPassword, tvGoToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginpage);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLogin = findViewById(R.id.btnLogin);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvGoToSignUp = findViewById(R.id.tvGoToSignUp);

        btnLogin.setOnClickListener(v -> {
            String email = etLoginEmail.getText().toString();
            String password = etLoginPassword.getText().toString();
            if (email.isEmpty()) {
                etLoginEmail.setError("Please enter your email ");
                etLoginEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                etLoginPassword.setError("Please enter your password");
                etLoginPassword.requestFocus();
                return;
            }
            Intent intent = new Intent( Loginpage.this, MainActivity.class);
            Snackbar.make(v, "Login Successful", Snackbar.LENGTH_SHORT).setAction("Home", view -> {

                startActivity(intent);
            }).show();
        });


        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(Loginpage.this, ForgotPage.class);
            startActivity(intent);
        });
        tvGoToSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Loginpage.this, SignUP.class);
            startActivity(intent);
        });


    }
}