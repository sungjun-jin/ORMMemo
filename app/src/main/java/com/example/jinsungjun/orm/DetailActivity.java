package com.example.jinsungjun.orm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinsungjun.orm.adapter.MemoAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView textNo, textTitle, textContent, textDate, textAuthor;
    Button btnDelete;
    List<Memo> data;
    Memo memo;

    MemoAdapter adapter;

    static DBConnect con;


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

        con = new DBConnect(this);


        adapter = new MemoAdapter();

        loadData();
        getMemo();
        setData();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailActivity.this);

                //제목세팅
                alertDialogBuilder.setTitle("메모 삭제");

                alertDialogBuilder
                        .setMessage("메모를 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("삭제",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        con.delete(data.get(memo.position));
                                        data.remove(memo.position);
                                        Toast.makeText(getBaseContext(), "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                        MainActivity.getData(getBaseContext());
                                        adapter.setDataAndRefresh(data);
                                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Log.d("terminated","term");


                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void loadData() {

        this.data = MainActivity.data;
    }

    public void getMemo() {

        Intent intent;
        intent = getIntent();
        int position = intent.getIntExtra(Const.DETAIL_MEMO, 0);
        memo = data.get(position);
    }

    public void setData() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        textNo.setText("당신의 " + memo.no + "번째 메모");
        textTitle.setText(memo.title);
        textContent.setText(memo.memo);
        textAuthor.setText("작성자 : " + memo.author);
        textDate.setText("작성일 : " + sdf.format(memo.timestamp));
    }

    @Override
    public void onBackPressed() {

        MainActivity.getData(getBaseContext());
        adapter.setDataAndRefresh(data);
        super.onBackPressed();
    }
}
