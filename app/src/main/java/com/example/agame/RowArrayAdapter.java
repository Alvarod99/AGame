package com.example.agame;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class RowArrayAdapter extends ArrayAdapter<RowPartidos> {
    Context context;

    public RowArrayAdapter(Context context, int resourceId, List<RowPartidos> partidos){
        super(context,resourceId,partidos);
        this.context=context;
    }

    private class RowItemHolder{
        TextView txtSport_title, txtHome_team, txtAway_team;
        Button BtPrice1, BtPriceX, BtPrice2;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        RowItemHolder holder = null;
        RowPartidos RowPartidos = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.rowpartidos,null);
            holder = new RowItemHolder();

            holder.txtSport_title = (TextView) convertView.findViewById(R.id.Deporte);
            holder.txtHome_team = (TextView) convertView.findViewById(R.id.Home_team);
            holder.txtAway_team = (TextView) convertView.findViewById(R.id.Away_team);
            holder.BtPrice1 = (Button) convertView.findViewById(R.id.Price1);
            holder.BtPrice2 = (Button) convertView.findViewById(R.id.Price2);
            holder.BtPriceX = (Button) convertView.findViewById(R.id.PriceX);
            convertView.setTag(holder);
        }
        else
            holder = (RowItemHolder) convertView.getTag();


        holder.txtSport_title.setText(RowPartidos.getSport_title());
        holder.txtHome_team.setText(RowPartidos.getHome_team());
        holder.txtAway_team.setText(RowPartidos.getAway_team());
        holder.BtPrice1.setText(RowPartidos.getPrice1().toString());
        holder.BtPrice2.setText(RowPartidos.getPrice2().toString());
        holder.BtPriceX.setText(RowPartidos.getPriceX().toString());

        holder.BtPrice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),Apuestas.class);
                i.putExtra("Cuota",RowPartidos.getPrice1());
                i.putExtra("id",RowPartidos.getId());
                i.putExtra("Resultado","1");
                view.getContext().startActivity(i);

            }
        });

        holder.BtPrice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),Apuestas.class);
                i.putExtra("Cuota",RowPartidos.getPrice2());
                i.putExtra("id",RowPartidos.getId());
                i.putExtra("Resultado","2");
                view.getContext().startActivity(i);
            }
        });

        holder.BtPriceX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),Apuestas.class);
                i.putExtra("Cuota",RowPartidos.getPriceX());
                i.putExtra("id",RowPartidos.getId());
                i.putExtra("Resultado","X");
                view.getContext().startActivity(i);
            }
        });

        return convertView;

    }

}
