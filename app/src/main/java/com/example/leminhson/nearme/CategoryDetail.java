package com.example.leminhson.nearme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryDetail extends AppCompatActivity {

    DetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        String message = intent.getStringExtra(HomeActivity.EXTRA_MESSAGE);
        switch (message)
        {
            case "Cây Xăng":  adapter = new DetailAdapter(this, Petro());break;
            case "ATM":  adapter = new DetailAdapter(this, ATM());break;
            case "Nhà hàng":  adapter = new DetailAdapter(this, Restaurant());break;
            case "Sân bóng mini":  adapter = new DetailAdapter(this, Football());break;
            case "Phòng tập Gym":  adapter = new DetailAdapter(this, Gym());break;
            case "Rạp chiếu phim":  adapter = new DetailAdapter(this, Cinema());break;
            case "Siêu thị":  adapter = new DetailAdapter(this, Supermarket());break;
        }
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Intent intent = new Intent(CategoryDetail.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    protected ArrayList<Model> Petro(){
        ArrayList<Model> models = new ArrayList<Model>();
        models.add(new Model(R.drawable.xang,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.xang,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.xang,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.xang,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.xang,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.xang,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.xang,"123 Lý Thường Kiệt","4.7km"));
        return models;
    }

    protected ArrayList<Model> ATM(){
        ArrayList<Model> models = new ArrayList<Model>();
        models.add(new Model(R.drawable.money,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.money,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.money,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.money,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.money,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.money,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.money,"123 Lý Thường Kiệt","4.7km"));
        return models;
    }

    protected ArrayList<Model> Restaurant(){
        ArrayList<Model> models = new ArrayList<Model>();
        models.add(new Model(R.drawable.restaurant,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.restaurant,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.restaurant,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.restaurant,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.restaurant,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.restaurant,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.restaurant,"123 Lý Thường Kiệt","4.7km"));
        return models;
    }

    protected ArrayList<Model> Football(){
        ArrayList<Model> models = new ArrayList<Model>();
        models.add(new Model(R.drawable.football,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.football,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.football,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.football,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.football,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.football,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.football,"123 Lý Thường Kiệt","4.7km"));
        return models;
    }

    protected ArrayList<Model> Gym(){
        ArrayList<Model> models = new ArrayList<Model>();
        models.add(new Model(R.drawable.gym,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.gym,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.gym,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.gym,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.gym,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.gym,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.gym,"123 Lý Thường Kiệt","4.7km"));
        return models;
    }

    protected ArrayList<Model> Cinema(){
        ArrayList<Model> models = new ArrayList<Model>();
        models.add(new Model(R.drawable.cinema,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.cinema,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.cinema,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.cinema,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.cinema,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.cinema,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.cinema,"123 Lý Thường Kiệt","4.7km"));
        return models;
    }

    protected ArrayList<Model> Supermarket(){
        ArrayList<Model> models = new ArrayList<Model>();
        models.add(new Model(R.drawable.supermarket,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.supermarket,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.supermarket,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.supermarket,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.supermarket,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.supermarket,"123 Lý Thường Kiệt","4.7km"));
        models.add(new Model(R.drawable.supermarket,"123 Lý Thường Kiệt","4.7km"));
        return models;
    }
}
