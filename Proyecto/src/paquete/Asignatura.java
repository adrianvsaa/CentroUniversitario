package paquete;

import java.util.ArrayList;
public class Asignatura {
	private int identificador;
	private String nombre;
	private String siglas;
	private String profesorCoordinador;
	private int curso;
	private ArrayList<Integer> preRequisitos;
	private ArrayList<Grupo> grupos = new ArrayList<Grupo>();
	
	public Asignatura(){
		
	}
	
	public Asignatura(int identificador, int idGrupo, char tipoGrupo){
		this.identificador = identificador;
		grupos.add(new Grupo(idGrupo, tipoGrupo));
	}
	
	public void addGrupo(int idGrupo, char tipoGrupo){
		grupos.add(new Grupo(idGrupo, tipoGrupo));
	}
}
