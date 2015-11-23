package paquete;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.io.FileInputStream;

public class Gestion {

	static LinkedHashMap<String, Alumno> mapaAlumnos = new LinkedHashMap<String, Alumno>();
	static LinkedHashMap<String, Profesor> mapaProfesores = new LinkedHashMap<String, Profesor>();
	static LinkedHashMap<Integer, Asignatura> mapaAsignaturas = new LinkedHashMap<Integer, Asignatura>();
	
	public static void main(String[] args){
		
	}
	
	public static void leerArchivoPersonas() throws IOException{
		String archivo="personas.txt";
		if(!GestionErrores.existeArchivo(archivo)){
			
		}
		else {
			Scanner entrada = new Scanner(new FileInputStream(archivo));
			while(entrada.hasNextLine()){
				
			}
			entrada.close();
		}
	}
	
	public static void leerArchivoAsignaturas() throws IOException{
		String archivo="asignaturas.txt";
		if(!GestionErrores.existeArchivo(archivo)){
			
		}
		else {
			Scanner entrada = new Scanner(new FileInputStream(archivo));
			while(entrada.hasNextLine()){
				
			}
			entrada.close();
		}
	}
	
}
