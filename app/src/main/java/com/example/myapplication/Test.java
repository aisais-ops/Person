package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Test extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f=new File("C:\\Users\\ifmotica\\AndroidStudioProjects\\MyApplication4\\app\\src\\main\\java\\com\\example\\myapplication\\","file.txt");
                FileWriter fw = null;
                try {
                    fw = new FileWriter(f);
                    PrintWriter pw=new PrintWriter(fw);
                    pw.println("lol");
                    System.out.println("ok################################################################");
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}