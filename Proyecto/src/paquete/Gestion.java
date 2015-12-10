package paquete;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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
				String instruccion = entradaInstrucciones.nextLine();
				if(instruccion.toCharArray()[0]=='*')
					continue;
				String[] auxiliar = instruccion.trim().split(" ");
				editarArchivoAvisos("Comando: "+instruccion);
				switch(auxiliar[0]){
					case "InsertaPersona":
						insertarPersona(instruccion);
						break;
					case "AsignaCoordinador":
						asignarCoordinador(instruccion.trim().split("\\s+"));
						break;
					case "AsignaCargaDocente":
						asignarCargaDocente(instruccion.trim().split("\\s+"));
						break;
					case "Matricula":
						matricularAlumno(instruccion.trim().split("\\s+"));
						break;
					case "AsignaGrupo":
						asignarGrupoAlumno(instruccion.trim().split("\\s+"));
						break;
					case "Evalua":
						evaluarAsignatura(instruccion.trim().split("\\s+"));
						break;
					case "Expediente":
						obtenerExpedienteAlumno(instruccion.trim().split("\\s+"));
						break;
					case "ObtenerCalendarioClases":
						obtenerCalendarioProfesor(instruccion.trim().split("\\s+"));
						break;
					default:
						editarArchivoAvisos("Comando inexistente");
						break;
				}
				editarArchivoAvisos("\n");
				
			}
			entradaInstrucciones.close();
		}
		//escribirMapas();
	}
	
	public static void leerArchivoPersonas() throws IOException{
		String archivo = "personas.txt";
		if(!GestionErrores.existeArchivo(archivo)){
			
		}
		else {
			Scanner entrada = new Scanner(new FileInputStream(archivo));
			while(entrada.hasNextLine()){
				String auxiliar = entrada.nextLine().trim();
				String dni = entrada.nextLine().trim();
				String nombre = entrada.nextLine().trim();
				String apellidos = entrada.nextLine().trim();
				Calendar fechaNacimiento = stringToCalendar(entrada.nextLine());
				if(auxiliar.trim().equals("alumno")){
					Calendar fechaIngreso = stringToCalendar(entrada.nextLine().trim());
					String asignaturasSuperadas = entrada.nextLine().trim();
					String docenciaRecibida = entrada.nextLine().trim();
					Alumno a = new Alumno(dni.trim(), nombre.trim(), apellidos.trim(), fechaNacimiento, fechaIngreso, docenciaRecibida, 
							asignaturasSuperadas);
					mapaAlumnos.put(dni, a);
				}
				else{
					String categoria = entrada.nextLine().trim();
					String departamento = entrada.nextLine().trim();
					String horas = entrada.nextLine().trim();
					String docenciaImpartida = entrada.nextLine().trim();
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
				String identificador = entrada.nextLine().trim();
				String nombre = entrada.nextLine().trim();
				String siglas = entrada.nextLine().trim();
				String curso = entrada.nextLine().trim();
				String coordinador = entrada.nextLine().trim();
				String preRequisitos = entrada.nextLine().trim();
				String gruposA = entrada.nextLine().trim();
				String gruposB = entrada.nextLine().trim();
				mapaAsignaturas.put(Integer.parseInt(identificador.trim()), new Asignatura(Integer.parseInt(identificador.trim()), nombre, siglas, 
						Integer.parseInt(curso.trim()), coordinador.trim(), preRequisitos.trim(), gruposA.trim(), gruposB.trim()));
				if(entrada.hasNext())
					entrada.nextLine();
			}
			entrada.close();
		}
	}
	
	
	public static void editarArchivoAvisos(String aviso)throws IOException{
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

	public static void insertarPersona(String instruccion) throws IOException{
		String[] token = instruccion.trim().split("\"");
		String[] comando1 = token[0].trim().split("\\s+");
		if(token.length!=7&&token.length!=5){
			editarArchivoAvisos("Numero de comandos incorrecto");
			return;
		}
		if(!GestionErrores.comprobarDNI(comando1[2])){
			editarArchivoAvisos("DNI incorrecto");
			return;
		}	
			if(comando1[1].equals("alumno")){
				String[] comando2 = token[4].trim().split("\\s+");
				Calendar fecha = stringToCalendar(comando2[0]);
				if(comando1.length!=3||comando2.length!=2||token.length!=5){
					editarArchivoAvisos("Numero de comandos incorrecto");
					return;
				}
				if(!GestionErrores.comprobarFecha(fecha)){
					editarArchivoAvisos("Fecha Incorrecta");
					return;
				}
				Calendar fechaIngreso = stringToCalendar(comando2[1]);
				if(!GestionErrores.comprobarFechaIngreso(fechaIngreso, fecha)){
					editarArchivoAvisos("Fecha Incorrecta");
					return;
				}
				if(mapaAlumnos.get(comando1[2])!=null){
					editarArchivoAvisos("Alumno ya existente");
					return;
				}
				mapaAlumnos.put(comando1[2], new Alumno(comando1[2], token[1], token[3], fecha, fechaIngreso));
				editarArchivoAvisos("OK");
			}
			else{
				if(token.length!=7||token[4].trim().split("\\s+").length!=2||token[6].trim().split("\\s+").length!=1){
					editarArchivoAvisos("Numero de comandos incorrecto");
					return;
				}
				Calendar fecha = stringToCalendar(token[4].trim().split(" ")[1]);
				if(!GestionErrores.comprobarFecha(fecha)){
					editarArchivoAvisos("Fecha Incorrecta");
					return;
				}
				if(!GestionErrores.comprobarHorasAsignables(Integer.parseInt(token[6].trim()), token[4].trim().trim().split("\\s+")[0])){
					editarArchivoAvisos("Número de horas incorrecto");
					return;
				}
				if(mapaProfesores.get(comando1[2])!=null){
					editarArchivoAvisos("Profesor ya existente");
					return;
				}
				mapaProfesores.put(comando1[2], new Profesor(comando1[2], token[1].trim(), token[3].trim(), fecha, token[4].trim().split("\\s+")[0], 
						token[5].trim(), Integer.parseInt(token[6].trim())));
				editarArchivoAvisos("OK");
				return;
			}
		return;
	}
	
	public static void asignarCoordinador(String[] comando) throws IOException{
		if(mapaProfesores.get(comando[1])==null){
			editarArchivoAvisos("Profesor inexistente");
			return;
		}
		if(!GestionErrores.comprobarAsignaturaSiglas(comando[2], mapaAsignaturas)){
			editarArchivoAvisos("Asignatura Inexistente");
			return;
		}
		if(!GestionErrores.comprobarTitularidad(mapaProfesores.get(comando[1]))){
			editarArchivoAvisos("Profesor no titular");
			return;
		}
		if(!GestionErrores.comprobarCoordinadorDos(comando[1], mapaAsignaturas)){
			editarArchivoAvisos("Profesor ya es coordinador de dos materias");
			return;
		}
		mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2])).setCoordinador(comando[1]);
		editarArchivoAvisos("OK");
		
	}
	
	public static void asignarCargaDocente(String[] comando)throws IOException{
		if(comando.length!=5){
			editarArchivoAvisos("Numero de comandos incorrecto");
			return;
		}
		if(mapaProfesores.get(comando[1])==null){
			editarArchivoAvisos("Profesor inexistente");
			return;
		}
		if(mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2]))==null){
			editarArchivoAvisos("Asignatura Inexistente");
			return;
		}
		if(!GestionErrores.comprobarTipoGrupo(comando[3])){
			editarArchivoAvisos("Tipo de grupo incorrecto");
			return;
		}
		if(!GestionErrores.comprobarExistenciaGrupo(mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2])), 
				Integer.parseInt(comando[4]),comando[3].toCharArray()[0])){
			editarArchivoAvisos("Grupo Inexistente");
			return;
		}
		if(!GestionErrores.comprobarAsignacionGrupo(mapaProfesores, Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2]), 
				Integer.parseInt(comando[4]),comando[3].toCharArray()[0])){
			editarArchivoAvisos("Grupo ya asignado");
			return;
		}
		if(!GestionErrores.comprobarNumeroHoras(mapaProfesores.get(comando[1]), mapaAsignaturas, mapaAsignaturas.get(
				Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2])).getGrupo(Integer.parseInt(comando[4]),comando[3].toCharArray()[0]).getDuracion())){
			editarArchivoAvisos("Horas asignables superior al maximo");
			return;
		}
		if(!GestionErrores.comprobarSolapeProfesor(mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2])),
				mapaProfesores.get(comando[1]), Integer.parseInt(comando[4]), comando[3].toCharArray()[0], mapaAsignaturas)){
			editarArchivoAvisos("Se genera solape");
			return;
		}
		mapaProfesores.get(comando[1]).addDocencia(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[3]), Integer.parseInt(comando[4]),
				comando[3].toCharArray()[0]);
		editarArchivoAvisos("OK");
		
		
	}
	
	public static void matricularAlumno(String[] comando) throws IOException{
		if(comando.length!=3){
			editarArchivoAvisos("Numero de comandos Incorrecto");
			return;
		}
		if(mapaAlumnos.get(comando[1].trim())==null){
			editarArchivoAvisos("Alumno inexistente");
			return;
		}
		if(mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2]))==null){
			editarArchivoAvisos("Asignatura Inexistente");
			return;
		}
		if(mapaAlumnos.get(comando[1].trim()).comprobarMatricula(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2]))){
			editarArchivoAvisos("Ya es alumno de la asignatura indicada");
			return;
		}
		if(!GestionErrores.comprobarRequisitos(mapaAlumnos.get(comando[1]), mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, 
				comando[2])))){
			editarArchivoAvisos("No cumple requisitos");
			return;
		}
		mapaAlumnos.get(comando[1]).matricula(new Asignatura(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2])));
		editarArchivoAvisos("OK");
	}
	
	public static void asignarGrupoAlumno(String[] comando)throws IOException{
		if(comando.length!=5){
			editarArchivoAvisos("Numero de comandos incorrecto");
			return;
		}
		if(mapaAlumnos.get(comando[1].trim())==null){
			editarArchivoAvisos("Alumno inexistente");
			return;
		}
		if(mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2]))==null){
			editarArchivoAvisos("Asignatura inexistente");
			return;
		}
		if(!mapaAlumnos.get(comando[1].trim()).comprobarMatricula(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2]))){
			editarArchivoAvisos("Alumno no matriculado");
			return;
		}
		if(!GestionErrores.comprobarTipoGrupo(comando[3].trim())){
			editarArchivoAvisos("Tipo de grupo incorrecto");
			return;
		}
		if(!mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2])).comprobarGrupo(Integer.parseInt(comando[4].trim()),
				comando[3].trim().toCharArray()[0])){
			editarArchivoAvisos("Grupo Inexistente");
			return;
		}
		if(!GestionErrores.comprobarSolapeAlumno(mapaAlumnos.get(comando[1].trim()), mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2])),
				mapaAsignaturas,comando[3].trim().toCharArray()[0], Integer.parseInt(comando[4].trim()))){
			editarArchivoAvisos("Se genera solape");
			return;
		}
		if(mapaAlumnos.get(comando[1].trim()).comprobarGrupo(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2]), 
				comando[3].trim().toCharArray()[0])){
			editarArchivoAvisos("El alumno ya esta matriculado en un grupo de tipo "+comando[3].trim().toCharArray()[0]+" de esta asignatura");
		}
		mapaAlumnos.get(comando[1].trim()).asignarGrupo(mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[2])),
		comando[3].trim().toCharArray()[0], Integer.parseInt(comando[4].trim()));
		editarArchivoAvisos("OK");
	}
	
	public static void evaluarAsignatura(String[] comando) throws IOException{
		if(comando.length!=4){
			editarArchivoAvisos("Numero de comandos Incorrecto");
			return;
		}
		if(mapaAsignaturas.get(Asignatura.siglasToIdentificador(mapaAsignaturas, comando[1].trim()))==null){
			editarArchivoAvisos("Asignatura Inexistente");
			return;
		}
		if(!GestionErrores.existeArchivo(comando[3].trim())){
			editarArchivoAvisos("Fichero de notas inexistente");
			return;
		}
		if(!GestionErrores.comprobarEvaluacionAlumno(mapaAlumnos, comando[2], Asignatura.siglasToIdentificador(mapaAsignaturas, comando[1].trim()))){
			editarArchivoAvisos("Asignatura ya evaluada en ese curso Academico");
			return;
		}
		GestionErrores.comprobarFicheroNotas(mapaAlumnos, comando[3].trim(), Asignatura.siglasToIdentificador(mapaAsignaturas, comando[1].trim()),
				comando[2].trim());
		return;
		
	}
	
	public static void obtenerExpedienteAlumno(String[] comando) throws IOException{
		if(comando.length!=3){
			editarArchivoAvisos("Numero de comandos incorrecto");
			return;
		}
		if(mapaAlumnos.get(comando[1].trim())==null){
			editarArchivoAvisos("Alumno inexistente");
			return;
		}
		if(!GestionErrores.comprobarExpediente(mapaAlumnos.get(comando[1].trim()).getAsignaturasSuperadas())){
			editarArchivoAvisos("Expediente Vacio");
			return;
		}
		mapaAlumnos.get(comando[1].trim()).expediente(comando[2].trim(), mapaAsignaturas);
		editarArchivoAvisos("OK");
	}
	
	public static void obtenerCalendarioProfesor(String[] instruccion)throws IOException{
		if(instruccion.length!=3){
			editarArchivoAvisos("Numero de comandos Incorrecto");
			return;
		}
		if(mapaProfesores.get(instruccion[1].trim())==null){
			editarArchivoAvisos("Profesor Inexistente");
			return;
		}
		if(!GestionErrores.comprobarDocencia(mapaProfesores.get(instruccion[1].trim()))){
			editarArchivoAvisos("Profesor sin asignaciones");
			return;
		}
		mapaProfesores.get(instruccion[1].trim()).obtenerCalendario(instruccion[2].trim(), mapaAsignaturas);
		editarArchivoAvisos("OK");
	}
	
	public static Calendar stringToCalendar(String string){
		String[] auxiliar = string.trim().split("/");
		Calendar fecha = Calendar.getInstance();
		fecha.set(Integer.parseInt(auxiliar[2].trim()), Integer.parseInt(auxiliar[1]), Integer.parseInt(auxiliar[0]));
		return fecha;
		
	}
}
