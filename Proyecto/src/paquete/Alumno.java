package paquete;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Alumno extends Persona implements Comparable<Alumno>{
	private Calendar fechaIngreso = Calendar.getInstance();
	private LinkedHashMap<Integer, Asignatura> docenciaRecibida = new LinkedHashMap<Integer, Asignatura>();
	private LinkedHashMap<Integer, Nota> asignaturasSuperadas = new LinkedHashMap<Integer, Nota>();
	
	/**
	 * Constructor de la clase Alumno para alumnos sin docenciarecibida ni asignaturas superadas
	 * @param dni identificador del alumno
	 * @param nombre variable tipo String con el nombre del alumno
	 * @param apellidos variable tipo String con los apellidos del alumno
	 * @param fechaNacimiento variable tipo Calendar con la fecha de Nacimiento del alumno
	 * @param fechaIngreso variable tipo Calendar con la fecha de ingreso del alumno
	 */
	
	public Alumno(String dni, String nombre, String apellidos, Calendar fechaNacimiento, Calendar fechaIngreso){
		super(dni, nombre, apellidos, fechaNacimiento);
		this.fechaIngreso = fechaIngreso;
	}
	
	/**
	 * Constructor completo de alumno
	 * @param dni identificador del alumno
	 * @param nombre variable tipo String con el nombre del alumno
	 * @param apellidos variable tipo String con los apellidos del alumno
	 * @param fechaNacimiento variable tipo Calendar con la fecha de Nacimiento del alumno
	 * @param fechaIngreso variable tipo Calendar con la fecha de ingreso del alumno
	 * @param docenciaRecibida variable tipo String que contiene las asignaturas que cursa el alumno
	 * @param asignaturasSuperadas variable tipo String que contiene las asignaturas aprobadas por el alumno
	 */
	
	public Alumno(String dni, String nombre, String apellidos, Calendar fechaNacimiento, Calendar fechaIngreso, String docenciaRecibida,
			String asignaturasSuperadas) {
		super(dni, nombre, apellidos, fechaNacimiento);
		this.fechaIngreso = fechaIngreso;
		if(asignaturasSuperadas.trim().length()!=0){
			String aux[] = asignaturasSuperadas.trim().split(";");
			for(int i= 0; i<aux.length; i++){
				String[] aux2 = aux[i].trim().split(" ");
				Nota n = new Nota(Float.parseFloat(aux2[2]), aux2[1].trim());
				this.asignaturasSuperadas.put(Integer.parseInt(aux2[0].trim()), n);
			}
		}
		if(docenciaRecibida.length()!=0){
			String aux[] = docenciaRecibida.split(";");
			for(int i=0; i<aux.length; i++) {
				String aux2[] = aux[i].trim().split(" ");
				if(aux2.length==1){
					this.docenciaRecibida.put(Integer.parseInt(aux2[0]), new Asignatura(Integer.parseInt(aux2[0])));
				}
				else {
					if(this.docenciaRecibida.get(Integer.parseInt(aux2[0]))!=null){
						this.docenciaRecibida.get(Integer.parseInt(aux2[0])).addGrupo(Integer.parseInt(aux2[2]), aux2[1].toCharArray()[0]);
					}
					else {
						this.docenciaRecibida.put(Integer.parseInt(aux2[0]), new Asignatura(Integer.parseInt(aux2[0]), Integer.parseInt(aux2[2]),
								aux2[1].toCharArray()[0]));
					}
				}
			}
		}
	}
	
	/**
	 * Metodo que retorna la fecha de ingreso
	 * @return fechaIngreso variable tipo Calendar con la fecha de ingreso del alumno
	 */
	
	public String getFechaIngreso(){
		SimpleDateFormat aux = new SimpleDateFormat("dd/MM/YYYY");
		return aux.format(fechaIngreso.getTime());
	}
	
	/**
	 * Metodo que retorna el mapa de asignaturas superadas
	 * @return asignaturasSuperadas variable tipo Map que contiene las asignaturas aprobadas por el alumno
	 */
	
	public LinkedHashMap<Integer, Nota> getAsignaturasSuperadas(){
		return asignaturasSuperadas;
	}
	
	/**
	 * Metodo que retorna em mapa de docenciaRecibida
	 * @return docenciaRecibida variable tipo Map que contiene las asignaturas cursadas por el alumno
	 */
	
	public LinkedHashMap<Integer, Asignatura> getDocenciaRecibida(){
		return this.docenciaRecibida;
	}
	
	/**
	 * Metodo que añade a la docencia recibida una asignatura
	 * @param asignatura variable tipo Asignatura en la que se quiere matricular el alumno
	 */
	
	public void matricula(Asignatura asignatura){
		docenciaRecibida.put(asignatura.getIdentificador(), asignatura);
		return;
	}
	
	/**
	 * Metodo que añade un grupo a una asignatura de la docencia recibida
	 * @param asignatura variable tipo Asignatura que cursa el alumno
	 * @param tipoGrupo variable tipo char que corresponde con el tipo de grupo en el que se quiere anotas
	 * @param idGrupo variable tipo integer que corresponde con el identificador del grupo en el que se quiere anotar
	 */
	
	public void asignarGrupo(Asignatura asignatura, char tipoGrupo, int idGrupo){
		docenciaRecibida.get(asignatura.getIdentificador()).addGrupo(idGrupo, tipoGrupo);
		return;
	}
	
	/**
	 * Metodo que si una asignatura esta aprobada añade la asignatura a las asignaturas superadas y la quita de la docencia recibida
	 * en caso de estar suspensa solo lo borra de la docencia recibida
	 * @param idAsignatura identificador de la asignatura que se quiere evaluar
	 * @param nota variable de la clase Nota que contiene la nota de la asignatura evaluada
	 */
	
	public void evaluarAsignatura(int idAsignatura, Nota nota){
		if(nota.getNota()>=5){
			asignaturasSuperadas.put(idAsignatura, nota);
			docenciaRecibida.remove(idAsignatura);
		}
		else
			docenciaRecibida.remove(idAsignatura);
		return;
	}
	
	/**
	 * Metodo que calcula la nota media del expediente del alumno
	 * @return notaMedia variable tipo float que contiene la nota media del expediente del alumno
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
	public float calcularNotaExpediente() throws IOException{
		if(!GestionErrores.comprobarExpediente(asignaturasSuperadas)){
			return -1;
		}
		Set<Integer> keys = asignaturasSuperadas.keySet();
		int i=0;
		float aux = 0;
		for(Integer key:keys){
			aux += asignaturasSuperadas.get(key).getNota();
			i++;
		}
		return aux/i;
	}
	
	/**
	 * Metodo que imprime en un fichero el expediente del alumno
	 * @param fichero nombre del fichero en el que hay que imprimir el expediente
	 * @param mapaAsignaturas coleccion de asignaturas
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
	public void expediente(String fichero, LinkedHashMap<Integer, Asignatura> mapaAsignaturas)throws IOException{
		File archivo = new File(fichero);
		BufferedWriter salida = new BufferedWriter(new FileWriter(archivo));
		Set<Integer> keys = asignaturasSuperadas.keySet();
		for(int key:keys){
			asignaturasSuperadas.get(key).setNombreAsignatura(mapaAsignaturas.get(key).getNombre());
			asignaturasSuperadas.get(key).setCursoAsignatura(mapaAsignaturas.get(key).getCurso());
		}
		List<Nota> lista = new LinkedList<Nota>(asignaturasSuperadas.values());
		Collections.sort(lista, new ComparadorNombre());
		Collections.sort(lista);
		for (int i = 0; i < lista.size(); i++) {
			salida.write(lista.get(i).getCursoAsignatura()+" "+lista.get(i).getNombreAsignatura()+" "+ lista.get(i).getNota()+" "
					+lista.get(i).getAnoAcademico()+"\n");
		}
		salida.write("Nota Media:\t"+ this.calcularNotaExpediente());
		salida.close();
	}
	
	/**
	 * Metodo que devuelve el string correspondiente a la hora de obtener el expediente del alumno
	 * @return string variable tipo String que devuelve el expediente en forma de String
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
	public String stringExpediente() throws IOException{
		if(calcularNotaExpediente()>=0)
			return getApellidos().trim()+",\t"+ getNombre()+"\t"+getDNI()+"\t"+calcularNotaExpediente(); 
		else
			return getApellidos().trim()+",\t"+ getNombre()+"\t"+getDNI()+"\tExpediente Vacio";
		}
	
	/**
	 * Metodo que devuelve el string correspondiente al alumno que se imprimira en la clase persona 
	 * @return string variable tipo String con los parametros del alumno en formato String
	 */
	
	public String stringFichero(){
		SimpleDateFormat aux = new SimpleDateFormat("dd/MM/YYYY");
		String auxiliarSuperadas = "";
		Set<Integer> keys = asignaturasSuperadas.keySet();
		boolean ponercoma = false;
		for(int key:keys){
			if(ponercoma)
				auxiliarSuperadas += "; ";
			auxiliarSuperadas += key+" "+asignaturasSuperadas.get(key).toString();
			ponercoma =true;
		}
		ponercoma = false;
		String auxiliarDocencia = "";
		keys = docenciaRecibida.keySet();
		for(int key:keys){
			if(ponercoma)
				auxiliarDocencia += "; ";
			auxiliarDocencia += docenciaRecibida.get(key).stringPersona();
			ponercoma = true;
		}
		return super.toString()+"\n"+aux.format(fechaIngreso.getTime())+"\n"+auxiliarSuperadas+"\n"+auxiliarDocencia;
	}
	
	/**
	 * Metodo que comprueba si una asignatura ha sido superada
	 * @param anoAcademico ano en el que se quiere evaluar una asignatura
	 * @param idAsignatura identificador de una asignatura 
	 * @return boolean verdadero si la asignatura no ha sido evaluada, falso si ha sido evaluado 
	 */

	
	public boolean comprobarEvaluacion(String anoAcademico, int idAsignatura){
		if(asignaturasSuperadas.get(idAsignatura)==null)
			return true;
		if(asignaturasSuperadas.get(idAsignatura).getAnoAcademico().trim().equals(anoAcademico.trim()))
			return false;
		return true;
	}
	
	/**
	 * Metodo que comprueba si una asignatura es cursada por el alumno
	 * @param idAsignatura identificador de una asignatura
	 * @return boolean verdadero si la asignatura es cursada, falso si no
	 */
	
	public boolean comprobarMatricula(int idAsignatura){
		if(docenciaRecibida.get(idAsignatura)==null)
			return false;
		else
			return true;
	}
	
	/**
	 * Metodo que comprueba si una asignatura esta aprobada
	 * @param idAsignatura identificador de la asignatura
	 * @return boolean  verdadero si la asignatura ha sido superada, falso si no
	 */
	
	public boolean comprobarAprobado(int idAsignatura){
		if(asignaturasSuperadas.get(idAsignatura)!=null)
			return true;
		return false;
			
	}
	
	/**
	 * Metodo que comprueba si el alumno tiene ocupado el espacio de tiempo entre la hora de salida y la hora de entrada 
	 * lo que generaria solape
	 * @param horaEntrada hora de entrada de un grupo que se quiera asignar
	 * @param horaSalida hora de salida de un grupo que se quiera asignar
	 * @param dia dia de un grupo que se quiera asignar
	 * @param mapaAsignaturas colección se asignaturas
	 * @return boolean verdadero si tiene esas horas libres, falso si no
	 */
	
	public boolean comprobarHorario(int horaEntrada, int horaSalida, char dia, LinkedHashMap<Integer, Asignatura> mapaAsignaturas){
		Set<Integer> keys = docenciaRecibida.keySet();
		return GestionErrores.comprobarHorario(keys, horaEntrada, horaSalida, dia, this);
	}
	
	/**
	 * Metodo que compara un alumno por apellidos
	 */
	
	public int compareTo(Alumno a) {
		return getApellidos().compareTo(a.getApellidos());
	}
	
}


class ComparadorNota implements Comparator<Alumno>{
	
	/**
	 * Metodo que compara alumnos por la nota de expediente
	 */
	
	public int compare(Alumno a1, Alumno a2){
		try{
		if(a1.calcularNotaExpediente()<a2.calcularNotaExpediente())
			return 1;
		else 
			return -1;
		} catch(Exception e){
			return 1;
		}
	}
}
