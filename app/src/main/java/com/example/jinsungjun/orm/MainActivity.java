package com.example.jinsungjun.orm;

import android.content.Context;
import android.content.DialogInterface;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinsungjun.orm.adapter.MemoAdapter;
import com.j256.ormlite.dao.Dao;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;


public class MainActivity extends AppCompatActivity {

    TextView textNo, textDate;
    EditText editTitle, editMemo, editAuthor;
    Button btnPost;

    RecyclerView recyclerView;
    static DBConnect con;
    static List<Memo> data;
    MemoAdapter adapter;
    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();

        //DB 연결
        con = new DBConnect(this);
        //Data를 가져오는 함수
        getData(this);
        adapter = new MemoAdapter();
        adapter.setDataAndRefresh(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //여기서 화면의 값을 가져오고
                String title = editTitle.getText().toString();
                String content = editMemo.getText().toString();
                String author = editAuthor.getText().toString();

                if (title.equals("") || content.equals("")) {
                    Toast.makeText(getBaseContext(), "제목과 내용 모두 다 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    // 메모 작성자 익명처리
                    if (author.equals(""))
                        author = "익명";

                    //필수값 가져오기
                    long timestamp = System.currentTimeMillis();


                    try {
                        Memo memo = new Memo(title, content, author, timestamp);
                        Dao<Memo, Integer> memoDao = con.getMemoDao();
                        memoDao.create(memo);
                        Toast.makeText(getBaseContext(), "성공적으로 메모가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        reloadData();
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "DB 오류" + e.getMessage(), LENGTH_LONG).show();
                    }
                }

            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();

                //메세지 박스 출력
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                //제목세팅
                alertDialogBuilder.setTitle("메모 삭제");

                alertDialogBuilder
                        .setMessage("메모를 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("삭제",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        data.remove(position);
                                        adapter.setDataAndRefresh(data);
                                        Toast.makeText(getBaseContext(), "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        reloadData();

                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void setView() {

        textNo = findViewById(R.id.textNo);
        textDate = findViewById(R.id.textDate);
        editAuthor = findViewById(R.id.editAuthor);
        editTitle = findViewById(R.id.editTitle);
        editMemo = findViewById(R.id.editMemo);
        btnPost = findViewById(R.id.btnPost);
        recyclerView = findViewById(R.id.recyclerView);

    }

    public static void getData(Context context) {
        try {
            Dao<Memo, Integer> memoDao = con.getMemoDao();
            data = memoDao.queryForAll();
        } catch (Exception e) {
            Toast.makeText(context, "DB 오류" + e.getMessage(), LENGTH_LONG).show();
        }

    }

    public void hideKeyBoard() {
        //키보드 숨기는 함수
        imm.hideSoftInputFromWindow(editMemo.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editTitle.getWindowToken(), 0);

    }

    public void reloadData() {

        //데이터를 재 로딩 해주는 함수
        getData(getBaseContext());
        adapter.setDataAndRefresh(data);
    }


}
