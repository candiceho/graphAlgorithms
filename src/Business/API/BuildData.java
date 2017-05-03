/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.API;

import Business.Graph;
import Business.buildSmall;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author CANDICEHO
 */
public class BuildData {
    public void toFile(final String fileDir,buildSmall bs,Graph gra) {
        FileOutputStream FO = null;
        try {
            FO = new FileOutputStream(fileDir);
            Iterator itor = bs.getSet().iterator();  
            while (itor.hasNext()){ 
                String key = (String)itor.next();       //name
                String value = bs.getE(key);            //email
                if (gra.getHash(value) != null){
                    HashMap<String,Double> hm = gra.getHash(value);
                    Iterator itor1 = hm.keySet().iterator();  
                    while (itor1.hasNext()){ 
                        String value1 = (String)itor1.next();    //email
                        if (bs.emaExist(value1)){   
                            String  key1 = bs.getName(value1);   //name
                            double d = hm.get(value1);
                            int b = (int) d * 100;
                            String out = value + " " + value1 + " " + b + "\r\n";
                            FO.write(out.getBytes());
                        }
                        //.getBytes()
                    } // while  
                } // if
            } // while
        } catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            try {
                FO.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
                                            //       BuildData(Graph graph) {
                                            //	
                                            //		randomRelatives();
                                            //	}
		
}
