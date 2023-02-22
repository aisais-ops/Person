package com.example.myapplication;
public class PersonException extends Exception{
    public PersonException () {}
    public PersonException (String msg) {super(msg);}
    public String toString() {return getMessage();}
}
