package paquete;

import java.util.Comparator;

public class Nota implements Comparable<Nota>{
	private float notaTeoria;
	private float notaPractica;
	private float nota;
	private String anoAcademico;
	private String nombreAsignatura;
	private int cursoAsignatura;
	
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
	
	public int compareTo(Nota n){
		if(cursoAsignatura<n.getCursoAsignatura())
			return -1;
		if(cursoAsignatura>n.getCursoAsignatura())
			return 1;
		return 0;
	}
	
	public void setNombreAsignatura(String nombre){
		nombreAsignatura = nombre;
	}
	
	public void setCursoAsignatura(int curso){
		cursoAsignatura = curso;
	}
	
	public String getNombreAsignatura(){
		return nombreAsignatura;
	}
	
	public int getCursoAsignatura(){
		return cursoAsignatura;
	}
}

class ComparadorNombre implements Comparator<Nota>{
	public int compare(Nota n1, Nota n2){
		return n1.getNombreAsignatura().compareTo(n2.getNombreAsignatura());
	}
}
