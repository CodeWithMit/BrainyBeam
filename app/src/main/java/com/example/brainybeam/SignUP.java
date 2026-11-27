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

public class SignUP extends AppCompatActivity {
    Button btnSignUp;
    EditText etName, etRegisterEmail, etContact, etRegisterPassword, etConfirmPassword;
    TextView tvGoToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnSignUp = findViewById(R.id.btnSignUp);
        etName = findViewById(R.id.etName);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etContact = findViewById(R.id.etContact);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvGoToLogin= findViewById(R.id.tvGoToLogin);

        btnSignUp.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etRegisterEmail.getText().toString();
            String contact = etContact.getText().toString();
            String password = etRegisterPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();
            Intent intent = new Intent(SignUP.this, Loginpage.class);

            if(name.isEmpty() || name.length()<3){
                etName.setError("Please enter your name");
                etName.requestFocus();
                return;
            }
            if(email.isEmpty()){
                etRegisterEmail.setError("Please enter your email");
                etRegisterEmail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etRegisterEmail.setError("Please enter a valid email");
                etRegisterEmail.requestFocus();
                return;
            }
            if(contact.isEmpty()){
                etContact.setError("Please enter your contact number");
                etContact.requestFocus();
                return;
            }
            if(contact.length()!=10){
                etContact.setError("Please enter a valid contact number");
                etContact.requestFocus();
                return;
            }
            if(password.isEmpty()){
                etRegisterPassword.setError("Please enter your password");
                etRegisterPassword.requestFocus();
                return;
            }
            if(password.length() < 8){
                etRegisterPassword.setError("Password must be at least 8 characters long");
                etRegisterPassword.requestFocus();
                return;
            }
            if(confirmPassword.isEmpty()){
                etConfirmPassword.setError("Please enter your confirm password");
                etConfirmPassword.requestFocus();
                return;
            }
            if(!password.equals(confirmPassword)){
                etConfirmPassword.setError("Password does not match");
                etConfirmPassword.requestFocus();
                return;
            }

            Snackbar.make(v, "Sign Up Successful", Snackbar.LENGTH_SHORT).setAction("Login", view -> startActivity(intent)).show();

        });
        tvGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUP.this, Loginpage.class);
            startActivity(intent);
        });
    }
}