package nway;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.ecore.EObject;

public class MaximalCliquesWithoutPivot {
	
	ArrayList<Vertex> graph = new ArrayList<Vertex>();
	
    class Vertex implements Comparable<Vertex> {
        EObject x;	// 节点类型是EObject

        int degree;
        ArrayList<Vertex> nbrs = new ArrayList<Vertex>();

        public EObject getX() {
            return x;
        }

        public void setX(EObject x) {
            this.x = x;
        }

        public int getDegree() {
            return degree;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }

        public ArrayList<Vertex> getNbrs() {
            return nbrs;
        }

        public void setNbrs(ArrayList<Vertex> nbrs) {
            this.nbrs = nbrs;
        }

        public void addNbr(Vertex y) {
            this.nbrs.add(y);
            if (!y.getNbrs().contains(y)) {
                y.getNbrs().add(this);
                y.degree++;
            }
            this.degree++;

        }

        public void removeNbr(Vertex y) {
            this.nbrs.remove(y);
            if (y.getNbrs().contains(y)) {
                y.getNbrs().remove(this);
                y.degree--;
            }
            this.degree--;

        }

        @Override
        public int compareTo(Vertex o) {
            if (this.degree < o.degree) {
                return -1;
            }
            if (this.degree > o.degree) {
                return 1;
            }
            return 0;
        }
    }
	
    // lyt: 需要传入参数
    void initGraph(Set<EObject> vertices, EList<Match> edges) {
        graph.clear();
        vertices.forEach( eObject -> {
        	Vertex V = new Vertex();
        	V.setX(eObject);
        	graph.add(V);
        });       
             
        for(int i=0; i<edges.size(); i++) {
        	EObject left = edges.get(i).getLeft();
        	EObject right = edges.get(i).getRight();
        	Vertex vertexU = null;
        	Vertex vertexV = null;
        	for(int j=0; j<graph.size(); j++) {
        		Vertex v = graph.get(j);
        		if(v.x == left) {
        			vertexU = v;
        		}else if(v.x == right) {
        			vertexV = v;
        		}
        	}
        	vertexU.addNbr(vertexV);        	
        }
        
    }
    
    void Bron_KerboschPivotExecute(EList<EList<EObject>> maximalCliques) {
        ArrayList<Vertex> X = new ArrayList<Vertex>();
        ArrayList<Vertex> R = new ArrayList<Vertex>();
        ArrayList<Vertex> P = new ArrayList<Vertex>(graph);
        Bron_KerboschWithoutPivot(R, P, X, "", maximalCliques);
    }
    
    // Version without a Pivot
    void Bron_KerboschWithoutPivot(ArrayList<Vertex> R, ArrayList<Vertex> P,
                                   ArrayList<Vertex> X, String pre, EList<EList<EObject>> maximalCliques) {

        System.out.print(pre + " " + printSet(R) + ", " + printSet(P) + ", "
                + printSet(X));
        if ((P.size() == 0) && (X.size() == 0)) {
            printClique(R);
            // lyt: 保存一下
            EList<EObject> eList = new BasicEList<>();
            R.forEach(v -> {
            	eList.add(v.x);
            });
            maximalCliques.add(eList);            
            return;
        }
        System.out.println();

        ArrayList<Vertex> P1 = new ArrayList<Vertex>(P);

        for (Vertex v : P) {
            R.add(v);
            Bron_KerboschWithoutPivot(R, intersect(P1, getNbrs(v)),
                    intersect(X, getNbrs(v)), pre + "\t", maximalCliques);
            R.remove(v);
            P1.remove(v);
            X.add(v);
        }
    }
    
    String printSet(ArrayList<Vertex> Y) {
        StringBuilder strBuild = new StringBuilder();

        strBuild.append("{");
        for (Vertex v : Y) {
            strBuild.append("" + (v.getX()) + ",");
        }
        if (strBuild.length() != 1) {
            strBuild.setLength(strBuild.length() - 1);
        }
        strBuild.append("}");
        return strBuild.toString();
    }
    
    void printClique(ArrayList<Vertex> R) {
        System.out.print("  -------------- Maximal Clique : ");
        for (Vertex v : R) {
            System.out.print(" " + (v.getX()));
        }
        System.out.println();
    }
    
    // Finds nbr of vertex v
    ArrayList<Vertex> getNbrs(Vertex v) {
        EObject eObject = v.getX();     
        Vertex vertex = null;
        for(int j=0; j<graph.size(); j++) {
        	vertex = graph.get(j);
        	if(vertex.x == eObject) {
        		break;
        	}
        }
		return vertex.nbrs;               
    }
    
    // Intersection of two sets
    ArrayList<Vertex> intersect(ArrayList<Vertex> arlFirst,
                                ArrayList<Vertex> arlSecond) {
        ArrayList<Vertex> arlHold = new ArrayList<Vertex>(arlFirst);
        arlHold.retainAll(arlSecond);
        return arlHold;
    }
}
