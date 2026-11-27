package com.example.brainybeam;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUP extends AppCompatActivity {
    Button btnSignUp;
    EditText etName, etRegisterEmail, etContact, etRegisterPassword, etConfirmPassword;
    TextView tvGoToLogin;
    SQLiteDatabase db;

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


        db = openOrCreateDatabase(ConstantName.DATABASE_NAME, MODE_PRIVATE, null);
        db.execSQL(ConstantName.CREATE_TABLE_QUERY);


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
            }else{
                String SearchQuery = "SELECT " +
                        ConstantName.COLUMN_EMAIL+
                        ", " +
                        ConstantName.COLUMN_CONTACT +
                        " FROM " +
                        ConstantName.TABLE_NAME + " WHERE " +
                        ConstantName.COLUMN_EMAIL + " = ? OR " +
                        ConstantName.COLUMN_CONTACT + " = ? LIMIT 1";
                String[] searchArgs = {email, contact};
                try (Cursor cursor = db.rawQuery(SearchQuery, searchArgs)) {
                    if (cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            String emailFromDB = cursor.getString(cursor.getColumnIndexOrThrow(ConstantName.COLUMN_EMAIL));
                            String contactFromDB = cursor.getString(cursor.getColumnIndexOrThrow(ConstantName.COLUMN_CONTACT));
                            if (email.equals(emailFromDB)) {
                                etRegisterEmail.setError("Email already exists");
                                etRegisterEmail.requestFocus();
                                return;
                            }
                            if (contact.equals(contactFromDB)) {
                                etContact.setError("Contact already exists");
                                etContact.requestFocus();
                                return;
                            }
                        }
                    }
                }

                String sql = "INSERT INTO " + ConstantName.TABLE_NAME + "(" +
                        ConstantName.COLUMN_NAME + ", " +
                        ConstantName.COLUMN_EMAIL + ", " +
                        ConstantName.COLUMN_CONTACT + ", " +
                        ConstantName.COLUMN_PASSWORD + ") VALUES (?, ?, ?, ?)";
                db.execSQL(sql, new String[]{name, email, contact, password});
                db.close();

                Toast.makeText(SignUP.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUP.this, Loginpage.class);
                startActivity(intent);
                //Snackbar.make(v, "Registration Successful", Snackbar.LENGTH_SHORT).setAction("Login", view -> startActivity(intent)).show();
            }



        });
        tvGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUP.this, Loginpage.class);
            startActivity(intent);
        });
    }
}