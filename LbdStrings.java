package org.optimia.scanaps.intfs;

import static java.time.temporal.ChronoUnit.DAYS;


import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface LbdStrings extends Strings {

    BiConsumer<TextView, String> setTextViewText = TextView::setText;
    BiConsumer<EditText, String> setEditTextText = TextView::setText;
    BiConsumer<TextInputEditText, String> setTextInputEditTextText = TextView::setText;
    Function<EditText, String> getEditTextText = (v) -> v.getText().toString();


    //Concatena cadenas
    BiFunction<String, String, String> concatena = String::concat;
    BiFunction<String, String, String> concatDosPuntos = (s, k) -> concatena.apply(concatena.apply(s, DOS_PUNTOS), k);
    BiFunction<String, String, String> concatSlash = (s, k) -> concatena.apply(concatena.apply(s, SEP_DIR), k);
    BiFunction<String, String, String> concatEspacio = (s, k) -> concatena.apply(concatena.apply(s, ESPACIO), k);

    //Añade un salto de linea al final del String.
    Function<String, String> addSaltoLinea = (t) -> concatena.apply(t, SALTO_LINEA);

    //Elimina contenido.
    Function<String, String> delSaltoLinea = (t) -> t.replaceAll(SALTO_LINEA, VACIO);
    Function<String, String> delComillaDoble = (t) -> t.replaceAll(COMILLA_DOBLE, VACIO);
    Function<String, String> delComillaSimple = (t) -> t.replaceAll(COMILLA_SIMPLE, VACIO);
    Function<String, String> unEspacio = (t) -> t.replaceAll(ESPACIO+ESPACIO, ESPACIO);

    //Devuelve true si se le pasa un String con salto de linea "\n".
    Function<String, Boolean> contieneSaltoLinea = (s) -> s.contains(SALTO_LINEA);

    //Contrasta una cadena con formato fecha con el reloj del equipo
    Function<String, Integer> dias = (t) -> Math.toIntExact(DAYS.between(LocalDate.now(), LocalDate.parse(t)));

    //Devuelve si el String enviado es un ean13.
    Function<String, Boolean> isEan13 = (s) -> Pattern.compile("^[0-9]{13}$").matcher(s).find();

    //Devuelve si el String enviado es un upc12.
    Function<String, Boolean> isUpc12 = (s) -> Pattern.compile("^[0-9]{12}$").matcher(s).find();

    //Devuelve si el string enviado son solamente numeros
    Function<String, Boolean> isNumeric = (s) -> Pattern.compile("^[0-9]$").matcher(s).find();

    //Convierte un vector de bytes en un string con formato hexadecimal
    Function<byte[], String> encodeHexString = (b) -> {
        StringBuilder hex = new StringBuilder();
        for (byte i : b) {
            hex.append(String.format("%02X", i));
        }
        return hex.toString();
    };

    //Retornar el md5 del string en formato hexadecimal
    Functional.FunctionSuchAlgorithm<String, String> md5 = (s) -> {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(s.getBytes());
        return encodeHexString.apply(md.digest());
    };

    //Valida si la cadena input se ajusta al patron
    BiFunction<String,String,Boolean>  patternMatches = (input, patron) ->  Pattern.compile(patron).matcher(input).matches();

    Function<String,Boolean> isEmail = (email) -> patternMatches.apply(email,Strings.EXPRESION_MAIL);

    Function<String,String> correctorSintactico = (s) -> {
        String str =  LbdStrings.unEspacio.apply(s.trim());
        CharSequence t = str;
        StringBuilder nt = new StringBuilder(t.length());
        nt.append(Character.toUpperCase(t.charAt(0)));
        boolean mayuscula = false;
        for(int i=1;i<t.length();i++){
            if(t.charAt(i)==46){// . = 46
                nt.append(t.charAt(i));
                nt.append(Strings.ESPACIO);
                if(i<t.length()-1)if(t.charAt(i+1)==32) i++;
                mayuscula = true;
            }else if(t.charAt(i)==44 || t.charAt(i)==58 || t.charAt(i)==59){ //,=44 :=58 ;=59
                nt.append(t.charAt(i));
                nt.append(Strings.ESPACIO);
                if(i<t.length()-1)if(t.charAt(i+1)==32) i++;
                mayuscula = false;
            }else{
                if(mayuscula){
                    nt.append(Character.toUpperCase(t.charAt(i)));
                    mayuscula=false;
                }else{
                    nt.append(t.charAt(i));
                }
            }
        }
        return nt.toString();
    };

}
