package com.example.brainybeam;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPage extends AppCompatActivity {
    Button btnForgotPassword;
    EditText etForgotPasswordEmail, etForgotPassword, etForgotPasswordConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        etForgotPasswordEmail = findViewById(R.id.etForgotPasswordEmail);
        etForgotPassword = findViewById(R.id.etForgotPassword);
        etForgotPasswordConfirm = findViewById(R.id.etForgotPasswordConfirm);
        View.OnClickListener onClickListener = v -> {
            String email = etForgotPasswordEmail.getText().toString();
            String password = etForgotPassword.getText().toString();
            String confirmPassword = etForgotPasswordConfirm.getText().toString();
            if (email.isEmpty()) {
                etForgotPasswordEmail.setError("Please enter your email");
                etForgotPasswordEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etForgotPasswordEmail.setError("Please enter a valid email");
                etForgotPasswordEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                etForgotPassword.setError("Please enter your password");
                etForgotPassword.requestFocus();
                return;
            }
            if(password.length() < 8){
                etForgotPassword.setError("Password must be at least 8 characters long");
                etForgotPassword.requestFocus();
                return;
            }
            if (confirmPassword.isEmpty()) {
                etForgotPasswordConfirm.setError("Please enter your confirm password");
                etForgotPasswordConfirm.requestFocus();
                return;
            }
            if (!password.equals(confirmPassword)) {
                etForgotPasswordConfirm.setError("Password does not match");
                etForgotPasswordConfirm.requestFocus();
                return;
            }
            finish();

        };
        btnForgotPassword.setOnClickListener(onClickListener);
    }
}