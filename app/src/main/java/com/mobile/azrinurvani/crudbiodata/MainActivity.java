package com.mobile.azrinurvani.crudbiodata;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobile.azrinurvani.crudbiodata.adapter.BiodataAdapter;
import com.mobile.azrinurvani.crudbiodata.model.DataBiodata;
import com.mobile.azrinurvani.crudbiodata.model.ResponseGetBiodata;
import com.mobile.azrinurvani.crudbiodata.model.ResponseInsertBiodata;
import com.mobile.azrinurvani.crudbiodata.network.ConfigRetrofit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    EditText editNama,editAlamat;
    Spinner spinnerHobi;
    RadioButton rbPerempuan,rbLaki;
    String itemHobi,jekel;

    String arrayHobi[] = {"Musik","Olahraga","Baca Buku","Ngoding","Travelling","dll"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                panggil method dialog input
                displayDialogInput();
            }
        });
        tampilkanData();

    }

    private void tampilkanData() {
//        progress dialog ketika aplikasi di jalankan
        final ProgressDialog loading = ProgressDialog.show(this,"","Loading...",false);

        ConfigRetrofit.service.getBiodata().enqueue(new Callback<ResponseGetBiodata>() {
            @Override
            public void onResponse(Call<ResponseGetBiodata> call, Response<ResponseGetBiodata> response) {
                loading.dismiss();
                if (response.isSuccessful()){
                    String pesan = response.body().getPesan();
                    int status = response.body().getStatus();
                    if (status==1){
                        Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        List<DataBiodata> dataBiodata = response.body().getDataBiodata();
                        BiodataAdapter adapter = new BiodataAdapter(dataBiodata);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    }else{
                        Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGetBiodata> call, final Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        Toast.makeText(MainActivity.this, "Tidak ada koneksi, pesan : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    //    Tambahkan method untuk menampilkan dialog Input
    private void displayDialogInput() {
//        menempelkan layout dialog input pada sebuah alert
        LayoutInflater inflater = getLayoutInflater();
        View alertlayout = inflater.inflate(R.layout.dialog_input,null);

//        inisialisasi id
        editNama = alertlayout.findViewById(R.id.editNama);
        editAlamat= alertlayout.findViewById(R.id.editAlamat);

        spinnerHobi = alertlayout.findViewById(R.id.spinnerHobi);
        rbLaki = alertlayout.findViewById(R.id.rbLaki);
        rbPerempuan = alertlayout.findViewById(R.id.rbPerempuan);

//        Membuat adapater spinner
        ArrayAdapter<String>adapterHobi =
                new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,arrayHobi);

        adapterHobi.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerHobi.setAdapter(adapterHobi);
        spinnerHobi.setOnItemSelectedListener(new MyOnItemSelectedListener());

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(alertlayout);
        dialog.setTitle("Input Biodata");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                pilihanJekel();
                String nama = editNama.getText().toString();
                String hobi = spinnerHobi.getSelectedItem().toString();
                String alamat = editAlamat.getText().toString();

                ConfigRetrofit.service.insertBiodata(nama,jekel,hobi,alamat).enqueue(new Callback<ResponseInsertBiodata>() {
                    @Override
                    public void onResponse(Call<ResponseInsertBiodata> call, Response<ResponseInsertBiodata> response) {
                        if (response.isSuccessful()){
                            String pesan = response.body().getPesan();
                            int status = response.body().getStatus();
                            if (status==1){
                                Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                recreate();
                            }else{
                                Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseInsertBiodata> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Tidak ada koneksi, pesan : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void pilihanJekel() {
        if (rbLaki.isChecked()){
            jekel = rbLaki.getText().toString();
        }else if(rbPerempuan.isChecked()){
            jekel = rbPerempuan.getText().toString();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.exit) {
            exitAplikasi();
        }

        return super.onOptionsItemSelected(item);
    }

    private void exitAplikasi() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Keluar Aplikasi");
        alert.setMessage("Apakah anda yakin keluar ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }


    //    method override dari OnItemSelectedListener
    class MyOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itemHobi = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
