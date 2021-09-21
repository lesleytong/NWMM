package nway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.ecore.EObject;

public class MaximalCliquesWithPivot {

	ArrayList<Vertex> graph = new ArrayList<Vertex>();

	class Vertex implements Comparable<Vertex> {
		EObject x; // 节点类型是EObject

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
	void initGraph(Set<EObject> vertices, Set<Match> edges) {
		graph.clear();
		vertices.forEach(eObject -> {
			Vertex V = new Vertex();
			V.setX(eObject);
			graph.add(V);
		});

		edges.forEach(e -> {
			EObject left = e.getLeft();
			EObject right = e.getRight();
			Vertex vertexU = null;
			Vertex vertexV = null;
			for (int j = 0; j < graph.size(); j++) {
				Vertex v = graph.get(j);
				if (v.x == left) {
					vertexU = v;
				} else if (v.x == right) {
					vertexV = v;
				}
			}
			vertexU.addNbr(vertexV);
		});
	}
	
	// origin
	void initGraph(Set<EObject> vertices, List<Match> edges) {
		graph.clear();
		vertices.forEach(eObject -> {
			Vertex V = new Vertex();
			V.setX(eObject);
			graph.add(V);
		});

		for (int i = 0; i < edges.size(); i++) {
			EObject left = edges.get(i).getLeft();
			EObject right = edges.get(i).getRight();
			Vertex vertexU = null;
			Vertex vertexV = null;
			for (int j = 0; j < graph.size(); j++) {
				Vertex v = graph.get(j);
				if (v.x == left) {
					vertexU = v;
				} else if (v.x == right) {
					vertexV = v;
				}
			}

			try {
				vertexU.addNbr(vertexV);
			} catch (Exception e) {
				// tmp
				System.out.println("MaximalClique line 106");
				e.printStackTrace();
			}

		}

	}

	void Bron_KerboschPivotExecute(List<List<EObject>> maximalCliques) {
		ArrayList<Vertex> X = new ArrayList<Vertex>();
		ArrayList<Vertex> R = new ArrayList<Vertex>();
		ArrayList<Vertex> P = new ArrayList<Vertex>(graph);
		Bron_KerboschWithPivot(R, P, X, "", maximalCliques);
	}

	// Version with a Pivot
	void Bron_KerboschWithPivot(ArrayList<Vertex> R, ArrayList<Vertex> P, ArrayList<Vertex> X, String pre,
			List<List<EObject>> maximalCliques) {

//		System.out.print(pre + " " + printSet(R) + ", " + printSet(P) + ", " + printSet(X));

		if ((P.size() == 0) && (X.size() == 0)) {
//			printClique(R);
			// lyt: 保存一下
			EList<EObject> eList = new BasicEList<>();
			R.forEach(v -> {
				eList.add(v.x);
			});
			maximalCliques.add(eList);
			return;
		}

//		System.out.println();
		ArrayList<Vertex> P1 = new ArrayList<Vertex>(P);
		// Find Pivot
		Vertex u = getMaxDegreeVertex(union(P, X));
//		System.out.println("" + pre + " Pivot is " + (u.x));

		// P = P \ Nbrs(u)
		P = removeNbrs(P, u);
		for (Vertex v : P) {
			R.add(v);
			Bron_KerboschWithPivot(R, intersect(P1, getNbrs(v)), intersect(X, getNbrs(v)), pre + "\t", maximalCliques);
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
		// PENDING: 用stream
		Vertex vertex = null;
		for (int j = 0; j < graph.size(); j++) {
			vertex = graph.get(j);
			if (vertex.x == eObject) {
				break;
			}
		}
		return vertex.nbrs;
	}

	// Intersection of two sets
	ArrayList<Vertex> intersect(ArrayList<Vertex> arlFirst, ArrayList<Vertex> arlSecond) {
		ArrayList<Vertex> arlHold = new ArrayList<Vertex>(arlFirst);
		arlHold.retainAll(arlSecond);
		return arlHold;
	}

	// Union of two sets
	ArrayList<Vertex> union(ArrayList<Vertex> arlFirst, ArrayList<Vertex> arlSecond) {
		ArrayList<Vertex> arlHold = new ArrayList<Vertex>(arlFirst);
		arlHold.addAll(arlSecond);
		return arlHold;
	}

	// get max degree vertex in g
	Vertex getMaxDegreeVertex(ArrayList<Vertex> g) {
		Collections.sort(g);
		return g.get(g.size() - 1);
	}

	// Removes the neigbours
	ArrayList<Vertex> removeNbrs(ArrayList<Vertex> arlFirst, Vertex v) {
		ArrayList<Vertex> arlHold = new ArrayList<Vertex>(arlFirst);
		arlHold.removeAll(v.getNbrs());
		return arlHold;
	}

}
