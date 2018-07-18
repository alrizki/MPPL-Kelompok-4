package laundry.laundry.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import laundry.laundry.R;
import laundry.laundry.database.pelanggan;


/**
 * Created by Saepul Uyun on 4/26/2018.
 */

//adapter dari List status order (menampilkan 2 variable yaitu nama dan alamat)
public class ListByNameOrder extends ArrayAdapter<pelanggan> {

    private Activity context;
    private List<pelanggan> plgnList;

    //Inisialisasi untuk layout
    public ListByNameOrder(Activity context, List<pelanggan> plgnList){
        super(context, R.layout.activity_listbynameorder, plgnList);
        this.context = context;
        this.plgnList = plgnList;
    }


    @NonNull
    @Override
    //function untuk inisialisasi nama dan Status pada layout yang akan ditampilkan pada class list laundry.
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_listbynameorder,null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNama);
        TextView textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewStatus);

        pelanggan plgn = plgnList.get(position);

        textViewName.setText(plgn.getNama());
        textViewStatus.setText(plgn.getStatus());

        return listViewItem;
    }

}