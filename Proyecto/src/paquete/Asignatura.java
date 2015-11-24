package paquete;

import java.util.ArrayList;
public class Asignatura {
	private int identificador;
	private String nombre;
	private String siglas;
	private String coordinador;
	private int curso;
	private ArrayList<Integer> requisitos = new ArrayList<Integer>();
	private ArrayList<Grupo> grupos = new ArrayList<Grupo>();
	
	public Asignatura(int identificador){
		this.identificador = identificador;
	}
	
	public Asignatura(int identificador, int idGrupo, char tipoGrupo){
		this.identificador = identificador;
		grupos.add(new Grupo(idGrupo, tipoGrupo));
	}
	
	public Asignatura(int identificador, String nombre, String siglas, int curso, String coordinador, String requisitos, String gruposA,String gruposB){
		this.identificador = identificador;
		this.nombre = nombre;
		this.siglas = siglas;
		this.curso = curso;
		this.coordinador = coordinador;
		if(requisitos.length()>0){
			String aux[] = requisitos	.trim()
										.split(",");
			for(int i=0; i<aux.length; i++){
				this.requisitos.add(Integer.parseInt(aux[i]));
			}
		}
		if(gruposA.length()>0){
			String aux[] = gruposA	.trim()
										.split(";");
			for(int i =0; i<aux.length; i++){
				String aux2[] = aux[i]	.trim()
										.split(" ");
				this.grupos.add(new Grupo(Integer.parseInt(aux2[0].trim()), 'A', aux2[1].trim().toCharArray()[0], aux2[2], aux2[3]));
			}
		}
		if(gruposB.length()>0){
			String aux[] = gruposB	.trim()
										.split(";");
			for(int i =0; i<aux.length; i++){
				String aux2[] = aux[i]	.trim()
										.split(" ");
				this.grupos.add(new Grupo(Integer.parseInt(aux2[0]), 'B', aux2[1].trim().toCharArray()[0], aux2[2], aux2[3]));
			}
		}
	}
	public void addGrupo(int idGrupo, char tipoGrupo){
		if(tipoGrupo=='A')
			grupos.add(new Grupo(idGrupo, tipoGrupo));
		else
			grupos.add(new Grupo(idGrupo, tipoGrupo));
	}
	
	public String salidaFichero(){
		String auxRequisitos = null;
		for(int i=0; i<requisitos.size(); i++){
			auxRequisitos += Integer.toString(requisitos.get(i));
			if((i+1)<requisitos.size())
				auxRequisitos += ", ";
		}
		String auxGruposA = null;
		for(int i=0; i<grupos.size(); i++){
			auxGruposA += Integer.toString(identificador)+grupos.get(i).salidaAsignaturas('A');
		}
		String auxGruposB = null;
		for(int i=0; i<grupos.size(); i++){
			auxGruposB += Integer.toString(identificador)+grupos.get(i).salidaAsignaturas('B');
		}
		return Integer.toString(identificador)+"\n"+nombre+"\n"+siglas+"\n"+Integer.toString(curso)+"\n"+coordinador+"\n"+auxRequisitos+"\n"
		+auxGruposA+"\n"+auxGruposB+"\n";
	}
	
	public String salidaPersona(){
		if(grupos.size()>0){
			String auxiliar = null;
			for (int i=0; i<grupos.size(); i++){
				auxiliar += Integer.toString(identificador)+grupos.get(i)+salidaPersona();
			}
			return auxiliar;
		}
		else 
			return null;
	}
	
	public String toString(){
		return Integer.toString(identificador)+"\n"+nombre+"\n"+siglas+"\n"+coordinador+"\n"+grupos;
	}
}
