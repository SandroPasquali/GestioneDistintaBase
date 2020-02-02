package db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */






import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gestionediba.model.DiBaBean;


/**
 *
 * @author pasquali
 */
public class DiBaDAO {

    
    
	
	
    public List<DiBaBean> recursiveBOMExplosion(String parent,List<DiBaBean> list) {
        
       
       //utilizzo di sql ricorsivo di mysqla per determinare la discendenza di un elemento padre in distinta base
       //inoltre utilizzo la idMap
       
        
        String sql="with recursive cte (padre,filio,lvl) as\r\n" + 
        		"(\r\n" + 
        		" select     padre,\r\n" + 
        		"            filio, 1 lev\r\n" + 
        		" from       anle200f\r\n" + 
        		" where      padre = ?\r\n" + 
        		" union all\r\n" + 
        		" select     p.padre,\r\n" + 
        		"            p.filio, lvl+1\r\n" + 
        		" from       anle200f p\r\n" + 
        		" inner join cte\r\n" + 
        		"         on p.padre = cte.filio\r\n" + 
        		")\r\n" + 
        		"select * from cte;";
        		
        		
        
        //Connection conn = DBConnect.getConnectionByAS400DriverManager();
        //Connection conn = DBConnect.getConnectionByAS400PooledConnection();
        //Connection conn = DBConnect.getConnectionByMySQLDriverManager();
        Connection conn = DBConnect.getConnectionByMySQLPool();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, parent);
            
            //List<DiBaBean> children = new ArrayList<DiBaBean>();

            ResultSet res = pst.executeQuery();
            
            if (res.isBeforeFirst()) {
                // ci sono elementi
                while (res.next()) {
                	list.add(new DiBaBean(res.getString("padre"),res.getString("filio"),res.getInt("lvl")));
            	}//wend
            } else {
                //no nci sono figli
                //System.out.println("non ci sono figli x : " + parent);
            }
            res.close();
            pst.close();
            conn.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }//eom
 
	
	
	/*
    public List<DiBaBean> recursiveBOMExplosion(String parent,Map<String,DiBaBean> idMap) {
        
       
       //utilizzo di sql ricorsivo di mysqla per determinare la discendenza di un elemento padre in distinta base
       //inoltre utilizzo la idMap
       
        
        String sql="with recursive cte (padre,filio,lvl) as\r\n" + 
        		"(\r\n" + 
        		" select     padre,\r\n" + 
        		"            filio, 1 lev\r\n" + 
        		" from       anle200f\r\n" + 
        		" where      padre = ?\r\n" + 
        		" union all\r\n" + 
        		" select     p.padre,\r\n" + 
        		"            p.filio, lvl+1\r\n" + 
        		" from       anle200f p\r\n" + 
        		" inner join cte\r\n" + 
        		"         on p.padre = cte.filio\r\n" + 
        		")\r\n" + 
        		"select * from cte;";
        		
        		
        
        //Connection conn = DBConnect.getConnectionByAS400DriverManager();
        //Connection conn = DBConnect.getConnectionByAS400PooledConnection();
        //Connection conn = DBConnect.getConnectionByMySQLDriverManager();
        Connection conn = DBConnect.getConnectionByMySQLPool();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, parent);
            
            List<DiBaBean> children = new ArrayList<DiBaBean>();

            ResultSet res = pst.executeQuery();
            
            if (res.isBeforeFirst()) {
                // ci sono elementi
                while (res.next()) {
                	if(idMap.get(res.getString("padre"))==null) {
                		DiBaBean bean = new DiBaBean(res.getString("padre"),res.getString("filio"),res.getInt("lvl"));
                		idMap.put(bean.getPadre(), bean);
                	}else {
                		children.add(idMap.get(res.getString("padre")));
                	}
                }//wend
            } else {
                //no nci sono figli
                //System.out.println("non ci sono figli x : " + parent);
            }
            res.close();
            pst.close();
            conn.close();
            return children;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }//eom
    */
	
public List<DiBaBean> recursiveBOMImplosion(String parent) {
        
        /*
            utilizzo di sql ricorsivo di mysqla per determinare la discendenza di un elemento padre in distinta base
        */
        
        String sql="with recursive cte (filio, padre,lvl) as\r\n" + 
        		"(\r\n" + 
        		" select     \r\n" + 
        		"            filio, padre, 1 lev\r\n" + 
        		" from       anle200f\r\n" + 
        		" where      filio = ?\r\n" + 
        		" union all\r\n" + 
        		" select     p.filio,\r\n" + 
        		"            p.padre, lvl+1\r\n" + 
        		" from       anle200f p\r\n" + 
        		" inner join cte\r\n" + 
        		"         on p.filio = cte.padre\r\n" + 
        		")\r\n" + 
        		"select * from cte;";
        
        //Connection conn = DBConnect.getConnectionByAS400DriverManager();
        //Connection conn = DBConnect.getConnectionByAS400PooledConnection();
        //Connection conn = DBConnect.getConnectionByMySQLDriverManager();
        Connection conn = DBConnect.getConnectionByMySQLPool();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, parent);
            
            List<DiBaBean> children = new ArrayList<DiBaBean>();

            ResultSet res = pst.executeQuery();
            
            if (res.isBeforeFirst()) {
                // ci sono elementi
                while (res.next()) {
                  DiBaBean bean = new DiBaBean(res.getString("padre"),res.getString("filio"),res.getInt("lvl"));
                  children.add(bean);
                }//wend
            } else {
                //no nci sono figli
                //System.out.println("non ci sono figli x : " + parent);
            }
            res.close();
            pst.close();
            conn.close();
            return children;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }//eom
	
    public List<String> searchChildrenOf(String parent) {
        
        /*
            Procedura Ricorsiva per determinare la discendenza di un elemento padre in distinta base
        */
        
        
        String sql = "SELECT padre,filio FROM test.anle200f WHERE padre=? order by padre, filio";

        //Connection conn = DBConnect.getConnectionByAS400DriverManager();
        //Connection conn = DBConnect.getConnectionByAS400PooledConnection();
        //Connection conn = DBConnect.getConnectionByMySQLDriverManager();
        Connection conn = DBConnect.getConnectionByMySQLPool();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, parent);
            
            List<String> children = new ArrayList<String>();

            ResultSet res = pst.executeQuery();
            
            if (res.isBeforeFirst()) {
                // ci sono elementi
                while (res.next()) {
                    //String child = res.getString("filio");
                    //System.out.println("trovato diglio di "+parent +"=: "+ child);
                    //children.add(child);
                    
                    children.add(res.getString("filio"));
                }//wend
            } else {
                //no nci sono figli
                //System.out.println("non ci sono figli x : " + parent);
            }
            res.close();
            pst.close();
            conn.close();
            return children;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }

    }//eom
    
    public List<String> searchParentOf(String child) {
        
        /*
            Procedura Ricorsiva per l'implosione di un elemento figlio al padre in distinta base
        */
        
        
        String sql = "SELECT filio,padre FROM test.anle200f WHERE filio=? order by filio,padre";
        //String sql = "SELECT filio,padre FROM pollive.anle201l WHERE filio=? order by filio,padre";

        //Connection conn = DBConnect.getConnectionByAS400DriverManager();
        //Connection conn = DBConnect.getConnectionByAS400PooledConnection();
        //Connection conn = DBConnect.getAS400JDBCConnectionPoolDataSource();
        
        //Connection conn = DBConnect.getConnectionByMySQLDriverManager();
        Connection conn = DBConnect.getConnectionByMySQLPool();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, child);
            
            List<String> children = new ArrayList<String>();

            ResultSet res = pst.executeQuery();
            
            if (res.isBeforeFirst()) {
                // ci sono elementi
                while (res.next()) {
                    //String child = res.getString("filio");
                    //System.out.println("trovato diglio di "+parent +"=: "+ child);
                    //children.add(child);
                    
                    children.add(res.getString("padre"));
                }//wend
            } else {
                //no nci sono figli
                //System.out.println("non ci sono figli x : " + parent);
            }
            res.close();
            pst.close();
            conn.close();
            return children;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }

    }//eom
    
    public List<String> getAllParents() {
        /*
            SCOPO: recuperare tutti i nodi padre della diba
        */
        String sql = "SELECT tipo_parte, padre \r\n" + 
        		"FROM \r\n" + 
        		"	articoli art \r\n" + 
        		"INNER JOIN \r\n" + 
        		"	anle200f diba ON (art.CDPAR=diba.PADRE)\r\n" + 
        		"WHERE art.TIPO_PARTE NOT IN ('3-MP')\r\n" + 
        		"GROUP BY tipo_parte, padre\r\n" + 
        		"ORDER BY tipo_parte, padre";
 
        //Connection conn = DBConnect.getConnectionByAS400DriverManager();
        //Connection conn = DBConnect.getConnectionByAS400PooledConnection();
        //Connection conn = DBConnect.getAS400JDBCConnectionPoolDataSource();
        //Connection conn = DBConnect.getConnectionByMySQLDriverManager();
        Connection conn = DBConnect.getConnectionByMySQLPool();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            //pst.setString(1, child);
            List<String> parents = new ArrayList<String>();
            ResultSet res = pst.executeQuery();
            if (res.isBeforeFirst()) {
                // ci sono elementi
                while (res.next()) {
                    parents.add(res.getString("padre"));
                }//wend
            } else {
                //no nci sono figli
                //System.out.println("non ci sono figli x : " + parent);
            }
            res.close();
            pst.close();
            conn.close();
            return parents;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }//eom

    
    
    
    
    
    
    
    
    
    /**
    public List<String> getParents(String child) {
        
       
        
        
        String sql = "SELECT * FROM test.anle200f WHERE filio=? order by filio, padre";

        //Connection conn = DBConnect.getConnectionByAS400DriverManager();
        //Connection conn = DBConnect.getConnectionByAS400PooledConnection();
        //Connection conn = DBConnect.getConnectionByMySQLDriverManager();
        Connection conn = DBConnect.getConnectionByMySQLPool();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, child);
            
            ObservableList<String> children = FXCollections.observableArrayList();

            ResultSet res = pst.executeQuery();
            
            if (res.isBeforeFirst()) {
                // ci sono elementi
                while (res.next()) {
                    //String child = res.getString("filio");
                    //System.out.println("trovato diglio di "+parent +"=: "+ child);
                    //children.add(child);
                    
                    children.add(res.getString("padre"));
                }//wend
            } else {
                //no nci sono figli
                //System.out.println("non ci sono figli x : " + parent);
            }
            res.close();
            pst.close();
            conn.close();
            return children;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }

    }//eom
    */



    public Map<String, List<String>> searchByClasseMerceologicaName(String clamer){
        /*
            Obiettivo:cerco tutti i componenti della diba per la clamer indicata
            output: map<clamer,lista<filio>>
        */
        
        //String classeMerceologica;
        List<String> listaComponenti= new ArrayList();
        Map<String,List<String>> mapElementiXClaMer = new HashMap();
        
        
        
        
        String sql ="SELECT "
                + "artFilio.classe_merc,filio "
                + "FROM "
                + "gruppo.articoli artPadre INNER JOIN anle201l anle "
                + "ON "
                + "(artPadre.cdpar=padre) INNER JOIN gruppo.articoli artFilio "
                + "ON "
                + "(filio=artFilio.cdpar)"
                + "WHERE "
                + "artFilio.classe_merc IN (?)"
                + " AND artFilio.tipo_parte=? "
                + "GROUP BY artFilio.classe_merc,filio "
                + "order by artFilio.classe_merc,filio";

        //Connection conn = DBConnect.getConnectionByAS400DriverManager();
        //Connection conn = DBConnect.getConnectionByAS400PooledConnection();
        //Connection conn = DBConnect.getAS400JDBCConnectionPoolDataSource();
        
        //Connection conn = DBConnect.getConnectionByMySQLDriverManager();
        Connection conn = DBConnect.getConnectionByMySQLPool();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, clamer);
            pst.setString(2, "3-MP");
            
            //List<String> children = FXCollections.observableArrayList();

            ResultSet res = pst.executeQuery();
            
            if (res.isBeforeFirst()) {
                // ci sono elementi
                while (res.next()) {
                    //String child = res.getString("filio");
                    //System.out.println("trovato diglio di "+parent +"=: "+ child);
                    listaComponenti.add(res.getString("filio").trim());
                }//wend
            } //if
            mapElementiXClaMer.put(clamer, listaComponenti);
            
            res.close();
            pst.close();
            conn.close();
            return mapElementiXClaMer;
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }//eom

    public List<String> getAllMpClaMer() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        //String classeMerceologica;
        List<String> listaClaMer = new ArrayList();

        String sql = "SELECT "
                + "artFilio.classe_merc "
                + "FROM "
                + "gruppo.articoli artPadre INNER JOIN anle201l anle "
                + "ON "
                + "(artPadre.cdpar=padre) INNER JOIN gruppo.articoli artFilio "
                + "ON "
                + "(filio=artFilio.cdpar) "
                + "WHERE "
                + " artFilio.tipo_parte=?"
                + " AND "
                + " artFilio.classe_merc IN ('43-Zucchine','01-aceto')"
                + "GROUP BY artFilio.classe_merc "
                + "order by artFilio.classe_merc ";

        //Connection conn = DBConnect.getConnectionByAS400DriverManager();
        //Connection conn = DBConnect.getConnectionByAS400PooledConnection();
        //Connection conn = DBConnect.getAS400JDBCConnectionPoolDataSource();

        //Connection conn = DBConnect.getConnectionByMySQLDriverManager();
        Connection conn = DBConnect.getConnectionByMySQLPool();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            //pst.setString(1, clamer);
            pst.setString(1, "3-MP");

            ResultSet res = pst.executeQuery();

            if (res.isBeforeFirst()) {
                // ci sono elementi
                while (res.next()) {
                    //String child = res.getString("filio");
                    //System.out.println("trovato diglio di "+parent +"=: "+ child);
                    listaClaMer.add(res.getString("classe_merc").trim());
                }//wend
            } //if

            res.close();
            pst.close();
            conn.close();
            return listaClaMer;
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }//eom

    public Map<String, List<String>> tbd() {
        DiBaDAO dao = new DiBaDAO();

        List<String> listClaMer = dao.getAllMpClaMer();

        Map<String, List<String>> map = new HashMap();
        String clamer = null;

        for (Iterator<String> it = listClaMer.iterator(); it.hasNext();) {
            clamer = it.next();
            Map<String, List<String>> tmp = dao.searchByClasseMerceologicaName(clamer);
            if (tmp.size() > 0) {
                map.putAll(tmp);
            }//if            
        }//for
        return map;
    }//
    
    
    
    
}//eoc
