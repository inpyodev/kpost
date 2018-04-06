package com.xinkle.kpostguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private SocketManager mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,KPostActivity.class);
                startActivity(intent);
            }
        });

        Button btnRequestSupport = findViewById(R.id.btnRequestSupport);
        final TextView tvRequestID = findViewById(R.id.tvRequestID);
        final TextView tvUUID = findViewById(R.id.tvUUID);

        btnRequestSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit;
                ApiService apiService;

                retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
                apiService = retrofit.create(ApiService.class);

                Call<ResponseBody> response = apiService.request("0.0.0.0");
                response.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String body = response.body().string();
                            String request_id = new JSONObject(body).getString("request_id");
                            String uuid = new JSONObject(body).getString("uuid");
                            tvRequestID.setText(request_id);
                            tvUUID.setText(uuid);

                            mSocket = SocketManager.getInstance("116.39.0.146", 7778, m_Handler);
                            mSocket.setmUUID(uuid);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        Button btnSocketConnect = findViewById(R.id.btnSocketConnect);
        btnSocketConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonob = new JSONObject();
                try {
                    jsonob.put("msg_type", "login");
                    jsonob.put("uuid", mSocket.getmUUID());
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

                    if (msg.obj.toString().equals("Connected")) {
                        Intent intent=new Intent(MainActivity.this,KPostActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };
}
