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
	
	public static void main(String[] args) throws IOException{
		Personas.poblar();
		Asignaturas.poblar();
		Personas.mostrarPantalla();
		File ejecucion = new File("ejecucion.txt");
		if(!ejecucion.exists()){
			
		}
		else{
			Calendar fechaActual = Calendar.getInstance();
			SimpleDateFormat formatoFecha = new SimpleDateFormat("HH:mm   dd/MM/YYYY");
			aviso("\n\nNueva Ejecucion:\t"+formatoFecha.format(fechaActual.getTime())+"\n");
			Scanner entradaInstrucciones = new Scanner(new FileInputStream("ejecucion.txt"));
			while(entradaInstrucciones.hasNext()){
				String instruccion = entradaInstrucciones.nextLine();
				if(instruccion.charAt(0)=='*')
					continue;
				String[] token = instruccion.trim().split("\\s+");
				switch(token[0]){
					case "InsertaPersona":
						aviso(insertaPersona);
						if(token[1].trim().equals("alumno"))
							Personas.añadir(instruccion, mapaAlumnos);
						else
							Personas.añadir(instruccion, mapaProfesores);
						break;
					case "AsignaCoordinador":
						aviso(asignaCoordinador);
						Asignaturas.asignarCoordinador(instruccion.trim().split("\\s+"));
						break;
					case "AsignaCargaDocente":
						aviso(asignaCargaDocente);
						Profesores.asignarCargaDocente(instruccion.trim().split("\\s+"));
						break;
					case "Matricula":
						aviso(matricularAlumno);
						Alumnos.matricular(instruccion.trim().split("\\s+"));
						break;
					case "AsignaGrupo":
						aviso(asignaGrupo);
						Alumnos.asignarGrupo(instruccion.trim().split("\\s+"));
						break;
					case "Evalua":
						aviso(evaluarAsignatura);
						Alumnos.evaluarAsignatura(instruccion.trim().split("\\s+"));
						break;
					case "Expediente":
						aviso(expedienteAlumno);
						Alumnos.obtenerExpediente(instruccion.trim().split("\\s+"));
						break;
					case "ObtenerCalendarioClases":
						aviso(calendarioProfesor);
						Profesores.obtenerCalendario(instruccion.trim().split("\\s+"));
						break;
					case "OrdenaAlumnosXNota":
						aviso(ordenaAlumnosNota);
						Alumnos.ordenarXNota(instruccion.trim().split("\\s+"));
						break;
					case "InsertaAsignatura":
						aviso(insertaAsignatura);
						Asignaturas.añadir(instruccion);
						break;
					default:
						aviso(cIncorrecto);
						break;
				}
				aviso("\n");
				
			}
			entradaInstrucciones.close();
		}
		Personas.imprimir();
		Asignaturas.imprimir();
	}
	
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
	
	public static Calendar stringToCalendar(String string){
		String[] auxiliar = string.trim().split("/");
		Calendar fecha = Calendar.getInstance();
		fecha.set(Integer.parseInt(auxiliar[2].trim()), Integer.parseInt(auxiliar[1]), Integer.parseInt(auxiliar[0]));
		return fecha;
		
	}
}
