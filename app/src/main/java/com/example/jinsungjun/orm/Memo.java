package com.example.jinsungjun.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "memo")

public class Memo {

    //Column 정의
    @DatabaseField(generatedId = true) //auto increment
    public int no;
    @DatabaseField
    public String title;
    @DatabaseField
    public String memo;
    @DatabaseField
    public String author;
    @DatabaseField
    public long timestamp;

    public int position;

    public Memo() {

        //디폴트 생성자 필수
    }

    public Memo(String title, String memo, String author, long timestamp) {

        this.title = title;
        this.memo = memo;
        this.author = author;
        this.timestamp = timestamp;
    }
}
