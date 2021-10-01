package com.example.cliente;

import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;



public class PublicidadFragment extends Fragment  {

   private Spinner spinner_cat;
   private Button btn_pagar;
   private EditText date_f,date_i;
   private EditText descripcion,titulo,url;
   private EditText fechainicio, fechafinal,horainicio,horafinal;
   private TextView dias,monto,precio;
   String idusuario;
   DatePickerDialog.OnDateSetListener setListener;
   MenuInicioActivity menuInicioActivity = new MenuInicioActivity();

    //Declaring views
    private Button buttonChoose;

    private Button buttonRegistrar;
    private ImageView imageView;



    private int PICK_IMAGE_REQUEST = 1;


    private static final int STORAGE_PERMISSION_CODE = 123;
    int y,m,d;
    int y2,m2,d2;
    int hr,mn;
    int hr2,mn2;
    private Bitmap bitmap;

    private Uri filePath;
    public static final String UPLOAD_URL = "http://192.168.1.125:2020/APIS/patrocinador/uploadImages.php";
    Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_publicidad, container, false);
        //Cargando Elementos UI
        spinner_cat = (Spinner) view.findViewById(R.id.spin_cat);
        date_i=(EditText) view.findViewById(R.id.txt_date2);
        date_f=(EditText) view.findViewById(R.id.txt_date);
        horainicio=(EditText)view.findViewById(R.id.txt_horai);
        horafinal=(EditText)view.findViewById(R.id.txt_horaf);
        descripcion = (EditText) view.findViewById(R.id.mtxt_descripcion);
        titulo= (EditText) view.findViewById(R.id.txt_titulo);
        url = (EditText)view.findViewById(R.id.txt_url);

        dias=(TextView)view.findViewById(R.id.txt_dias);
        monto=(TextView)view.findViewById(R.id.txtMonto);
        precio=(TextView)view.findViewById(R.id.txtprecio);
        idusuario = getActivity().getIntent().getExtras().getString("idusuario");

        buttonChoose = (Button) view.findViewById(R.id.buttonChoose);

        buttonRegistrar = (Button)view.findViewById(R.id.btnRegistrar);
        imageView = (ImageView) view.findViewById(R.id.uploadimg);




        //Carga de datos al spinner de categorias

        String[] arraySpinner = new String[] {
                "Posicionar marca",
                "Anunciar Productos u Ofertas",
                "Publicidad institucional",
                "Publicidad sin fines de lucro",
                "Publicidad de servicio publico"

        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cat.setAdapter(adapter);

        //Carga de datos al datepicker




        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute=calendar.get(Calendar.MINUTE);




        long now = System.currentTimeMillis() - 1000;

        String today = year+"-"+(month+1)+"-"+day;
        String hournow =  hour+":"+minute+":00";
        String tomorrow = year+"-"+(month+1)+"-"+(day+1);
        String hourtomorrow = (hour+1)+":"+minute+":00";
        hr2=hour+1;
        date_i.setText(today);
        date_f.setText(tomorrow);
        horainicio.setText(hournow);
        horafinal.setText(hourtomorrow);




        date_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date =year+"-"+month+"-"+day;
                        y=year;
                        m=month-1;
                        d=day;

                        date_i.setText(date);

                        try {
                            int diff=getDaysDifference(date_i.getText().toString(),date_f.getText().toString());
                            dias.setText(diff+" dias");
                            monto.setText(""+Float.parseFloat(precio.getText().toString())*diff);

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.getMessage()+"SALIO",Toast.LENGTH_SHORT).show();
                        }
                    }

                },y,m,d);
                datePickerDialog.getDatePicker().setMinDate(now);

                String datef= date_f.getText().toString();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date d = f.parse(datef);
                    long milliseconds = d.getTime();
                    datePickerDialog.getDatePicker().setMaxDate(milliseconds);

                    datePickerDialog.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });




        date_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {



                    @Override
                    public void onDateSet(DatePicker view, int yearf, int monthf, int dayf) {
                        monthf = monthf+1;
                        String date =yearf+"-"+monthf+"-"+dayf;
                        y2=yearf;
                        m2=monthf-1;
                        d2=dayf;
                        date_f.setText(date);

                        try {
                            int diff=getDaysDifference(date_i.getText().toString(),date_f.getText().toString());
                            dias.setText(diff+" dias");
                            monto.setText(""+Float.parseFloat(precio.getText().toString())*diff);


                        } catch (ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.getMessage()+"SALIO",Toast.LENGTH_SHORT).show();
                        }
                    }
                },y2,m2,d2);
                String datei= date_i.getText().toString();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date d = f.parse(datei);
                    long milliseconds = d.getTime();
                    datePickerDialog.getDatePicker().setMinDate(milliseconds);

                    datePickerDialog.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

      horainicio.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              int seconds=0;
              TimePickerDialog timePickerDialog =  TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                  @Override
                  public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                      horainicio.setText(String.format("%02d:%02d", hourOfDay, minute));
                      hr=hourOfDay;
                      mn=minute;
                  }
              }, hour, minute, seconds, true);
                timePickerDialog.setMinTime(hour,minute,0);
             timePickerDialog.setMaxTime(hr2,mn2,0);
              timePickerDialog.show(getFragmentManager(),"TimePickerDialog");


          }
      });

        horafinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int seconds=0;
               TimePickerDialog timePickerDialog =  TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePickerDialog view, int hourOfDayf, int minutef, int second) {
                       horafinal.setText(String.format("%02d:%02d", hourOfDayf, minutef));
                       hr2=hourOfDayf;
                       mn2=minutef;
                   }
               }, hour, minute, seconds, true);
               timePickerDialog.setMinTime(hr,mn,0);
                timePickerDialog.show(getFragmentManager(),"TimePickerDialog");

            }
        });


        //descripcion antiscroll

        descripcion.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (descripcion.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }


        });

        requestStoragePermission();



        //Setting clicklistener
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Anuncios")
                        .setMessage("¿Quieres registrar tu anuncio?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                              //  Toast.makeText(getContext(), "Yes", Toast.LENGTH_SHORT).show();
                                if(IsValid(titulo) && IsValid(descripcion) && IsValid(url)&& IsValidImage(filePath,getContext())){

                                    uploadMultipart();
                                    ejecutarServicio("http://192.168.1.125:2020/APIS/patrocinador/registrarAnuncio.php");


                                }

                            }})
                        .setNegativeButton(android.R.string.no, null).show();



               // menuInicioActivity.buscarAnuncio("http://192.168.1.125:2020/APIS/patrocinador/consultaranuncio.php");
            }
        });

        return view;
    }


    public void uploadMultipart() {


        //getting the actual path of the image
        String path = getPath(filePath);


        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            new MultipartUploadRequest(getContext(), uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", "none") //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Registro exitoso")
                        .setPositiveButton("ok", null)
                        .setMessage( "Su anuncio se ha publicado exitosamente. \n"+response )
                        .show();
                titulo.getText().clear();
                descripcion.getText().clear();
                url.getText().clear();
                monto.setText("0.00");

                dias.setText("0 dias");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){



            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                String imagenString =getBase64String(bitmap);

                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("categoria",spinner_cat.getSelectedItem().toString());
                parametros.put("titulo",titulo.getText().toString());
                parametros.put("descr",descripcion.getText().toString());
                parametros.put("image",imagenString);
                parametros.put("url","http://"+url.getText().toString());
                parametros.put("fechainicio",date_i.getText().toString()+" "+horainicio.getText().toString());
                parametros.put("fechafinal",date_f.getText().toString()+" "+horafinal.getText().toString());
                parametros.put("monto",monto.getText().toString());
                parametros.put("idper", idusuario);


                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return base64String;
    }

    public static int getDaysDifference(String datei,String datef) throws ParseException {
        Date fromDate=new SimpleDateFormat("yyyy-MM-dd").parse(datei);
        Date toDate=new SimpleDateFormat("yyyy-MM-dd").parse(datef);
        if(fromDate==null||toDate==null)
            return 0;

        return (int)( (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }


    public static boolean IsValid(EditText campo){
        boolean valido;

        if(campo.length()>0){
            valido=true;

        }else{
            valido=false;
            campo.requestFocus();
            new AlertDialog.Builder(campo.getContext())
                    .setTitle("Campo Obligatorio")
                    .setPositiveButton("ok", null)
                    .setMessage( "Este campo es obligatorio, no puede quedar vacio."  )
                    .show();
        }

        return valido;
    };


    public static boolean IsValidImage(Uri campo, Context cnt){
        boolean valido;

        if(campo!=null){
            valido=true;

        }else{
            valido=false;

            new AlertDialog.Builder(cnt)
                    .setTitle("Imagen Obligatoria")
                    .setPositiveButton("ok", null)
                    .setMessage( "No se subio ninguna imagen para la publicidad."  )
                    .show();
        }

        return valido;
    };

  


}