package laundry.laundry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import laundry.laundry.linkdatabase.tbl_user;

/**
 * Created by Saepul Uyun on 4/4/2018.
 */

public class Registrasi extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;

    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String UsernameP = "username";
    public static final String PasswordP = "password";

    @BindView(R.id.signinLink) TextView _signinLink;
    @BindView(R.id.backbtn) TextView _backbtn;

    EditText editUsername, editPassword, editNama, editAlamat, editEmail, editNohp, editHarga;
    Button btnRegistrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        ButterKnife.bind(this);

        //init var
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editNama = findViewById(R.id.editNama);
        editAlamat = findViewById(R.id.editAlamat);
        editEmail = findViewById(R.id.editEmail);
        editNohp = findViewById(R.id.editNohp);
        editHarga = findViewById(R.id.editHarga);

        btnRegistrasi = findViewById(R.id.btnRegistrasi);

        _signinLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        _backbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });


        //init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabel_user = database.getReference("tbl_user");

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(Registrasi.this);
                mDialog.setMessage("Mohon Tunggu Sebentar");
                mDialog.show();

                tabel_user.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //cek jika menggunakan Username yang terkapakai
                        if (dataSnapshot.child(editUsername.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(Registrasi.this, "Username Sudah Terpakai", Toast.LENGTH_SHORT).show();
                        }
                        else if(editUsername.getText().toString().length()==0){
                            mDialog.dismiss();
                            Toast.makeText(Registrasi.this, "Username Belum Diisi", Toast.LENGTH_SHORT).show();
                        }
                        else if(editEmail.getText().toString().length()==0){
                            mDialog.dismiss();
                            Toast.makeText(Registrasi.this, "Email Belum Diisi", Toast.LENGTH_SHORT).show();
                        }
                        else if(editPassword.getText().toString().length()==0) {
                            mDialog.dismiss();
                            Toast.makeText(Registrasi.this, "Password Belum Diisi", Toast.LENGTH_SHORT).show();
                        }
                        else if(editNama.getText().toString().length()==0){
                            mDialog.dismiss();
                            Toast.makeText(Registrasi.this, "Nama Belum Diisi", Toast.LENGTH_SHORT).show();
                        }
                        //Mengecek Form Email
                        else if(editAlamat.getText().toString().length()==0){
                            mDialog.dismiss();
                            Toast.makeText(Registrasi.this, "Alamat Belum Diisi", Toast.LENGTH_SHORT).show();
                        }
                        //Mengecek Form Email
                        else if(editNohp.getText().toString().length()==0){
                            mDialog.dismiss();
                            Toast.makeText(Registrasi.this, "No HP Belum Diisi", Toast.LENGTH_SHORT).show();
                        }
                        else if(editHarga.getText().toString().length()==0){
                            mDialog.dismiss();
                            Toast.makeText(Registrasi.this, "Harga Belum Diisi", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mDialog.dismiss();
                            tbl_user tbl_user = new tbl_user(editUsername.getText().toString(), editPassword.getText().toString(), editNama.getText().toString(), editAlamat.getText().toString(), editEmail.getText().toString(), editNohp.getText().toString(), editHarga.getText().toString());
                            tabel_user.child(editUsername.getText().toString()).setValue(tbl_user);
                            Toast.makeText(Registrasi.this, "Berhasil Registrasi", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), Homepemilik.class);

                            intent.putExtra(Nama, tbl_user.getNama());
                            intent.putExtra(Alamat, tbl_user.getAlamat());
                            intent.putExtra(Email, tbl_user.getEmail());
                            intent.putExtra(Nohp, tbl_user.getNohp());
                            intent.putExtra(Harga, tbl_user.getHarga());
                            intent.putExtra(UsernameP, tbl_user.getUsername());
                            intent.putExtra(PasswordP, tbl_user.getPassword());

                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(Registrasi.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
