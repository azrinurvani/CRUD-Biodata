package com.mobile.azrinurvani.crudbiodata;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobile.azrinurvani.crudbiodata.model.ResponseInsertBiodata;
import com.mobile.azrinurvani.crudbiodata.network.ConfigRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDataActivity extends AppCompatActivity {


    String id;
    String itemHobi, jekel;


    String arrayHobi[] = {"Musik", "Olahraga", "Baca Buku", "Ngoding", "Travelling", "dll"};
    @BindView(R.id.editNama)
    EditText editNama;
    @BindView(R.id.rbPerempuan)
    RadioButton rbPerempuan;
    @BindView(R.id.rbLaki)
    RadioButton rbLaki;
    @BindView(R.id.spinnerHobi)
    Spinner spinnerHobi;
    @BindView(R.id.editAlamat)
    EditText editAlamat;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    public String nama;
    public String alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        ButterKnife.bind(this);


        id = getIntent().getStringExtra("id");
        nama = getIntent().getStringExtra("nama");
        alamat = getIntent().getStringExtra("alamat");

        ArrayAdapter<String> adapterHobi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayHobi);
        adapterHobi.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerHobi.setAdapter(adapterHobi);
        spinnerHobi.setOnItemSelectedListener(new MyOnItemSelectedListener());

        editNama.setText(nama);
        editAlamat.setText(alamat);


    }

    @OnClick(R.id.btnUpdate)
    public void onViewClicked() {
        updateData();
    }

    private void updateData() {
        pilihanJekel();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Update Data");
        alert.setCancelable(false);
        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigRetrofit.service.updateBiodata(id,nama,jekel,itemHobi,alamat).enqueue(new Callback<ResponseInsertBiodata>() {
                    @Override
                    public void onResponse(Call<ResponseInsertBiodata> call, Response<ResponseInsertBiodata> response) {
                        if (response.isSuccessful()){
                            String pesan = response.body().getPesan();
                            int status = response.body().getStatus();
                            if (status==1){
                                Toast.makeText(EditDataActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditDataActivity.this,MainActivity.class));
                                finish();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseInsertBiodata> call, Throwable t) {
                        Toast.makeText(EditDataActivity.this,
                                "Gagal koneksi, pesan : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
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
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_delete,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.menuHapus){
            deleteData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteData() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Hapus data");
        alert.setCancelable(false);
        alert.setMessage("Apakah anda yakin ?");
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigRetrofit.service.deleteBiodata(id).enqueue(new Callback<ResponseInsertBiodata>() {
                    @Override
                    public void onResponse(Call<ResponseInsertBiodata> call, Response<ResponseInsertBiodata> response) {
                        if (response.isSuccessful()){
                            String pesan = response.body().getPesan();
                            int status = response.body().getStatus();
                            if (status==1){
                                Toast.makeText(EditDataActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditDataActivity.this,MainActivity.class));
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseInsertBiodata> call, Throwable t) {

                    }
                });
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


    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itemHobi = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
