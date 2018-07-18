package laundry.laundry.LaundryCustomer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import laundry.laundry.LaundryOwner.HomeOwnerLaundry;
import laundry.laundry.R;
import laundry.laundry.database.tbl_user;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;

    public static final String Nama = "nama";
    public static final String Alamat= "alamat";
    public static final String Email= "email";
    public static final String Nohp= "nohp";
    public static final String Harga= "harga";
    public static final String Username = "username";
    public static final String Password = "password";
    public static final String Usernamep = "Usernamep";
    public static final String namaLaundry = "namalaundry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (googleServicesAvailable()) {
            setContentView(R.layout.activity_maps);
            initMap();
        } else {
            // No Google Maps Layout
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        //inisialisai firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference maps = database.getReference("maps");

        //inisialisai variabel pada layout
        final Intent intent = getIntent();
        double lat = 0;
        double lng = 0;
        final String username = intent.getStringExtra(InfoLaundry.Usernamep);
        final String namaLaundry = intent.getStringExtra(InfoLaundry.namaLaundry);
        lat = intent.getDoubleExtra("lat", 107.615207);
        lng = intent.getDoubleExtra("lng", -6.886965);

        //function untuk menampilkan marker pada maps isi datanya.
        if(mGoogleMap != null){

            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.marker, null);

                    TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);

                    LatLng ll = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText("Latitude: " + ll.latitude);
                    tvLng.setText("Longitude: " + ll.longitude);
                    tvSnippet.setText(marker.getSnippet());

                    return v;
                }
            });
        }


        //function zoom default lokasi maps
        goToLocationZoom(lng, lat , 15);

        MarkerOptions options = new MarkerOptions()
                .title(namaLaundry)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                .position(new LatLng(lng, lat))
                .snippet("Your Location");
        mGoogleMap.addMarker(options);


    }

    //function untuk zoom maps ketika melakukan search
    private void goToLocationZoom(double lat, double lng, int zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);


    }

    //function untuk membuat menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //function fitur untuk memilih jenis maps
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //function untuk fitur kembali ke tampilan sebelumnya
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("tbl_user");

    @Override
    public void onBackPressed() {
        Intent i;

        super.onBackPressed();
        i = new Intent(   Maps.this, InfoLaundry.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final Intent intent = getIntent();
        final String username = intent.getStringExtra(HomeOwnerLaundry.Username);
        final ProgressDialog mDialog = new ProgressDialog(Maps.this);
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}