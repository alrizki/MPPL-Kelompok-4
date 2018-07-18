package laundry.laundry.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import laundry.laundry.R;
import laundry.laundry.database.tbl_user;

import java.util.List;

/**
 * Created by Saepul Uyun on 4/6/2018.
 */

//adapter dari list laundry (menampilkan 2 variable yaitu nama dan alamat)
public class ListByName extends ArrayAdapter<tbl_user>{
    private Activity context;
    private List<tbl_user> userList;

    //Inisialisasi untuk layout
    public ListByName(Activity context, List<tbl_user> userList){
        super(context, R.layout.activity_llistbyname, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    //function untuk inisialisasi nama dan alamat pada layout yang akan ditampilkan pada class list laundry.
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_llistbyname,null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNama);
        TextView textViewAlamat = (TextView) listViewItem.findViewById(R.id.textViewAlamat);

        tbl_user user = userList.get(position);

        textViewName.setText(user.getNama());
        textViewAlamat.setText(user.getAlamat());

        return listViewItem;
    }

}
