package com.example.mobileprogrammingjava;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etRePassword, etNickname, etPhone;
    private Button btnRegister, btnCheckUsername, btnCheckNick; // 수정된 변수: btnCheckNick

    // DBHelper 클래스 인스턴스 생성
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Edge-to-Edge 모드 활성화
        enableEdgeToEdge();

        // layout/activity_register.xml 파일과 연결
        setContentView(R.layout.activity_register);

        // DBHelper 클래스 인스턴스 초기화
        dbHelper = new DBHelper(this);

        // EditText 및 Button 위젯 초기화
        etUsername = findViewById(R.id.editTextId_Reg);
        etPassword = findViewById(R.id.editTextPass_Reg);
        etRePassword = findViewById(R.id.editTextRePass_Reg);
        etNickname = findViewById(R.id.editTextNick_Reg);
        etPhone = findViewById(R.id.editTextPhone_Reg);
        btnRegister = findViewById(R.id.btnRegister_Reg);
        btnCheckUsername = findViewById(R.id.btnCheckId_Reg); // 수정된 변수: btnCheckUsername
        btnCheckNick = findViewById(R.id.btnCheckNick_Reg); // 추가된 버튼: btnCheckNick

        // btnCheckUsername 버튼에 대한 클릭 리스너 설정
        btnCheckUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 아이디 가져오기
                String username = etUsername.getText().toString();

                // 입력된 아이디가 비어 있는지 확인
                if (!username.isEmpty()) {
                    // DBHelper를 사용하여 입력된 아이디가 이미 데이터베이스에 있는지 확인
                    boolean isUsernameExists = dbHelper.checkUsernameExists(username);
                    if (isUsernameExists) {
                        // 이미 사용 중인 아이디인 경우 사용자에게 메시지 표시
                        Toast.makeText(RegisterActivity.this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 사용 가능한 아이디인 경우 사용자에게 메시지 표시
                        Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 아이디가 비어 있는 경우 사용자에게 메시지 표시
                    Toast.makeText(RegisterActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // btnCheckNick 버튼에 대한 클릭 리스너 설정
        btnCheckNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 닉네임 가져오기
                String nickname = etNickname.getText().toString();

                // 입력된 닉네임이 비어 있는지 확인
                if (!nickname.isEmpty()) {
                    // DBHelper를 사용하여 입력된 닉네임이 이미 데이터베이스에 있는지 확인
                    boolean isNicknameExists = dbHelper.checkNicknameExists(nickname);
                    if (isNicknameExists) {
                        // 이미 사용 중인 닉네임인 경우 사용자에게 메시지 표시
                        Toast.makeText(RegisterActivity.this, "이미 사용 중인 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 사용 가능한 닉네임인 경우 사용자에게 메시지 표시
                        Toast.makeText(RegisterActivity.this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 닉네임이 비어 있는 경우 사용자에게 메시지 표시
                    Toast.makeText(RegisterActivity.this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // btnRegister 버튼에 대한 클릭 리스너 설정
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText 위젯에서 사용자가 입력한 값 가져오기
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String rePassword = etRePassword.getText().toString();
                String nickname = etNickname.getText().toString();
                String phone = etPhone.getText().toString();

                // 모든 필드가 채워졌는지 확인
                if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty() || nickname.isEmpty() || phone.isEmpty()) {
                    // 필드가 비어 있는 경우 사용자에게 메시지 표시
                    Toast.makeText(RegisterActivity.this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    // 비밀번호가 다른 경우 사용자에게 메시지 표시
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // DBHelper를 사용하여 사용자를 데이터베이스에 추가
                    boolean isInserted = dbHelper.addUser(username, password, nickname, phone);
                    if (isInserted) {
                        // 사용자가 성공적으로 등록된 경우 사용자에게 메시지 표시 및 액티비티 종료
                        Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 사용자가 등록에 실패한 경우 사용자에게 메시지 표시
                        Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 뷰에 Edge-to-Edge 모드 적용
        View mainView = findViewById(R.id.register);

        Button btnBack_Main = (Button)findViewById(R.id.btnBack_Main);
        btnBack_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //화면전환
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnRegister_Reg = (Button)findViewById(R.id.btnRegister_Reg);
        btnRegister_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //화면전환
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

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