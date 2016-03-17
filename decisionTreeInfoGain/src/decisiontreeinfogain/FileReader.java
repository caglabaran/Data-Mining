/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreeinfogain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author test
 */
public class FileReader {

    private String path;
   
    public FileReader(String path) {
        this.path = path;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    //Dosyadan sutun isimlerini ve dataları okur ayri ayri arraylere kaydeder
    public void getRecords(int column, ArrayList<ArrayList<String>> dataTable,ArrayList<String> attiributeName) {
        try {
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));;

            String str;
            str = reader.readLine();
            String[] att = str.split(",");

            for (int i = 0; i < column; i++) {
                attiributeName.add(att[i]);
            }
            
            while ((str = reader.readLine()) != null) {
                String[] ar = str.split(",");
                ArrayList<String> row = new ArrayList<String>();
                for (int i = 0; i < column; i++) {
                    row.add(ar[i]);
                }

                dataTable.add(row);
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("File Read Error");
        }
    }

    

}
