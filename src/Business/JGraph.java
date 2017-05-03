/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import com.mxgraph.layout.mxCircleLayout;
import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.util.HashMap;
import java.util.Iterator;


public class JGraph extends JFrame{
    static Graph gra;
    static  buildSmall b;
    private static final long serialVersionUID = -2764911804288120883L;
    
    public JGraph(buildSmall bs, Graph g){
        //GraphModel model= new DefaultGraphModel();
        //super("Hello, World!");
        int x1 = 0;      
        int y1 = 0; //int x2=0; int y2=0; int i=0;
        int n = 0;
        HashMap <String, Object> jgraph = new HashMap<String, Object>();
        Object os[] = new Object [150];
        final mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try {
            Iterator itor = bs.getSet().iterator();  
            while (itor.hasNext()){
                String key = (String)itor.next();
                String value = bs.getE(key);   //email
                if (g.isAt(value)){
                    HashMap hm = g.getHash(value);
                    Iterator itor1 = hm.keySet().iterator();  
                    while (itor1.hasNext()){  
                        Object v1 = null,v2=null;
                        String key1 = (String)itor1.next();
                        if (bs.emaExist(key1)){
                            String key2 = bs.getName(key1);
                            double value1 = (double) hm.get(key1);
                            if (!jgraph.containsKey(key)){
                                int size = jgraph.size();
                                if (size % 38 == 0 && size != 0){
                                    n += 1; 
                                    x1 = 0; 
                                    y1 = 0;
                                }   
                                if (n == 0){
                                    v1 = graph.insertVertex(parent, null, key, 550 - x1, 0 + y1, 100,
                                    25, "ROUNDED; strokrColor=red;fillColor=red");
                                    x1 += 16;
                                    y1 += 10;
                                    jgraph.put(key, v1);
                                }// if(size%==0&&size!=0&&n!=0) { n=n+100;x1=0;}
                                if (n == 1){
                                    v1 = graph.insertVertex(parent, null, key, 0 + x1, 320 + y1, 100,
                                    25, "ROUNDED; strokrColor=red;fillColor=red");
                                    x1 += 16;
                                    y1 += 10;
                                    jgraph.put(key, v1);
                                }
                                if (n == 2){
                                    v1 = graph.insertVertex(parent, null, key, 550 + x1, 640 - y1, 100,
                                    25, "ROUNDED; strokrColor=red;fillColor=red");
                                    x1 += 16;
                                    y1 += 10;
                                    jgraph.put(key, v1);
                                }
                                if (n == 3){
                                    v1 = graph.insertVertex(parent, null, key, 1100 - x1, 320 - y1, 100,
                                    25, "ROUNDED; strokrColor=red;fillColor=red");
                                    x1 += 16;
                                    y1 += 10;
                                    jgraph.put(key, v1);
                                }
                            } else {
                                v1 = jgraph.get(key);
                            }
                            
                            if (!jgraph.containsKey(key2)){
                                int size = jgraph.size();
                                if (size % 38 == 0 && size != 0){
                                    n += 1; 
                                    x1 = 0; 
                                    y1 = 0;
                                }   
                                if (n == 0){
                                    v2 = graph.insertVertex(parent, null, key2, 550 - x1, 0 + y1, 100,          //83 15
                                    25, "ROUNDED; strokrColor=red;fillColor=red");
                                    x1 += 16;
                                    y1 += 10;
                                    jgraph.put(key2, v2);
                                }// if(size%==0&&size!=0&&n!=0) { n=n+100;x1=0;}
                                if (n == 1){
                                    v2 = graph.insertVertex(parent, null, key2, 0 + x1, 320 + y1, 100,
                                    25, "ROUNDED; strokrColor=red;fillColor=red");
                                    x1 += 16;
                                    y1 += 10;
                                    jgraph.put(key2, v2);
                                }
                                if (n == 2){
                                    v2 = graph.insertVertex(parent, null, key2, 550 + x1, 640 - y1, 100,
                                    25, "ROUNDED; strokrColor=red;fillColor=red");
                                    x1 += 16;
                                    y1 += 10;
                                    jgraph.put(key2, v2);
                                }
                                if (n == 3){
                                    v2 = graph.insertVertex(parent, null, key2, 1100 - x1, 320 - y1, 100,
                                    25, "ROUNDED; strokrColor=red;fillColor=red");
                                    x1 += 16;
                                    y1 += 10;
                                    jgraph.put(key2, v2);
                                }
                                                            //  v1 = graph.insertVertex(parent, null, key, 1+x1, 1+n, 83,
                                                            // 15,"ROUNDED; strokrColor=red;fillColor=red");
                                                            // os[i]=v1;
                                                            // i++;
                                                            //  x1+=108;
                                                            //  if(n==0) n=n+100;

                            } 
                                                            //                    if(!jgraph.containsKey(key2)){
                                                            //                       //  int size=jgraph.size();
                                                            //                       //   if(size%12==0&&size!=0&&n!=0) {n=n+100; x1=0;}
                                                            //                        v2 = graph.insertVertex(parent, null,key2,
                                                            //		         1+x1, 1+n, 83, 15);
                                                            //                       // x1+=108;
                                                            //                       // if(n==0) n+=100;
                                                            //                    jgraph.put(key2,v2);                    
                                                            //                        }
                            else {
                                v2 = jgraph.get(key2);
                            }
                            value1 = value1 * 100;
                            int valuer = (int) value1;
                            graph.insertEdge(parent, null, valuer, v1, v2); 
                        }
                    }  
                    n = 0;
                }
            }
            mxCircleLayout m = new mxCircleLayout(graph, 10);
            m.circle(os, 50, 0, 0);
            m.execute(parent);
        }
        finally {
            graph.getModel().endUpdate();
        }
//		ImageIcon z = new ImageIcon("W020130917309080362395.jpg");        //hus;
        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
        graphComponent.zoom(0.2);       //getGraphControl().new MouseAdapter()
    }    
                                                        //                graphComponent.scrollCellToVisible(graph);  
                                                        //                graphComponent.setBackgroundImage(z);
                                                        //		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
                                                        //		{
                                                        //		
                                                        ////			public void mouseReleased(MouseEvent e)
                                                        ////			{
                                                        ////				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
                                                        ////				
                                                        ////				if (cell != null)
                                                        ////				{
                                                        ////					System.out.println("cell="+graph.getLabel(cell));
                                                        ////				}
                                                        ////			}
                                                        //		})
}

