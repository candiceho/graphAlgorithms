/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.API;

import Business.Betweenness;
import Business.Contacts;
import Business.Dictionary;
import Business.FinalResult;
import Business.Graph;
import Business.JGraph;
import Business.buildSmall;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFrame;

public class Final {
    private static final File dataDir = new File("D:\\enron_mail_20150507\\maildir\\arnold-j"); 
    private static  String path= "D:\\enron_mail_20150507\\maildir";
    // private static final File dataDir = new File("D:\\A");            // sucsess
    // private static final File dataDir = new File("D:\\arnold-j");         // sucsess
    // private static final File dataDir = new File("D:\\arora-h");            // sucsess
    // private static final File dataDir = new File("D:\\presto-k");          // success
    private static final Graph graph= new Graph();
    private static final Dictionary dictionary = new Dictionary();
    private static final Contacts contact= new Contacts();
    private static final BuildData data = new BuildData();
    private static final  buildSmall bs =new buildSmall();
    private static final Betweenness bet=new Betweenness();
    private static final FinalResult result= new FinalResult();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ExtraInfo.Tokenize(dataDir,graph,dictionary);  
        System.out.println(graph.keysNum());
        dictionary.mulEmail(graph);
        System.out.println(graph.keysNum());
        ExtraInfo.getFileName(path,bs,bet);
        System.out.println(bs.personNum());
        dictionary.Combine(bs, graph);
        // bs.personNum();  
        graph.buildContact(contact);      
        //   data.toFile("D:\\data.txt", bs, graph);
        // System.out.println(bet.bNum());
        try {						
                                            //			FileOutputStream fos = new FileOutputStream("D:/Between.ser");
                                            //			ObjectOutputStream oos = new ObjectOutputStream(fos);
                                            //			oos.writeObject(bet);
                                            //			oos.close();

                                            //                        FileOutputStream fos2 = new FileOutputStream("D:/Dat.ser");
                                            //			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
                                            //                        oos2.writeObject(dictionary);
                                            //			oos2.close();

            FileInputStream fis = new FileInputStream("D:/Between.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Betweenness bt  =   (Betweenness) ois.readObject();
            ois.close();

                                            //                        FileInputStream fis2 = new FileInputStream("D:/Dat.ser");
                                            //			ObjectInputStream ois2 = new ObjectInputStream(fis2);
                                            //			Dictionary d2 =  (Dictionary) ois2.readObject();
                                            //			ois2.close();
                                            //                        
                                                   //   System.out.println(bt.bNum());
                                            //             d2.getkey();
                                            //              System.out.println(graph1.keysNum());  
                                              //  System.out.println( graph.Analysis(bs, contact,bt));                         

                        
            System.out.println( graph.Analysis(bs, contact,bt,result));     
            result.sortPerson();
            String out= result.printResult();
            System.out.println(out);
                        
                                            //read.performActionTest(result);
                                            //  read.visualizationGraph(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JGraph frame = new JGraph(bs,graph);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(3000, 2400);
        frame.setVisible(true);
    }    
}


