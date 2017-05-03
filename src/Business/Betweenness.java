/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author CANDICEHO
 */
public class Betweenness implements Serializable{
    HashMap<String, Double> between= new HashMap<String, Double>();
    
    public void addP(String s1){
        if (between.containsKey(s1)){
            return;
        }
        else{
            between.put(s1,0.0);
        }
    }
    
    public boolean mapExist(String s1){
      if (between.containsKey(s1)){
          return true;
      }
      else{
          return false;
      }
    }
 
    public double getBetween(String s1){
        if (between.containsKey(s1)){
            return between.get(s1);
        }
        else{
            return 0;
        }
    }
    
    public void putBetween(String s1, double d){
        if (between.containsKey(s1)){
            between.put(s1, d);
        }
    }
    
    public int bNum(){
        return between.size();
    }
}
