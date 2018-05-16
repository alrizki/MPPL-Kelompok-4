package laundry.laundry;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import laundry.laundry.linkdatabase.pelanggan;
import laundry.laundry.linkdatabase.tbl_user;

import static laundry.laundry.Listlaundry.Nama;
import static laundry.laundry.R.id.viewUsername;

/**
 * Created by Saepul Uyun on 4/27/2018.
 */

public class Liststatusplgn extends AppCompatActivity {

    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String Username = "username";
    public static final String Password = "password";

    MaterialSearchView searchView;

    TextView viewUsername;

    ListView listviewPlgn;

    DatabaseReference databasePlgn;

    List<pelanggan> plgnList;

    ArrayAdapter<tbl_user> adapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liststatusplgn);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Daftar Progress Laundry");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("tbl_user");

        searchView = findViewById(R.id.searchView);

        listviewPlgn = (ListView) findViewById(R.id.listviewPlgn);

        final Intent intent = getIntent();

        String nama = intent.getStringExtra(Login.Nama);

        plgnList = new ArrayList<>();

        final String username = intent.getStringExtra(Infolaundry.Username);

        databasePlgn = FirebaseDatabase.getInstance().getReference("plgn").child(username);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databasePlgn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                plgnList.clear();

                for(DataSnapshot plgnSnapshot : dataSnapshot.getChildren()){
                    pelanggan plgn = plgnSnapshot.getValue(pelanggan.class);
                    plgnList.add(plgn);
                }

                Listbynameplgn plgnListAdapter = new Listbynameplgn(Liststatusplgn.this, plgnList);
                listviewPlgn.setAdapter(plgnListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("tbl_user");

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, Infolaundry.class));// close this activity and return to preview activity (if there is any)
            if (item.getItemId() == android.R.id.home) {

                final Intent intent = getIntent();
                final String username = intent.getStringExtra(Infolaundry.Username);
                final ProgressDialog mDialog = new ProgressDialog(Liststatusplgn.this);
                mDialog.setMessage("Mohon menunggu..");
                mDialog.show();

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //cek jika user tidak terdaftar didatabase
                        if (dataSnapshot.child(username.toString()).exists()) {

                            //ambil data user
                            mDialog.dismiss();
                            final tbl_user tbl_user = dataSnapshot.child(username.toString()).getValue(laundry.laundry.linkdatabase.tbl_user.class);
                            if (tbl_user.getUsername().equals(username.toString())) {
                                Intent intent = new Intent(getApplicationContext(), Infolaundry.class);
                                intent.putExtra(Nama, tbl_user.getNama());
                                intent.putExtra(Alamat, tbl_user.getAlamat());
                                intent.putExtra(Email, tbl_user.getEmail());
                                intent.putExtra(Nohp, tbl_user.getNohp());
                                intent.putExtra(Harga, tbl_user.getHarga());
                                intent.putExtra(Username, tbl_user.getUsername());
                                intent.putExtra(Password, tbl_user.getPassword());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(Liststatusplgn.this, Infolaundry.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final Intent intent = getIntent();
        final String username = intent.getStringExtra(Infolaundry.Username);
        final ProgressDialog mDialog = new ProgressDialog(Liststatusplgn.this);
        mDialog.setMessage("Mohon menunggu..");
        mDialog.show();

        table_user.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //cek jika user tidak terdaftar didatabase
                if (dataSnapshot.child(username.toString()).exists()) {

                    //ambil data user
                    mDialog.dismiss();
                    final tbl_user tbl_user = dataSnapshot.child(username.toString()).getValue(laundry.laundry.linkdatabase.tbl_user.class);
                    if (tbl_user.getUsername().equals(username.toString())) {
                        Intent intent = new Intent(getApplicationContext(), Infolaundry.class);
                        intent.putExtra(Nama, tbl_user.getNama());
                        intent.putExtra(Alamat, tbl_user.getAlamat());
                        intent.putExtra(Email, tbl_user.getEmail());
                        intent.putExtra(Nohp, tbl_user.getNohp());
                        intent.putExtra(Harga, tbl_user.getHarga());
                        intent.putExtra(Username, tbl_user.getUsername());
                        intent.putExtra(Password, tbl_user.getPassword());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}