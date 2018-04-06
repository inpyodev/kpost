package com.xinkle.kpostguide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class KPostActivity extends Activity{
    private SocketManager mSocket;
    Button btnYegum;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kpost);

        mSocket = SocketManager.getInstance();
        mSocket.changeHandler(m_Handler);

        btnYegum = findViewById(R.id.btnYegum);
        btnYegum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img = findViewById(R.id.KPostMainImage);
                img.setImageResource(R.drawable.kp_tab1_app);
                btnYegum.setBackgroundColor(Color.WHITE);

                JSONObject jsonob = new JSONObject();
                try {
                    jsonob.put("msg_type", "screen_update");
                    jsonob.put("uuid", mSocket.getmUUID());
                    jsonob.put("current_screen", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSocket.sendData(jsonob.toString());

            }
        });

    }

    private Handler m_Handler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 0: // 소켓 생성 완료
                    // 로딩화면 제거
                    break;
                case 1: // 데이터 수신 완료
                    // 수신 데이터 토스트로 띄움.
                    Log.d("KPOST", msg.obj.toString());

                    if (msg.obj.toString().equals("1")) {
                        btnYegum.setBackgroundColor(Color.BLUE);
                    } else if(msg.obj.toString().equals("2")) {
                        img.setImageResource(R.drawable.kp_tab1_app_hl);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(KPostActivity.this,KPostActivity2.class);
                                startActivity(intent);

                                JSONObject jsonob = new JSONObject();
                                try {
                                    jsonob.put("msg_type", "screen_update");
                                    jsonob.put("uuid", mSocket.getmUUID());
                                    jsonob.put("current_screen", 2);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mSocket.sendData(jsonob.toString());

                            }
                        });
                    }
                    break;
            }
        }
    };
}
