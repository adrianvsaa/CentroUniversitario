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
	
	/**
	 * Constructor solo con el identificador
	 * @param identificador variable tipo int identificador de la asignatura
	 */
	
	public Asignatura(int identificador){
		this.identificador = identificador;
	}
	
	/**
	 * Constructor para crear una asignatura con un grupo
	 * @param identificador variable tipo int identificador de la asignatura
	 * @param idGrupo variable tipo int identificador del grupo
	 * @param tipoGrupo variable tipo char con el tipo del grupo
	 */ 
	
	public Asignatura(int identificador, int idGrupo, char tipoGrupo){
		this.identificador = identificador;
		grupos.add(new Grupo(idGrupo, tipoGrupo));
	}

	/**
	 * Constructor completo de asignatura
	 * @param identificador variale tipo int identificador de la asignatura
	 * @param nombre variable tipo String con el nombre de la asignatura
	 * @param siglas variable tipo String con las sigas de las asignatura
	 * @param curso variable tipo int con el curso de la asignatura
	 * @param coordinador variable tipo String con el identificador del profesor coordinador de la asignatura
	 * @param requisitos variable tipo String con los identificadores de las asignaturas requisito
	 * @param gruposA variable tipo String con los grupos de Teoria de la asignatura
	 * @param gruposB variable tipo String con los grupos de práctica de la asignatura
	 */

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
	
	/**
	 * Metodo que retorna el identificador
	 * @return identificador variable tipo int con el identificador de la asignatura
	 */
	
	public int getIdentificador(){
		return identificador;
	}
	
	/**
	 * Metodo que retorna las siglas
	 * @return siglas variable tipo String con las siglas de la asignatura
	 */
	
	public String getSiglas(){
		return siglas;
	}
	
	/**
	 * Metodo que retorna el coordinador
	 * @return coordinador variable tipo String con el dni del profesor coordinador
	 */
	
	public String getCoordinador(){
		return coordinador;
	}
	
	/**
	 * Metodo que retorna el grupo correspondiente al identificador y tipo indicado
	 * @param idGrupo variable tipo int con el identificador del grupo
	 * @param tipoGrupo variable tipo char con el tipo de grupo
	 * @return grupo objeto Grupo que se desea obtener
	 */
	
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
	
	/**
	 * Metodo que retorna la lista de grupos
	 * @return grupos coleccion de grupos de la asignatura
	 */
	
	public ArrayList<Grupo> getGrupos(){
		return grupos;
	}
	
	/**
	 * Metodo que retorna el nombre de la asignatura
	 * @return nombre varible tipo String con el nombre de la Asignatura
	 */
	
	public String getNombre(){
		return this.nombre;
	}
	
	/**
	 * Metodo que retorna el curso al que pertenece la asignatura 
	 * @return curso variable tipo int con el curso de la asignatura
	 */
	
	public int getCurso(){
		return this.curso;
	}
	
	/**
	 * Metodo que retorna la lista de requisitos para matricularse en la asignatura
	 * @return requisitos coleccion de los identificadores de las asignaturas requisito
	 */
	
	public ArrayList<Integer> getRequisitos(){
		return requisitos;
	}
	
	/**
	 * Metodo que modifica el coordinador de la asignatura
	 * @param coordinador variable tipo String con el identificador del nuevo coordinador
	 */
	
	public void setCoordinador(String coordinador){
		this.coordinador = coordinador;
	}
	
	/**
	 * Metodo que añade un grupo a la lista de grupos
	 * @param idGrupo variable tipo int con el identificador del nuevo grupo
	 * @param tipoGrupo variable tipo char con el tipo del nuevo grupo
	 */
	
	public void addGrupo(int idGrupo, char tipoGrupo){
		if(tipoGrupo=='A')
			grupos.add(new Grupo(idGrupo, tipoGrupo));
		else
			grupos.add(new Grupo(idGrupo, tipoGrupo));
	}
	
	/**
	 * Metodo que retorna el string para imprimirlo en el fichero personas
	 * @return string variable tipo String con la asignatura para imprimirla en las personas
	 */
	
	public String stringPersona(){
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
	
	/**
	 * Metodo que retorna el string para imprimirlo en el fichero de asignaturas 
	 * @return string variable tipo String con la asignatura para imprimirla
	 */
	
	public String stringFichero(){
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
	
	/**
	 * Metodo que comprueba en la asignatura si existe en la lista de grupos el grupo correspondiente al identificador y tipo correspondiente 
	 * @param idGrupo variable tipo int con el identificador de un grupo
	 * @param tipoGrupo variable tipo char con el tipo de un grupo
	 * @return boolean verdadero si el grupo existe, falso si no
	 */
	
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
	
	/**
	 * Metodo que comprueba si existe en la lista de grupos algun grupo correspondiente al tipo indicado
	 * @param tipoGrupo variable tipo char que contiene el tipo de un grupo
	 * @return boolean verdadero si existe un grupo de ese tipo, falso si no
	 */
	
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
