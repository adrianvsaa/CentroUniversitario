package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

public class Profesor extends Persona{
	private String categoria;
	private String departamento;
	private int horasAsignables;
	private LinkedHashMap<Integer, Asignatura> docenciaImpartida = new LinkedHashMap<Integer, Asignatura>();
	
	/**
	 * Constructor con sin docencia impartida
	 * @param dni variable tipo String
	 * @param nombre variable tipo String
	 * @param apellidos variable tipo String
	 * @param fechaNacimiento variable tipo Calendar
	 * @param categoria variable tipo String
	 * @param departamento variable tipo String
	 * @param horasAsignables variable tipo int
	 */
	
	public Profesor(String dni, String nombre, String apellidos, Calendar fechaNacimiento, String categoria, String departamento, int horasAsignables){
		super(dni, nombre, apellidos, fechaNacimiento);
		this.categoria = categoria;
		this.departamento = departamento;
		this.horasAsignables = horasAsignables;
	}
	
	/**
	 * Constructor completo
	 * @param dni variable tipo String
	 * @param nombre variable tipo String
	 * @param apellidos variable tipo String
	 * @param fechaNacimiento variable tipo Calendar
	 * @param categoria variable tipo String
	 * @param departamento variable tipo String
	 * @param horasAsignables variable tipo int
	 * @param docenciaImpartida variable tipo String
	 */
	
	public Profesor(String dni, String nombre, String apellidos, Calendar fechaNacimiento, String categoria, String departamento, int horasAsignables, 
			String docenciaImpartida) {
		super(dni, nombre, apellidos, fechaNacimiento);
		this.categoria = categoria;
		this.departamento = departamento;
		this.horasAsignables = horasAsignables;
		if(docenciaImpartida.length()>0){
			String aux[] = docenciaImpartida.split(";");
			for(int i=0; i<aux.length; i++) {
				String aux2[] = aux[i].trim().split(" ");
				if(this.docenciaImpartida.get(Integer.parseInt(aux2[0]))!=null){
					this.docenciaImpartida.get(Integer.parseInt(aux2[0])).addGrupo(Integer.parseInt(aux2[2]), aux2[1].toCharArray()[0]);
				}
				else {
					this.docenciaImpartida.put(Integer.parseInt(aux2[0]), new Asignatura(Integer.parseInt(aux2[0]), Integer.parseInt(aux2[2]),
							aux2[1].toCharArray()[0]));
				}
			}
		}
		
	}
	
	/**
	 * Metodo que añade a la asignatura correspondiente al identificador recibido un grupo
	 * @param identificador de una asignatura
	 * @param identificadorGrupo del grupo al que se imparte clase
	 * @param tipoGrupo del grupo al que se imparte clase
	 */
	
	public void addDocencia(int identificador, int identificadorGrupo, char tipoGrupo){
		if(docenciaImpartida.get(identificador)!=null){
			docenciaImpartida.get(identificador).addGrupo(identificadorGrupo, tipoGrupo);
		}
		else
			docenciaImpartida.put(identificador, new Asignatura(identificador, identificadorGrupo, tipoGrupo));
		return;
	}
	
	/**
	 * Metodo que retorna la categoria del profesor
	 * @return categoria del profesor
	 */
	
	public String getCategoria(){
		return categoria;
	}
	
	/**
	 * Metodo que retorna el numero de horas asignables de un profesor
	 * @return horasAsigables del profesor
	 */
	
	public int getHorasAsignables(){
		return horasAsignables;
	}
	
	/**
	 * Metodo que retorna el departamento correspondiente al profesor
	 * @return departamento variable tipo String
	 */
	
	public String getDepartamento(){
		return departamento;
	}
	
	/**
	 * Metodo que retorna la docencia impartida por el profesor
	 * @return docenciaImpartida variable tipo Map
	 */
	
	public LinkedHashMap<Integer, Asignatura> getDocenciaImpartida(){
		return docenciaImpartida;
	}
	
	/**
	 * Metodo que retorna el numero de horas asignadas que tiene un profesor
	 * @param mapaAsignaturas coleccion de asignaturas
	 * @return horasImpartidas variable tipo int con el numero de horas que imparte el profesor
	 */
	
	public int getHorasImpartidas(LinkedHashMap<Integer, Asignatura> mapaAsignaturas){
		int retorno=0;
		Set<Integer> keys = docenciaImpartida.keySet();
		for(int key:keys){
			ArrayList<Grupo> gruposProfesor = docenciaImpartida.get(key).getGrupos();
			ArrayList<Grupo> grupos = mapaAsignaturas.get(key).getGrupos();
			for(int i = 0; i<gruposProfesor.size(); i++){
				for(int j=0; j<grupos.size(); j++){
					if(grupos.get(j).getIdentificador()==gruposProfesor.get(i).getIdentificador()&&gruposProfesor.get(i).getTipo()==grupos.get(j).getTipo()){
						retorno += grupos.get(j).getDuracion();
					}
				}
			}
		}
		return retorno;
	}
	
	/**
	 * Metodo que escribe en el fichero recibido el horario correspondiente al profesor
	 * @param fichero nombre del fichero en donde se imprimira el calendario
	 * @param mapaAsignaturas coleccion de aignaturas
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
	public void obtenerCalendario(String fichero, LinkedHashMap<Integer, Asignatura> mapaAsignaturas) throws IOException{
		ArrayList<Grupo> aux = new ArrayList<Grupo>();
		File archivo = new File(fichero);
		BufferedWriter salida = new BufferedWriter(new FileWriter(archivo));
		salida.write("Dia\t Hora\t Asignatura\t Tipo grupo\t Id grupo\n");
		Set<Integer> keys = docenciaImpartida.keySet();
		for(int key:keys){
			ArrayList<Grupo> gruposProfesor = docenciaImpartida.get(key).getGrupos();
			ArrayList<Grupo> grupos = mapaAsignaturas.get(key).getGrupos();
			for(int i = 0; i<gruposProfesor.size(); i++){
				for(int j=0; j<grupos.size(); j++){
					if(grupos.get(j).getIdentificador()==gruposProfesor.get(i).getIdentificador()&&gruposProfesor.get(i).getTipo()==grupos.get(j).getTipo()){
						aux.add(grupos.get(j));
						//salida.write(grupos.get(j).getDia()+"; "+grupos.get(j).getHoraEntrada()+" ;"+mapaAsignaturas.get(key).getSiglas()+"; "+
					//grupos.get(j).getTipo()+"; "+grupos.get(j).getIdentificador()+"\n");
					}
				}
			}
			Collections.sort(aux, new ComparadorHora());
			Collections.sort(aux);
			for(int j=0; j<aux.size(); j++)
				salida.write(aux.get(j).getDia()+"; "+aux.get(j).getHoraEntrada()+" ;"+mapaAsignaturas.get(key).getSiglas()+"; "+
						aux.get(j).getTipo()+"; "+aux.get(j).getIdentificador()+"\n");
		}
		salida.close();
	}
	
	/**
	 * Metodo que retorna el string que se imprime en el fichero personas correspondiente a los profesores
	 * @return string para fichero personas
	 */
	
	public String salidaFichero(){
		String auxiliarDocencia = "";
		Set<Integer> keys = docenciaImpartida.keySet();
		boolean ponercoma = false;
		for(int key:keys){
			if(ponercoma)
				auxiliarDocencia += "; ";
			auxiliarDocencia += docenciaImpartida.get(key).stringPersona();
			ponercoma = true;
		}
		return super.toString()+"\n"+categoria+"\n"+departamento+"\n"+horasAsignables+"\n"+auxiliarDocencia;
	}
	
	/**
	 * Metodo que comprueba si el profesor imparte el grupo correspondiente de la asignatura indicada en los parametros que se le envian al metodo
	 * @param identificador de la asignatura
	 * @param idGrupo variable tipo int 
 	 * @param tipoGrupo variable tipo char
	 * @return boolean verdadero si no imparte, falso si imparte
	 */
	
	public boolean comprobarAsignacion(int identificador, int idGrupo, char tipoGrupo){
		if(docenciaImpartida.get(identificador)==null)
			return true;
		if(docenciaImpartida.get(identificador).comprobarGrupo(idGrupo, tipoGrupo))
			return false;
		return true;
	}
	
	/**
	 * Metodo que comprueba si las horas a las que se pretende meter una asignacion el profesor tiene hora libre
	 * @param horaEntrada del grupo que se quiere asignar
	 * @param horaSalida del grupo que se quiere asignar
	 * @param dia del grupo que de quiere asignar
	 * @return boolean verdadero si el horario esta libre, falso si no
	 */
	
	public boolean comprobarHorario(int horaEntrada, int horaSalida, char dia){
		Set<Integer> keys = docenciaImpartida.keySet();
		return GestionErrores.comprobarHorario(keys, horaEntrada, horaSalida, dia, this);
	}
	
	/**
	 * Metodo que retorna si la docenciaImpartida esta vacia
	 * @return boolean verdadero si la docencia esta vacia falso si no
	 */
	
	public boolean comprobarDocenciaVacia(){
		if(docenciaImpartida.size()==0)
			return true;
		else
			return false;
	}
}
