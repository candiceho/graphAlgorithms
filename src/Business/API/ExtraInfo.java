/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.API;


import Business.Betweenness;
import Business.Dictionary;
import Business.Graph;
import Business.buildSmall;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import opennlp.tools.util.InvalidFormatException;


/**
 *
 * @author CANDICEHO
 */
public class ExtraInfo {  
    int filenumber = 0;
    static String path2 = "";
    public static void Tokenize(File f, Graph gra, Dictionary DTA) throws InvalidFormatException, IOException {
        if( f.isDirectory()){
            for (File t:f.listFiles())
            Tokenize(t,gra,DTA);
        } else {         
             if(f.isFile()){                                       // && f.getName().endsWith(".txt")){   
                int fr = 0; 
                int to = 0;
                int xfr = 0;
                int cc = 0;
                int bcc = 0;
                int too = 0;
                String output1 = "";
                String output2 ="";
                String output3 ="";
                String output4 = "";
                String output5 = "";          
                StringTokenizer tokens = new StringTokenizer(readFile(f));
                while(tokens.hasMoreElements()){  
                    String now =  tokens.nextToken();
                    if (now.equalsIgnoreCase("From:")){      // check "From"
                        if(fr == 0){
                            String next = tokens.nextToken(); 
                            if (!next.contains("@")){   
                                String n = next;
                                String nn = tokens.nextToken();
                                if (nn.contains(">") && nn.contains("@")){   
                                    String z = n.trim() + nn.trim(); 
                                    String z2 = z.replace("<", "");
                                    z2 = z2.replace(">", "");  
                                    z2 = deletecoma(z2);
                                    if (checkEmail(z2)){
                                        output1 = z2;
                                        fr += 1;  
                                    } else 
                                        break;
                                } else 
                                    break;
                            } else {
                                String adv = deletecoma(next);
                                if (checkEmail(adv)){
                                    output1=adv;
                                    fr += 1;
                                } else 
                                    break;
                            }
                        } else 
                            break;
                    } // if from
                    
                    if (now.equalsIgnoreCase("To:")){     // check"To"       
                        if (fr == 1 && to == 0){ 
                            String next = tokens.nextToken();
                            String adv = deletecoma(next);
                            if (adv.contains("@")){
                                while (adv.contains("@")){           
                                    if (checkEmail(adv)){
                                        output2 = adv;
                                        if(output2 != null && output1 != null){
                                            gra.addVer(output1);
                                            gra.addEdge(output1, output2);   
                                            too += 1;                       
                                        }
                                    } 
                                    if (tokens.hasMoreElements())
                                        adv = deletecoma(tokens.nextToken());
                                    else  
                                        break;
                                } // while
                                to += 1;
                                if (too == 0)  
                                    break;                    
                            } else
                                break;                  
                        } else 
                            break;
                    } // if  to   
  
                    if (fr == 1 && to == 1 && too > 0 && xfr == 0 && now.contains("X-From")){              // check "From Name"
                        String a = "";
                        String temp = tokens.nextToken();
                        while (!temp.contains("X-To:") && !temp.contains("<") && !temp.contains("(") && !temp.contains("-")){ //&&!temp.contains("(")&&!temp.contains("-"))
                            a += temp + " ";
                            temp = tokens.nextToken();
                        }
                        if (!a.contains("@")){
                            output5=multiEmail(a);
                            System.out.println(output1);
                            System.out.println(output5 + "\n");
                            if(output5 != null && output1 != null) 
                                DTA.addEm(output5, output1);
                        } 
                        xfr += 1;
                    } // if x - from
                    if (fr == 1 && to == 1 && cc == 0 && now.equals("Cc:")){          // check "To Name"
                        String next = tokens.nextToken();
                        String adv = deletecoma(next);
                        while (!adv.contains(":")){          //(!temp.equals("Mime-Version:"))  
                            if (checkEmail(adv)){
                                output3 = adv;
                                gra.addEdge(output1, output3);
                            } 
                            if (tokens.hasMoreElements())
                                adv = deletecoma(tokens.nextToken());
                            else 
                                break;  
                        }  
                        cc += 1;
                    } // if cc          
                }  
            }
        }
    }

    public static void getFileName(String path,buildSmall b, Betweenness bet) throws IOException{
        int zz = 0;
        File file = new File(path);
        String [] fileName = file.list();
        for (int i = 0; i < fileName.length; i++){
            zz = 0;
            path2 = path+"\\" + fileName[i];    
            File file2 = new File(path2);
            String [] fileName2 = file2.list();
            for (int j = 0; j < fileName2.length; j++){
               if (fileName2[j].equalsIgnoreCase("sent_items")){
                    zz += 1;
                    path2 = path2 + "\\" + fileName2[j] + "\\1";      
                    File f = new File(path2);
                    if (f.isFile()){
                        String output1 = "";
                        String output2 = "";
                        StringTokenizer tokens = new StringTokenizer(readFile(f));               
                        while(tokens.hasMoreElements()){  
                            String now = tokens.nextToken();
                            if (now.equalsIgnoreCase("From:")){      // check "From"  
                                String next = tokens.nextToken();
                                if (!next.contains("@")){
                                    String n = next;
                                    String nn = tokens.nextToken();
                                    if (nn.contains(">") && nn.contains("@")){
                                        String z = n.trim() + nn.trim();
                                        String z2 = z.replace("<", "");
                                        z2 = z2.replace(">", "");
                                        output1 = z2;
                                    } else 
                                        break;
                                } else {
                                    String adv = deletecoma(next);
                                    if (checkEmail(adv)) 
                                        output1 = adv; 
                                    else 
                                        break;
                                }  
                            } // if from
                            
                            if (now.contains("X-From")){              // check "From Name"   fr==1&&to==1&&too>0&&xfr==0&&
                                String a = "";
                                String temp = tokens.nextToken();
                                while (!temp.contains("X-To:") && !temp.contains("<") && !temp.contains("(") && !temp.contains("@")){ //&&!temp.contains("-")) //&&!temp.contains("(")&&!temp.contains("-"))
                                    a += temp + " ";
                                    temp=tokens.nextToken();
                                }
                                output2=multiEmail(a);
                                System.out.println(output1); 
                                System.out.println(output2 + "\n");
                                b.addPerson(output2, output1);
                                bet.addP(output1);
                            }         
                        }  
                    }
                    break;
                }
            } // for files
            
            if (zz == 0){
                for (int j = 0; j < fileName2.length; j++){
                    if (fileName2[j].equalsIgnoreCase("sent") || fileName2[j].equalsIgnoreCase("_sent_mail")){ 
                        path2 = path2 + "\\" + fileName2[j] + "\\1";      
                        File f = new File(path2);     
                        if (f.isFile()){
                            String output3 = "";
                            String output4 = "";
                            StringTokenizer tokens = new StringTokenizer(readFile(f));               
                            while(tokens.hasMoreElements()){  
                                String now =  tokens.nextToken();
                                if (now.equalsIgnoreCase("From:")){      // check "From"
                                    String next = tokens.nextToken();
                                    String adv = deletecoma(next);
                                    if (checkEmail(adv)){ 
                                        output3 = adv;
                                    } else 
                                        break;
                                } // if from
                                if (now.contains("X-From")){              // check "From Name"   fr==1&&to==1&&too>0&&xfr==0&&
                                    String a = "";
                                    String temp = tokens.nextToken();
                                    while (!temp.contains("X-To:") && !temp.contains("<") && !temp.contains("(") && !temp.contains("@")){ //&&!temp.contains("-")) //&&!temp.contains("(")&&!temp.contains("-"))
                                        a += temp + " ";
                                        temp = tokens.nextToken();
                                    }
                                    output4 = multiEmail(a);
                                    System.out.println(output3);
                                    System.out.println(output4+"\n");
                                    b.addPerson(output4, output3);
                                    bet.addP(output3);
                                } // if x-from   
                            } // while files
                        }
                        break;
                    } 
                }
            } 
        }
        // corner case
        b.addPerson("Jane M Tholt", "jane.tholt@enron.com");
        b.addPerson("Mary Hain", "mary.hain@enron.com");
        b.addPerson("Steven Harris", "steven.harris@enron.com");
        b.addPerson("Clint Dean", "clint.dean@enron.com");
        b.addPerson("Chris Stokley", "chris.stokley@enron.com");
        //   b.addPerson("Mark Taylor", "mark.taylor@enron.com");
    }

    public static String multiEmail(String s1){
        if (s1 == null) 
            return null;
        if (s1.contains("@")) 
            return null;
        String a[] = new String[10];
        String b[] = new String[10];
        int n = 0;
        String s2 = "";
        //String s3 = "";
        if (s1.contains(",")){
            StringTokenizer token1 = new StringTokenizer(s1, ",");
            while (token1.hasMoreElements() && n < 2){
                String temp = (token1.nextToken()).trim();
                n += 1;
                a[2-n] = temp;
            }
            if (n == 1)
                s2 = a[1];
            if (n == 0) 
                return null;
            else {
                if (a[0].contains(" ")){
                    StringTokenizer token2 = new StringTokenizer(a[0]);
                    while (token2.hasMoreElements()){
                        s2 += (token2.nextToken()).trim() + " "; 
                    }
                } else 
                    s2 += a[0] + " ";
                
                if (a[1].contains(" ")){
                    StringTokenizer token3 = new StringTokenizer(a[1]);
                    while (token3.hasMoreElements()){
                        s2 += (token3.nextToken()).trim() + " "; 
                    }
                } else  
                    s2 += a[1];
            } // else
        } else {
            StringTokenizer token4 = new StringTokenizer(s1);
            while(token4.hasMoreElements()&&n<10){ 
                String temp = token4.nextToken();
                n += 1;
                b[n - 1] = temp.trim();   
                s2 += b[n - 1] + " ";
            }   
            if(n == 0) 
                return null;           
        }
        String s = s2.trim();
        String ss = s.replace(".", "");
        if (ss.equalsIgnoreCase("Mark"))  
            return "Mark E Taylor";
        else 
            return ss.replace("\"", "");
    }
 
    public static boolean checkEmail(String str){
        int i;
        if (str.contains("@")){          
            i = str.indexOf("@");             
            if ((str.substring(i + 1, str.length())).equalsIgnoreCase("enron.com") && i < 26)            // 28  
                return true;
            else    
                return false;    
        }
        else  
            return false;      
    }
    

    public static String readFile(File fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
     
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }               

    public static String deletecoma(String str){
        int i;
        if ( str.contains(",")){ 
            i = str.indexOf(",");
            return str.substring(0, i);
        } else 
            return str;
    }
}



// old code
//              if (m==1&&n==1&&now.equalsIgnoreCase("X-To:"))           // check "To Name"
//              {
//                    String temp =tokens.nextToken();
//                    String a="";
//                    while (!temp.contains("X-cc:")&&!temp.contains("<"))
//                    {
//                        a+=temp+" ";
//                        temp=tokens.nextToken();
//                           }
//                 output4=a;
//              }       
//           if (fr==1&&to==1&&cc==1&&bcc==0&&now.equals("Bcc:"))           // check "To Name"
//              {    
//                     String next=tokens.nextToken();
//                     String adv= deletecoma(next);
//                while(!adv.contains(":"))                       // (!temp.equals("X-From:"))
//                    {        
//                       if (checkEmail(adv)) 
//                       {
//                         output4=adv;
////                         gra.addVer(output1);
////                         gra.addVer(output2);
//                         gra.addEdge(output1, output4);
//                        } 
//                    if (tokens.hasMoreElements())
//                        adv=deletecoma(tokens.nextToken());
//                    else 
//                        break;
//                     }  
//                          bcc+=1;
//                }

         
//       public static void findName(File f) throws InvalidFormatException, IOException {      
//	InputStream is = new FileInputStream(path+"en-ner-person.bin");
//	TokenNameFinderModel model = new TokenNameFinderModel(is);
//	NameFinderME nameFinder = new NameFinderME(model);
//         if( f.isDirectory())
//        {         for (File t:f.listFiles())
//             findName(t); 
//        }
//         else {
//        if(f.isFile() ){  
//        InputStream isToken = new FileInputStream(path+"en-token.bin");          
//        TokenizerModel modelToken = new TokenizerModel(isToken);     
//	Tokenizer tokenizer = new TokenizerME(modelToken);    
//        String tokens[] = tokenizer.tokenize(readFile(f));
//        Span nameSpans[] = nameFinder.find(tokens);
//            for(Span s: nameSpans)
//                    System.out.println(tokens[s.getStart()]+" "+tokens[s.getEnd()-1]);
//          }
//         }
//         is.close();
//}
//         { for(int i = 0; i < dataFile.length; i++){ 
//             if( dataFile[i].isDirectory())
//             {
//             if(dataFile[i].isFile() && dataFile[i].getName().endsWith(".txt")){        
//               String tokens[] = tokenizer.tokenize(readFile(dataFile[i]));
//               for (String a : tokens)
//                       System.out.println(a);
//                 }   
//                }
//             else {
//                File data2= new File(filepath+"\\"+dataFile[i].getName()) ;              
//             Tokenize(filepath+"\\"+dataFile[i].getName());     
//                 }       
//                                                }                
//         System.out.println(dataFiles.length);
//            is.close();
//}


//    //Sentence Detector
//    public static void SentenceDetect() throws InvalidFormatException,IOException {
//        System.out.println("*************************Senetence Detect***************************");
//	//String paragraph = "Hi. How are you? This is Mike.";
//	// always start with a model, a model is learned from training data
//	InputStream is = new FileInputStream(path+"en-sent.bin");
//	SentenceModel model = new SentenceModel(is);
//	SentenceDetectorME sdetector = new SentenceDetectorME(model);
// 
//        for(int i = 0; i < dataFiles.length; i++){   
//          if(dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")){    
//
//        String sentences[] = sdetector.sentDetect(readFile(dataFiles[i]));
//	for (String a : sentences)
//		System.out.println(a);
//            }   
//        }    
//	is.close();
//}

                                                                                    //Name Finder
//                                                                                    public static void findName(File f) throws IOException {
//                                                                                        System.out.println("*************************Find Name***************************");
//                                                                                        InputStream is = new FileInputStream(path+"en-ner-person.bin");
//
//                                                                                        TokenNameFinderModel model = new TokenNameFinderModel(is);
//
//                                                                                        NameFinderME nameFinder = new NameFinderME(model);
//
//
//                                                                                         if( f.isDirectory())
//                                                                                        {         for (File t:f.listFiles())
//                                                                                             findName(t); 
//
//                                                                                        }
//                                                                                         else 
//                                                                                         {
//                                                                                             if(f.isFile() ){
//
//
//                                                                                //        for(int i = 0; i < dataFiles.length; i++){ 
//                                                                                //            
//                                                                                //          if(dataFiles[i].isFile() ){     //  && dataFiles[i].getName().endsWith(".txt")
//
//                                                                                        InputStream isToken = new FileInputStream(path+"en-token.bin");
//
//                                                                                        TokenizerModel modelToken = new TokenizerModel(isToken); 
//
//                                                                                        Tokenizer tokenizer = new TokenizerME(modelToken);
//
//                                                                                        String tokens[] = tokenizer.tokenize(readFile(f));
//
//                                                                                        Span nameSpans[] = nameFinder.find(tokens);
//                                                                                            for(Span s: nameSpans)
//                                                                                                    System.out.println(tokens[s.getStart()]+" "+tokens[s.getEnd()-1]);
//                                                                                          }
//                                                                                     } 	
//                                                                                         is.close();
//                                                                                }

//                                                                                    public static String extraName(String str){            // nexttokens.trim();
//                                                                                    int i,j;
//                                                                                    String s=str;
//                                                                                    String s1,s2,tem;
//
//                                                                                    while (s.contains("<")&& s.contains(">"))
//                                                                                    {   
//                                                                                        i=s.indexOf("<");
//                                                                                        j=s.indexOf(">");
//
//                                                                                        if (i==0)
//                                                                                                 { s=s.substring(j+1,s.length());}
//                                                                                        else if (j==s.length()-1)
//                                                                                                  { s= s.substring(0, i);}
//                                                                                             else
//                                                                                                 {  
//                                                                                                    tem=s;
//                                                                                                    s1=s.substring(0, i);
//                                                                                                    s2=tem.substring(j+1,s.length());
//                                                                                                    s=s1+"+"+s2;
//                                                                                                  }
//
//                                                                                    }
//                                                                                         return s;
//
//                                                                                    }
//  public static String deleteLine(String str){
//      int i;
//        if ( str.contains("-"))
//            
//        {
//            
//            i = str.indexOf("-");
//            String s1=str.substring(0, i);
//            String s2=str.substring(i+1, str.length());
//            
//            return s1.trim()+s2.trim();
//                
//              
//            
//        } 
//        else return str;
//    
//    }
 
//       public static String transToName(String str){
//         int i;
//         String s1;
//            if ( str.contains("@"))    {
//             i=str.indexOf("@");
//             s1=str.substring(0,i);
//              // s1=str.substring(0, i-1);
//              // s2=str.substring(i+1,str.length()-1);
//                return s1;    
//            }
//            else return str;
//        }
       