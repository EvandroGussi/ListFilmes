package com.example.evandro.app13mob;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3500;
    TextView tvNotificationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initControls();
        setNotificationData(getIntent().getExtras());

        String result = doInBackground();

        carregar(result);
    }

    private void initControls() {
        tvNotificationDetails = (TextView) findViewById(R.id.tvNotificationDetails);
    }

    private void setNotificationData(Bundle extras) {
        if (extras == null)
            return;
        StringBuilder text = new StringBuilder("");
        text.append("");
        text.append("\n");
        text.append("\n");
        if (extras.containsKey("title")) {
            text.append("Title: ");
            text.append(extras.get("title"));
        }
        text.append("\n");
        if (extras.containsKey("message")) {
            text.append("Message: ");
            text.append(extras.get("message"));
        }
        tvNotificationDetails.setText(text);
    }

    private void carregar(String result) {
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.alpha);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }

        final Bundle bundle = new Bundle();
        bundle.putString("resultSplash", result);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    protected String doInBackground(String... params) {
        try {
            URL url = new URL("http://www.mocky.io/v2/58b9b1740f0000b614f09d2f");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                BufferedReader stream = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String linha = "";
                StringBuilder resposta = new StringBuilder();
                while ((linha = stream.readLine()) != null) {
                    resposta.append(linha);
                }

                connection.disconnect();

                return resposta.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
