package paquete;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.io.FileInputStream;

public class Gestion {

	static LinkedHashMap<String, Alumno> mapaAlumnos = new LinkedHashMap<String, Alumno>();
	static LinkedHashMap<String, Profesor> mapaProfesores = new LinkedHashMap<String, Profesor>();
	static LinkedHashMap<Integer, Asignatura> mapaAsignaturas = new LinkedHashMap<Integer, Asignatura>();
	
	public static void main(String[] args) throws IOException{
		leerArchivoPersonas();
		System.out.println(mapaAlumnos.values());
	}
	
	public static void leerArchivoPersonas() throws IOException{
		String archivo="personas.txt";
		if(!GestionErrores.existeArchivo(archivo)){
			
		}
		else {
			Scanner entrada = new Scanner(new FileInputStream(archivo));
			while(entrada.hasNextLine()){
				String auxiliar;
				auxiliar = entrada.nextLine();
				String dni = entrada.nextLine();
				String nombre = entrada.nextLine();
				String apellidos = entrada.nextLine();
				String aux = entrada.nextLine();
				String aux2[] = aux.split("/");
				Calendar fechaNacimiento = Calendar.getInstance();
				fechaNacimiento.set(Integer.parseInt(aux2[2]), Integer.parseInt(aux2[1]), Integer.parseInt(aux2[0]));
				if(auxiliar.equals("alumno")){
					String aux1 = entrada.nextLine();
					String aux21[] = aux1.split("/");
					Calendar fechaIngreso = Calendar.getInstance();
					fechaIngreso.set(Integer.parseInt(aux21[2]), Integer.parseInt(aux21[1]), Integer.parseInt(aux21[0]));
					String asignaturasSuperadas = entrada.nextLine();
					String docenciaRecibida = entrada.nextLine();
					Alumno a = new Alumno(dni.trim(), nombre.trim(), apellidos.trim(), fechaNacimiento, fechaIngreso, docenciaRecibida, 
							asignaturasSuperadas);
					mapaAlumnos.put(dni, a);
				}
				else{
					String categoria = entrada.nextLine();
					String departamento = entrada.nextLine();
					String horas = entrada.nextLine();
					String docenciaImpartida = entrada.nextLine();
					Profesor p = new Profesor(dni.trim(), nombre.trim(), apellidos.trim(), fechaNacimiento, categoria, departamento, 
							Integer.parseInt(horas));
					mapaProfesores.put(dni, p);
				}
				if(entrada.hasNext())
					entrada.nextLine();
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
