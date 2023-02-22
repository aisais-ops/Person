package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import java.io.*;

import android.os.Environment;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity2 extends AppCompatActivity{
    TextView tv;
    ListView listView;
    Button saveTextFile,saveBinaryFile,back;
    ArrayAdapter<Person> adapter2;
    ArrayList<Person> list=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv=(TextView) findViewById(R.id.tv);
        listView=(ListView) findViewById(R.id.listView) ;
        back=(Button) findViewById(R.id.back);
        saveTextFile=(Button) findViewById(R.id.saveTextfile);
        saveBinaryFile=(Button) findViewById(R.id.saveBinaryfile);
        //récupérer Intent et lire l'extra
        Intent i=getIntent();
        Serializable s=i.getSerializableExtra("list");
        if(s !=null) list =(ArrayList<Person>)s ;
        //Trier la collection
        //Collections.sort(list) ;
        Collections.sort(list, new Comparator<Person>(){
            public int compare(Person p1,Person p2){
                return p2.getDateVacc2().compareTo(p1.getDateVacc1()); //ordre décroissant de vaccin2
            }}) ;
        //afficher la valeur de l’extra dans un TextView
        String ch="";
        for(Person p : list)ch+=p+"\n";
        tv.setText(ch);
        //afficher la valeur de l’extra dans un ListView
        adapter2=new ArrayAdapter<>( MainActivity2.this,android.R.layout.simple_list_item_single_choice,list);
        listView.setAdapter(adapter2);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //associer les listeners aux buttons
        saveTextFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    saveInTextFile("persons.txt",list);
                    Toast.makeText(MainActivity2.this,"Successfull save ",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity2.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        saveBinaryFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f=null;
                try {
                    f=new File("persons.bin");
                    saveInBinaryFile(f,list);
                    Toast.makeText(MainActivity2.this,"Successfull save in"+f.getAbsolutePath(),Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity2.this,"Not Successfull save in"+f.getAbsolutePath()+":"+e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                intent.putExtra("list",list);
                startActivity(intent);
            }
        });
    }
    public static void saveInBinaryFile(File f, List l)throws Exception{
        ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(f));
        for(Object o:l) writer.writeObject(o);
        writer.close();
    }
    public static void saveInTextFile(String filename,List l)throws Exception{
        File root = new File(Environment.getExternalStorageDirectory().toString(), "Notes");
        //   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        // if external memory exists and folder with name Notes this will create folder.
        if (!root.exists())  root.mkdirs(); //
        File f = new File(root,  filename);
        PrintWriter writer=new PrintWriter(new BufferedWriter (new FileWriter (f)));
        for (Object o : l) writer.println(o.toString());
        writer.close();
    }

}

