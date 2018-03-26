package com.example.liyuan.justgo;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class FileSaver {
    private static final String TAG = "edu.subram43purdue.travelplanner.FileSaver";
    private static String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static String VACATION_PATH = BASE_PATH+"/vacations.dat";


    private static Object readObject(String path) {
        Object obj = null;
        try{
            FileInputStream fileInputStream = new FileInputStream (new File (path));
            ObjectInputStream objectInputStream = new ObjectInputStream (fileInputStream);

            obj = objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static void writeObject(Object obj, String path){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream (new File (path));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream (fileOutputStream);

            objectOutputStream.writeObject(obj);


            fileOutputStream.close();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeVacationList(ArrayList<Vacation> vacationList){
        writeObject(vacationList,VACATION_PATH);
    }



    public static ArrayList<Vacation> readVacationList() {
        ArrayList<Vacation> vacation = (ArrayList<Vacation>)readObject(VACATION_PATH);
        if(vacation==null)
            vacation = new ArrayList<Vacation> ();
        return vacation;
    }
}
