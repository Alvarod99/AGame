package com.example.agame;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RowArrayAdapter extends ArrayAdapter<rowpartidos> {
    Context context;

    public RowArrayAdapter(Context context, int resourceId, List<rowpartidos> partidos){
        super(context,resourceId,partidos);
        this.context=context;
    }

    private class RowItemHolder{
        TextView txtSport_title, txtHome_team, txtAway_team;
        Button BtPrice1, BtPriceX, BtPrice2;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        RowItemHolder holder = null;
        rowpartidos RowPartidos = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.rowpartidos,null);
            holder = new RowItemHolder();

            holder.txtSport_title = (TextView) convertView.findViewById(R.id.Deporte);//no se si es este sport_title
            holder.txtHome_team = (TextView) convertView.findViewById(R.id.Home_team);//no se si es este home_team
            holder.txtAway_team = (TextView) convertView.findViewById(R.id.Away_team);//no se si es este away_team
            holder.BtPrice1 = (Button) convertView.findViewById(R.id.Price1);//no se si es este price1
            holder.BtPrice2 = (Button) convertView.findViewById(R.id.Price2);//no se si es este price2
            holder.BtPriceX = (Button) convertView.findViewById(R.id.PriceX);//no se si es este priceX
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



        return convertView;

    }

}
