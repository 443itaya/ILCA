package com.example.yoshimi.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.TextureView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import java.io.*;
import java.util.ArrayList;
import android.os.Handler;
import android.util.Log;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog progressDialog;

    private final static String TAG_READ = "read";
    private EditText edittext;
    private String text;
    private Handler handler = new Handler();
    private ArrayList<String> namelist = new ArrayList<String>();

    private ArrayList<SampleListItem> listItems;
    private ListView listView;
    private SampleListAdapter adapter;

    int U4Info[] = new int[20];
    int M1Info[] = new int[20];
    int M2Info[] = new int[20];

    String[] M2name = {
            "大黒智貴","嶋川司","高谷友貴","田村聡明",
            "田村聡明","津崎隆広","富岡亮登","冨田龍太郎","那須大晃",
            "本田雄亮","山下俊樹", "山本泰士","JANSSENS"
    };
    String[] M1name = {
            "新井友輔","伊藤稔","岡田祥","川村航平",
            "神田章博", "中村誠司","坂東航","平井友樹",
            "藤本康輝","村野翔太", "森本陸","森本祥之"
    };
    String[] U4name = {
            "藤井隆裕","早矢仕篤弘","平末優希","池田太郎",
            "板谷佳美", "川合由夏","木原香織","小林加奈",
            "久留亜沙美","前田侑哉", "長倉静音","中本絢景",
            "布部あかり","佐藤華和子","関田いづみ","八十田周作","米田浩崇"
    };

    String[] exist = new String[50];

    private final static String URL = "http://172.20.10.3:3000/users";
    //private final static String URL = "http://192.168.1.9:3000/users";
    //http://www.yahoo.co.jp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // プログレスダイアログを表示する
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        for (int i = 0; i < U4Info.length; i++){
            U4Info[i] = 0;
            M1Info[i] = 0;
            M2Info[i] = 0;
        }

        thread();

        // レイアウトからリストビューを取得
        listView = (ListView)findViewById(R.id.listView);

//        // リストビューに表示する要素を設定
//        listItems = new ArrayList<>();
//        for (int i = 0; i < exist.length; i++) {
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);  // 今回はサンプルなのでデフォルトのAndroid Iconを利用
//            SampleListItem item = new SampleListItem(bmp, exist[i] );
//            listItems.add(item);
//        }
//
//        // 出力結果をリストビューに表示
//        adapter = new SampleListAdapter(this, R.layout.list_item, listItems);
//        listView.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("在室者一覧");
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            Toast.makeText(this,"setting clicked",Toast.LENGTH_LONG).show();

            thread();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void thread(){
        //スレッドの生成
        Thread thread = new Thread(new Runnable() {
            public void run() {

//                        //HTTP通信
//                        try {
//                            text = new String(http2data(URL));
//                        } catch (Exception e) {
//                            text = null;
//                        }

                String url = URL;
                String requestJSON = "JSON文字列";

                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(false);
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    Log.i("OSA030", "doPost start.:" + conn.toString());

                    conn.connect();
//
//                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//                            os.write(requestJSON.getBytes("UTF-8"));
//                            os.flush();
//                            os.close();

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        StringBuffer responseJSON = new StringBuffer();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        while ((inputLine = reader.readLine()) != null) {
                            responseJSON.append(inputLine);
                        }
                        ObjectMapper mapper = new ObjectMapper();
                        Log.w("aaa",responseJSON.toString());
                        ArrayList<Map<String,Object>> test = mapper.readValue(responseJSON.toString(), new TypeReference<ArrayList<Map<String,Object>>>() {});
                        Log.i("aaa", test.get(0).toString());
                        Map<String,Object> user1 = test.get(1);
                        test.size();
                        Log.w("aaa",user1.get("name").toString());

                        namelist.clear();
                        for (int i = 0; i < U4Info.length; i++){
                            U4Info[i] = 0;
                            M1Info[i] = 0;
                            M2Info[i] = 0;
                        }

                        for(int i=0; i<test.size(); i++){
                            Map<String,Object> user = test.get(i);
                            Boolean exist = (Boolean) user.get("exist");
                            if(exist == true){
                                namelist.add ((String) user.get("name"));
                            }
                        }

                    }
                } catch (IOException e) {
                    Log.e("OSA030", "error orz:" + e.getMessage(), e);
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }

                //ハンドラの生成
                handler.post(new Runnable() {
                    public void run() {
                        if (namelist != null) {
                            //Toast.makeText(MainActivity.this, namelist.toString(), Toast.LENGTH_LONG).show();//名前表示はこれを変えたら良さそう！！
                            exist = namelist.toArray(new String[namelist.size()]);
                            Log.w("aaaaaaaaaaa","100");
                            // リストビューに表示する要素を設定
                            listItems = new ArrayList<>();
                            for (int i = 0; i < exist.length; i++) {
                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);  // 今回はサンプルなのでデフォルトのAndroid Iconを利用
                                SampleListItem item = new SampleListItem(bmp, exist[i] );
                                listItems.add(item);
                            }

                            // 出力結果をリストビューに表示
                            adapter = new SampleListAdapter(getApplicationContext(), R.layout.list_item, listItems);
                            listView.setAdapter(adapter);


                            for(int i = 0; i < exist.length; i++){
                                for (int j = 0; j < M2name.length; j++){
                                    if (exist[i].equals(M2name[j])) {
                                        M2Info[j] = 1;
                                    }
                                }
                                for (int j = 0; j < M1name.length; j++){
                                    if (exist[i].equals(M1name[j])) {
                                        M1Info[j] = 1;
                                    }
                                }
                                for (int j = 0; j < U4name.length; j++){
                                    if (exist[i].equals(U4name[j])) {
                                        U4Info[j] = 1;
                                    }
                                }
                            }

                            // プログレスダイアログを閉じ、Handler に処理を引き継ぐ
                            progressDialog.dismiss();

                        } else {
                            Toast.makeText(MainActivity.this, "読み込み失敗しました", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
        thread.start();
    }

    //HTTP通信
    public static byte[] http2data(String path) throws Exception {
        byte[] w = new byte[1024];
        HttpURLConnection c = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        Log.w("aaaaaaaaaaa","1");
        try {
            Log.w("aaaaaaaaaaa","2");
            //HTTP接続のオープン
            URL url = new URL(path);
            Log.w("aaaaaaaaaaa","3");
            c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            Log.w("aaaaaaaaaaa","4");
            c.connect();
            Log.w("aaaaaaaaaaa","5");
            in = c.getInputStream();
            Log.w("aaaaaaaaaaa","conect");


            //バイト配列の読み込み
            out = new ByteArrayOutputStream();
            while (true) {
                int size = in.read(w);
                if (size <= 0) break;
                out.write(w, 0, size);
            }
            Log.w("aaaaaaaaaaa","close");
            out.close();

            //HTTP接続のクローズ
            in.close();
            c.disconnect();
            Log.w("aaaaaaaaaaa","return");
            return out.toByteArray();
        } catch (Exception e) {
            try {
                if (c != null) c.disconnect();
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (Exception e2) {
            }
            throw e;
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all) {
//            Toast.makeText(this,"all",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
//        else if (id == R.id.miki) {
//
//        } else if (id == R.id.makihara) {
//
//        }
        else if (id == R.id.m2) {
//            Toast.makeText(this,"M2",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, M2Activity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("M2Info", M2Info);
            intent.putExtra("M2name",M2name);
            intent.putExtra("M1Info", M1Info);
            intent.putExtra("M1name",M1name);
            intent.putExtra("U4Info", U4Info);
            intent.putExtra("U4name",U4name);
            startActivity(intent);
            overridePendingTransition(0,0);
        } else if (id == R.id.m1) {
//            Toast.makeText(this,"M1",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, M1Activity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("M2Info", M2Info);
            intent.putExtra("M2name",M2name);
            intent.putExtra("M1Info", M1Info);
            intent.putExtra("M1name",M1name);
            intent.putExtra("U4Info", U4Info);
            intent.putExtra("U4name",U4name);
            startActivity(intent);
            overridePendingTransition(0,0);
        } else if (id == R.id.u4) {
//            Toast.makeText(this,"U4",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, U4Activity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("M2Info", M2Info);
            intent.putExtra("M2name",M2name);
            intent.putExtra("M1Info", M1Info);
            intent.putExtra("M1name",M1name);
            intent.putExtra("U4Info", U4Info);
            intent.putExtra("U4name",U4name);
            startActivity(intent);
            overridePendingTransition(0,0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
