package gestionediba.model;

public class DiBaBean {
	String padre;
	String filio;
	int level;
	
	public DiBaBean(String padre, String filio, int level) {
		super();
		this.padre = padre;
		this.filio = filio;
		this.level=level;
	}
	
	public DiBaBean() {
		super();
		this.padre = "";
		this.filio = "";
		this.level=0;
	}

	public String getPadre() {
		return padre;
	}

	public void setPadre(String padre) {
		this.padre = padre;
	}

	public String getFilio() {
		return filio;
	}

	public void setFilio(String filio) {
		this.filio = filio;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
	
	
}//eoc
