package com.example.myapplication;
import java.io.Serializable; import java.util.*; import java.text.*;
public class Person implements Serializable, Comparable<Person>{
    private static int nb=0;
    private final int id;
    private String nom,ville,adresse;
    private int age,poids;
    private double taille;
    private boolean vaccine1,vaccine2;
    private Date dateVacc1,dateVacc2;
    enum Genre{Mr,Mme,Mle}
    private Genre genre;

    public Person(){id=++nb;}
    public Person(int id){this.id=id;}
    public int getId(){return id;}

    public String getNom(){return nom;}
    public void setNom(String nom)throws PersonException{
        if(nom.isEmpty())throw new PersonException("nom vide!!");
        if(!nom.matches("^[a-zA-Z- ]{1,40}$"))throw new PersonException("nom invalide!!");
        this.nom=nom;
    }
    public int getAge(){return age;}
    public void setAge(int age)throws PersonException{
        if(age<16 || age>30)throw new PersonException("age must be between 16 and 30");
        this.age=age;
    }
    public int getPoids(){return poids;}
    public void setPoids(int poids)throws PersonException{
        if(poids<40 || poids>100)throw new PersonException("poids must be between 40 and 100");
        this.poids=poids;
    }
    public double getTaille(){return taille;}


    
    public void setTaille(double taille)throws PersonException{
        if(taille<1 || taille>2)throw new PersonException("taille must be between 1 and 2");
        this.taille=taille;
    }
    public String getVille(){return ville;}
    public void setVille(String ville){this.ville=ville;}
    public boolean isVaccine1(){return vaccine1;}
    public void setVaccine1(boolean vaccine1){this.vaccine1=vaccine1;}
    public boolean isVaccine2(){return vaccine2;}
    public void setVaccine2(boolean vaccine2){this.vaccine2=vaccine2;}
    public Date getDateVacc1() {return dateVacc1;}
    public void setDateVacc1(Date dateVacc1)throws PersonException{
        Calendar c=Calendar.getInstance();c.set(2020,6,1);Date d=c.getTime();
        if(dateVacc1.compareTo(d)<0 ||dateVacc1.compareTo(new Date())>0)throw new PersonException("date vaccin 1 must between 01/07/2020 and now");
        this.dateVacc1=dateVacc1;
    }
    public Date getDateVacc2() {return dateVacc2;}
    public void setDateVacc2(Date dateVacc2)throws PersonException{
        if(dateVacc2.compareTo(dateVacc1)<0 ||dateVacc2.compareTo(new Date())>0)throw new PersonException("date vaccin 2 must betwwen date vaccin1 and now");
        this.dateVacc2=dateVacc2;
    }
    public Genre getGenre(){return genre;}
    public void setGenre(Genre genre){this.genre=genre;}
    public String getAdresse(){return adresse;}
    public void setAdresse(String adresse)throws PersonException{
        if(adresse.length()<3 || adresse.length()>100)throw new PersonException("adresse must have between 3 and 100 characters");
        this.adresse=adresse;}
    public String toString(){
        return  "id :"+getId()+"- "+
                "genre :"+getGenre()+", "+
                "nom :"+getNom().toUpperCase()+", "+
                "age :"+getAge()+" ans, "+
                "poids :"+getPoids()+" kg, "+
                "taille :"+new DecimalFormat("0.00").format(getTaille())+" m, "+
                "adresse : "+getAdresse()+", "+
                "ville :"+getVille()+". "+
                "vaccin1 : "+(isVaccine1()? "le "+ new SimpleDateFormat("dd/MM/yyyy").format(getDateVacc1()) : "non")+". "+
                "vaccin2 : "+(isVaccine2()?"le "+ new SimpleDateFormat("dd/MM/yyyy").format(getDateVacc2()) : "non");
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person= (Person) o;
        return id == person.getId();
        //return id == person.getId()&&nom.equals(person.getNom());
    }
    public int compareTo(Person p) {
        int a= this.id-p.getId();
        int b=this.nom.compareTo(p.getNom());
        return a!=0?a:b;
    }}
