package com.waoqi.msettopbox;

import android.app.DevInfoManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chinamobile.SWDevInfoManager;
import com.chinamobile.authclient.AuthClient;
import com.chinamobile.authclient.Constants;
import com.waoqi.msettopboxs.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AuthClient mAuthClient = AuthClient.getIntance(MainActivity.this);
        mAuthClient.login("", "", new AuthClient.CallBack() {
            @Override
            public void onResult(JSONObject jsonObject) {
                try {
                    int resultCode = jsonObject.getInt(Constants.VALUNE_KEY_RESULT_CODE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        mAuthClient.getToken(new AuthClient.CallBack() {
//            @Override
//            public void onResult(final JSONObject json) {
//                try {
//
//                    if (resultCode == Constants.RESULT_OK) {
//                        Log.d("AuthClientTest", "token=" + json.getString(Constants.VALUNE_KEY_TOKEN));
//                    } else {
//                        Log.d("AuthClientTest", "resultCode=" + resultCode);
//                        Log.d("AuthClientTest", "ResultDesc=" + json.getString(Constants.VALUNE_KEY_RESULT_DESC));
//                    }
//                } catch (JSONException e) {
//                    //TODOAuto-generatedcatchblock
//                    e.printStackTrace();
//                }
//            }
//        });

    }


}
