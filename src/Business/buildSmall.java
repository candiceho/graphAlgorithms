/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author CANDICEHO
 */
public class buildSmall {
    public HashMap <String, String> small= new HashMap<String, String> ();

    public void addPerson(String s1, String s2){
        if(!small.containsKey(s1) && s1 != null && s2 != null){
            small.put(s1,s2);
        } else 
            return;
    }
    
    public Set getSet(){   
        return small.keySet();
    }

    public int personNum(){ 
        int n = small.size();
        return n;
    }
    
    public void putE(String s1, String s2){
        if (small.containsKey(s1))
            small.put(s1, s2);
    }

    public String getE(String s1){
        if (small.containsKey(s1))
            return small.get(s1);
        else 
            return null;
   }
   
    public boolean mapExist(String s1){
        if (small.containsKey(s1))
            return true;
        else 
            return false;
    }
   
    public boolean emaExist(String s1){
        if (small.containsValue(s1))
            return true;
        else
            return false;
    }
    
    public String getName(String s1){
        String output = "";
        if (small.containsValue(s1)){
            Iterator itor1 = small.keySet().iterator();  
            while(itor1.hasNext()){
                String key1 = (String)itor1.next();
                if (small.get(key1).equalsIgnoreCase(s1))
                    output = key1;
            }
        }
        return output;
    }
        // public double getBetween(String s1){
        //    if (small.containsKey(s1))
        //        return hm.get(s1);
        //    else return 0.0;
        //}

        //public void putBetween(String s1,double i){
        //if (small.containsKey(s1))
        //     small.put(s1, i);
        //}
}
