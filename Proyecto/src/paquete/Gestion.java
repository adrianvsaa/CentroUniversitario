package paquete;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public class Gestion implements Constantes{

	static LinkedHashMap<String, Persona> mapaAlumnos = new LinkedHashMap<String, Persona>();
	static LinkedHashMap<String, Persona> mapaProfesores = new LinkedHashMap<String, Persona>();
	static LinkedHashMap<Integer, Asignatura> mapaAsignaturas = new LinkedHashMap<Integer, Asignatura>();
	
	/**
	 * Metodo que gestiona el resto de la aplicacion
	 * @param args parametros que se pasan por linea de comandos
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
	public static void main(String[] args) throws IOException{
		Personas.poblar();
		Asignaturas.poblar();
		File ejecucion = new File("ejecucion.txt");
		if(!ejecucion.exists()){
			
		}
		else{
			Calendar fechaActual = Calendar.getInstance();
			SimpleDateFormat formatoFecha = new SimpleDateFormat("HH:mm   dd/MM/YYYY");
			File avisos = new File("avisos.txt");
			if(avisos.exists())
				aviso("\n\nNueva Ejecucion:\t"+formatoFecha.format(fechaActual.getTime())+"\n");
			else
				aviso("Nueva Ejecucion:\t"+formatoFecha.format(fechaActual.getTime())+"\n");
			Scanner entradaInstrucciones = new Scanner(new FileInputStream("ejecucion.txt"));
			while(entradaInstrucciones.hasNext()){
				try {
					String instruccion = entradaInstrucciones.nextLine();
					if(instruccion.charAt(0)=='*')
						continue;
					String[] token = instruccion.trim().split("\\s+");
					switch(token[0].toLowerCase()){
						case "insertapersona":
							aviso(insertaPersona);
							if(token[1].trim().equals("alumno"))
								Personas.anadir(instruccion, mapaAlumnos);
							else
								Personas.anadir(instruccion, mapaProfesores);
							break;
						case "asignacordinador":
							aviso(asignaCoordinador);
							Asignaturas.asignarCoordinador(instruccion.trim().split("\\s+"));
							break;
						case "asignacargadocente":
							aviso(asignaCargaDocente);
							Profesores.asignarCargaDocente(instruccion.trim().split("\\s+"));
							break;
						case "matricula":
							aviso(matricularAlumno);
							Alumnos.matricular(instruccion.trim().split("\\s+"));
							break;
						case "asignagrupo":
							aviso(asignaGrupo);
							Alumnos.asignarGrupo(instruccion.trim().split("\\s+"));
							break;
						case "evalua":
							aviso(evaluarAsignatura);
							Alumnos.evaluarAsignatura(instruccion.trim().split("\\s+"));
							break;
						case "expediente":
							aviso(expedienteAlumno);
							Alumnos.obtenerExpediente(instruccion.trim().split("\\s+"));
							break;
						case "obtenercalendarioclases":
							aviso(calendarioProfesor);
							Profesores.obtenerCalendario(instruccion.trim().split("\\s+"));
							break;
						case "ordenaalumnosxnota":
							aviso(ordenaAlumnosNota);
							Alumnos.ordenarXNota(instruccion.trim().split("\\s+"));
							break;
						case "insertaasignatura":
							aviso(insertaAsignatura);
							Asignaturas.anadir(instruccion);
							break;
						default:
							aviso(cIncorrecto);
							break;
					}
					aviso("\n");
				} catch(Exception a){
					
				}
				
			}
			entradaInstrucciones.close();
		}
		//Personas.imprimir();
		//Asignaturas.imprimir();
		Personas.mostrarPantalla();
	}
	
	/**
	 * Metodo que escribe el parametro recibido aviso en el fichero avisos
	 * @param aviso variable tipo String que se imprime en el fichero avisos
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
	public static void aviso(String aviso)throws IOException{
		File fichero =new File("avisos.txt");
		BufferedWriter salida=null;
		try{
		   	salida = new BufferedWriter(new FileWriter(fichero, true));
		  	salida.write(aviso);
		 }catch(IOException ax){
		    ax.printStackTrace();
		 } finally{
			 salida.close();
		 }
		return;
	}
	
	/**
	 * Metodo que recibe una variable tipo String en formato dd/MM/aaaa y retorna ese string pasandolo a tipo calendar
	 * @param string variable tipo String que se quiere convertir a calendar
	 * @return Calendar variable tipo Calendar resultado de convertir el String
	 */
	
	public static Calendar stringToCalendar(String string){
		String[] auxiliar = string.trim().split("/");
		Calendar fecha = Calendar.getInstance();
		fecha.set(Integer.parseInt(auxiliar[2].trim()), Integer.parseInt(auxiliar[1]), Integer.parseInt(auxiliar[0]));
		return fecha;
		
	}
}
