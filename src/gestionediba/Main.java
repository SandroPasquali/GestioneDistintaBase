package gestionediba;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import db.DiBaDAO;
import excel.WriteExcel;

import gestionediba.model.DiBaBean;
import gestionediba.model.Model;
import gestionediba.model.RootLeveBean;
import jxl.write.Font;

public class Main {
	
	private static DiBaDAO dao=null;
	private static Graph<String, DefaultEdge> graph = null;
	private static Model model;
	
	public static void main(String[] args) throws IOException {
		
		
		//testMultilevelExplosion();
		testMultileveImplosion();
		
		//g=model.getG();
		//model.printGraph();
		//model.doMultilevelExplosion(root,2);
		
	
		//model.printGraph();
		//model.doMultilevelImplosion(cmp,99);
		
		//g=buildSimpleDirectedGraph(root);
		//doBestFirstView(g,root);
		//doSimpleDirectedGraphStatistic(g);
		
		//fillRootMaxLevel(g,"pf1");
		
		//List<RootLeveBean> l=dibaScan();
		//print(l);
		
		//testMultileveImplosion();
		//testWriteExcel();
		
	}//main

	
	private static void testMultilevelExplosion() {
		//BOMManager bm = new BOMManager();
		//String root="770453"; // esempio di PF a 6 livelli
		//String root="011013"; //esempio di PF
		//String root="3100200"; 
		//String root="mp1"; 
		String root="pf1"; 
		model = new Model();
		model.doMultilevelExplosion(root, 99);
		model.printGraph();
	}//eom


	private static void testMultileveImplosion() {
		//BOMManager bm = new BOMManager();
		//String root="770453"; // esempio di PF a 6 livelli
		//String root="011013"; //esempio di PF
		//String root="3100200"; 
		String root="mp1"; 
		//String root="pf1"; 
		
		model = new Model();
		model.doMultilevelImplosion(root, 99);
		model.printGraph();
		
	}





	
	
	
	

	private static void testWriteExcel() throws IOException {
		String fileOut="test2.xlsx";
		String sheetName="MyTest";
		WriteExcel we=new WriteExcel(fileOut,sheetName);
		we.createHeaderFont();
	}

	
	private void pippo() {
		
	}
	
}






