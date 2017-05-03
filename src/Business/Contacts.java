/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author CANDICEHO
 */
public class Contacts {
    HashMap <String, HashSet> contact= new HashMap<String, HashSet>();
    
    public void addCon(String s1,String s2){
        if (s1 == null || s2 == null){
            return;
        }
        if (!contact.containsKey(s1)){     
            HashSet email = new HashSet();   
            email.add(s2);  
            contact.put(s1,email);         
//          HashSet email2=new HashSet();
//          email2.add(s1); 
//          contact.put(s2,email2);
        } else { 
            if(contact.get(s1).contains(s2)){
//            contact.get(s1).add(s2);
//            HashSet email2=new HashSet();
//            email2.add(s1);        
//            contact.put(s2,email2);
                return;
            } else {
                contact.get(s1).add(s2);
//            HashSet email2=new HashSet();
//            email2.add(s1);        
//            contact.put(s2,email2);
            }
        }
//else {
//       if(contact.get(s2).contains(s1))          
//         {
//            //contact.get(s1).add(s2);
//            HashSet email2=new HashSet();
//            email2.add(s2);        
//            contact.put(s1,email2);
//
//         }
//         else 
//         {
//            contact.get(s2).add(s1);
//            
//       // System.out.println(s2+contact.get(s2).size());
//            HashSet email2=new HashSet();
//            email2.add(s2);        
//            contact.put(s1,email2);
//
//         }
// 
//
// }
    }
  
    public Set contactSet(){
        return contact.keySet();
    }
  
    public boolean isKey(String s1){
        if (contact.containsKey(s1)){
            return true;
        } else {
            return false;
        }
    }
 
    public int setNum(){
        return contact.size();
    }
    
    public HashSet getSet(String s1){
        if (contact.containsKey(s1)){
            return contact.get(s1);
        } else {
            return null;
        }
    }
}