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

public class KPostActivity2 extends Activity {
    private SocketManager mSocket;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kpost2);

        mSocket = SocketManager.getInstance();
        mSocket.changeHandler(m_Handler);

        img = findViewById(R.id.KPostMainImage2);

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

                    if (msg.obj.toString().equals("3")) {
                        img.setImageResource(R.drawable.kp_prodlist_hl);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                img.setImageResource(R.drawable.kp_proto_1);
                                update_screen(3);
                            }
                        });
                    } else if(msg.obj.toString().equals("4")) {
                        img.setImageResource(R.drawable.kp_proto_1_hl);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                img.setImageResource(R.drawable.kp_proto_2);
                                update_screen(4);
                            }
                        });
                    } else if(msg.obj.toString().equals("5")) {
                        img.setImageResource(R.drawable.kp_proto_2_hl);

                    }
                    break;
            }
        }
    };

    public void update_screen(int screen) {
        JSONObject jsonob = new JSONObject();
        try {
            jsonob.put("msg_type", "screen_update");
            jsonob.put("uuid", mSocket.getmUUID());
            jsonob.put("current_screen", screen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.sendData(jsonob.toString());
    }
}
