package laundry.laundry;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import laundry.laundry.linkdatabase.pelanggan;
import laundry.laundry.linkdatabase.tbl_user;

public class Inputpelanggan extends AppCompatActivity {

    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String Username = "username";
    public static final String Password = "password";

    TextView viewUsername;
    EditText editPlgn;
    Spinner editStatus;

    Button btnSimpan;

    ListView listViewPlgn;

    DatabaseReference databasePlgn;

    List<pelanggan> plgnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputpelanggan);

        viewUsername = (TextView) findViewById(R.id.viewUsername);
        editPlgn = (EditText) findViewById(R.id.editPlgn);
        editStatus = (Spinner) findViewById(R.id.editStatus);


        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        listViewPlgn = (ListView) findViewById(R.id.listviewPlgn);

        final Intent intent = getIntent();

        plgnList = new ArrayList<>();

        final String username = intent.getStringExtra(Homepemilik.Username);

        viewUsername.setText(username);

        databasePlgn = FirebaseDatabase.getInstance().getReference("plgn").child(username);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               savePlgn();
            }
        });

        listViewPlgn.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                pelanggan plgn = plgnList.get(i);

                showUpdateDialog(username, plgn.getPlgnId(), plgn.getNama(), plgn.getStatus());

                return false;
            }
        });
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

                Listbynameplgn plgnListAdapter = new Listbynameplgn(Inputpelanggan.this, plgnList);
                listViewPlgn.setAdapter(plgnListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void savePlgn(){
        String namaplgn = editPlgn.getText().toString().trim();
        String status = editStatus.getSelectedItem().toString();

        if(!TextUtils.isEmpty(namaplgn)){
            String idplgn = databasePlgn.push().getKey();

            pelanggan plgn = new pelanggan(idplgn, namaplgn, status);

            databasePlgn.child(idplgn).setValue(plgn);
            Toast.makeText(this, "Data berhasil di simpan", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
    }

    private void showUpdateDialog(final String Username, final String plgnId, final String Nama, final String Status){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_updatestatus, null);

        dialogBuilder.setView(dialogView);

        final Spinner editStatus = (Spinner) dialogView.findViewById(R.id.editStatus);
        final Button btnSimpans = (Button) dialogView.findViewById(R.id.btnSimpans);

        dialogBuilder.setTitle("Update Status " + Nama);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnSimpans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String status = editStatus.getSelectedItem().toString();

                updateStatus(Username, plgnId, Nama, status);

                alertDialog.dismiss();
            }
        });
    }

    private boolean updateStatus(String Username, String plgnId, String Nama, String Status){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("plgn").child(Username).child(plgnId);

        pelanggan plgn = new pelanggan(plgnId, Nama, Status);

        databaseReference.setValue(plgn);

        Toast.makeText(this, "Status Berhasil di Ubah", Toast.LENGTH_LONG).show();

        return true;
    }

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("tbl_user");

    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(   Inputpelanggan.this, Homepemilik.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final Intent intent = getIntent();
        final String username = intent.getStringExtra(Homepemilik.Username);
        final ProgressDialog mDialog = new ProgressDialog(Inputpelanggan.this);
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
                        Intent intent = new Intent(getApplicationContext(), Homepemilik.class);
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
