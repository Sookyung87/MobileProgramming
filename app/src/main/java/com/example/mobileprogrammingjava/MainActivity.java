package com.example.mobileprogrammingjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Edge-to-Edge 모드 활성화
        enableEdgeToEdge();

        // 레이아웃 설정
        setContentView(R.layout.activity_main);

        // DBHelper 초기화
        dbHelper = new DBHelper(this);

        // EditText와 Button 위젯 초기화
        etUsername = findViewById(R.id.editTextId);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnCheckNick_Reg);
        btnRegister = findViewById(R.id.btnRegister);

        // 로그인 버튼 클릭 리스너 설정
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자명과 비밀번호 가져오기
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // 사용자명 또는 비밀번호가 비어 있는지 확인
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // DBHelper를 사용하여 사용자 확인
                    boolean isValid = dbHelper.checkUser(username, password);
                    if (isValid) {
                        // 로그인 성공 메시지 표시
                        Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        // 메인 화면으로 이동
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 로그인 실패 메시지 표시
                        Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 회원가입 버튼 클릭 리스너 설정
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 전환
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Edge-to-Edge 모드 설정
        View mainView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        });
    }

    // Edge-to-Edge 모드 활성화 메서드
    private void enableEdgeToEdge() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }
}
