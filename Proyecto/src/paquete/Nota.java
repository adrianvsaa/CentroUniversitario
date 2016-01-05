package paquete;

import java.util.Comparator;

public class Nota implements Comparable<Nota>{
	private float notaTeoria;
	private float notaPractica;
	private float nota;
	private String anoAcademico;
	private String nombreAsignatura;
	private int cursoAsignatura;
	
	/**
	 * Constructor con nota teoria nota practica y ano academico
	 * @param notaTeoria variable tipo float nota de teoria 
	 * @param notaPractica variable tipo float nota de practica
	 * @param anoAcademico variable tipo String con el ano academico
	 */
	
	Nota(float notaTeoria, float notaPractica, String anoAcademico){
		this.notaTeoria = notaTeoria;
		this.notaPractica = notaPractica;
		this.anoAcademico = anoAcademico;
		this.nota = notaTeoria+notaPractica;
	}
	
	/**
	 * Constrructor con nota y año academico
	 * @param nota variable tipo float con nota
	 * @param anoAcademico variable tipo Sting con el ano academico
	 */
	
	Nota(float nota, String anoAcademico){
		this.nota = nota;
		this.anoAcademico = anoAcademico;
	}
	
	/**
	 * Metodo que retorna la nota
	 * @return nota 
	 */
	
	public float getNota(){
		return nota;
	}
	
	/**
	 * Metodo que retorna el ano academico
	 * @return anoAcademico
	*/
	
	public String getAnoAcademico(){
		return anoAcademico;
	}
	
	/**
	 * Metodo que retorna el nombre de la asignatura a la que pertenece la nota
	 * @return nombreAsignatura
	 */
	
	public String getNombreAsignatura(){
		return nombreAsignatura;
	}
	
	/**
	 * Metodo que retorna el curso de la asignatura a la que pertenece la nota
	 * @return cursoAsignatura variable tipo int con el curso academico de la asignatura
	 */
	
	public int getCursoAsignatura(){
		return cursoAsignatura;
	}
	
	/**
	 * Metodo que modifica la nota de practica
	 * @param notaPractica variable tipo float con la nota de practica de la asignatura
	 */
	
	public void setNotaPractica(float notaPractica){
		this.notaPractica = notaPractica;
		this.nota = this.notaPractica + this.notaTeoria;
		return;
	}
	
	/**
	 * Metodo que modifica la nota de teoria
	 * @param notaTeoria variable tipo float con la nota de teoria de la asignatura
	 */
	
	public void setNotaTeoria(float notaTeoria){
		this.notaTeoria = notaTeoria;
		this.nota = this.notaPractica + this.notaTeoria;
		return;
	}
	
	/**
	 * Metodo que modifica el nombre de la asignatura
	 * @param nombre variable tipo String con el nombre de la asignatura
	 */
	
	public void setNombreAsignatura(String nombre){
		nombreAsignatura = nombre;
	}
	
	/**
	 * Metodo que modifica el curso de la asignatura
	 * @param curso variable tipo int con el curso academico de la asignatura
	 */
	
	public void setCursoAsignatura(int curso){
		cursoAsignatura = curso;
	}
	
	/**
	 * Metodo que retorna el string correspondiente a una Nota
	 */
	
	public String toString(){
		return anoAcademico+" "+Float.toString(nota);
	}
	
	/**
	 * Metodo que compara por el parametro nota
	 */ 
	
	public int compareTo(Nota n){
		if(cursoAsignatura<n.getCursoAsignatura())
			return -1;
		if(cursoAsignatura>n.getCursoAsignatura())
			return 1;
		return 0;
	}
	
}

class ComparadorNombre implements Comparator<Nota>{
	/**
	 * Metodo que compara por el nombre de la asignatura
	 */
	public int compare(Nota n1, Nota n2){
		return n1.getNombreAsignatura().compareTo(n2.getNombreAsignatura());
	}
}
