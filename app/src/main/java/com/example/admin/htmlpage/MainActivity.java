package com.example.admin.htmlpage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.admin.htmlpage.db.DaoMaster;
import com.example.admin.htmlpage.db.DaoSession;
import com.example.admin.htmlpage.db.User;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.generator.Entity;

public class MainActivity extends AppCompatActivity {


    public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        //Add @JavascriptInterface to call this method from > 4.2 Android Version
        @JavascriptInterface
        public void SaveData(String s) {
            User user = new User();
            user.setEmail(s);
            user.setId((long) 123);
            daoSession.getUserDao().insertOrReplace(user);
            Toast.makeText(mContext, "Data Saved: " + daoSession.getUserDao().load((long) 123).getEmail(), Toast.LENGTH_SHORT).show();
        }
    }

    DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"users-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        WebView wvMe = (WebView) findViewById(R.id.wvMe);
        WebSettings webSettings = wvMe.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(this);
        wvMe.addJavascriptInterface(myJavaScriptInterface, "MainActivity");
        wvMe.loadUrl("file:///android_asset/index.html");




    }


}
