package com.example.intenexplicito;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private EditText etTelefono;
    private ImageButton btnllamar,btnCamara;

    private String numerotelefono;
    //codigo constante parar llamda
    private final int PHONE_CODE =100;
    private final int CAMERA_CODE =50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InicializarVistas();
        btnllamar.setOnClickListener(view -> {
            obtenerinformacion();
            activarserviciodellamada();
        });
        btnCamara.setOnClickListener(view -> {
            activarservicioCamara();
        });
    }

    private void activarservicioCamara() {
        Intent intentCamara = new Intent("android.media.caption.IMAGE");
        //startActivityForResult(intentCamara);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case CAMERA_CODE:
                if (requestCode == Activity.RESULT_OK){

                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void activarserviciodellamada() {
        if(!numerotelefono.isEmpty()){
            //evalua si la version es menor para su forma de trabajar
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
              //version nueva
              requestPermissions(new String[]{Manifest.permission.CALL_PHONE},PHONE_CODE);
            }else {
                //version antigua
                configurarVersionAntigua();
                //todo
            }
        }
    }
    private void configurarVersionAntigua() {
        //crear intent explicito
        Intent intenCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numerotelefono));
        if (revisarPermisos(Manifest.permission.CALL_PHONE)){
            startActivity(intenCall);
        }

    }
    private void obtenerinformacion() {
        numerotelefono=etTelefono.getText().toString();
    }
    private void InicializarVistas() {
        etTelefono= findViewById(R.id.etTelefono);
        btnllamar= findViewById(R.id.btnllamar);
        btnCamara= findViewById(R.id.btnCamara);
    }
    private boolean revisarPermisos(String permiso){
        //valor entero q representa un permiso requerido en la aplicacion
        int valorPermiso=this.checkCallingOrSelfPermission(permiso);
        return valorPermiso == PackageManager.PERMISSION_GRANTED;


    }
}