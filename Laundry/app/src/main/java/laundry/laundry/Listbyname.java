package laundry.laundry;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import laundry.laundry.linkdatabase.tbl_user;

import java.util.List;

/**
 * Created by Saepul Uyun on 4/6/2018.
 */

public class Listbyname extends ArrayAdapter<tbl_user>{
    private Activity context;
    private List<tbl_user> userList;

    public Listbyname(Activity context, List<tbl_user> userList){
        super(context, R.layout.activity_listbyname, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_listbyname,null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNama);
        TextView textViewAlamat = (TextView) listViewItem.findViewById(R.id.textViewAlamat);

        tbl_user user = userList.get(position);

        textViewName.setText(user.getNama());
        textViewAlamat.setText(user.getAlamat());

        return listViewItem;
    }

}
