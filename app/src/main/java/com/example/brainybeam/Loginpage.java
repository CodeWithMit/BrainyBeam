package com.example.brainybeam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Loginpage extends AppCompatActivity {
    Button btnLogin;
    EditText etLoginEmail, etLoginPassword;
    TextView tvForgotPassword, tvGoToSignUp;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


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

        db= openOrCreateDatabase(ConstantName.DATABASE_NAME, MODE_PRIVATE, null);
        db.execSQL(ConstantName.CREATE_TABLE_QUERY);

        sharedPreferences = getSharedPreferences(ConstantName.SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();


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

            String sql = "SELECT * FROM " + ConstantName.TABLE_NAME + " WHERE (" +
                    ConstantName.COLUMN_EMAIL + " = ? OR " +
                    ConstantName.COLUMN_CONTACT + " = ? ) AND " +
                    ConstantName.COLUMN_PASSWORD + " = ? LIMIT 1";
            String[] args = {email, email, password};
            try (Cursor cursor = db.rawQuery(sql, args)) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        int Cid = cursor.getInt(cursor.getColumnIndexOrThrow(ConstantName.COLUMN_ID));
                        String Cname = cursor.getString(cursor.getColumnIndexOrThrow(ConstantName.COLUMN_NAME));
                        String Cemail = cursor.getString(cursor.getColumnIndexOrThrow(ConstantName.COLUMN_EMAIL));
                        String Ccontact = cursor.getString(cursor.getColumnIndexOrThrow(ConstantName.COLUMN_CONTACT));

                        editor.putInt(ConstantName.COLUMN_ID, Cid);
                        editor.putString(ConstantName.COLUMN_NAME, Cname);
                        editor.putString(ConstantName.COLUMN_EMAIL, Cemail);
                        editor.putString(ConstantName.COLUMN_CONTACT, Ccontact);
                        editor.apply();
                        Intent intent = new Intent(Loginpage.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    etLoginEmail.setError("Invalid email or password");
                    etLoginEmail.requestFocus();
                    return;
                }

            }
            db.close();
//            Toast.makeText(Loginpage.this, "Login Successful", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Loginpage.this, MainActivity.class);
//            Snackbar.make(v, "Login Successful", Snackbar.LENGTH_SHORT).setAction("Home", view -> {
//
//                startActivity(intent);
//            }).show();
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