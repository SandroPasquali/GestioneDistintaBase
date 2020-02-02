package gestionediba.model;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import db.DiBaDAO;



/**
 * SCOPO: tenere memorizzato il grafo g
 * @author pasquali
 */
public class Model {

    private Graph<String, DefaultEdge> g;
    private List<DiBaBean> dibaBeanList;
    private Map<String,DiBaBean> idMap;
    
    
    
    
    
	public Model() {
		super();
		idMap = new HashMap<String, DiBaBean>();
		dibaBeanList= new ArrayList<DiBaBean>();
		g = new SimpleDirectedGraph<>(DefaultEdge.class);
		
		//TODO creare istanza grafo
		//this.g = g;
		//this.dibaBeanList = dibaBeanList;
	}//EOC



	
	
	
	
	public void doMultilevelExplosion(String root, int level) {
		//TODO separare la parte di stampa da quella di restituzione del grafo
		//TODO impostato il livello di profondità, verificare se è possibile utilizzare il sub graph invece che il BFS
		final int EXPLOSION = 0;
		int TYPE_QUERY = EXPLOSION;
		DiBaDAO dao= new DiBaDAO();
		//List<DiBaBean> list = dao.recursiveBOMExplosion(root);
		dao.recursiveBOMExplosion(root,dibaBeanList);
		
		buildSimpleDirectedGraphV03(root,TYPE_QUERY);
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
		
		
	}
	


	


	/*
	public void doMultilevelExplosion(String root,int lvl) {
		DiBaDAO dao= new DiBaDAO();
		int currentLevel=-1;
		final int TYPE_OF_QRY=0;
		
		this.dibaBeanList= dao.recursiveBOMExplosion(root);
		buildSimpleDirectedGraphV03(TYPE_OF_QRY); //TODO OCCHIO
		
		BreadthFirstIterator<String,DefaultEdge> bfi = new BreadthFirstIterator<String,DefaultEdge>(this.g,root);
		System.out.println("i nodi del livello richiesto sono:");
		while(bfi.hasNext()) {
			String v=bfi.next();
			currentLevel=bfi.getDepth(v);
			if(currentLevel<=lvl) {
				System.out.println(v+"|"+"liv "+currentLevel);
			}else {
				break;
			}
		}//wend
		//System.out.println("to string di g: "+g.toString());
	}//eom
	
	*/
	public void doMultilevelImplosion(String root,int lvl) {
		DiBaDAO dao= new DiBaDAO();
		final int TYPE_OF_QRY=1;
		dao.recursiveBOMImplosion(root,idMap);
		buildSimpleDirectedGraphV03(root,TYPE_QUERY); //TODO OCCHIO
		
		BreadthFirstIterator<String,DefaultEdge> bfi = new BreadthFirstIterator<String,DefaultEdge>(this.g,root);
		int currentLevel=-1;
		System.out.println("i nodi del livello richiesto sono:");
		while(bfi.hasNext()) {
			String v=bfi.next();
			currentLevel=bfi.getDepth(v);
			if(currentLevel<=lvl) {
				System.out.println(v+"|"+"liv "+currentLevel);
			}else {
				break;
			}
		}//wend
		System.out.println("to string di g: "+g.toString());
	}//eom

	public void buildSimpleDirectedGraphV03(String root,int typeOfQry) {
		
		//DiBaDAO dao = new DiBaDAO();
		//dao.recursiveBOMExplosion(root, idMap);
		
		//System.out.println("nr di elementi nella lista: "+dibaBeanList.size());
		
		
		
		Iterator<DiBaBean> itr = dibaBeanList.iterator();
		DiBaBean bean;
		while(itr.hasNext()) {
			//bean=new DiBaBean();
			bean=itr.next();
			String padre=bean.getPadre();
			String filio=bean.getFilio();
			
			// 2) aggiungi i vertici
			this.g.addVertex(padre);
			this.g.addVertex(filio);
			
			// 3) aggiungi gli archi
			addEdge(filio,padre,typeOfQry);
		}//wend
		//System.out.println(g.toString());
	}//eom

	
	
	
	private void addEdge(String filio, String padre,int typeOfQry) {
		
		switch(typeOfQry) {
			case 0:
				this.g.addEdge(padre,filio);
				break;
			case 1:
				this.g.addEdge(filio,padre);
				break;
		}//switch
	}//eom

	
	
	private  void doSimpleDirectedGraphStatistic() {
	
		int totalVertex=this.g.vertexSet().size();
		int totalEdges=this.g.edgeSet().size();
		int totalPossibleEdges=	totalVertex*(totalVertex-1);
		int density=totalEdges%totalPossibleEdges;
		
		System.out.println("nr di vertici presenti nel grafo: "+totalVertex);
		System.out.println("nr di edge presenti nel grafo "+totalEdges);
		System.out.println("nr di edges possibili di un grafo completo e orientato = n*(n-1):"+totalPossibleEdges);
		System.out.println("la densità del grafo è "+density);
}//eom

	private void doBestFirstView(Graph<String, DefaultEdge> g, String root) {
	
	BreadthFirstIterator<String,DefaultEdge> bfi = new BreadthFirstIterator<String,DefaultEdge>(g,root);
	while(bfi.hasNext()) {
		String v=bfi.next();
		int lev=bfi.getDepth(v);
		System.out.println("nodo "+v+"|"+"liv "+lev);
	}//wend
}

	
	// TODO RICERCARE LA DISTINTA + LUNGA
	private  RootLeveBean fillRootMaxLevel(Graph<String, DefaultEdge> g,String root) {
	/*
	 * SCOPO: tenere traccia per il g dato del suo nr di livelli
	 * RETURN: 
	 */
		int currentLevel = -1; //flag init
		BreadthFirstIterator<String,DefaultEdge> bfi = new BreadthFirstIterator<String,DefaultEdge>(g,root);
		while(bfi.hasNext()) {
			String v=bfi.next();
			int tmpLevel=bfi.getDepth(v);
			if(tmpLevel>=currentLevel) {
				currentLevel = tmpLevel;
			}
			//System.out.println("nodo "+v+"|"+"liv "+lev);
		}//wend
		
		//System.out.println("root: "+rootLevelBean.getRoot()+"|"+"MaxLvl: "+rootLevelBean.getLevel());
		return new RootLeveBean(root,g,currentLevel);
	}//eom
	
	private  List<RootLeveBean> dibaScan() {
		//Graph<String, DefaultEdge> g;
		DiBaDAO dao= new DiBaDAO();
		List<String> nodes =dao.getAllParents();
		Iterator<String> itr= nodes.iterator();
		
		List<RootLeveBean> dibaBeanList= new ArrayList<RootLeveBean>();
		long i =0;
		while(itr.hasNext()) {
			g=new SimpleDirectedGraph<>(DefaultEdge.class);
			System.out.println("item nr:"+i++);
			String root=itr.next();
			
			//buildSimpleDirectedGraph(root,0);
			dibaBeanList.add(fillRootMaxLevel(this.g, root));
		}//wend
		System.out.println("nr elementi della lista: "+dibaBeanList.size());
		return dibaBeanList;
	}//eom
	
	public void printGraph() {
		System.out.println(getG());
	}

	public Graph<String, DefaultEdge> getG() {
		return g;
	}
   
    
}//eoc
