package laundry.laundry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Saepul Uyun on 4/14/2018.
 */

public class Infolaundry extends AppCompatActivity {

    TextView viewNama;
    TextView viewAlamat;
    TextView viewEmail;
    TextView viewNohp;
    TextView viewHarga;

    Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infolaundry);

        viewNama = (TextView) findViewById(R.id.viewNama);
        viewAlamat = (TextView) findViewById(R.id.viewAlamat);
        viewEmail = (TextView) findViewById(R.id.viewEmail);
        viewNohp = (TextView) findViewById(R.id.viewNohp);
        viewHarga = (TextView) findViewById(R.id.viewHarga);


        Intent intent = getIntent();

        String nama = intent.getStringExtra(Listlaundry.Nama);
        String alamat = intent.getStringExtra(Listlaundry.Alamat);
        String email = intent.getStringExtra(Listlaundry.Email);
        String nohp = intent.getStringExtra(Listlaundry.Nohp);
        String harga = intent.getStringExtra(Listlaundry.Harga);

        viewNama.setText(nama);
        viewAlamat.setText(alamat);
        viewEmail.setText(email);
        viewNohp.setText(nohp);
        viewHarga.setText(harga);
        }
}
