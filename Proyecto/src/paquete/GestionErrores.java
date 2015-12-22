package paquete;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.IOException;
import java.io.FileInputStream;

public class GestionErrores {
	
	public static boolean comprobarExpediente(LinkedHashMap<Integer, Nota> asignaturasSuperadas){
		if(asignaturasSuperadas.size()==0)
			return false;
		else
			return true;
	}
	
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
	
	public static boolean comprobarFechaIngreso(Calendar fechaIngreso, Calendar fecha){
		int edadMaxima = 65, edadMinima = 15;
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
	
	public static boolean comprobarTitularidad(Profesor profesor){
		if(profesor.getCategoria().equals("titular"))
			return true;
		return false;
	}
	
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
	
	public static boolean comprobarTipoGrupo(String tipo){
		if(tipo.equals("A")||tipo.equals("B"))
			return true;
		return false;
	}
	
	public static boolean comprobarExistenciaGrupo(Asignatura a, int idGrupo, char tipoGrupo){
		if(a.comprobarGrupo(idGrupo, tipoGrupo))
			return true;
		return false;
	}
	
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
	
	public static boolean comprobarNumeroHoras(Profesor p, LinkedHashMap<Integer, Asignatura> mapaAsignaturas, int duracion){
		if((p.getHorasImpartidas(mapaAsignaturas)+duracion)<=p.getHorasAsignables())
			return true;
		else
			return false;
		
	}
	
	public static boolean comprobarSolapeProfesor(Asignatura a, Profesor p, int idGrupo, char tipoGrupo, LinkedHashMap<Integer, Asignatura> mapaAsignaturas){
		if(p.comprobarHorario(a.getGrupo(idGrupo, tipoGrupo).getHoraEntrada(), a.getGrupo(idGrupo, tipoGrupo).getHoraSalida(),
				a.getGrupo(idGrupo, tipoGrupo).getDia(), mapaAsignaturas))
			return true;
		else
			return false;
	}
	
	public static boolean comprobarDocencia(Profesor p){
		if(p.comprobarDocenciaVacia())
			return false;
		else
			return true;
	}
	
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
	
	public static boolean comprobarFicheroNotas(LinkedHashMap<String, Persona> mapaAlumnos, String fichero, int idAsignatura, String anoAcademico)
			throws IOException{
		boolean retorno = true;
		int i = 0;
		Scanner entrada = new Scanner(new FileInputStream(fichero));
		while(entrada.hasNextLine()){
			i++;
			String[] auxiliar = entrada.nextLine().trim().split("\\s+");
			if(mapaAlumnos.get(auxiliar[0])==null){
				Gestion.aviso("Error en línea <"+i+">: Alumno inexistente: <"+auxiliar[0]+">");
				retorno = false;
				continue;
			}
			if(!((Alumno)mapaAlumnos.get(auxiliar[0])).comprobarMatricula(idAsignatura)){
				Gestion.aviso("Error en línea <"+i+">: Alumno no matriculado: <"+auxiliar[0]+">");
				retorno = false;
				continue;
			}
			try{
				if(Float.parseFloat(auxiliar[1])<0||Float.parseFloat(auxiliar[1])>5){
					Gestion.aviso("Error en línea <"+i+">: Nota grupo A/B incorrecta");
					continue;
				}
				if(Float.parseFloat(auxiliar[2])<0||Float.parseFloat(auxiliar[2])>5){
					Gestion.aviso("Error en línea <"+i+">: Nota grupo A/B incorrecta");
					continue;
				}
			} catch(Exception notas){
				Gestion.aviso("Error en línea <"+i+">: Nota grupo A/B incorrecta");
				continue;
			}
			Gestion.aviso("Linea <"+i+">: OK");
			((Alumno)mapaAlumnos.get(auxiliar[0])).evaluarAsignatura(idAsignatura, new Nota(Float.parseFloat(auxiliar[1]), 
					Float.parseFloat(auxiliar[2]), anoAcademico));
		}
		entrada.close();
		return retorno;
	}
	
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
	
	public static boolean comprobarSolapeAlumno(Alumno a, Asignatura asig, LinkedHashMap<Integer, Asignatura> mapaAsignaturas, char tipoGrupo, 
			int idGrupo){
		if(a.comprobarHorario(asig.getGrupo(idGrupo, tipoGrupo).getHoraEntrada(), asig.getGrupo(idGrupo, tipoGrupo).getHoraSalida(), 
				asig.getGrupo(idGrupo, tipoGrupo).getDia(),	mapaAsignaturas))
			return true;
		else
			return false;
	}
}
