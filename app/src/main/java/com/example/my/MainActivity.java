package com.example.my;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText editTextInput;
    File filePath = null;
    String a="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);



        editTextInput = findViewById(R.id.editTextTextPersonName);

        String s=editTextInput.getText().toString();
        String path1=a+".docx";

        a=path1;

       // filePath = new File(getExternalFilesDir(null), "f.docx");
        String path = Environment.getExternalStorageDirectory().toString();
        filePath = new File(path, new StringBuilder().append("/documents/PCM_APP/").append("testing1.docx").toString());
       // filePath=new File(,"text.docx");
       // filePath = new File(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "test.docx");

        try {
            if (!filePath.exists()){
                filePath.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void buttonCreate(View view) {

        try {

            String path = Environment.getExternalStorageDirectory().toString();

            //File file=new File(pcmc);
            File file = new File(path, "/documents/PCM_APP/"+"sam.jpg");


            XWPFDocument xwpfDocument = new XWPFDocument();
            XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
            XWPFRun xwpfRun = xwpfParagraph.createRun();

           // InputStream pictureInputStream = new FileInputStream("D:\\logo.jpg");
            InputStream pictureInputStream = new FileInputStream(file);
            xwpfRun.addPicture(pictureInputStream, XWPFDocument.PICTURE_TYPE_JPEG, "sam.jpg", 300, 150);



            xwpfRun.setText(editTextInput.getText().toString());
            xwpfRun.setFontSize(24);

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            xwpfDocument.write(fileOutputStream);

            if (fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
                pictureInputStream.close();
                Toast.makeText(MainActivity.this,"File created",Toast.LENGTH_LONG).show();
            }
            xwpfDocument.close();
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this," fail ",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}