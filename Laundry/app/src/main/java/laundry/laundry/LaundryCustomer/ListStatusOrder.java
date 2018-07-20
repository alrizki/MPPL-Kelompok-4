package laundry.laundry.LaundryCustomer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import laundry.laundry.R;
import laundry.laundry.database.pelanggan;
import laundry.laundry.database.tbl_user;

/**
 * Created by Saepul Uyun on 5/21/2018.
 */

public class ListStatusOrder extends AppCompatActivity {

    //Inisialisai Intent
    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String Username = "username";
    public static final String Password = "password";

    //inisialisasi variable
    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @BindView(R.id.search_btn) TextView _mSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liststatusorder);
        ButterKnife.bind(this);

        //inisialisasi variable layout
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("List Status Laundry");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        final Intent intent = getIntent();

        final String username = intent.getStringExtra(InfoLaundry.Username);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("plgn").child((username));

        mSearchField = findViewById(R.id.search_field);

        mResultList = findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        //function jika tombol search di tekan.
        _mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);

            }
        });
        String searchText = mSearchField.getText().toString();
        firebaseUserSearch(searchText);
    }

    //function untuk fillter data saat tombol click di tekan.
    private void firebaseUserSearch(String searchText) {

        Query firebaseSearchQuery = mUserDatabase.orderByChild("nama").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<pelanggan, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<pelanggan, UsersViewHolder>(

                pelanggan.class,
                R.layout.list_layout_search,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, pelanggan model, int position) {

                viewHolder.setDetails(getApplicationContext(), model.getNama(), model.getStatus(), model.getTanggal());

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }


    // View Holder Class
    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String userName, String userStatus, String userTgl){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_status = (TextView) mView.findViewById(R.id.status_text);
            TextView user_tgl = (TextView) mView.findViewById(R.id.tgl_text);


            user_name.setText(userName);
            user_status.setText(userStatus);
            user_tgl.setText(userTgl);

        }
    }

    //function untuk fitur kembali ke tampilan home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(ListStatusOrder.this, InfoLaundry.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            final Intent intent = getIntent();
            final String username = intent.getStringExtra(InfoLaundry.Username);
            final ProgressDialog mDialog = new ProgressDialog(ListStatusOrder.this);
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
                            Intent intent = new Intent(getApplicationContext(), InfoLaundry.class);
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
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //function untuk fitur kembali ke tampilan sebelumnya.
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("tbl_user");
    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(ListStatusOrder.this, InfoLaundry.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final Intent intent = getIntent();
        final String username = intent.getStringExtra(InfoLaundry.Username);
        final ProgressDialog mDialog = new ProgressDialog(ListStatusOrder.this);
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
                        Intent intent = new Intent(getApplicationContext(), InfoLaundry.class);
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
