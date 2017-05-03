/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.io.Serializable;
import static java.lang.Integer.min;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;



/**
 *
 * @author CANDICEHO
 */
public class Graph implements Serializable {
    public HashMap<String,HashMap> graph = new HashMap<String,HashMap>();
    double deMax = 0,cloMax = 0,sentMax = 0,recMax = 0,beMax = 0,totalMax = 0;
    String deName,cloName,sentName,recName, betName,totalName;
    HashMap <String,Double> path = new HashMap<String, Double>();
    HashMap <String ,Double> sPath = new HashMap<String, Double>();
    int pathNum = Integer.MAX_VALUE;
    double pathTime = 0.0; 
    int keynum;
 //   boolean exist=false;
 //  String shortpath;

    HashMap<Integer, String> ANS = new HashMap <Integer, String>();
    HashMap<String, Integer> cut = new HashMap<String, Integer>();
    int N;
    int root; int depth;
    int map[][];
    int f[], low[],  dep[];
    int vis[];
    int children=0;
 // int sentpeople;
    
// public void buildSmall (buildSmall bs){
//        int contact=0;
//        double sent=0.00;
//        double receive=0.00;
//        
//  Iterator itor = graph.keySet().iterator();  
//   while(itor.hasNext())  
// {  
//  String key = (String)itor.next();  
//  
//      if (graph.containsKey(key))   
//      {
//          HashMap hm=graph.get(key);
//           Iterator itor1 = hm.keySet().iterator();  
//        while(itor1.hasNext())  
//               {  
//        String key1 = (String)itor1.next();  
//        sent+=(double)hm.get(key1);
//                }
//          contact=hm.size();
//          Set set= hm.keySet();         
//        Iterator itor2 = graph.keySet().iterator();  
//        while(itor2.hasNext())  
//                {  
//        String key2 = (String)itor2.next();  
//        if (graph.get(key2).containsKey(key)&&!set.contains(key2))
//            receive+= (double)graph.get(key2).get(key);
//          contact+=1;     
//                 } 
//        
////        
////        if (contact>10&&sent>5&&receive>5) 
////            bs.addPerson(key);
//      }
// 
// }
//
//    } 
        
   public void addVer (String nev){
        if (graph.containsKey(nev)|| nev == null){
            return;
        } else { 
            HashMap<String,Double> edge = new HashMap<String, Double>();
            graph.put(nev,edge);
        }
    } 
    
    public void addEdge (String sta, String end){
//      double i=0.00;
        if (end == null || sta.equalsIgnoreCase(end)){
            return;
        }
        if ( graph.containsKey(sta)){                   //&&graph.containsKey(end))
            HashMap<String,Double> hmm = graph.get(sta);
                if (hmm.containsKey(end)){  
                    double i = (double)hmm.get(end);
                    hmm.put(end, i + 0.01);
                } else {
                    hmm.put(end,0.01); 
                }
        } else {         
            addVer(sta);
            //addVer(end);
            addEdge(sta,end);
        }    
    }
 
  public String Analysis(buildSmall bs,Contacts con,Betweenness bet, FinalResult re){
        //HashMap h = tarjan(bs,con); 
    //  if (h.isEmpty()) System.out.println("NO");
        DecimalFormat df = new DecimalFormat("######0.0000");  
        DecimalFormat fd = new DecimalFormat("######0.00");  
        String output ="\tName           Degree   S      R      Close      Far    Between  CutPoi Transi\n";
    //  AllShortest(bs,bet,con);  
        Iterator itor = bs.getSet().iterator();  
        while(itor.hasNext()){
            String key = (String)itor.next();
            System.out.println(key);
            String value = bs.getE(key);
            int cu = 0;
            double cu1 = 0.0;
            int d = ContactNum(value,con);   
            double d1 = d; 
            d1 = d1 / 100;

            double s = toNum(value);  
            String ss = fd.format(s);
            double s1 = s / 100;

            double r = fromNum(value,con);     
            String rr = fd.format(r);
            double r1 = r / 10;

            double c = shortestPath(value,con);                                          //shortestPath(value,con);     //-32768----32767
            String cc = df.format(c); 
            //double c1=c;

            double f = 1 / c * 10;
            String ff = df.format(f);
            double f1 = f;

            double b = bet.getBetween(value);
            String bb = fd.format(b);
            double b1 = b / 100;

            if (cut.containsKey(value)){   
                cu = cut.get(value);
                cu1 = cu; 
                cu1 = cu1 / 100;
            }
            int tr = transitivity(value,con);
            tr = (tr - 10000) / 1000;
            output += String.format("%-19s",key) + String.format("%7d",d) + String.format("%8s",ss)
                   + String.format("%8s",rr) + String.format("%10s",cc) + String.format("%10s",ff)
                   + String.format("%8s",bb) + String.format("%6d",cu) + String.format("%6d",tr) + "\n";
       //     double d1=d;           
       //     double s1=s;           
       //     double r1=r;       
       //     double c1=c2*100000;
       //     double f1=f;
       //     double b1=b;
            double t = d1 * 0.18 + s1 * 0.10 + r1 * 0.06 + f1 * 0.18 + b1 * 0.18 + cu1 * 0.15 + tr * 0.15;
            re.addTotal(key, t);
            if (t > totalMax){
                totalMax = t;
                totalName = key;
            } else if (t == totalMax){
                totalName += key;
            }
        } // while
        return output;
    } 

    public int transitivity(String s1,Contacts c){
        HashSet visit = new HashSet(); //   int i=1;
        Stack s = new Stack();
        s.push(s1);
        visit.add(s1);
        //printNode(rootNode);
        while(!s.isEmpty()){
            String now = (String) s.peek();
            if(c.isKey(now)){
                HashSet hs =  c.getSet(now);
                Iterator itor1 = hs.iterator();  
                while(itor1.hasNext()){     
                    String key = (String)itor1.next();  
                //Node child=getUnvisitedChildNode(n);
                    if(!visit.contains(key)){
                        visit.add(key);
                        //printNode(child);
                        s.push(key);
                    }
                } // while
            } // if
            s.pop();
        } // while
        return visit.size();
    }
  
    public double shortestPath(String start,Contacts c){               //un directed    un weighted   closeness 
        double shortpath = 0.0; 
        int n = 0;
        HashMap <String,Integer> weight = new HashMap<String, Integer>();
        Iterator itor1 = c.contactSet().iterator();  
        while(itor1.hasNext()){     
            String key1 = (String)itor1.next();  
            weight.put(key1,Integer.MAX_VALUE);
        }
        LinkedList<String> q = new LinkedList<String>();  
        q.addLast(start);  
        if (c.isKey(start)){
            weight.put(start,0);
        } else {
            System.out.println("This Person is NOT In The Graph!!!");
            return 0;
        }
        while(!q.isEmpty()){  
            String v = q.removeFirst();  
            if (!c.getSet(v).isEmpty()){   
                HashSet hm =  c.getSet(v);
                // if (itor2.hasNext()) 
                Iterator itor2 = hm.iterator();  
                while(itor2.hasNext()){  
                    String key2 = (String)itor2.next();  
                    if (!weight.containsKey(key2)){ 
                        continue;
                    } else {
                        if(weight.get(key2) == Integer.MAX_VALUE){  
                            weight.put(key2,weight.get(v) + 1);  
                            q.addLast(key2);  
                        }
                    } // else       
                } // while  
            } // if
        } // while 
   
        Iterator itor3 = weight.keySet().iterator();  
        while(itor3.hasNext()){
            String key3 = (String)itor3.next();  
            if(weight.get(key3) != Integer.MAX_VALUE){   
                shortpath += weight.get(key3);          
                n += 1;               
            }  
        }
        return shortpath / n;              ///n;
    }
 
    public void AllShortest(buildSmall b, Betweenness be,Contacts c){           //,Contacts c
        int nn = 0;
        Iterator itor1 = b.getSet().iterator();  
        while(itor1.hasNext()){ 
            String key1 = (String)itor1.next();  
            String value1 = b.getE(key1);
            Iterator itor2 = b.getSet().iterator();  
            while(itor2.hasNext()){ 
                String key2 = (String)itor2.next();  
                String value2 = b.getE(key2);
                if (!value1.equalsIgnoreCase(value2)){       //&&c.isKey(value1))    
                    Stack<String> stack = new Stack();
                    stack.push(value1);
                    allShortPath(value2,stack,c);          // allShortPath(null,value2,stack,c);
                    nn += 1;
                    System.out.println("finish" + nn);
                    putPath(be);
                } // if
            } // inner while
        } // while
    }
 

    public void allShortPath( String end, Stack<String> s, Contacts c) {        // depends on DFS      String undertop,   ,Contacts c
        if (s.isEmpty()){
            return;
        }
        String k=s.peek().toString();
        //String uk= undertop;
        if (k.equalsIgnoreCase(end)){
            System.out.println("start and end CANNOT be same!");
            return; 
        }
        if (c.isKey(k)&&c.isKey(end)){                //if (graph.containsKey(k)&&graph.containsKey(end))
            if(!c.getSet(k).isEmpty()){             // if(!getHash(k).isEmpty())  {   
                HashSet hm = c.getSet(k);                            //   HashMap<String, Double> hm = getHash(k);  
                Iterator itor = hm.iterator();  
                while(itor.hasNext()){  
                    String key = (String)itor.next();  
                    if (s.contains(key)){
                //      if(key!=uk) 
                        continue;
                    }
                    if (key.equalsIgnoreCase(end)){
                        checkStack(s); 
                        break;
                    }
                    s.push(key);
                    if (s.size() > pathNum || s.size() > 20) {
                        System.out.println("OOOOOOOOOOOOOOOO" + s.size() + " " + pathNum);
                        break;
                    } else{
                        allShortPath(end,s,c);            //allShortPath(k,end,s,c);
                    }
                } // while
            } // if
            s.pop();
        } else {
            return;
        }
    }

  
  
    public void checkStack(Stack<String> s){
        if (s.size() < pathNum){                  // if (size>pathNum)    
            pathNum = s.size();             // pathNum=s.size();
            path.clear();
            for (String i : s){
                path.put(i, 1.0);
            }
            pathTime = 1;
        } else if (s.size() == pathNum){                  //    else if (s.size()==pathNum)
            for (String i : s){
                if (path.containsKey(i)){ 
                    double n = path.get(i);   
                    path.put(i, n + 1);       
                } else {
                    path.put(i, 1.0);
                }
            }
            pathTime += 1;
        }
        else return;
    }
   
// public double pathSize(Stack<String> s){          // weighted directed betweenness
////     String a[]=new String [150];
//     int n=0;
//     double path=0.0;
////     for (String i:s)
////     { 
////     a[n]=i;
////     n++;
////     } 
// for (int i=0; i<s.size();i++)
// { 
//     String a= s.pop();
//     String b= s.pop();   
//     if (!getHash(b).isEmpty()){
//     HashMap<String, Double> hm= getHash(b);
//    
//     double z= hm.get(a);
//       path+=z;
//       s.push(b);
//     }
//     else return 0.0;
// } 
// return path;
// }
// 
 
    public void putPath(Betweenness b){
        Iterator itor = path.keySet().iterator();  
        while(itor.hasNext()){    
            String key = (String)itor.next();  
            if (b.mapExist(key)){ 
                double bb = path.get(key)/pathTime; 
                double dd = b.getBetween(key);
                b.putBetween(key, bb + dd);
            }
        }
        path.clear();
        pathNum = Integer.MAX_VALUE;         // pathNum=0;   
        pathTime = 0;
    }



// public double getDPath(String start, Contacts con){              // weighted  directed  closeness
//     double p=0.0;
//    // int n=0;
//  Dijkstra(start,con);
//  Iterator itor = sPath.keySet().iterator();  
//        while(itor.hasNext())  
//   {    
//    String key = (String)itor.next();   
//   // if (sPath.get(key)!=Double.MAX_VALUE)
//      p+=sPath.get(key);
//    //  n+=1;
//     
//                }
//       // double x=p/n;
//        sPath.clear();
//     return p;
// }
//   
//    
// public void Dijkstra(String start, Contacts con){
//    if (graph.containsKey(start)&& !getHash(start).isEmpty()) {
//        
//  //  HashMap <String,Double> sPath= new HashMap<String, Double>();
//    HashMap <String, Double> hm = getHash(start);
//       Iterator itor = con.contactSet().iterator();  
//        while(itor.hasNext())  
//   {    String key = (String)itor.next();  
//    if (hm.containsKey(key))  sPath.put(key,hm.get(key));
//    else  sPath.put(key,0.0);
//   }
//   
//       sPath.put(start,0.0);
//       
//
//    HashSet<String> open=(HashSet<String>) con.contactSet();
//    open.remove(start);
//    HashSet<String> close=new HashSet<String>();  
//    close.add(start);
//
//    String nearest=getChild(start,open);                    //get Nodes who is nearest
//        if(nearest==null){  
//            return;  
//        }  
//        close.add(nearest);  
//        open.remove(nearest);
// 
//        
//        HashMap <String, Double> next = getHash(nearest);
//        for (String key:next.keySet())
//        {  if(open.contains(key)){
//            double newpath;
//            newpath = sPath.get(nearest)+ next.get(key);
//            if(newpath>sPath.get(key)){
//            sPath.put(key, newpath);
//            }
//        }
//        }
//        Dijkstra(start,con);
//        Dijkstra(nearest,con);
//    }
//    else return; 
// }   
//     
//    private String getChild(String node, HashSet h){  
//        String res=null;  
//        double maxDis=0.0;  
//   HashMap<String,Double> contacts=getHash(node);  
//        for(String child:contacts.keySet()){  
//            if(h.contains(child)){  
//                double distance=contacts.get(child);  
//                if(distance>maxDis){  
//                    maxDis=distance;  
//                    res=child;  
//                }  
//            }  
//        }  
//        return res;  
//    }  
//   

  public void buildContact(Contacts c){            // Betweenness b
      Iterator itor1 = graph.keySet().iterator();  
        while (itor1.hasNext()){
            String key1 = (String)itor1.next(); 
        //  b.addP(key1);
            if (!graph.get(key1).isEmpty()){
                HashMap hm = graph.get(key1);
                Iterator itor2 = hm.keySet().iterator();  
                while(itor2.hasNext()){
                    String key2 = (String)itor2.next();  
                    // b.addP(key2);
                    c.addCon(key1, key2);
                    c.addCon(key2, key1);
                    // b.addP(key1);
                    //  b.addP(key2);
                }
            }       
//            Iterator itor3 = graph.keySet().iterator();  
//        while(itor3.hasNext())  
//                {  
//        String key3 = (String)itor3.next();  
//        if (graph.get(key3).containsKey(key1))
//            c.addCon(key1, key3);
//        c.addCon(key3,)
                // }       
        }  
    }  

    public String getAllKeys(){
        String output = "";
        Iterator itor = graph.keySet().iterator();  
        while(itor.hasNext()){  
            String key = (String)itor.next();  
            output += key + "\n";
            //sentpeople+=1;
            //String value = map.get(key);
        }  
        return output;
    }

//    public int getSentpeople() {
//        return sentpeople;
//    }
     
    public int keysNum(){
        int n = graph.size();
        return n; 
    }
        
    public double sentToNum(String v1, String v2){
        double i = 0;
        if (graph.containsKey(v1)){
            HashMap<String, Double> hmm = graph.get(v1);
            if (hmm.containsKey(v2)){
                i = (double) hmm.get(v2);
            }
        }
        return i;
    }

    public int toPeopleNum(String v1){
        int num = 0;
        if (graph.containsKey(v1)){
            HashMap<String,Double> hm = graph.get(v1);
            Iterator itor = hm.keySet().iterator();  
            while (itor.hasNext()){  
                //String key = (String)itor.next();  
                num += 1;
            }
        }
        return num;
    }
        
    public int fromPeopleNum(String v1, Contacts con){
        int num = 0;
        if (con.isKey(v1)){
            Iterator itor = graph.keySet().iterator();  
            while (itor.hasNext()){  
                String key = (String)itor.next();  
                HashMap<String, Double> hm = graph.get(key);  
                if (hm.containsKey(v1)){           
                    num += 1;
                }    
            } // while
        } // if 
        return num; 
   }

    public double fromNum(String s1, Contacts con){
        double re = 0.00;  
        if (con.isKey(s1)){
            //HashMap hm=graph.get(s1);
            Iterator itor = graph.keySet().iterator();  
            while(itor.hasNext()){  
                String key = (String)itor.next();  
                HashMap<String, Double> hm = graph.get(key);  
                if (hm.containsKey(s1)){            
                    re += (double)hm.get(s1);                 
                }       
            } // while
        } // if
        return re; 
    }
 
    public double toNum(String s1) {  
        double  s = 0.00;
        if (graph.containsKey(s1)){
            HashMap<String,Double> hm = graph.get(s1);
            Iterator itor = hm.keySet().iterator(); 
            while(itor.hasNext()){  
                String key = (String)itor.next();  
                s += (double)hm.get(key);
            } 
        }
        return s;     
    }
 
 
    public String allReceive(String s1,Contacts con){
        String output = "";      
        if (con.isKey(s1)){     //HashMap hm=graph.get(s1);
            Iterator itor = graph.keySet().iterator();  
            while(itor.hasNext()){  
                String key = (String)itor.next();  
                HashMap hm = graph.get(key);
                if (hm.containsKey(s1)){            
                    output += key + "\n";    
                }                 
            } 
        }
//      else output="This Person is NOT in This Graph!!!"; 
        return output;     
    }

 
    public String ALlSent(String v1){
        String output = "";
        if (graph.containsKey(v1)){  
            HashMap<String,Double> hm = graph.get(v1);
            Iterator itor = hm.keySet().iterator();  
            while (itor.hasNext()){  
                String key = (String)itor.next();  
                // int value = (int)hm.get(key); 
                output += key + "\n";     
            } // while
        } // if
//      else {     
//         output="This Person Did NOT Send Any Email!!!";            
//              }
        return output;
    }

    public String CommonF(String s1, String s2, Contacts c){
        String output = "";
        if (c.isKey(s1) && c.isKey(s2)){
            HashSet hm1 = c.getSet(s1);
            HashSet hm2 =c.getSet(s2);
            Iterator itor = hm1.iterator();  
            while(itor.hasNext()){  
                String key = (String)itor.next(); 
                if(hm2.contains(key)) output += key + "\n";
            }
        }
        else output = "No Common Friend!";
        return output;
    }

  
    public String Contacts(String s1, Contacts con){
        String output = "";
        if (con.isKey(s1)){
            HashSet hs = con.getSet(s1);
            Iterator itor1 = hs.iterator();  
            while(itor1.hasNext()){
               String key = (String)itor1.next();  
               output += key + "\n";
            }
                                //        Iterator itor2 = graph.keySet().iterator();  
                                //        while(itor2.hasNext())  
                                //                {  
                                //        String key = (String)itor2.next();  
                                //        if (graph.get(key).containsKey(s1)&&!set.contains(key))
                                //          output+=key+"\n";      
                                //                 } 
        } // if
                                //      else {       
                                //         output="This Person is NOT in This Graph!!!";       
                                //              }
  
        return output;
    } 

    /**
     *
     * @param s1
     * @return
     */
    public int ContactNum(String s1, Contacts con){
        int num = 0;
        if (graph.containsKey(s1)){
            HashMap hm = graph.get(s1);
            num = hm.size();
            Set set = hm.keySet();
                        //       Iterator itor1 = hm.keySet().iterator();  
                        //        while(itor1.hasNext())  
                        //        {
                        //             String key = (String)itor1.next();  
                        //              output+=key+"\n";
                        //        
                        //        }
            Iterator itor2 = graph.keySet().iterator();  
            while(itor2.hasNext()){  
                String key = (String)itor2.next();  
                if (graph.get(key).containsKey(s1)&&!set.contains(key)){
                    num += 1;
                }  
            } 
        } else {
            Iterator itor2 = graph.keySet().iterator();  
            while(itor2.hasNext()){  
                String key = (String)itor2.next();  
                if (graph.get(key).containsKey(s1))
                    num += 1;     
            }
        }
        return num;
    } 
 
    public HashMap<String,Integer> tarjan(buildSmall bs,Contacts c){
        int i = 1;
        N = c.setNum();//  N=bs.personNum();
        Iterator itor1 = c.contactSet().iterator();  
        while(itor1.hasNext()){   
            String key = (String)itor1.next(); 
            //   String email = bs.getE(key);
            ANS.put(i, key);   
            i++;
        }       
        root = 0;
        depth = 0;
        f = new int [N+1];
        dep = new int[N+1];
        low = new int[N+1];
        vis = new int [N+1];
        dfs(1,c,bs);
        //boolean ans= false;
        if(root > 1){   
            //ans=true;
            cut.put(ANS.get(1), root); 
        } 
        for (int j = 2; j <= N; j++){
            if (f[j] > 0)  {
                // ans=true; 
                cut.put(ANS.get(j), f[j] + 1);  
            }
        }    
        return cut;
    }
    
    public void dfs(int now, Contacts c, buildSmall bs){
        //col[now]=grey;
        HashSet hs ;
        depth ++;
        dep[now] = depth;
        low[now] = depth;
        vis[now] = 1;
        String e = ANS.get(now);
        if (c.isKey(e)){  
            hs = c.getSet(e);
            System.out.println(e + hs.size());
            for( int i = 1;i <= N;i++){
                String ee = ANS.get(i);
                if (hs.contains(ee)){
                    if (vis[i] != 1){
                        dfs(i, c, bs); 
                        low[now] = min(low[now],low[i]); 
                        if (low[i] >= dep[now]&&now != 1)
                            f[now]++;
                        else if (now == 1) 
                            root++;
                    }
                    else 
                        low[now] = min(low[now], dep[i]);
                }
            } // for  
        } // if
    }
  
//   public boolean checkExist(String v1){
//
//          if (graph.containsKey(v1))
//          return true;
//       else 
//           return false;
//       
//   }
//   

    public static String transName(String str){
        int i;
        String s1;
        if (str.contains("@")){
            i = str.indexOf("@");
            s1 = str.substring(0, i);
             // s1=str.substring(0, i-1);
             // s2=str.substring(i+1,str.length()-1);
            return s1;    
        } else
            return str;
    }
  
    public HashMap<String,Double> getHash(String s1){ 
        if (graph.containsKey(s1)){
            if (!graph.get(s1).isEmpty()){
                HashMap<String,Double> hm = graph.get(s1);
                return hm;
            } else
                return null;
        } else 
            return null;
    }
  
    public void removeEmail(String s1){
        if (graph.containsKey(s1)){
            graph.remove(s1);  
        }
    }
  
    public boolean isAt(String s1){
    if (graph.containsKey(s1))
        return true;
    else 
        return false;
    }
  
    public Set getKeySet(){
        return graph.keySet();
    }

    public void setPath(HashMap<String, Double> path) {
        this.path = path;
    }

    public void setPathNum(int pathNum) {
        this.pathNum = pathNum;
    }

    public void setPathTime(double pathTime) {
        this.pathTime = pathTime;
    }

    public int getPathNum() {
        return pathNum;
    }

    public double getPathTime() {
        return pathTime;
    }

    public HashMap<String, Double> getPath() {
        return path;
    }

    public double getDeMax() {
        return deMax;
    }

    public double getCloMax() {
        return cloMax;
    }

    public double getSentMax() {
        return sentMax;
    }

    public double getRecMax() {
        return recMax;
    }

    public double getTotalMax() {
        return totalMax;
    }

    public String getDeName() {
        return deName;
    }

    public String getCloName() {
        return cloName;
    }

    public String getSentName() {
        return sentName;
    }

    public String getRecName() {
        return recName;
    }

    public String getTotalName() {
        return totalName;
    }
  
                //  if (i!=father &&col[i]==grey)       low[now]=min(low[now],dep[i]);
                //  if (col[i]==white){ dfs(i,now,depth+1); child+=1; low[now]=min(low[now],low[i]);
                //           if (now==1&&child>1||(now!=1&&low[i]>=dep[now]))
                //           {
                //               tag[now]=true;
                //               children+=1;}
}  
    
    
// old code
//    public class Dijkstra {  
//    HashSet<Vertex> open=new HashSet<Vertex>();  
//    HashSet<Vertex> close=new HashSet<Vertex>();  
//    HashMap<String,Integer> path=new HashMap<String,Integer>();          //for path 
//    HashMap<String,String> pathInfo=new HashMap<String,String>();       //for pathinfo
//    public void init(){        
//        path.put("B", 1);  
//        pathInfo.put("B", "A->B");  
//        path.put("C", 1);  
//        pathInfo.put("C", "A->C");  
//        path.put("D", 4);  
//        pathInfo.put("D", "A->D");  
//        path.put("E", Integer.MAX_VALUE);  
//        pathInfo.put("E", "A");  
//        path.put("F", 2);  
//        pathInfo.put("F", "A->F");  
//        path.put("G", 5);  
//        pathInfo.put("G", "A->G");  
//        path.put("H", Integer.MAX_VALUE);  
//        pathInfo.put("H", "A");   
//        //将初始节点放入close,其他节点放入open  
//      // Vertex start=new MapBuilder().build(open,close);  
//        //return start;  
//    }  
//    public void computePath(Vertex start){  
//        Vertex nearest=getShortestPath(start);                    //get Nodes who is nearest
//        if(nearest==null){  
//            return;  
//        }  
//        close.add(nearest);  
//      }
//    public void printPathInfo(){  
//        Set<HashMap.Entry<String, String>> pathInfos=pathInfo.entrySet();  
//        for(HashMap.Entry<String, String> pathInfo:pathInfos){  
//            System.out.println(pathInfo.getKey()+":"+pathInfo.getValue());  
//        }  
//    }  
//    private Vertex getShortestPath(Vertex node){  
//        Vertex res=null;  
//        int minDis=Integer.MAX_VALUE;  
//        HashMap<Vertex,Integer> childs=node.getChild();  
//        for(Vertex child:childs.keySet()){  
//            if(open.contains(child)){  
//                int distance=childs.get(child);  
//                if(distance<minDis){  
//                    minDis=distance;  
//                    res=child;  
//                }  
//            }  
//        }  
//        return res;  
//    }  
//}      
        