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
		String auxRequisitos = "";
		for(int i=0; i<requisitos.size(); i++){
			if(i>0)
				auxRequisitos += ", "+Integer.toString(requisitos.get(i));
			else
				auxRequisitos = Integer.toString(requisitos.get(i));
		}
		String auxGruposA = "";
		boolean ponercoma = false;
		for(int i=0; i<grupos.size(); i++){
			if(grupos.get(i).getTipo()!='A')
				continue;
			if(ponercoma)
				auxGruposA += "; "+grupos.get(i).salidaAsignaturas('A');
			else {
				auxGruposA = grupos.get(i).salidaAsignaturas('A');
				ponercoma = true;
			}
		}
		String auxGruposB = "";
		ponercoma = false;
		for(int i=0; i<grupos.size(); i++){
			if(grupos.get(i).getTipo()!='B')
				continue;
			if(ponercoma)
				auxGruposB += "; "+grupos.get(i).salidaAsignaturas('B');
			else {
				auxGruposB = grupos.get(i).salidaAsignaturas('B');
				ponercoma =true;
			}
		}
		return Integer.toString(identificador)+"\n"+nombre+"\n"+siglas+"\n"+Integer.toString(curso)+"\n"+coordinador+"\n"+auxRequisitos+"\n"
		+auxGruposA+"\n"+auxGruposB;
	}
	
	public String salidaPersona(){
		if(grupos.size()>0){
			String auxiliar = "";
			boolean ponercoma = false;
			for (int i=0; i<grupos.size(); i++){
				if(ponercoma)
					auxiliar+= "; ";
				auxiliar += Integer.toString(identificador)+" "+grupos.get(i).salidaPersona();
				ponercoma = true;
			}
			return auxiliar;
		}
		else 
			return Integer.toString(identificador);
	}
	
	public String toString(){
		return grupos.toString();
	}
	
	public int getIdentificador(){
		return identificador;
	}
	
	public void setCoordinador(String coordinador){
		this.coordinador = coordinador;
	}
	
	public String getSiglas(){
		return siglas;
	}
	
	public String getCoordinador(){
		return coordinador;
	}
	
	public boolean comprobarGrupo(int idGrupo, char tipoGrupo){
		boolean comprobar= false;
		for(int i=0; i<grupos.size(); i++){
			if(grupos.get(i).getIdentificador()==idGrupo&&grupos.get(i).getTipo()==tipoGrupo){
				comprobar = true;
				break;
			}
		}
		return comprobar;
	}
	
	public Grupo getGrupo(int idGrupo, char tipoGrupo){
		int i;
		for(i=0; i<grupos.size(); i++){
			if(idGrupo==grupos.get(i).getIdentificador()&&tipoGrupo==grupos.get(i).getTipo())
				break;
		}
		if(idGrupo==grupos.get(i).getIdentificador()&&tipoGrupo==grupos.get(i).getTipo())
			return grupos.get(i);
		else 
			return null;
	}
	
	public ArrayList<Grupo> getGrupos(){
		return grupos;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public int getCurso(){
		return this.curso;
	}
	
	public ArrayList<Integer> getRequisitos(){
		return requisitos;
	}
	
	public boolean comprobarGrupoTipo(char tipoGrupo){
		boolean retorno = false;
		for(int j=0; j<grupos.size(); j++){
			if(grupos.get(j).getTipo()==tipoGrupo){
				retorno = true;
				break;
			}
		}
		return retorno;
	}
	
}
