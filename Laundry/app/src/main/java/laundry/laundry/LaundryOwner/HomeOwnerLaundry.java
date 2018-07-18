package laundry.laundry.LaundryOwner;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import laundry.laundry.R;
import laundry.laundry.database.maps;
import laundry.laundry.database.tbl_user;


/**
 * Created by Saepul Uyun on 4/21/2018.
 */

public class HomeOwnerLaundry extends AppCompatActivity {

    //inisialisasi intent
    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String Username = "username";
    public static final String Password = "password";


    //inisialisi variable
    @BindView(R.id.Inputplgn) TextView _inputplgn;
    @BindView(R.id.btnEdit) TextView _btnedit;
    @BindView(R.id.backbtn) TextView _backbtn;
    @BindView(R.id.addMaps) TextView _addMaps;

    TextView viewNama;
    TextView viewAlamat;
    TextView viewEmail;
    TextView viewNohp;
    TextView viewHarga;
    TextView viewUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeowner);
        ButterKnife.bind(this);

        //inisialisasi variable pada layout
        viewNama = (TextView) findViewById(R.id.viewNama);
        viewAlamat = (TextView) findViewById(R.id.viewAlamat);
        viewEmail = (TextView) findViewById(R.id.viewEmail);
        viewNohp = (TextView) findViewById(R.id.viewNohp);
        viewHarga = (TextView) findViewById(R.id.viewHarga);
        viewUsername = (TextView) findViewById(R.id.viewUsername);

        //inisialisai firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("tbl_user");

        //memanggil isi data intent pada tampilan sebelumnya.
        final Intent intent = getIntent();

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

        final DatabaseReference maps = database.getReference("maps");

        //funtion ketika tombol maps di click dan untuk mengecek keaslian data yang di samakan ke database serta intent data ke class maps.
        _addMaps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent intent = getIntent();
                final String username = intent.getStringExtra(HomeOwnerLaundry.Username);
                final ProgressDialog mDialog = new ProgressDialog(HomeOwnerLaundry.this);
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
                                                Intent intent = new Intent(getApplicationContext(), MapsOwner.class);
                                                intent.putExtra("lat", map.getLat());
                                                intent.putExtra("lng", map.getLng());
                                                intent.putExtra(Nama, tbl_user.getNama());
                                                intent.putExtra(Username, tbl_user.getUsername());
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                                finish();
                                            }
                                        } else {
                                            Intent intent = new Intent(getApplicationContext(), MapsOwner.class);
                                            intent.putExtra(Nama, tbl_user.getNama());
                                            intent.putExtra(Username, tbl_user.getUsername());
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        //function ketika tombol back ke home di click
        _backbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //cek jika user tidak terdaftar didatabase
                        if (dataSnapshot.child(username.toString()).exists()) {

                            //ambil data user
                            final tbl_user tbl_user = dataSnapshot.child(username.toString()).getValue(laundry.laundry.database.tbl_user.class);
                            if (tbl_user.getUsername().equals(username.toString())) {
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                intent.putExtra(Nama, tbl_user.getNama());
                                intent.putExtra(Alamat, tbl_user.getAlamat());
                                intent.putExtra(Email, tbl_user.getEmail());
                                intent.putExtra(Nohp, tbl_user.getNohp());
                                intent.putExtra(Harga, tbl_user.getHarga());
                                intent.putExtra(Username, tbl_user.getUsername());
                                intent.putExtra(Password, tbl_user.getPassword());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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


        //function ketika tombol order di click untuk ke class order
        _inputplgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(HomeOwnerLaundry.this);
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
                                Intent intent = new Intent(getApplicationContext(), Order.class);
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



        //function jika tombol edit profile di click
        _btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(HomeOwnerLaundry.this);
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

                                showUpdateDialog(tbl_user.getUsername(), tbl_user.getPassword(), tbl_user.getNama(), tbl_user.getAlamat(),tbl_user.getEmail(), tbl_user.getNohp(),tbl_user.getHarga());
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

    //Untuk Update Profile Laundry
    private void showUpdateDialog(final String Username, final String Password, final String Nama, final String Alamat, final String Email, final String Nohp, final String Harga){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_updateprofile, null);

        dialogBuilder.setView(dialogView);

        final EditText upNama = dialogView.findViewById(R.id.upNama);
        final EditText upAlamat = dialogView.findViewById(R.id.upAlamat);
        final EditText upPassword = dialogView.findViewById(R.id.upPassword);
        final EditText upNohp = dialogView.findViewById(R.id.upNohp);
        final EditText upHarga = dialogView.findViewById(R.id.upHarga);
        final EditText upEmail = dialogView.findViewById(R.id.upEmail);
        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        //dialogBuilder.setTitle("Update Profile " + Username);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        //validation form
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = upNama.getText().toString().trim();
                String alamat = upAlamat.getText().toString();
                String password = upPassword.getText().toString();
                String nohp = upNohp.getText().toString();
                String harga = upHarga.getText().toString();
                String email = upEmail.getText().toString();
                //validasi email
                if(TextUtils.isEmpty(email)){
                    upEmail.setError("Email tidak boleh kosong");
                    return;
                    //validasi Password
                }else if(TextUtils.isEmpty(password)){
                    upPassword.setError("Password tidak boleh kosong");
                    return;
                    //validasi Nama Laundry
                } else if(TextUtils.isEmpty(nama)){
                    upNama.setError("Nama tidak boleh kosong");
                    return;
                    //validasi Alamat
                }else if(TextUtils.isEmpty(alamat)){
                    upAlamat.setError("Alamat tidak boleh kosong");
                    return;
                    //validasi No Handphone
                }else if(TextUtils.isEmpty(nohp)){
                    upNohp.setError("No Handphone tidak boleh kosong");
                    return;
                    //validasi Harga
                }else if(TextUtils.isEmpty(harga)){
                    upHarga.setError("Harga tidak boleh kosong");
                    return;
                }

                updateProfile(Username, password, nama, alamat, email, nohp, harga);

                alertDialog.dismiss();
            }
        });
    }

    //Menyimpan Data yang sudah di update
    private boolean updateProfile(String Username, String Password, String Nama, String Alamat, String Email, String Nohp, String Harga){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tbl_user").child(Username);

        tbl_user user = new tbl_user(Username, Password, Nama, Alamat, Email, Nohp, Harga);

        databaseReference.setValue(user);
        //Peringatan Berhasil
        Toast.makeText(this, "Data Berhasil di Ubah, Silahkan Logout dan Login Kembali", Toast.LENGTH_LONG).show();

        return true;
    }

    //funtion untuk kembali ke tampilan sebelumnya.
    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(HomeOwnerLaundry.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
