package laundry.laundry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import laundry.laundry.linkdatabase.tbl_user;

/**
 * Created by Saepul Uyun on 4/6/2018.
 */

public class Listlaundry extends AppCompatActivity {

    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";



    ListView listviewUser;

    List<tbl_user> userList;

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listlaundry);

        databaseUser = FirebaseDatabase.getInstance().getReference("tbl_user");
        listviewUser = (ListView) findViewById(R.id.listviewUser);
        userList = new ArrayList<>();

        listviewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tbl_user userlist = userList.get(i);

                Intent intent = new Intent(getApplicationContext(), Infolaundry.class);

                intent.putExtra(Nama, userlist.getNama());
                intent.putExtra(Alamat, userlist.getAlamat());
                intent.putExtra(Email, userlist.getEmail());
                intent.putExtra(Nohp, userlist.getNohp());
                intent.putExtra(Harga, userlist.getHarga());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userList.clear();

                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    tbl_user user = userSnapshot.getValue(tbl_user.class);

                    userList.add(user);
                }

                Listbyname adapter = new Listbyname(Listlaundry.this, userList);
                listviewUser.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
