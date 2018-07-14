package com.example.jinsungjun.orm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinsungjun.orm.adapter.MemoAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView textNo,textTitle,textContent,textDate,textAuthor;
    Button btnDelete;
    List<Memo> data;
    Memo memo;

    MemoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textNo = findViewById(R.id.textNo);
        textTitle = findViewById(R.id.textTitle);
        textContent = findViewById(R.id.textContent);
        textDate = findViewById(R.id.textDate);
        textAuthor = findViewById(R.id.textAuthor);
        btnDelete = findViewById(R.id.btnDelete);

        adapter = new MemoAdapter();

        loadData();
        getMemo();
        setData();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }

    public void loadData() {

        this.data = MainActivity.data;
    }

    public void getMemo() {

        Intent intent;
        intent = getIntent();
        int position = intent.getIntExtra(Const.DETAIL_MEMO,0);
        memo = data.get(position);
    }

    public void setData() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        textNo.setText(memo.no + "");
        textTitle.setText(memo.title);
        textContent.setText(memo.memo);
        textAuthor.setText("작성자 : " + memo.author);
        textDate.setText("작성일 : " + sdf.format(memo.timestamp));
    }



}
