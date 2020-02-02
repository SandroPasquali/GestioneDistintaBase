package gestionediba.model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/*
 * SCOPO: tenere traccia del nr max di livelli di root nella sua diba
 */
public class RootLeveBean {
	String root;
	Graph<String, DefaultEdge> graph;
	int nrOfLevels;
	
	

	public RootLeveBean(String root, Graph<String, DefaultEdge> graph, int nrOfLevels) {
		super();
		this.root = root;
		this.graph = graph;
		this.nrOfLevels = nrOfLevels;
	}

	public RootLeveBean() {
		super();
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public Graph<String, DefaultEdge> getGraph() {
		return graph;
	}

	public void setGraph(Graph<String, DefaultEdge> graph) {
		this.graph = graph;
	}

	public int getNrOfLevels() {
		return nrOfLevels;
	}

	public void setNrOfLevels(int nrOfLevels) {
		this.nrOfLevels = nrOfLevels;
	}

	
	
	
	
}//eoc
