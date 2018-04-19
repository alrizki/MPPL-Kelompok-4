package laundry.laundry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import laundry.laundry.linkdatabase.tbl_user;

/**
 * Created by Saepul Uyun on 4/4/2018.
 */

public class Registrasi extends AppCompatActivity {

    EditText editUsername, editPassword, editNama, editAlamat, editEmail, editNohp, editHarga;
    Button btnRegistrasi;

    //TextInputLayout variables
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutAlamat;
    private TextInputLayout textInputLayoutNohp;
    private TextInputLayout textInputLayoutHarga;

    private ValidationHelper validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.text_input_layout_name);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.text_input_layout_email);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.text_input_layout_password);
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.text_input_layout_username);
        textInputLayoutAlamat = (TextInputLayout) findViewById(R.id.text_input_layout_alamat);
        textInputLayoutNohp = (TextInputLayout) findViewById(R.id.text_input_layout_nohp);
        textInputLayoutHarga = (TextInputLayout) findViewById(R.id.text_input_layout_harga);

        //init var
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editNama = findViewById(R.id.editNama);
        editAlamat = findViewById(R.id.editAlamat);
        editEmail = findViewById(R.id.editEmail);
        editNohp = findViewById(R.id.editNohp);
        editHarga = findViewById(R.id.editHarga);

        btnRegistrasi = (Button) findViewById(R.id.btnRegistrasi);

        validation = new ValidationHelper(this);


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
                        if (!validation.isEditTextFilled(editUsername, textInputLayoutName, getString(R.string.error_message_name))) {
                            return;
                        }
                        // validasi username harus isi
                        else if (!validation.isEditTextFilled(editUsername, textInputLayoutName, getString(R.string.error_message_name))) {
                            return;
                        }
                        //validasi password harus isi
                        else if (!validation.isEditTextFilled(editPassword, textInputLayoutName, getString(R.string.error_message_name))) {
                            return;
                        }
                        //validasi nama harus isi
                        else if (!validation.isEditTextFilled(editNama, textInputLayoutName, getString(R.string.error_message_name))) {
                            return;
                        }
                        //validasi alamat harus isi
                        else if (!validation.isEditTextFilled(editAlamat, textInputLayoutName, getString(R.string.error_message_name))) {
                            return;
                        }
                        //validasi email harus isi
                        else if (!validation.isEditTextFilled(editEmail, textInputLayoutName, getString(R.string.error_message_name))) {
                            return;
                        }
                        //validasi No HP harus isi
                        else if (!validation.isEditTextFilled(editNohp, textInputLayoutName, getString(R.string.error_message_name))) {
                            return;
                        }
                        //validasi harga harus isi
                        else if (!validation.isEditTextFilled(editHarga, textInputLayoutName, getString(R.string.error_message_name))) {
                            return;
                        }



                        else {
                            mDialog.dismiss();
                            tbl_user tbl_user = new tbl_user(editUsername.getText().toString(), editPassword.getText().toString(), editNama.getText().toString(), editAlamat.getText().toString(), editEmail.getText().toString(), editNohp.getText().toString(), editHarga.getText().toString());
                            tabel_user.child(editUsername.getText().toString()).setValue(tbl_user);
                            Toast.makeText(Registrasi.this, "Berhasil Registrasi", Toast.LENGTH_SHORT).show();
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
}
