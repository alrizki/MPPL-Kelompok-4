package laundry.laundry.LaundryOwner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import laundry.laundry.Home;
import laundry.laundry.R;
import laundry.laundry.database.tbl_user;



public class Login extends AppCompatActivity {

    @BindView(R.id.signupLink) TextView _signupLink;
    @BindView(R.id.backbtn) TextView _backbtn;

    EditText Username, Password;
    Button btnLogin;

    //inisialisasi intent
    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String UsernameP = "username";
    public static final String PasswordP = "password";

    ListView listviewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //inisialisasi variabel layout.
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        getWindow().setBackgroundDrawableResource(R.drawable.background);

        //function tombol back ke home
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

        //function tombol signup di click
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        //init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("tbl_user");

        List<tbl_user> userList;

        //function tombol llogin di click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(Login.this);
                mDialog.setMessage("Mohon menunggu..");
                mDialog.show();

                //validation form
                if(Username.getText().toString().length()==0){
                    mDialog.dismiss();
                    Toast.makeText(Login.this, "Username Belum Diisi", Toast.LENGTH_SHORT).show();
                } else if(Password.getText().toString().length()==0){
                    mDialog.dismiss();
                    Toast.makeText(Login.this, "Password Belum Diisi", Toast.LENGTH_SHORT).show();
                }else {

                    //cek ke database
                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //cek jika user tidak terdaftar didatabase
                            if (dataSnapshot.child(Username.getText().toString()).exists()) {


                                //ambil data user
                                mDialog.dismiss();
                                final tbl_user tbl_user = dataSnapshot.child(Username.getText().toString()).getValue(tbl_user.class);
                                if (tbl_user.getPassword().equals(Password.getText().toString())) {
                                    Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeOwnerLaundry.class);

                                    intent.putExtra(Nama, tbl_user.getNama());
                                    intent.putExtra(Alamat, tbl_user.getAlamat());
                                    intent.putExtra(Email, tbl_user.getEmail());
                                    intent.putExtra(Nohp, tbl_user.getNohp());
                                    intent.putExtra(Harga, tbl_user.getHarga());
                                    intent.putExtra(UsernameP, tbl_user.getUsername());
                                    intent.putExtra(PasswordP, tbl_user.getPassword());

                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Login.this, "Usename/Password Salah!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(Login.this, "User belum terdaftar di database", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
    }

    //function untuk kembali ke tampilan sebelumnya.
    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(Login.this, Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
