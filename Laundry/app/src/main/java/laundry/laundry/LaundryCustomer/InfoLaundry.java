package laundry.laundry.LaundryCustomer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import laundry.laundry.LaundryOwner.Login;
import laundry.laundry.R;
import laundry.laundry.database.tbl_user;
import laundry.laundry.database.maps;

/**
 * Created by Saepul Uyun on 4/14/2018.
 */

public class InfoLaundry extends AppCompatActivity {

    //inisiallisasi intent
    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String Username = "username";
    public static final String Password = "password";
    public static final String Usernamep = "Usernamep";
    public static final String namaLaundry = "namalaundry";

    //inisialisasi variabel
    @BindView(R.id.listBtn) TextView _listbtn;
    @BindView(R.id.backbtn) TextView _backbtn;
    @BindView(R.id.btnMaps) TextView _btnMaps;

    TextView viewNama;
    TextView viewAlamat;
    TextView viewEmail;
    TextView viewNohp;
    TextView viewHarga;
    TextView viewUsername;

    Button btnShare;
    Intent shareIntent;
    String shareBody = "This is a great App, you should try it out";

    DatabaseReference databaseMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infolaundry);
        ButterKnife.bind(this);

        //inisialisasi variabel layout
        viewNama = (TextView) findViewById(R.id.viewNama);
        viewAlamat = (TextView) findViewById(R.id.viewAlamat);
        viewEmail = (TextView) findViewById(R.id.viewEmail);
        viewNohp = (TextView) findViewById(R.id.viewNohp);
        viewHarga = (TextView) findViewById(R.id.viewHarga);
        viewUsername = (TextView) findViewById(R.id.viewUsername);

        //inisiallisasi firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("tbl_user");
        final DatabaseReference maps = database.getReference("maps");


        //memanggil isi data intent pada tampilan sebelumnya.
        Intent intent = getIntent();

        String nama = intent.getStringExtra(Login.Nama);
        String alamat = intent.getStringExtra(Login.Alamat);
        String email = intent.getStringExtra(Login.Email);
        String nohp = intent.getStringExtra(Login.Nohp);
        String harga = intent.getStringExtra(Login.Harga);
        final String username = intent.getStringExtra(Login.UsernameP);

        viewNama.setText(nama);
        viewAlamat.setText(alamat);
        viewEmail.setText(email);
        viewNohp.setText(nohp);
        viewHarga.setText(harga);

        btnShare = (Button) findViewById(R.id.btnShare);

        //function button share ketika di click
        btnShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/pain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share Via"));
            }
        });

        //function ketika tombol maps di click
        _btnMaps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Maps.class);
                startActivity(intent);

                final ProgressDialog mDialog = new ProgressDialog(InfoLaundry.this);
                mDialog.setMessage("Mohon menunggu..");
                mDialog.show();

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //cek jika user tidak terdaftar didatabase
                        if (dataSnapshot.child(username.toString()).exists()) {

                            //ambil data user
                            mDialog.dismiss();
                            final tbl_user tbl_user = dataSnapshot.child(username.toString()).getValue(laundry.laundry.database.tbl_user.class);
                            if (tbl_user.getUsername().equals(username.toString())) {
                                maps.addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //cek jika user tidak terdaftar didatabase
                                        final maps map = dataSnapshot.child(username).getValue(maps.class);
                                        if (dataSnapshot.child(username.toString()).exists()) {

                                            if (tbl_user.getUsername().equals(username.toString())) {
                                                Intent intent = new Intent(getApplicationContext(), Maps.class);
                                                intent.putExtra("lat", map.getLat());
                                                intent.putExtra("lng", map.getLng());
                                                intent.putExtra(Nama, tbl_user.getNama());
                                                intent.putExtra(Alamat, tbl_user.getAlamat());
                                                intent.putExtra(Email, tbl_user.getEmail());
                                                intent.putExtra(Nohp, tbl_user.getNohp());
                                                intent.putExtra(Harga, tbl_user.getHarga());
                                                intent.putExtra(Username, tbl_user.getUsername());
                                                intent.putExtra(Password, tbl_user.getPassword());


                                                startActivity(intent);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        //function ketika tomboll back di click
        _backbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(InfoLaundry.this, ListLaundry.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        //funtion ketika tombol order di click
        _listbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(InfoLaundry.this);
                mDialog.setMessage("Mohon menunggu..");
                mDialog.show();

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //cek jika user tidak terdaftar didatabase
                        if (dataSnapshot.child(username.toString()).exists()) {

                            //ambil data user
                            mDialog.dismiss();
                            final tbl_user tbl_user = dataSnapshot.child(username.toString()).getValue(laundry.laundry.database.tbl_user.class);
                            if (tbl_user.getUsername().equals(username.toString())) {

                                // Start the Signup activity
                                Intent intent = new Intent(getApplicationContext(), ListStatusOrder.class);
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
        });
    }

    //function untuk kembali ke tampilan sebelumnya.
    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(InfoLaundry.this, ListLaundry.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
