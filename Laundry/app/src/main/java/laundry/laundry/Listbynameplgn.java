package laundry.laundry;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import laundry.laundry.linkdatabase.pelanggan;
import laundry.laundry.linkdatabase.tbl_user;


/**
 * Created by Saepul Uyun on 4/26/2018.
 */

public class Listbynameplgn extends ArrayAdapter<pelanggan> {

    private Activity context;
    private List<pelanggan> plgnList;

    public Listbynameplgn (Activity context, List<pelanggan> plgnList){
        super(context, R.layout.activity_listbynameplgn, plgnList);
        this.context = context;
        this.plgnList = plgnList;
    }


    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_listbynameplgn,null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNama);
        TextView textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewStatus);

        pelanggan plgn = plgnList.get(position);

        textViewName.setText(plgn.getNama());
        textViewStatus.setText(plgn.getStatus());

        return listViewItem;
    }

}