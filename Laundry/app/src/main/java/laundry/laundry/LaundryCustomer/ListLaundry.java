package laundry.laundry.LaundryCustomer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import laundry.laundry.Adapter.ListByName;
import laundry.laundry.Home;
import laundry.laundry.R;
import laundry.laundry.database.tbl_user;

/**
 * Created by Saepul Uyun on 4/6/2018.
 */

public class ListLaundry extends AppCompatActivity {

    //Inisialisai Intent
    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String Username= "username";

    //inisialisasi variable
    ListView listviewUser;

    List<tbl_user> userList;

    ArrayAdapter<tbl_user> adapter;

    DatabaseReference databaseUser;

    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listlaundry);

        //inisialisasi variable layout
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("Daftar Laundry");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        databaseUser = FirebaseDatabase.getInstance().getReference("tbl_user");
        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        listviewUser = (ListView) findViewById(R.id.listviewUser);
        userList = new ArrayList<>();

        adapter = new ArrayAdapter<tbl_user>(this, android.R.layout.simple_list_item_1, userList);

        //function jika list view ke-i di clickdan untuk post data intent ke tampilan yang dituju.
        listviewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tbl_user userlist = userList.get(i);

                Intent intent = new Intent(getApplicationContext(), InfoLaundry.class);

                intent.putExtra(Nama, userlist.getNama());
                intent.putExtra(Alamat, userlist.getAlamat());
                intent.putExtra(Email, userlist.getEmail());
                intent.putExtra(Nohp, userlist.getNohp());
                intent.putExtra(Harga, userlist.getHarga());
                intent.putExtra(Username, userlist.getUsername());

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    //function yang berfungsi loop refresh halaman secara terus menerus.
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
                ListByName adapter = new ListByName(ListLaundry.this, userList);
                listviewUser.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

//    //
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_item,menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//        return true;
//    }


    //function untuk fitur kembali ke tampilan home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, Home.class));// close this activity and return to preview activity (if there is any)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }


    //function untuk fitur kembali ke tampilan sebelumnya
    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(ListLaundry.this, Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
