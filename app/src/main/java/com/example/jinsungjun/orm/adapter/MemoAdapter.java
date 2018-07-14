package com.example.jinsungjun.orm.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinsungjun.orm.Const;
import com.example.jinsungjun.orm.DetailActivity;
import com.example.jinsungjun.orm.Memo;
import com.example.jinsungjun.orm.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.Holder> {

    List<Memo> data;

    public void setDataAndRefresh(List<Memo> data) {

        this.data = data;
        notifyDataSetChanged();
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_memo,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Memo memo;
        memo = data.get(position);
        memo.position = position;
        holder.setData(memo);
        holder.setAllText();

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView textNo, textTitle, textMemo, textAuthor, textDate;
        Memo memo;

        public Holder(final View itemView) {
            super(itemView);

            textNo = itemView.findViewById(R.id.textNo);
            textTitle = itemView.findViewById(R.id.textTitle);
            textMemo = itemView.findViewById(R.id.textMemo);
            textAuthor = itemView.findViewById(R.id.editAuthor);
            textDate = itemView.findViewById(R.id.textDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 메모를 클릭했을 시 해당 메모의 상세 페이지로 이동                    T
                    Intent intent  = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra(Const.DETAIL_MEMO,memo.position);
                    itemView.getContext().startActivity(intent);

                }
            });

        }

        public void setData(Memo memo) {
            this.memo = memo;
        }

        public void setAllText() {

            setNo();
            setTitle();
            setMemo();
            setAuthor();
            setDate();
        }

        public void setNo() {
            textNo.setText(memo.no + "");
        }

        public void setTitle() {
            textTitle.setText(memo.title);
        }

        public void setMemo() {
            textMemo.setText(memo.memo);
        }

        public void setAuthor() {
            textAuthor.setText(memo.author);
        }

        public void setDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            textDate.setText(sdf.format(memo.timestamp));
        }
    }

}
