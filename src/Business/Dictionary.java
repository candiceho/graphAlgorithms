/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

//import static Business.Dictionary.dta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;



/**
 *
 * @author CANDICEHO
 */
public class Dictionary implements Serializable{
    public static HashMap<String,HashSet> dta = new HashMap<String,HashSet>();

    public void addEm(String s1,String s2){
        if (!dta.containsKey(s1)){
            HashSet email = new HashSet();   
            email.add(s2);
            dta.put(s1,email);
        } else if (s2 != null){ 
            dta.get(s1).add(s2);
        } else {
            return;
        }
    }

    public String getMEmail(String s1){
        String out = "";
        if(dta.containsKey(s1)){
            HashSet hs = dta.get(s1);
            Iterator itor = hs.iterator();  
            while(itor.hasNext()){  
                String key = (String)itor.next();  
                out += key + "\n";
            }
        }
        return out;
    }

    public String getMName(String s1){
        Iterator itor = dta.keySet().iterator();  
        while(itor.hasNext()){
            String key = (String)itor.next();  
            HashSet hs = dta.get(key);
            if (hs.contains(s1)){
                return key;
            }
        }
        return null;
    }    

    public int dKeyNum(){
        return dta.size();
    }
    
    //public HashSet getEma(String s1){
    //    
    //if (dta.containsKey(s1))
    //             
    // {
    //        HashSet em= dta.get(s1);
    //        return em;
    //         
    //        }
    //     
    //else          
    //    return null; 
    //
    //}
    //
    //public Set<String> getAllName(){
    //   Set<String> names = dta.keySet();
    //          return names;
    //}

    public void Combine(buildSmall b,Graph g){
        Iterator itor = b.getSet().iterator();  
        while(itor.hasNext()){  
            String key = (String)itor.next();
            if (dta.containsKey(key)){              
                HashSet <String> email = dta.get(key);
                if (email.size()<2||email.isEmpty()){
                    continue;
                } else {
                    Iterator itor1 = email.iterator();  
                    while(itor1.hasNext()){
                        String key1 = (String)itor1.next();  
                        if (g.isAt(key1)){
                            b.putE(key, key1);
                            break;
                        } // inner if      
                    } // inner while
                } // outer else
            } // outer if
        } // outer while 
   }

    public static void mulEmail(Graph gra){
        Iterator itor1 = dta.keySet().iterator();  
            while(itor1.hasNext()){  
            //   n+=1;
            // System.out.println(n);
                String key1 = (String)itor1.next();  
                HashSet <String> email = dta.get(key1);
             if (email.size()<2||email.isEmpty()){
                continue;
             } else {
                Iterator itor3 = email.iterator();  
                while(itor3.hasNext()){
                    String key3 = (String)itor3.next(); 
                    if (gra.getHash(key3) != null){       
                        HashMap <String,Double> hmm = gra.getHash(key3);
                        while(itor3.hasNext()){
                            String key4 = (String)itor3.next(); 
                            if(gra.getHash(key4) != null){
                                HashMap <String,Double> hm = gra.getHash(key4);
                                Iterator itor2 = hm.keySet().iterator();  
                                while(itor2.hasNext()){  
                                    String key2 = (String)itor2.next();
                                    double value1 = (double) hm.get(key2);
                                    if (!hmm.isEmpty() && hmm.containsKey(key2)){
                                        double value2 = (double) hmm.get(key2);
                                        hmm.put(key2,value1+value2);
                                    } else {
                                        hmm.put(key2,value1);
                                    }
                                } // while          
                                gra.removeEmail(key4); 
                                Iterator itor5 = gra.getKeySet().iterator();  
                                while(itor5.hasNext()){  
                                    String key5 = (String)itor5.next();
                                    if (gra.getHash(key5) != null){
                                        HashMap <String,Double> hmmm = gra.getHash(key5);
                                        if(hmmm.containsKey(key4)){
                                            if (hmmm.containsKey(key3)){
                                                double value3 = (double) hmmm.get(key4);
                                                double value4 = (double)hmmm.get(key3); 
                                                hmmm.put(key3,value3+value4); 
                                            } else {
                                                double value3 = (double)hmmm.get(key4); 
                                                hmmm.put(key3,value3);     
                                            }
                                            hmmm.remove(key4);
                                        }
                                    }
                                } // while      
                            } // if
                        }
                    }
                }
            }   
        } // while      
    } // public method
}

// old code
//public static void mulEmail(Graph gra){
//  Iterator itor1 = dta.keySet().iterator();  
//        while(itor1.hasNext())  
//                {  
//       
//        String key1 = (String)itor1.next();  
//        ArrayList email=dta.get(key1);
//     if (email.size()<2)
//         continue;
//     else 
//     {
//      HashMap hmm=gra.getHash((String) email.get(0));
//      for (int i=1; i<email.size(); i++)
//     {
//         HashMap hm=gra.getHash((String) email.get(i));
//         if (hm!=null){
//          Iterator itor2 = hm.keySet().iterator();  
//        while(itor2.hasNext())  
//                {  
//        String key2 = (String)itor2.next();
//         Integer value1=(Integer) hm.get(key2);
//             if (hmm.containsKey(key2)){Integer value2=(Integer) hmm.get(key2); hmm.put(key2,value1+value2);}
//             else{hmm.put(key2,value1);}
//                } 
//         }   
//        gra.removeEmail((String) email.get(i));
//     } 
//     }   
//
//                }      
//}

//
//public void addEm(String s1,String s2){
//    
// if (!dta.containsKey(s1))
//             
// {
//           ArrayList email=new ArrayList();   
//    
//           email.add(s2);
//            
//          dta.put(s1,email);
//            
//        }
// else 
//     {     
//        ArrayList em= dta.get(s1);
//        dta.get(s1).add(s2);   
//     }
//
//}  