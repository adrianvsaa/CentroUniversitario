package paquete;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

public class GestionErrores implements Constantes{
	
	/**
	 * Metodo que compruba si el expediente de un alumno esta vacio
	 * @param asignaturasSuperadas coleccion de asignaturas superadas de un alumno
	 * @return boolean falsi si la lista esta vacio verdadero si no
	 */
	
	public static boolean comprobarExpediente(LinkedHashMap<Integer, Nota> asignaturasSuperadas){
		if(asignaturasSuperadas.size()==0)
			return false;
		else
			return true;
	}
	
	/**
	 * Metodo que comprueba si el formato del dni recibido es el correcto
	 * @param dni variable tipo String
	 * @return boolean falso si el String es incorrecto, verdadeto si no
	 */
	
	public static boolean comprobarDNI(String dni){
		if(dni.length()!=9)
			return false;
		if(dni.charAt(8)<'A'||dni.charAt(8)>'Z')
			return false;
		try{
			String aux="";
			for(int i=0; i<dni.length()-2; i++)
				aux += dni.charAt(i);
			Integer.parseInt(aux);
		} catch(Exception d){
			return false;
		}
		return true;
	}
	
	/**
	 * Metodo que comprueba si la fecha recibida cumple los parametros especificados en la especificacion
	 * @param fecha variable tipo Calendar
	 * @return boolean falso si la fecha no es correcta, verdadero si lo es
	 */
	
	public static boolean comprobarFecha(Calendar fecha){
		Calendar fechaMinima = Calendar.getInstance();
		fechaMinima.set(1950, 1 , 1);
		Calendar fechaMaxima = Calendar.getInstance();
		fechaMaxima.set(2020, 1, 1);
		try{
			fecha.setLenient(false);
			fecha.getTime();
		} catch(Exception time){
			return false;
		}
		if((fecha.getTimeInMillis()-fechaMinima.getTimeInMillis())<0)
			return false;
		if((fechaMaxima.getTimeInMillis()-fecha.getTimeInMillis())<0)
			return false;
		return true;
	}
	
	/**
	 * Metodo que comprueba si la fecha de ingreso cumple los parametros de la especificacion
	 * @param fechaIngreso variable tipo Calendar
	 * @param fecha variable tipo Calendar
	 * @return boolean falso si la fecha de ingreso es incorrecta, verdadero si es correcta
	 */
	
	public static boolean comprobarFechaIngreso(Calendar fechaIngreso, Calendar fecha){
		int edadMaxima = 65, edadMinima = 15;
		if(!comprobarFecha(fechaIngreso))
			return false;
		try{
			fechaIngreso.setLenient(false);
			fechaIngreso.getTime();
		}catch(Exception time2){
			return false;
		}
		double aux = (fechaIngreso.getTimeInMillis()/1000/60/60/24-fecha.getTimeInMillis()/1000/60/60/24)/365;
		if(aux<edadMinima||aux>edadMaxima)
			return false;
		return true;
	}
	
	/**
	 * Metodo que comprueba si las horas asignables cumple los requisitos de la especificacion
	 * @param horas numero de horas que puede trabajar el profesor
	 * @param tipoProfesor tipo de profesor titular o asociado
	 * @return boolean falso si el numero de horas es incorrecto, verdadero si es correcto
	 */
	
	public static boolean comprobarHorasAsignables(int horas, String tipoProfesor){
		if(horas<0)
			return false;
		if(tipoProfesor.equals("titular")){
			if(horas>20)
				return false;
		}
		else
			if(horas>15)
				return false;
		return true;
	}
	
	/**
	 * Metodo que comprueba si un profesor es titular
	 * @param profesor objeto de la clase profesor
	 * @return boolean verdadero si el profesor es titular, falso si no
	 */
	
	public static boolean comprobarTitularidad(Profesor profesor){
		if(profesor.getCategoria().equals("titular"))
			return true;
		return false;
	}
	
	/**
	 * Metodo que comprueba si un alumno tiene en la asignatura correspontiente al identificador un grupo de ese tipo
	 * @param idAsignatura variable tipo int identificador de la asignatura
	 * @param tipoGrupo variable tipo char correspondiente a un tipo de grupo A o B
	 * @param a objeto de tipo alumno del que se quiere comprobar el grupo
	 * @return boolean falso si no tiene un grupo de ese tipo, verdadero si lo tiene
	 */
	
	public static boolean comprobarGrupo(int idAsignatura, char tipoGrupo, Alumno a){
		LinkedHashMap<Integer, Asignatura> docenciaRecibida = a.getDocenciaRecibida();
		if(docenciaRecibida.get(idAsignatura)==null)
			return false;
		if(docenciaRecibida.get(idAsignatura).comprobarGrupoTipo(tipoGrupo))
			return true;
		return false;
	}
	
	/**
	 * Metodo que comprueba si el profesor con el dni correspondiente a la variable coordinador es coordinador de 2 asignaturas
	 * @param coordinador variable tipo String con el dni del profesor
	 * @param mapaAsignaturas coleccion de asignaturas
	 * @return boolean verdadero si el profesor no es coordinador de 2 asignaturas, falso si lo es
	 */
	
	public static boolean comprobarCoordinadorDos(String coordinador, LinkedHashMap<Integer, Asignatura> mapaAsignaturas){
		boolean retorno=true;
		Set<Integer> keys = mapaAsignaturas.keySet();
		int veces = 0;
		for(int key:keys){
			if(mapaAsignaturas.get(key).getCoordinador().equals(coordinador)){
				veces ++;
			}
			if(veces==2){
				retorno = false;
				break;
			}
		}
		return retorno;
	}
	
	/**
	 * Metodo que comprueba si el tipo del grupo es correcto
	 * @param tipoGrupo variable tipo char que se quiere comprobar si es correcta
	 * @return  boolean verdadero si el tipoGrupo es correcto, falso si no lo es
	 */
	
	public static boolean comprobarTipoGrupo(char tipoGrupo){
		if((tipoGrupo == 'A') ||(tipoGrupo == 'B'))
			return true;
		return false;
	}
	
	/**
	 * Metodo que comprueba si el grupo correspondiente al identificador idGrupo y tipo tipoGrupo existe en la asignatura indicada
	 * @param a  objeto de la clase asignatura en la que se comprueba si existe el grupo
	 * @param idGrupo identificador del grupo que se quiere comprobar
	 * @param tipoGrupo tipo del grupo que se quiere comprobar
	 * @return boolean verdadero si existe el grupo, falso si no
	 */
	
	public static boolean comprobarExistenciaGrupo(Asignatura a, int idGrupo, char tipoGrupo){
		if(a.comprobarGrupo(idGrupo, tipoGrupo))
			return true;
		return false;
	}
	
	/**
	 * Metodo que comprueba si un grupo ya esta asignado a algun profesor
	 * @param mapaProfesores coleccion de profesores
	 * @param identificador variable tipo int con el identificador de una asignatura
	 * @param idGrupo variable tipo int con el identificador de un grupo
	 * @param tipoGrupo variable tipo char con el tipo de un grupo
	 * @return boolean verdadero si el grupo no esta asignado, falso si lo esta
	 */
	
	public static boolean comprobarAsignacionGrupo(LinkedHashMap<String, Persona> mapaProfesores, int identificador, int idGrupo, char tipoGrupo){
		boolean retorno = true;
		Set<String> keys = mapaProfesores.keySet();
		for(String key:keys){
			if(!((Profesor)mapaProfesores.get(key)).comprobarAsignacion(identificador, idGrupo, tipoGrupo)){
				retorno = false;
				break;
			}
		}
		return retorno;
	}
	
	/**
	 * Metodo que comprueba si se asigna un grupo el profesor cumple sus requisitos de horas maximas asignables
	 * @param p objeto de la clase profesor
	 * @param mapaAsignaturas coleccion de asignaturas
	 * @param duracion variable tipo int con la duracion de un grupo
	 * @return boolean verdadero si cumple los requisitos, falso si no
	 */
	
	public static boolean comprobarNumeroHoras(Profesor p, LinkedHashMap<Integer, Asignatura> mapaAsignaturas, int duracion){
		if((p.getHorasImpartidas(mapaAsignaturas)+duracion)<=p.getHorasAsignables())
			return true;
		else
			return false;
		
	}
	
	/**
	 * Metodo que comprueba si se le asigna un grupo a un profesor genera solape
	 * @param a asignatura a la que pertenece un grupo
	 * @param p profesor al que se comprueba si genera solape
	 * @param idGrupo identificador del grupo
	 * @param tipoGrupo tipo del grupo
	 * @param mapaAsignaturas coleccion de asignaturas
	 * @return boolean veradero si genera solape, falso si no
	 */
	
	public static boolean comprobarSolapeProfesor(Asignatura a, Profesor p, int idGrupo, char tipoGrupo, LinkedHashMap<Integer, Asignatura> mapaAsignaturas){
		if(p.comprobarHorario(a.getGrupo(idGrupo, tipoGrupo).getHoraEntrada(), a.getGrupo(idGrupo, tipoGrupo).getHoraSalida(),
				a.getGrupo(idGrupo, tipoGrupo).getDia()))
			return true;
		else
			return false;
	}
	
	/**
	 * Metodo que comprueba si la docencia de un profesor esta vacia
	 * @param p objeto de la clase profesor del que se comprueba si la docencia esta vacio
	 * @return boolean falso si la docencia esta vacio, verdadero si no lo esta
	 */
	
	public static boolean comprobarDocencia(Profesor p){
		if(p.comprobarDocenciaVacia())
			return false;
		else
			return true;
	}
	
	/**
	 * Metodo que comprueba si al evaluar una asignatura esa asignatura ya fue evaluada en ese curso academico
	 * @param mapaAlumnos coleccion de alumnos
	 * @param cursoAcademico variable tipo string con el ano de evaluacion que se quiere comprobar
	 * @param idAsignatura variable tipo int identificador de la asignatura
	 * @return boolean verdadero si la asigatura no esta evaluado, falso si lo esta
	 */
	
	public static boolean comprobarEvaluacionAlumno(LinkedHashMap<String, Persona> mapaAlumnos, String cursoAcademico, int idAsignatura){
		boolean retorno = true;
		Set<String> keys = mapaAlumnos.keySet();
		for(String key: keys){
			if(!((Alumno)mapaAlumnos.get(key)).comprobarEvaluacion(cursoAcademico, idAsignatura)){
				retorno = false;
				break;
			}
		}
		return retorno;
	}
	
	/**
	 * Metodo que comprueba el fichero correspondiente a la funcion evaluar asignatura y en caso de los datos del fichero son
	 * correctos llama al metodo evaluarASignatura de la clase alumno
	 * @param fichero string con el nombre del fichero en el que estan las notas
	 * @param idAsignatura identificar de la asignatura
	 * @param anoAcademico String con el ano en el que se evalua la asignatura
	 * @return boolean verdadero 
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
	public static boolean comprobarFicheroNotas(File fichero, int idAsignatura, String anoAcademico)
			throws IOException{
		int i = 0;
		Scanner entrada = new Scanner(new FileInputStream(fichero));
		while(entrada.hasNextLine()){
			i++;
			String[] auxiliar = entrada.nextLine().trim().split("\\s+");
			if(Gestion.mapaAlumnos.get(auxiliar[0])==null){
				Gestion.aviso(fNotasLineas+i+">: Alumno inexistente: <"+auxiliar[0]+">");
				continue;
			}
			if(!((Alumno)Gestion.mapaAlumnos.get(auxiliar[0])).comprobarMatricula(idAsignatura)){
				Gestion.aviso(fNotasLineas+i+">: Alumno no matriculado: <"+auxiliar[0]+">");
				continue;
			}
			try{
				if(Float.parseFloat(auxiliar[1])<0||Float.parseFloat(auxiliar[1])>5){
					Gestion.aviso(fNotasLineas+i+">: Nota grupo A/B incorrecta");
					continue;
				}
				if(Float.parseFloat(auxiliar[2])<0||Float.parseFloat(auxiliar[2])>5){
					Gestion.aviso(fNotasLineas+i+">: Nota grupo A/B incorrecta");
					continue;
				}
			} catch(Exception notas){
				Gestion.aviso(fNotasLineas+i+">: Nota grupo A/B incorrecta");
				continue;
			}
			Gestion.aviso(fNotasLineas+i+">: OK");
			((Alumno)Gestion.mapaAlumnos.get(auxiliar[0])).evaluarAsignatura(idAsignatura, new Nota(Float.parseFloat(auxiliar[1]), 
					Float.parseFloat(auxiliar[2]), anoAcademico));
		}
		entrada.close();
		return true;
	}
	
	/**
	 * Metodo que comprueba si un alumno al matricularse en una asignatura cumple sus requisitos
	 * @param alumno objeto de la clase alumno del que se comprueba si cumple los requisitos
	 * @param asignatura objeto de la clase alumno en el que se miran los requisitos
	 * @return boolean verdadero si cumple los requisitos, falso si no los cumple
	 */
	public static boolean comprobarRequisitos(Alumno alumno, Asignatura asignatura){
		boolean retorno=true;
		for(int i=0; i<asignatura.getRequisitos().size(); i++){
			if(!alumno.comprobarAprobado(asignatura.getRequisitos().get(i))){
				retorno = false;
				break;
			}
		}
		return retorno;
	}
	
	/**
	 * Metodo que comprueba si al asignarle un grupo a un alumno el horario de ese grupo genera solape en su horario de clases
	 * @param a objeto de la clase alumno 
	 * @param asig objeto de la clase asignatura
	 * @param tipoGrupo variable tipo char
	 * @param idGrupo variable tipo int
	 * @return boolean verdadero si no genera solape, falso si lo genera
	 */
	
	public static boolean comprobarSolapeAlumno(Alumno a, Asignatura asig, char tipoGrupo, int idGrupo){
		if(a.comprobarHorario(asig.getGrupo(idGrupo, tipoGrupo).getHoraEntrada(), asig.getGrupo(idGrupo, tipoGrupo).getHoraSalida(), 
				asig.getGrupo(idGrupo, tipoGrupo).getDia(),	Gestion.mapaAsignaturas))
			return true;
		else
			return false;
	}

	/**
	 * Metodo que comprueba si unas horas generan solape en el horario de una persona
	 * @param keys claves del mapa asignatura
	 * @param horaEntrada variable tipo int con la hora de entrada
	 * @param horaSalida variable tipo int con la hora de salida
	 * @param dia variable tipo char con el dia del grupo
	 * @param p objeto tipo persona
	 * @return boolean verdadero si el horario es valido falso si no lo es
	 */
	
	public static boolean comprobarHorario(Set<Integer> keys, int horaEntrada, int horaSalida, char dia, Persona p){
		boolean opcion = true;
		LinkedHashMap<Integer, Asignatura> mapa;
		if(p instanceof Alumno)
			mapa = ((Alumno)p).getDocenciaRecibida();
		else
			mapa = ((Profesor)p).getDocenciaImpartida();
		for(int key:keys){
			ArrayList<Grupo> grupos = Gestion.mapaAsignaturas.get(key).getGrupos();
			ArrayList<Grupo> gruposPersona = mapa.get(key).getGrupos();
			for(int i=0; i<gruposPersona.size(); i++){
				for(int j=0; j<grupos.size(); j++){
					if(gruposPersona.get(i).getIdentificador()==grupos.get(j).getIdentificador()&&gruposPersona.get(i).getTipo()==
							grupos.get(j).getTipo()){
						if(grupos.get(j).getDia()!=dia)
							continue;
						else{
							if(grupos.get(j).getHoraEntrada()==horaEntrada||horaEntrada-grupos.get(j).getHoraSalida()<0){
								opcion = false;
								break;
							}
						}
					}
				}
			}
			if(!opcion)
				break;		
		}
		return opcion;
	}
}
