package paquete;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public class Gestion{

	static LinkedHashMap<String, Persona> mapaAlumnos = new LinkedHashMap<String, Persona>();
	static LinkedHashMap<String, Persona> mapaProfesores = new LinkedHashMap<String, Persona>();
	static LinkedHashMap<Integer, Asignatura> mapaAsignaturas = new LinkedHashMap<Integer, Asignatura>();
	
	public static void main(String[] args) throws IOException{
		Personas.poblar();
		Asignaturas.poblar();
		//Peronas.mostrarPantalla();
		File ejecucion = new File("ejecucion.txt");
		if(!ejecucion.exists()){
			System.out.println("La ejecucion termino");
		}
		else{
			Scanner entradaInstrucciones = new Scanner(new FileInputStream("ejecucion.txt"));
			while(entradaInstrucciones.hasNext()){
				String instruccion = entradaInstrucciones.nextLine();
				if(instruccion.charAt(0)=='*')
					continue;
				String[] auxiliar = instruccion.trim().split("\\s+");
				aviso("Comando: "+instruccion);
				switch(auxiliar[0]){
					case "InsertaPersona":
						if(auxiliar[1].trim().equals("alumno"))
							Personas.añadir(instruccion, mapaAlumnos);
						else
							Personas.añadir(instruccion, mapaProfesores);
						break;
					case "AsignaCoordinador":
						Asignaturas.asignarCoordinador(instruccion.trim().split("\\s+"));
						break;
					case "AsignaCargaDocente":
						Profesores.asignarCargaDocente(instruccion.trim().split("\\s+"));
						break;
					case "Matricula":
						Alumnos.matricular(instruccion.trim().split("\\s+"));
						break;
					case "AsignaGrupo":
						Alumnos.asignarGrupo(instruccion.trim().split("\\s+"));
						break;
					case "Evalua":
						Alumnos.evaluarAsignatura(instruccion.trim().split("\\s+"));
						break;
					case "Expediente":
						Alumnos.obtenerExpediente(instruccion.trim().split("\\s+"));
						break;
					case "ObtenerCalendarioClases":
						Profesores.obtenerCalendario(instruccion.trim().split("\\s+"));
						break;
					case "OrdenaAlumnosXNota":
						Alumnos.ordenarXNota(instruccion.trim().split("\\s+"));
						break;
					case "InsertaAsignatura":
						Asignaturas.añadir(instruccion.trim().split("\\s+"));
						break;
					default:
						aviso("Comando inexistente");
						break;
				}
				aviso("\n");
				
			}
			entradaInstrucciones.close();
		}
		//Personas.imprimir();
		//Asignaturas.imprimir();
	}
	
	public static void aviso(String aviso)throws IOException{
		File fichero =new File("avisos.txt");
		try{
		   	BufferedWriter salida = new BufferedWriter(new FileWriter(fichero, true));
		  	salida.write(aviso);
		  	salida.write(System.getProperty("line.separator"));
		    salida.close();
		 }catch(IOException ax){
		    ax.printStackTrace();
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
