package paquete;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class Gestion{

	static LinkedHashMap<String, Alumno> mapaAlumnos = new LinkedHashMap<String, Alumno>();
	static LinkedHashMap<String, Profesor> mapaProfesores = new LinkedHashMap<String, Profesor>();
	static LinkedHashMap<Integer, Asignatura> mapaAsignaturas = new LinkedHashMap<Integer, Asignatura>();
	
	public static void main(String[] args) throws IOException{
		leerArchivoPersonas();
		leerArchivoAsignaturas();
		if(!GestionErrores.existeArchivo("ejecucion.txt")){
			System.out.println("La ejecucion termino");
		}
		else{
			Scanner entradaInstrucciones = new Scanner(new FileInputStream("ejecucion.txt"));
			while(entradaInstrucciones.hasNext()){
				String[] instruccion = entradaInstrucciones	.nextLine()
															.trim()
															.split(" ");
				switch(instruccion[0]){
					case "InsertaPersona":
						break;
					case "AsignaCoordinador":
						break;
					case "AsignaCargaDocente":
						break;
					case "Matricula":
						break;
					case "AsignaGrupo":
						break;
					case "Evalua":
						break;
					case "Expediente":
						break;
					case "ObtenerCalendarioClases":
						break;
					default:
						break;
				}
				
			}
			entradaInstrucciones.close();
		}
		escribirMapas();
	}
	
	public static void leerArchivoPersonas() throws IOException{
		String archivo = "personas.txt";
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
				Calendar fechaNacimiento = stringToCalendar(entrada.nextLine());
				if(auxiliar.equals("alumno")){
					Calendar fechaIngreso = stringToCalendar(entrada.nextLine());
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
							Integer.parseInt(horas), docenciaImpartida);
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
				String identificador = entrada.nextLine();
				String nombre = entrada.nextLine();
				String siglas = entrada.nextLine();
				String curso = entrada.nextLine();
				String coordinador = entrada.nextLine();
				String preRequisitos = entrada.nextLine();
				String gruposA = entrada.nextLine();
				String gruposB = entrada.nextLine();
				mapaAsignaturas.put(Integer.parseInt(identificador.trim()), new Asignatura(Integer.parseInt(identificador.trim()), nombre, siglas, 
						Integer.parseInt(curso), coordinador.trim(), preRequisitos.trim(), gruposA.trim(), gruposB.trim()));
				if(entrada.hasNext())
					entrada.nextLine();
			}
			entrada.close();
		}
	}
	
	public static void editarArchivoAvisos(String aviso)throws IOException{
		File fichero =new File("avisos.txt");
		StringBuilder contenido = new StringBuilder();
	    try {
	      BufferedReader entrada =  new BufferedReader(new FileReader(fichero));
	      try {
	        String line = null; 
	        while (( line = entrada.readLine()) != null){
	          contenido.append(line);
	          contenido.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        entrada.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    try{
	    	BufferedWriter salida = new BufferedWriter(new FileWriter(fichero));
	    	salida.write(aviso);
	    	salida.close();
	   }catch(IOException ax){
	    	ax.printStackTrace();
	    }
	}
	
	public static void escribirMapas() throws IOException{
		File fichero = new File("personas.txt");
		File archivo = new File("asignaturas.txt");
		BufferedWriter salida = new BufferedWriter(new FileWriter(fichero));
		Set<String> keys = mapaAlumnos.keySet();
		boolean ponerAsterisco = false;
		for(String key:keys){
			if(ponerAsterisco)
				salida.write("\n*\n"+"alumno\n"+mapaAlumnos.get(key).salidaFichero());
			else
				salida.write("alumno\n"+mapaAlumnos.get(key).salidaFichero());
			ponerAsterisco = true;
		}
		keys = mapaProfesores.keySet();
		for(String key:keys) {
			if(ponerAsterisco)
				salida.write("\n*\n"+"profesor\n"+mapaProfesores.get(key).salidaFichero());
			else
				salida.write("profesor\n"+mapaProfesores.get(key).salidaFichero());
			ponerAsterisco = true;
		}
		salida.close();
		salida = new BufferedWriter(new FileWriter(archivo));
		Set<Integer> keys2 = mapaAsignaturas.keySet();
		ponerAsterisco = false;
		for(int key:keys2){
			if(ponerAsterisco)
				salida.write("\n*\n"+mapaAsignaturas.get(key).salidaFichero());
			else
				salida.write(mapaAsignaturas.get(key).salidaFichero());
			ponerAsterisco = true;
		}
		salida.close();
	}

	public static void insertarPersona(String[] comando) throws IOException{
		try {
			boolean insertar=true;
			Calendar fecha = stringToCalendar(comando[5]);
			if(!GestionErrores.comprobarDNI(comando[2])){
				editarArchivoAvisos("DNI incorrecto");
				insertar = false;
			}
			if(!GestionErrores.comprobarFecha(fecha)){
				editarArchivoAvisos("Fecha Incorrecta");
				insertar = false;
			}
			
			if(comando[1].equals("alumno")){
				Calendar fechaIngreso = stringToCalendar(comando[6]);
				if(!GestionErrores.comprobarFechaIngreso(fechaIngreso, fecha)){
					insertar = false;
					editarArchivoAvisos("Fecha Incorrecta");
				}
				if(mapaAlumnos.get(comando[2])!=null){
					insertar = false;
					editarArchivoAvisos("Alumno ya existente");
				}
				if(insertar)
					mapaAlumnos.put(comando[2], new Alumno(comando[2], comando[3], comando[4], fecha, fechaIngreso));
			}
			else{
				if(!GestionErrores.comprobarHorasAsignables(Integer.parseInt(comando[7]), comando[5])){
					insertar = false;
					editarArchivoAvisos("Número de horas incorrecto");
				}
				if(mapaProfesores.get(comando[2])!=null){
					insertar = false;
					editarArchivoAvisos("Profesor ya existente");	
				}
				if(insertar)
					mapaProfesores.put(comando[2], new Profesor(comando[2], comando[3], comando[4], fecha, comando[5], comando[6], Integer.parseInt(comando[5])));
			}
		}catch(Exception insertarPersona){
			System.out.println("Error en ver persona");
		}
		return;
	}
	
	public static Calendar stringToCalendar(String string){
		String[] auxiliar = string.trim().split("/");
		Calendar fecha = Calendar.getInstance();
		try{
			fecha.set(Integer.parseInt(auxiliar[2]), Integer.parseInt(auxiliar[1], Integer.parseInt(auxiliar[0])));
		}catch(Exception time){
			return null;
		}
		return fecha;
		
	}
}
