package laundry.laundry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import laundry.laundry.linkdatabase.tbl_user;


/**
 * Created by Saepul Uyun on 4/21/2018.
 */

public class Homepemilik extends AppCompatActivity {

    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String Username = "username";
    public static final String Password = "password";

    @BindView(R.id.Inputplgn) TextView _inputplgn;
    @BindView(R.id.btnEdit) TextView _btnedit;
    @BindView(R.id.backbtn) TextView _backbtn;

    TextView viewNama;
    TextView viewAlamat;
    TextView viewEmail;
    TextView viewNohp;
    TextView viewHarga;
    TextView viewUsername;

    Button btnEdit, btnInputplgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepemilik);
        ButterKnife.bind(this);

        viewNama = (TextView) findViewById(R.id.viewNama);
        viewAlamat = (TextView) findViewById(R.id.viewAlamat);
        viewEmail = (TextView) findViewById(R.id.viewEmail);
        viewNohp = (TextView) findViewById(R.id.viewNohp);
        viewHarga = (TextView) findViewById(R.id.viewHarga);
        viewUsername = (TextView) findViewById(R.id.viewUsername);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("tbl_user");

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

        _backbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Listlaundry.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });


        _inputplgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(Homepemilik.this);
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
                                Intent intent = new Intent(getApplicationContext(), Inputpelanggan.class);
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



        _btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(Homepemilik.this);
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

    private void showUpdateDialog(final String Username, final String Password, final String Nama, final String Alamat, final String Email, final String Nohp, final String Harga){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_updateprofil, null);

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = upNama.getText().toString().trim();
                String alamat = upAlamat.getText().toString();
                String password = upPassword.getText().toString();
                String nohp = upNohp.getText().toString();
                String harga = upHarga.getText().toString();
                String email = upEmail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    upEmail.setError("Email tidak boleh kosong");
                    return;
                }else if(TextUtils.isEmpty(password)){
                    upPassword.setError("Password tidak boleh kosong");
                    return;
                } else if(TextUtils.isEmpty(nama)){
                    upNama.setError("Nama tidak boleh kosong");
                    return;
                }else if(TextUtils.isEmpty(alamat)){
                    upAlamat.setError("Alamat tidak boleh kosong");
                    return;
                }else if(TextUtils.isEmpty(nohp)){
                    upNohp.setError("No Handphone tidak boleh kosong");
                    return;
                }else if(TextUtils.isEmpty(harga)){
                    upHarga.setError("Harga tidak boleh kosong");
                    return;
                }

                updateProfile(Username, password, nama, alamat, email, nohp, harga);

                alertDialog.dismiss();
            }
        });
    }

    private boolean updateProfile(String Username, String Password, String Nama, String Alamat, String Email, String Nohp, String Harga){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tbl_user").child(Username);

        tbl_user user = new tbl_user(Username, Password, Nama, Alamat, Email, Nohp, Harga);

        databaseReference.setValue(user);

        Toast.makeText(this, "Data Berhasil di Ubah", Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(Homepemilik.this, Listlaundry.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
