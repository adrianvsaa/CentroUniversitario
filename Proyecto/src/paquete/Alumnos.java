package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Alumnos extends Personas{
	public static boolean comprobarGrupo(int idAsignatura, char tipoGrupo, Alumno a){
		LinkedHashMap<Integer, Asignatura> docenciaRecibida = a.getDocenciaRecibida();
		if(docenciaRecibida.get(idAsignatura)==null)
			return false;
		if(docenciaRecibida.get(idAsignatura).comprobarGrupoTipo(tipoGrupo))
			return true;
		return false;
	}
	
	public static void ordenarXNota(String[] instruccion) throws IOException{
		if(instruccion.length!= 2){
			Gestion.aviso("Numero de comandos incorrecto");
			return;
		}
		List<Persona> lista2= new LinkedList<Persona>(Gestion.mapaAlumnos.values());
		List<Alumno> lista = (List)lista2;
		Collections.sort(lista);
		Collections.sort(lista, new ComparadorNota());
		File archivo = new File(instruccion[1].trim());
		BufferedWriter salida = new BufferedWriter(new FileWriter(archivo));
		for(int i=0; i<lista.size(); i++){
			salida.write(((Alumno)lista.get(i)).salidaExpediente());
			salida.write(System.getProperty("line.separator"));
		}
		salida.close();
		return;
	}
	
	public static void matricular(String[] comando) throws IOException{
		if(comando.length!=3){
			Gestion.aviso("Numero de comandos Incorrecto");
		}
		else if(Gestion.mapaAlumnos.get(comando[1].trim())==null){
			Gestion.aviso("Alumno inexistente");
		}
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2]))==null){
			Gestion.aviso("Asignatura Inexistente");
		}
		else if(((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).comprobarMatricula(Asignaturas.siglasToIdentificador(comando[2]))){
			Gestion.aviso("Ya es alumno de la asignatura indicada");
		}
		else if(!GestionErrores.comprobarRequisitos((Alumno)Gestion.mapaAlumnos.get(comando[1]), Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])))){
			Gestion.aviso("No cumple requisitos");
		}
		else { ((Alumno)Gestion.mapaAlumnos.get(comando[1])).matricula(new Asignatura(Asignaturas.siglasToIdentificador(comando[2])));
		Gestion.aviso("OK");}
	}
	
	public static void asignarGrupo(String[] comando)throws IOException{
		if(comando.length!=5){
			Gestion.aviso("Numero de comandos incorrecto");
		}
		else if(Gestion.mapaAlumnos.get(comando[1].trim())==null){
			Gestion.aviso("Alumno inexistente");
		}
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2]))==null){
			Gestion.aviso("Asignatura inexistente");
		}
		else if(!((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).comprobarMatricula(Asignaturas.siglasToIdentificador(comando[2]))){
			Gestion.aviso("Alumno no matriculado");
		}
		else if(!GestionErrores.comprobarTipoGrupo(comando[3].trim())){
			Gestion.aviso("Tipo de grupo incorrecto");
		}
		else if(!Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])).comprobarGrupo(Integer.parseInt(comando[4].trim()),
				comando[3].trim().toCharArray()[0])){
			Gestion.aviso("Grupo Inexistente");
		}
		else if(!GestionErrores.comprobarSolapeAlumno((Alumno)Gestion.mapaAlumnos.get(comando[1].trim()), Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])),
				Gestion.mapaAsignaturas,comando[3].trim().toCharArray()[0], Integer.parseInt(comando[4].trim()))){
			Gestion.aviso("Se genera solape");
		}
		else if(Alumnos.comprobarGrupo(Asignaturas.siglasToIdentificador(comando[2]), comando[3].trim().charAt(0), (Alumno)Gestion.mapaAlumnos.get(comando[1].trim()))){
			Gestion.aviso("El alumno ya esta matriculado en un grupo de tipo "+comando[3].trim().toCharArray()[0]+" de esta asignatura");
		}
		else {
			((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).asignarGrupo(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])),
		comando[3].trim().toCharArray()[0], Integer.parseInt(comando[4].trim()));
		Gestion.aviso("OK");
		}
	}
	
	
	public static void evaluarAsignatura(String[] comando) throws IOException{
		File notas = new File(comando[3].trim());
		if(comando.length!=4){
			Gestion.aviso("Numero de comandos Incorrecto");
		}
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[1].trim()))==null){
			Gestion.aviso("Asignatura Inexistente");
		}
		else if(!notas.exists()){
			Gestion.aviso("Fichero de notas inexistente");
		}
		else if(!GestionErrores.comprobarEvaluacionAlumno(Gestion.mapaAlumnos, comando[2], Asignaturas.siglasToIdentificador(comando[1].trim()))){
			Gestion.aviso("Asignatura ya evaluada en ese curso Academico");
		}
		else 
			GestionErrores.comprobarFicheroNotas(Gestion.mapaAlumnos, comando[3].trim(), Asignaturas.siglasToIdentificador(comando[1].trim()),
				comando[2].trim());
	}
	
	
	public static void obtenerExpediente(String[] comando) throws IOException{
		if(comando.length!=3){
			Gestion.aviso("Numero de comandos incorrecto");
		}
		else if(Gestion.mapaAlumnos.get(comando[1].trim())==null){
			Gestion.aviso("Alumno inexistente");
		}
		else if(!GestionErrores.comprobarExpediente(((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).getAsignaturasSuperadas())){
			Gestion.aviso("Expediente Vacio");
		}
		else 
			((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).expediente(comando[2].trim(), Gestion.mapaAsignaturas);
		Gestion.aviso("OK");
	}
}
