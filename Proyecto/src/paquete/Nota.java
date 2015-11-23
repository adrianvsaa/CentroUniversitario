package paquete;

public class Nota {
	private float notaTeoria;
	private float notaPractica;
	private float nota;
	private String anoAcademico;
	
	Nota(float notaTeoria, float notaPractica, String anoAcademico){
		this.notaTeoria = notaTeoria;
		this.notaPractica = notaPractica;
		this.anoAcademico = anoAcademico;
		this.nota = notaTeoria+notaPractica;
	}
	
	Nota(float nota, String anoAcademico){
		this.nota = nota;
		this.anoAcademico = anoAcademico;
	}
	
	public float getNota(){
		return nota;
	}
	
	public String getAnoAcademico(){
		return anoAcademico;
	}
	
	public String toString(){
		return anoAcademico+" "+Float.toString(nota);
	}
	public void setNotaPractica(float notaPractica){
		this.notaPractica = notaPractica;
		this.nota = this.notaPractica + this.notaTeoria;
		return;
	}
	
	public void setNotaTeoria(float notaTeoria){
		this.notaTeoria = notaTeoria;
		this.nota = this.notaPractica + this.notaTeoria;
		return;
	}
}
