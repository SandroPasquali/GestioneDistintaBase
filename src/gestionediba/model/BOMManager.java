package gestionediba.model;

import java.util.Iterator;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import db.DiBaDAO;

public class BOMManager implements BOMManagerInterface {

	

	
	

	@Override
	public Graph<String, DefaultEdge> doMultileveExplosion(String root, int level) {
		//TODO separare la parte di stampa da quella di restituzione del grafo
		//TODO impostato il livello di profondità, verificare se è possibile utilizzare il sub graph invece che il BFS
		final int EXPLOSION = 0;
		int TYPE_QUERY = EXPLOSION;
		DiBaDAO dao= new DiBaDAO();
		//List<DiBaBean> list = dao.recursiveBOMExplosion(root);
		dao.recursiveBOMExplosion(root);
		
		Graph<String, DefaultEdge> g = buildSimpleDirectedGraph(root,list,TYPE_QUERY);
		BreadthFirstIterator<String,DefaultEdge> bfi = new BreadthFirstIterator<String,DefaultEdge>(g,root);
		int currentLevel=-1;
		System.out.println("i nodi del livello richiesto sono:");
		while(bfi.hasNext()) {
			String v=bfi.next();
			currentLevel=bfi.getDepth(v);
			if(currentLevel<=level) {
				System.out.println(v+"|"+"liv "+currentLevel);
			}else {
				break;
			}
		}//wend
		System.out.println("to string di g: "+g.toString());
		return g;
		
	}
	
	
	
	
	@Override
	public Graph<String, DefaultEdge> doMultilevelImplosion(String root, int level) {
		final int IMPLOSION = 1;
		int TYPE_QUERY = IMPLOSION;
		DiBaDAO dao= new DiBaDAO();
		List<DiBaBean> list = dao.recursiveBOMImplosion(root);
		Graph<String, DefaultEdge> g = buildSimpleDirectedGraph(root,list,TYPE_QUERY);
		BreadthFirstIterator<String,DefaultEdge> bfi = new BreadthFirstIterator<String,DefaultEdge>(g,root);
		int currentLevel=-1;
		System.out.println("i nodi del livello richiesto sono:");
		while(bfi.hasNext()) {
			String v=bfi.next();
			currentLevel=bfi.getDepth(v);
			if(currentLevel<=level) {
				System.out.println(v+"|"+"liv "+currentLevel);
			}else {
				break;
			}
		}//wend
		//System.out.println("to string di g: "+g.toString());
		return g;
	}//eom
	
	
	private Graph<String, DefaultEdge> buildSimpleDirectedGraph(String root,List<DiBaBean> l, int typeOfQry) {
		
		//dao= new DiBaDAO();
		Graph<String, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);
		
		//List<DiBaBean> result =dao.recursiveBOMExplosion(root);
		List<DiBaBean> result =l;
		
		//System.out.println("nr di elementi nella lista: "+result.size());
		
		Iterator<DiBaBean> itr = result.iterator();
		DiBaBean bean;
		//TODO ottimizzare la ridondanza del codice
		switch (typeOfQry) {
			case 0:
				while(itr.hasNext()) {
					bean=new DiBaBean();
					bean=itr.next();
					String padre=bean.getPadre();
					String filio=bean.getFilio();
					graph.addVertex(padre);
					graph.addVertex(filio);
					
					graph.addEdge(padre, filio);
				}//wend
				break;
			case 1:
				while(itr.hasNext()) {
					bean=new DiBaBean();
					bean=itr.next();
					String padre=bean.getPadre();
					String filio=bean.getFilio();
					graph.addVertex(padre);
					graph.addVertex(filio);
					
					graph.addEdge(filio, padre);
				}//wend
				break;
		}//switch
		//System.out.println(graph.toString());
		return graph;
	}//eom
	
	
	

}
