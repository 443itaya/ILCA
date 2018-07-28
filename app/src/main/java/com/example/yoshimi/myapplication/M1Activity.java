package com.example.yoshimi.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class M1Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int M2Info[] = new int[20];
    String[] M2name = new String[20];
    int M1Info[] = new int[20];
    String[] M1name = new String[20];
    int U4Info[] = new int[20];
    String[] U4name = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m1);

        Intent intent = getIntent();
        M2Info = intent.getIntArrayExtra("M2Info");
        M2name = intent.getStringArrayExtra("M2name");
        M1Info = intent.getIntArrayExtra("M1Info");
        M1name = intent.getStringArrayExtra("M1name");
        U4Info = intent.getIntArrayExtra("U4Info");
        U4name = intent.getStringArrayExtra("U4name");

//        //ここから
//        String[] M1name = {
//                "新井 友輔","伊藤 稔","岡田 祥","川村 航平",
//                "神田 章博", "中村 誠司","坂東 航","平井 友樹",
//                "藤本 康暉","村野 翔太", "森本 陸","森本 祥之"
//        };

        // レイアウトからリストビューを取得
        ListView listView = (ListView)findViewById(R.id.M1listView);

        // リストビューに表示する要素を設定
        ArrayList<SampleListItem> listItems = new ArrayList<>();
        for (int i = 0; i < M1name.length; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.img_m1);
            if (M1Info[i] == 0) {
                bmp = null;
            }
            SampleListItem item = new SampleListItem(bmp, M1name[i] );
            listItems.add(item);
        }

        // 出力結果をリストビューに表示
        GradeListAdapter adapter = new GradeListAdapter(this, R.layout.list_item_grade, listItems);
        listView.setAdapter(adapter);
        //ここまで

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("M1");
        setSupportActionBar(toolbar);



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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
