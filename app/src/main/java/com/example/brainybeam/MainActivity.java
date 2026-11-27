package com.example.brainybeam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {
    TextView textView;
    SharedPreferences sharedPreferences;

    Button btnLogout, MainbtnForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnLogout=findViewById(R.id.btnLogout);
        sharedPreferences=getSharedPreferences(ConstantName.SHARED_PREF_NAME, MODE_PRIVATE);
        String name=sharedPreferences.getString(ConstantName.COLUMN_NAME, "");
        String email=sharedPreferences.getString(ConstantName.COLUMN_EMAIL, "");
        String contact=sharedPreferences.getString(ConstantName.COLUMN_CONTACT, "");
        String password=sharedPreferences.getString(ConstantName.COLUMN_PASSWORD, "");
        if(name.isEmpty()){
            startActivity(new Intent(this, Loginpage.class));
            finish();
        }
        textView=findViewById(R.id.main_page_name);
        textView.setText(String.format("Welcome %s\n%s\n%s\n%s", name, email, contact, password));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            Handler.createAsync(getMainLooper()).postDelayed(() -> {
//                startActivity(new Intent(this, Loginpage.class));
//                finish();
//            }, 1000);
//        }
        btnLogout.setOnClickListener(v -> {
            sharedPreferences.edit().clear().apply();
            startActivity(new Intent(this, Loginpage.class));
            finish();
        });
    }
}
