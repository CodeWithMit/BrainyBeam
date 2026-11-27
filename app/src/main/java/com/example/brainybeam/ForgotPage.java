package com.example.brainybeam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPage extends AppCompatActivity {
    Button btnForgotPassword;
    EditText etForgotPasswordEmail, etForgotPassword, etForgotPasswordConfirm;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
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
        db = openOrCreateDatabase(ConstantName.DATABASE_NAME, MODE_PRIVATE, null);
        db.execSQL(ConstantName.CREATE_TABLE_QUERY);
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
            }else {
                String SearchQuery = "SELECT " +
                        ConstantName.COLUMN_EMAIL +
                        " FROM " +
                        ConstantName.TABLE_NAME + " WHERE " +
                        ConstantName.COLUMN_EMAIL + " = ? LIMIT 1";
                String[] searchArgs = {email};
                try (Cursor cursor = db.rawQuery(SearchQuery, searchArgs)) {
                    if (cursor.getCount() > 0) {
                        String sql = "UPDATE " + ConstantName.TABLE_NAME + " SET " +
                                ConstantName.COLUMN_PASSWORD + " = ? WHERE " +
                                ConstantName.COLUMN_EMAIL + " = ?";
                        db.execSQL(sql, new String[]{password, email});
                        db.close();
                        sharedPreferences = getSharedPreferences(ConstantName.SHARED_PREF_NAME, MODE_PRIVATE);
                        sharedPreferences.edit().clear().apply();

                        Toast.makeText(ForgotPage.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPage.this, Loginpage.class);
                        startActivity(intent);
                    } else {
                        etForgotPasswordEmail.setError("Invalid email");
                        etForgotPasswordEmail.requestFocus();
                        return;
                    }
                }catch ( Exception e){
                    Toast.makeText(ForgotPage.this, "Something went wrong" , Toast.LENGTH_SHORT).show();
                }

                db.close();
                finish();
            }
        };
        btnForgotPassword.setOnClickListener(onClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}