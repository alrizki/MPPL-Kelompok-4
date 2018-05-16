package laundry.laundry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
 * Created by Saepul Uyun on 4/14/2018.
 */

public class Infolaundry extends AppCompatActivity {

    public static final String Username = "username";

    @BindView(R.id.listBtn) TextView _listbtn;
    @BindView(R.id.backbtn) TextView _backbtn;

    TextView viewNama;
    TextView viewAlamat;
    TextView viewEmail;
    TextView viewNohp;
    TextView viewHarga;
    TextView viewUsername;

    Button btnviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infolaundry);
        ButterKnife.bind(this);

        viewNama = (TextView) findViewById(R.id.viewNama);
        viewAlamat = (TextView) findViewById(R.id.viewAlamat);
        viewEmail = (TextView) findViewById(R.id.viewEmail);
        viewNohp = (TextView) findViewById(R.id.viewNohp);
        viewHarga = (TextView) findViewById(R.id.viewHarga);
        viewUsername = (TextView) findViewById(R.id.viewUsername);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("tbl_user");


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

        _listbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(Infolaundry.this);
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
                                Toast.makeText(Infolaundry.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                                // Start the Signup activity
                                Intent intent = new Intent(getApplicationContext(), Liststatusplgn.class);
                                intent.putExtra(Username, tbl_user.getUsername());
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

    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(Infolaundry.this, Listlaundry.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
