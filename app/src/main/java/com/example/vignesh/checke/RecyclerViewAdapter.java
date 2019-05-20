package com.example.vignesh.checke;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hariharan Sivakumar on 3/3/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<Hist> getDataAdapter;

    public RecyclerViewAdapter(List<Hist> getDataAdapter, Context context){

        super();

        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listhist, parent, false);

        ViewHolder viewHolder = new ViewHolder(v,context,getDataAdapter);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Hist getDataAdapter1 =  getDataAdapter.get(position);

        holder.quiz_questions.setText(getDataAdapter1.getStop());

        holder.amounts.setText(getDataAdapter1.getAmount());
        holder.froms.setText(getDataAdapter1.getFrom());
        holder.tos.setText(getDataAdapter1.getTo());
        holder.heads.setText(getDataAdapter1.getHeads());
        holder.tokens.setText(getDataAdapter1.getTicketid());
        holder.oldamount.setText(getDataAdapter1.getOldamount());
        holder.totalamount.setText(getDataAdapter1.getTotalamount());



    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView quiz_questions;
        public TextView heads;
        public TextView amounts;
        public TextView froms;
        public TextView tos;
        public TextView tokens;
        public TextView oldamount;
        public TextView totalamount;
        List<Hist> content = new ArrayList<Hist>();
        Context context;

        public ViewHolder(View itemView,Context context,List<Hist> content) {

            super(itemView);
            this.content = content;
            this.context = context;
            itemView.setOnClickListener(this);
            quiz_questions = (TextView) itemView.findViewById(R.id.quiz_question) ;
            heads = (TextView) itemView.findViewById(R.id.not) ;
            tokens = (TextView)itemView.findViewById(R.id.token);
            froms = (TextView)itemView.findViewById(R.id.from);
            tos = (TextView)itemView.findViewById(R.id.to);
            amounts = (TextView)itemView.findViewById(R.id.amount);
            oldamount = (TextView)itemView.findViewById(R.id.oldbal);
            totalamount = (TextView)itemView.findViewById(R.id.total);


        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Hist hist = this.content.get(position);
            Intent intent = new Intent(this.context,ShowTicket.class);
            intent.putExtra("id",hist.getTicketid());
            intent.putExtra("from",hist.getFrom());
            intent.putExtra("to",hist.getTo());
            intent.putExtra("noh",hist.getHeads());
            intent.putExtra("oldbal",hist.getOldamount());
            intent.putExtra("total",hist.getTotalamount());
            intent.putExtra("amount",hist.getAmount());
            this.context.startActivity(intent);
        }
    }
}