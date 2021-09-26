package com.example.cliente;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;



import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    EditText edtNombres, edtApPaterno, edtApMaterno, edtNumCredencial, edtNumCelular, edtCorreo;
    CheckBox chTerminos, chPolitica, chEnvioPromo;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_registro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Crear Cuenta");
        setSupportActionBar(toolbar);

        edtNombres=(EditText)findViewById(R.id.edtNombres);
        edtApPaterno=(EditText)findViewById(R.id.edtApPa);
        edtApMaterno=(EditText)findViewById(R.id.edtApMa);
        edtNumCredencial=(EditText)findViewById(R.id.edtNumC);
        edtNumCelular=(EditText)findViewById(R.id.edtNumCel);
        edtCorreo=(EditText)findViewById(R.id.edtCorreo);
        chTerminos=(CheckBox) findViewById(R.id.chAcTermino);
        chPolitica=(CheckBox) findViewById(R.id.chAcPolitica);
        chEnvioPromo=(CheckBox) findViewById(R.id.chAcPromo);

    }


    public void registrarUsuario (View view){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(RegistroActivity.this),PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        /*Intent i = new Intent(this, VerificarActivity.class);
        i.putExtra("nombres",edtNombres.getText().toString());
        i.putExtra("appaterno",edtApPaterno.getText().toString());
        i.putExtra("apmaterno",edtApMaterno.getText().toString());
        i.putExtra("numcredencial",edtNumCredencial.getText().toString());
        i.putExtra("numcelular",edtNumCelular.getText().toString());
        i.putExtra("correo",edtCorreo.getText().toString());
        startActivity(i);
        edtNombres.setText("");
        edtApPaterno.setText("");
        edtApMaterno.setText("");
        edtNumCredencial.setText("");
        edtNumCelular.setText("");
        edtCorreo.setText("");
        chTerminos.setChecked(false);
        chPolitica.setChecked(false);
        chEnvioPromo.setChecked(false);*/
    }

    public static void setStatusBarGradiant(Activity activity) {
        Window window = activity.getWindow();
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable background = activity.getResources().getDrawable(R.drawable.color_degradado_toolbar);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data,this);
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String places = String.valueOf(place.getAddress());
                Toast.makeText(RegistroActivity.this,":"+places,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, VerificarActivity.class);
                i.putExtra("nombres", edtNombres.getText().toString());
                i.putExtra("appaterno", edtApPaterno.getText().toString());
                i.putExtra("apmaterno", edtApMaterno.getText().toString());
                i.putExtra("numcredencial", edtNumCredencial.getText().toString());
                i.putExtra("numcelular", edtNumCelular.getText().toString());
                i.putExtra("correo", edtCorreo.getText().toString());
                startActivity(i);
                edtNombres.setText("");
                edtApPaterno.setText("");
                edtApMaterno.setText("");
                edtNumCredencial.setText("");
                edtNumCelular.setText("");
                edtCorreo.setText("");
                chTerminos.setChecked(false);
                chPolitica.setChecked(false);
                chEnvioPromo.setChecked(false);
            }
        }
    }
}