package gestionediba.model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public interface BOMManagerInterface {
	
	public Graph<String, DefaultEdge> doMultileveExplosion(String node, int level);
	public Graph<String, DefaultEdge> doMultilevelImplosion(String node,int level);
}
