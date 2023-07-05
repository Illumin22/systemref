package com.btp.wmsscanner.MOD;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EncryptionModule {

    private static String concatData;
    public static String encryptData(String data) {

        List<String> newStringCollection = new ArrayList<>();
        List<String> newStringCollectionEncrypt = new ArrayList<>();
        HashMap<String, String> hashmap = new HashMap();
        hashmap.put("A", "V");
        hashmap.put("B", "W");
        hashmap.put("C", "M");
        hashmap.put("D", "T");
        hashmap.put("E", "D");
        hashmap.put("F", "J");
        hashmap.put("G", "I");
        hashmap.put("H", "A");
        hashmap.put("I", "E");
        hashmap.put("J", "L");
        hashmap.put("K", "F");
        hashmap.put("L", "Q");
        hashmap.put("M", "P");
        hashmap.put("N", "O");
        hashmap.put("O", "Y");
        hashmap.put("P", "C");
        hashmap.put("Q", "S");
        hashmap.put("R", "X");
        hashmap.put("S", "Z");
        hashmap.put("T", "H");
        hashmap.put("U", "R");
        hashmap.put("V", "G");
        hashmap.put("W", "N");
        hashmap.put("X", "K");
        hashmap.put("Y", "B");
        hashmap.put("Z", "U");

        hashmap.put("0", "5");
        hashmap.put("1", "4");
        hashmap.put("2", "8");
        hashmap.put("3", "7");
        hashmap.put("4", "3");
        hashmap.put("5", "9");
        hashmap.put("6", "1");
        hashmap.put("7", "6");
        hashmap.put("8", "0");
        hashmap.put("9", "2");

        hashmap.put("|", "#");
        hashmap.put(":", "%");
        hashmap.put(".", "^");
        hashmap.put("/", "~");
        hashmap.put(" ", "$");
        hashmap.put("-", "@");
        hashmap.put("Ñ", "*");

        String str = data;

        // Creating array of string length
        char[] ch = new char[str.length()];

        // Copy character by character into array
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }

        // adding string character to a raw data array
        for (char c : ch) {
            newStringCollection.add(""+c);
        }

        //adding string character to an encrypted data array
        for(String a : newStringCollection){

            if (hashmap.containsKey(a)) {
                String x = hashmap.get(a);
                newStringCollectionEncrypt.add(""+x);
            }
        }

        //Concatenate the encrypted data
        concatData = TextUtils.join("", newStringCollectionEncrypt);
        return concatData;
    }
}