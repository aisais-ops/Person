package com.example. myapplication;

//import static com.example.myapplication.R.*;
import androidx.appcompat.app.*;
import android.os.Bundle;
import android.view.*;//View Gravity
import android.widget.*;//ScrollView LinearLayout GridLayout TextView ImageView EditText DatePicker NumberPicker SeekBar Spinner CheckBox Switch RadioGroup RadioButton CompoundButton Button ImageButton ArrayAdapter Toast
import android.widget.LinearLayout.LayoutParams;
import android.content.*;//Context Intent DialogInterface
import android.graphics.*;//Typeface Color
import android.util.*;//AttributeSet
import java.text.*;//DecimalFormat SimpleDateFormat ParseException
import java.util.*;//List ArrayList Arrays Calendar GregorianCalendar Date
import java.io.Serializable;
import android.util.TypedValue;
import android.text.InputType;
import android.content.res.TypedArray;

public class MainActivity extends AppCompatActivity {
    EditText id,nom,adresse,taille,dateVacc1;
    DatePicker dateVacc2;
    NumberPicker age;
    SeekBar poids;
    Spinner ville;
    CheckBox vaccine1;
    Switch vaccine2;
    RadioGroup genre;
    Button add,update,listAll,remove,search,first,prev,next,last;
    ImageButton clear;

    ArrayList<Person>list= new ArrayList<>();
    int pos=0 ;
    //String dateFormat=getResources().getString(R.string.format_date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.setTheme( R.style.MyAppTheme );
        /*1-associer un layout*/
        setContentView2(this);
       //setContentView(R.layout.activity_main);
        /*2-initialiser les vues en les recherchant par leur id*/
        id=(EditText) findViewById(R.id.id);
        nom=(EditText) findViewById(R.id.nom);
        adresse=(EditText)findViewById(R.id.adresse);
        taille=(EditText)findViewById(R.id.taille);
        dateVacc1= (EditText) findViewById(R.id.dateVacc1);
        dateVacc2= (DatePicker) findViewById(R.id.dateVacc2);
        vaccine1=(CheckBox)findViewById(R.id.vaccine1);
        vaccine2=(Switch) findViewById(R.id.vaccine2);
        age=(NumberPicker)findViewById(R.id.age);
        poids=(SeekBar)findViewById(R.id.poids);
        ville=(Spinner)findViewById(R.id.ville);
        genre=(RadioGroup)findViewById(R.id.genre);
        clear=(ImageButton)findViewById(R.id.clear);
        add=(Button)findViewById(R.id.add);
        update=(Button)findViewById(R.id.update);
        remove=(Button)findViewById(R.id.remove);
        listAll=(Button)findViewById(R.id.listAll);
        search=(Button)findViewById(R.id.search);
        first=(Button)findViewById(R.id.first);
        prev=(Button)findViewById(R.id.prev);
        next=(Button)findViewById(R.id.next);
        last=(Button)findViewById(R.id.last);
        /*3-récupérer les extras*/
		Serializable s=getIntent().getSerializableExtra("list");
        if(s!=null)list=(ArrayList<Person>)s;
		/*4-initialiser les valeurs des vues*/ 
        reset(null);
        /*5-associer les listeners*/
        //associer OnCheckedChangeListener à checkbox et Switch

        vaccine1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                dateVacc1.setEnabled(isChecked);
                vaccine2.setEnabled(isChecked);
                dateVacc2.setEnabled(isChecked && vaccine2.isChecked());
            }
        });


        vaccine2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                dateVacc2.setEnabled(isChecked);
            }});

        //associer OnClickListener aux buttons
        clear.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {reset(v);}}) ;
        add.setOnClickListener(new View.OnClickListener() { public void onClick(View v) {add(v); }});
        update.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {update(v);}});
        remove.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {remove(v);}}) ;
        listAll.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {listAll(v);}});
        search.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {search(v);}});
        first.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {naviguer(v);}});
        prev.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {naviguer(v);}});
        next.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {naviguer(v);}});
        last.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {naviguer(v);}});
    }

    public void reset(View v) {
        id.setText("");nom.setText("");adresse.setText("");taille.setText("");dateVacc1.setText("");

        vaccine1.setChecked(false);
        vaccine2.setEnabled(false);vaccine2.setChecked(false);
        dateVacc1.setEnabled(false);
        dateVacc2.setEnabled(false);

        ArrayAdapter adapter1=ArrayAdapter.createFromResource(this,R.array.ville_array,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ville.setAdapter(adapter1);

        ville.setSelection(0);

        genre.check(R.id.Mr);

        age.setMinValue(getResources().getInteger(R.integer.min_age));
        age.setMaxValue(getResources().getInteger(R.integer.max_age));
        age.setValue(30);

        poids.setMax(getResources().getInteger(R.integer.max_poids));poids.setProgress(getResources().getInteger(R.integer.normal_poids));

        Calendar c = Calendar.getInstance();
        dateVacc2.updateDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        Calendar min=Calendar.getInstance();
       try {
           Date d=new SimpleDateFormat("dd/MM/yyyy").parse(getResources().getString(R.string.min_datevaccin1));
           dateVacc2.setMinDate(d.getTime());
       }catch (Exception e){}
        dateVacc2.setMaxDate(System.currentTimeMillis() );
    }
    public void add(View v) {
        Person p = getPerson(true);
        if (p == null) Toast.makeText(MainActivity.this, "AJOUT ECHOUE", Toast.LENGTH_SHORT).show();
        else {
            list.add(p);
            Toast.makeText(MainActivity.this, "AJOUT REUSSI", Toast.LENGTH_SHORT).show();
        }
    }
    public void update(View v){
        if(list.isEmpty()){
            Toast.makeText(MainActivity.this,"LISTE VIDE",Toast.LENGTH_SHORT).show();
            return;
        }
        Person p= getPerson(false);
        if(p==null)Toast.makeText(MainActivity.this,"MODIFICATION ECHOUEE",Toast.LENGTH_SHORT).show();
        else{
            list.set(pos , p);
            Toast.makeText(MainActivity.this,"MODIFICATION REUSSIE",Toast.LENGTH_SHORT).show();
        }
    }
    public void remove(View v){
        if(list.isEmpty()){
            Toast.makeText(MainActivity.this,"LISTE VIDE",Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(MainActivity.this).
                setMessage("Voulez vraiment supprimer la personne"+list.get(pos)+" ?").
                setPositiveButton("ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        list.remove(pos);
                        Toast.makeText(MainActivity.this,"SUPPRESSION REUSSIE",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
    public void listAll(View v){
        if(list.isEmpty()){
            Toast.makeText(MainActivity.this,"LISTE VIDE",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i=new Intent(MainActivity.this,MainActivity2.class); //créer un intent
        i.putExtra("list",list); //mettre l'extra list
        startActivity(i); //démarrer l'activité 2
    }
    public void search(View v){
        Person p;
        try{p=new Person(Integer.parseInt(id.getText().toString()));}
        catch(Exception e){ id.setError("id vide!!");return;}

        if(!list.contains(p))Toast.makeText(MainActivity.this,"0 result",Toast.LENGTH_SHORT).show();
        else{
            p=list.get(list.indexOf(p));
            new AlertDialog.Builder(MainActivity.this).setMessage(p+"").show();
        }
    }
    public void naviguer(View v){
        if(list.isEmpty())return;
        switch (v.getId()){
            case R.id.first:pos=0;break;
            case R.id.last: pos=list.size()-1;break;
            case R.id.prev: if(pos!=0)pos--;break;
            case R.id.next: if(pos!=list.size()-1)pos++;break;
        }
        Person item = (Person) list.get(pos);
        setPerson(item);
    }
    private void setPerson(Person item) {
        id.setText(item.getId() + "");
        nom.setText(item.getNom());
        adresse.setText(item.getAdresse()+"");
        taille.setText(new DecimalFormat("0.00").format(item.getTaille())+"");
        dateVacc1.setText(new SimpleDateFormat("dd/MM/yyyy").format(item.getDateVacc1()));
        vaccine1.setChecked(item.isVaccine1());
        vaccine2.setChecked(item.isVaccine2());
        age.setValue(item.getAge());
        poids.setProgress(item.getPoids());
        genre.check(item.getGenre() ==Person.Genre.Mr? R.id.Mr: (item.getGenre() == Person.Genre.Mme? R.id.Mme: R.id.Mle));
        List<String>villes=Arrays.asList(getResources().getStringArray(R.array.ville_array));
        ville.setSelection(villes.indexOf(item.getVille()));
        Calendar c = new GregorianCalendar(); c.setTime(item.getDateVacc2());
        dateVacc2.updateDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
    }
    private Person getPerson(boolean isnew) {
        /*create Person objet*/
        Person p=null;
        if(isnew)p=new Person();
        else
            try{p=new Person(Integer.parseInt(id.getText().toString()));}
            catch(Exception e){ id.setError("id vide!!");return null;}

        /*set attribute values*/
        //from EditText
        try{p.setNom(nom.getText().toString());}
        catch(Exception e){ nom.setError(e+"");return null;}
        try{p.setAdresse(adresse.getText().toString());}
        catch(Exception e){ adresse.setError(e+"");return null;}
        try{p.setTaille(Double.parseDouble(taille.getText().toString()));}
        catch(Exception e){ taille.setError(e+"");return null;}
        //from NumberPicker
        try{p.setAge(age.getValue());}
        catch(Exception e){ Toast.makeText(MainActivity.this,e+"",Toast.LENGTH_SHORT).show();return null;}
        //from SeekBar
        try{p.setPoids(poids.getProgress());}
        catch(Exception e){ Toast.makeText(MainActivity.this,e+"",Toast.LENGTH_SHORT).show();return null;}
        //from Spinner
        p.setVille((String) ville.getSelectedItem());
        //from CheckBox or RadioButton
        p.setVaccine1(vaccine1.isChecked());
        if(vaccine1.isChecked()){
            try{p.setDateVacc1(new SimpleDateFormat("dd/MM/yyyy").parse(dateVacc1.getText().toString()));}
            catch(ParseException e){dateVacc1.setError("date invalide");return null;}
            catch(PersonException e){dateVacc1.setError(e+"");return null;}
            //from Switch
            p.setVaccine2(vaccine2.isChecked());
            if(vaccine2.isChecked()){
                //from DatePicker
                Calendar c=Calendar.getInstance();
                c.set(dateVacc2.getYear(),dateVacc2.getMonth(),dateVacc2.getDayOfMonth());
                try{p.setDateVacc2(c.getTime());}
                catch(PersonException e){Toast.makeText(MainActivity.this,e+"",Toast.LENGTH_LONG).show();return null;}
            }
        }

        //from radioGroup
        int r=genre.getCheckedRadioButtonId();
            //methode 1 with RadioButtons id
        p.setGenre(r==R.id.Mr?Person.Genre.Mr:(r==R.id.Mme?Person.Genre.Mme:Person.Genre.Mle));
            //methode 2 with RadioButtons text
        RadioButton rb=(RadioButton)findViewById(r);
        p.setGenre(Person.Genre.valueOf(rb.getText().toString()));

        /*return Person object*/
        return p;
    }
    void setContentView2(Context c){
        ScrollView scrollView = new ScrollView(c);
        scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        LinearLayout linearLayout1 = new LinearLayout(c);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        linearLayout1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        TextView textView1 = new TextView(c);
        textView1.setText("Gestion du Personnel");
        textView1.setGravity(Gravity.CENTER);
        textView1.setBackgroundColor(getResources().getColor(R.color.lime));
        textView1.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
        float width = getResources().getDimension(R.dimen.font_size);
        textView1.setTextSize(width);
        textView1.setTypeface(textView1.getTypeface(),Typeface.BOLD_ITALIC);
        //textView1.setTextAppearance(c, R.style.my_textview_style);
        textView1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearLayout1.addView(textView1);

        ImageView imageView = new ImageView(c);
        imageView.setImageResource(R.drawable.logo);
        int width2 =  (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80, getResources().getDisplayMetrics());
        LayoutParams layout1 = new LayoutParams(width2,width2);
        layout1.width=width2;
        layout1.height=width2;
        layout1.gravity= Gravity.CENTER;
        imageView.setLayoutParams(layout1);
        imageView.setVisibility(View.VISIBLE);// View.INVISIBLE  View.GONE
        linearLayout1.addView(imageView);

        EditText id = new EditText(c);
        id.setId(R.id.id);//(View.generateViewId());
        id.setHint("id");
        id.setInputType(InputType.TYPE_CLASS_NUMBER);
        id.setEnabled(true);
        id.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearLayout1.addView(id);

        EditText nom = new EditText(c);
        nom.setId(R.id.nom);
        nom.setHint("Nom");
        nom.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        nom.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearLayout1.addView(nom);

        EditText taille = new EditText(c);
        taille.setId(R.id.taille);
        taille.setHint("Taille");
        taille.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        taille.setLayoutParams(new LayoutParams(-1,-2));
        linearLayout1.addView(taille);
        NumberPicker age = new NumberPicker(c);
        age.setId(R.id.age);
        age.setMinValue(getResources().getInteger(R.integer.min_age));//not working in xml
        age.setMaxValue(getResources().getInteger(R.integer.max_age));//not working in xml
        age.setValue(getResources().getInteger(R.integer.normal_age));
        age.setLayoutParams(new LayoutParams(-1,-2));
        linearLayout1.addView(age);

        SeekBar poids = new SeekBar(c);
        poids.setId(R.id.poids);
        //poids.setMin(40);
        poids.setMax(getResources().getInteger(R.integer.max_poids));
        poids.setProgress(getResources().getInteger(R.integer.normal_poids));
        poids.setLayoutParams(new LayoutParams(-1,-2));
        linearLayout1.addView(poids);

        Spinner ville = new Spinner(c);
        ville.setId(R.id.ville);
        String[]t=getResources().getStringArray(R.array.ville_array);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(c,android.R.layout.simple_spinner_item, t);
        //adapter1=ArrayAdapter.createFromResource(c,R.array.ville_array,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ville.setAdapter(adapter1);
        ville.setPrompt(getResources().getString(R.string.ville_prompt));
        ville.setLayoutParams(new LayoutParams(-1,-2));
        linearLayout1.addView(ville);

        CheckBox vaccine1 = new CheckBox(c);
        vaccine1.setId(R.id.vaccine1);
        vaccine1.setText("Vacciné");
        vaccine1.setChecked(false);
        vaccine1.setLayoutParams(new LayoutParams(-1,-2));linearLayout1.addView(vaccine1);

        EditText dateVacc1 = new EditText(c);
        dateVacc1.setId(R.id.dateVacc1);
        dateVacc1.setHint("Date vaccin 1");
        dateVacc1.setInputType(InputType.TYPE_CLASS_DATETIME|InputType.TYPE_DATETIME_VARIATION_DATE);dateVacc1.setEnabled(false);
        dateVacc1.setLayoutParams(new LayoutParams(-1,-2));linearLayout1.addView(dateVacc1);

        Switch vaccine2 = new Switch(c);
        vaccine2.setId(R.id.vaccine2);
        vaccine2.setChecked(false);
        vaccine2.setShowText(true);
        vaccine2.setTextOn("Yes");
        vaccine2.setTextOff("No");
        vaccine2.setEnabled(false);
        //vaccine2.setGravity(Gravity.CENTER);
        vaccine2.setLayoutParams(new LayoutParams(-1,-2));
        linearLayout1.addView(vaccine2);

        //DatePicker dateVacc2 = new DatePicker(c);
        //Constructor(Context context, @Nullable AttributeSet attrs,int defStyleAttr,int defStyleRes) // The defStyleRes could be used
        DatePicker dateVacc2 = new DatePicker(c, null,R.style.DatePickerSpinner);
        dateVacc2.setId(R.id.dateVacc2);
        dateVacc2.setEnabled(getResources().getBoolean(R.bool.disable));
        //dateVacc2.setMinDate();
        long now = System.currentTimeMillis() - 1000;
        dateVacc2.setMaxDate(now);
        //dateVacc2.setSpinnersShown(true);
        dateVacc2.setCalendarViewShown(false);
        dateVacc2.setLayoutParams(new LayoutParams(-1,-2));
        linearLayout1.addView(dateVacc2);

        RadioGroup genre = new RadioGroup(c);
        genre.setId(R.id.genre);
        genre.setOrientation(RadioGroup.HORIZONTAL);
        genre.setLayoutParams(new LayoutParams(-1,-2));

        RadioButton Mr = new RadioButton(c);
        Mr.setId(R.id.Mr);
        Mr.setText("Mr");Mr.setChecked(true);
        Mr.setLayoutParams(new LayoutParams(-2,-2));
        genre.addView(Mr); RadioButton Mme = new RadioButton(c);
        Mme.setId(R.id.Mme);
        Mme.setText("Mme");
        Mr.setLayoutParams(new LayoutParams(-2,-2));
        genre.addView(Mme);

        RadioButton Mle = new RadioButton(c);
        Mle.setId(R.id.Mle);
        Mle.setText("Mle");
        Mr.setLayoutParams(new LayoutParams(-2,-2));
        genre.addView(Mle);

        linearLayout1.addView(genre);

        EditText adresse = new EditText(c);
        adresse.setId(R.id.adresse);
        adresse.setHint("adresse");
        adresse.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        adresse.setLines(3);
        adresse.setGravity(Gravity.TOP|Gravity.LEFT);
        adresse.setMinLines(2);
        adresse.setMaxLines(4);
        adresse.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearLayout1.addView(adresse);

        GridLayout gridLayout1 = new GridLayout(c);
        gridLayout1.setOrientation(GridLayout.HORIZONTAL);
        gridLayout1.setColumnCount(3);
        /*GridLayout.LayoutParams layout2=new GridLayout.LayoutParams();
        layout2.width=GridLayout.LayoutParams.MATCH_PARENT;
        layout2.height=GridLayout.LayoutParams.WRAP_CONTENT;
        layout2.setGravity(Gravity.CENTER);*/
        gridLayout1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        Button add = new Button(c);
        add.setText("ajouter");
        add.setId(R.id.add);
        add.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        gridLayout1.addView(add);

        Button update = new Button(c);
        update.setText("modifier");
        update.setId(R.id.update);
        update.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        gridLayout1.addView(update);

        Button remove = new Button(c);
        remove.setText("supprimer");
        remove.setId(R.id.remove);
        remove.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        gridLayout1.addView(remove);

        ImageButton clear = new ImageButton(c);
        clear.setImageResource(R.drawable.clear);
        clear.setId(R.id.clear);
        clear.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        gridLayout1.addView(clear);

        Button listAll = new Button(c);
        listAll.setText("lister");
        listAll.setId(R.id.listAll);
        listAll.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        gridLayout1.addView(listAll);
        search = new Button(c);
        search.setText("rechercher");
        search.setId(R.id.search);
        search.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        gridLayout1.addView(search);

        linearLayout1.addView(gridLayout1);

        LinearLayout linearLayout2 = new LinearLayout(c);
        linearLayout2.setGravity(Gravity.CENTER);
        linearLayout2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        Button first = new Button(c);
        first.setId(R.id.first);first.setText("first");
        first.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        linearLayout2.addView(first);

        Button prev = new Button(c);
        prev.setId(R.id.prev);prev.setText("prev");
        prev.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        linearLayout2.addView(prev);

        Button next = new Button(c);
        next.setId(R.id.next);next.setText("next");
        next.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        linearLayout2.addView(next);

        Button last = new Button(c);
        last.setId(R.id.last);last.setText("last");
        last.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        linearLayout2.addView(last);

        linearLayout1.addView(linearLayout2);
        View v = new View(c);
        v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,5));
        v.setBackgroundColor(Color.parseColor("#B3B3B3")); linearLayout1.addView(v);

        /*ListView listView = new ListView(c);
        listView.setId(R.id.listView);
        listView.setLayoutParams(new LayoutParams(-1,-2));
        linearLayout1.addView(listView);*/

        scrollView.addView(linearLayout1);
        setContentView(scrollView);
    }
}
