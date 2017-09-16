package com.example.evandro.app13mob;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    Button fb, btEntrar;
    LoginButton loginButton;
    String id, name, email, gender, birthday;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        fb = (Button) findViewById(R.id.fb);
        loginButton = (LoginButton) findViewById(R.id.login_Facebook_button);
        btEntrar = (Button) findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle dados = intent.getExtras();

                TextInputLayout tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
                TextInputLayout tilSenha = (TextInputLayout) findViewById(R.id.tilSenha);







                try {
                    JSONObject json = new JSONObject(dados.getString("resultSplash"));

                    String login = json.getString("usuario");
                    String senha = json.getString("senha");

                    if (login.toString().equals(tilLogin.getEditText().getText().toString()) && senha.toString().equals(tilSenha.getEditText().getText().toString())) {

                        Toast.makeText(LoginActivity.this, "OK", Toast.LENGTH_SHORT).show();

                        Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intentLogin);
                        LoginActivity.this.finish();

                    } else {
                        Toast.makeText(LoginActivity.this, "Login ou Senha inválido.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        List<String> permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "public_profile", "AccessToken");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Sucessooo", Toast.LENGTH_SHORT).show();

                String accessToken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    id = object.getString("id");
                                    try {
                                        URL profile_pic = new URL("http://graph.facebook.com/" + id + "/picture?type=large");
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    name = object.getString("name");
                                    email = object.getString("email");
                                    gender = object.getString("gender");
                                    birthday = object.getString("birthday");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("name", name);
                parameters.putString("email", email);
                request.executeAsync();

                Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                intentLogin.putExtras(parameters);
                startActivity(intentLogin);
                LoginActivity.this.finish();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v) {
        if (v == fb) {
            loginButton.performClick();
        }
    }

    public boolean validar() {


        return true;
    }

}
