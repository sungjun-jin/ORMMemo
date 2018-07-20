package com.example.jinsungjun.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBConnect extends OrmLiteSqliteOpenHelper {

    private static final String DBNAME = "memo.db";
    private static final int DBVERSION = 1;

    public DBConnect(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        //create 쿼리 실행
        try {
            TableUtils.createTable(connectionSource,Memo.class); //테이블 자동생성
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {



    }
    //Memo 테이블에 접근하기 위한 변수
    private Dao<Memo, Integer> memoDao = null;
    public Dao<Memo, Integer> getMemoDao() throws Exception { //DB 오류 시 호출한 측으로 Exception 전달

        if(memoDao == null) {

            memoDao = getDao(Memo.class);
        }

        return memoDao;
    }

    public void delete(Memo memo) {

        try {
            Dao<Memo,Integer> dao = getDao(Memo.class);
            dao.delete(memo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
