/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import static Business.Dictionary.dta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 *
 * @author CANDICEHO
 */
public class FinalResult {
    public static LinkedHashMap<String,Double> result = new LinkedHashMap<String,Double>();
    public static HashMap<String, Double> total = new HashMap<String, Double>();

    public void addTotal(String s1, double s2){
    //if (s1==null) return;
        if (!total.containsKey(s1)){           
            total.put(s1,s2);
        } else {
            return;
        }
    } 

    public void addRe(String s1, Double s2){
        if(!result.containsKey(s1)){
            result.put(s1, s2);
            System.out.println(result.size() + "oooooooooooooo");
        } else {
            return;
        }
    }
    
//public void mutiEmail(){
//  Iterator itor1 = dta.keySet().iterator();  
//        while(itor1.hasNext())  
//                {  
//        String key1 = (String)itor1.next();  
//        HashSet <String> email=dta.get(key1);
//     if (email.size()<2||email.isEmpty())
//         continue;
//     else 
//     {   
//         Iterator itor3 = email.iterator();  
//         while(itor3.hasNext()){
//         String key3 = (String)itor3.next(); 
//         if (person.containsKey(key3)){ 
//             double time1=person.get(key3);
//              while(itor3.hasNext())  
//                {               
//         String key4 = (String)itor3.next(); 
//         if(person.containsKey(key4)){
//             double time2=person.get(key4);
//             person.put(key3, time1+time2);       
//             person.remove(key4); 
//         } 
//         }
//       }
//     }   
//       }      
//}
//}

    public String printResult(){
        String output = "";
        Iterator itor = result.keySet().iterator();  
        while(itor.hasNext()){
            String key = (String)itor.next(); 
            output += String.format("%-19s",key) + String.format("%15f",result.get(key)) + "\n";
          // output+=++String.format("%8s",ss)+String.format("%8s",rr)+String.format("%12s",cc)+String.format("%14s",ff)+Stri
          //  System.out.println(output+"ppppppppppppppppppppp");
        }
        return output;
    }

    public void sortPerson(){
        ArrayList keys = new ArrayList(total.keySet());
        Collections.sort(keys,new Comparator<Object>(){
            public int compare(Object o1,Object o2){
                if(Double.parseDouble(total.get(o1).toString()) < Double.parseDouble(total.get(o2).toString())){
                    return 1;
                } else if (Double.parseDouble(total.get(o1).toString()) == Double.parseDouble(total.get(o2).toString())){
                    return 0;
                } else {
                    return -1;
                }
            } // compare
        }                                                                                                                  );
        for (int i = 0; i < 150;i++){
            String n = (String) keys.get(i);
            addRe(n,total.get(n));
            System.out.println(result.size());
        }
    }

//public int number(){
//int n=0;
//     Iterator itor3 = person.keySet().iterator();  
//        while(itor3.hasNext())  
//        {   String key3 = (String)itor3.next();  
//        n++;
//}
//        return n;
//}
}