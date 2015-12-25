package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Alumnos implements Constantes{
	public static void ordenarXNota(String[] instruccion) throws IOException{
		if(instruccion.length!= 2){
			Gestion.aviso(nComandos);
			return;
		}
		List<Persona> lista2= new LinkedList<Persona>(Gestion.mapaAlumnos.values());
		List<Alumno> lista = (List)lista2;
		Collections.sort(lista);
		Collections.sort(lista, new ComparadorNota());
		File archivo = new File(instruccion[1].trim());
		BufferedWriter salida = new BufferedWriter(new FileWriter(archivo));
		for(int i=0; i<lista.size(); i++){
			salida.write(((Alumno)lista.get(i)).stringExpediente());
			salida.write(System.getProperty("line.separator"));
		}
		salida.close();
		Gestion.aviso("OK");
		return;
	}
	
	public static void matricular(String[] comando) throws IOException{
		if(comando.length!=3) {
			Gestion.aviso(nComandos);
			return;
		}
		String dni = comando[1].trim(), siglas = comando[2].trim();
		if(Gestion.mapaAlumnos.get(dni)==null)
			Gestion.aviso(alInex);
		
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas))==null)
			Gestion.aviso(asInex);
		
		else if(((Alumno)Gestion.mapaAlumnos.get(dni)).comprobarMatricula(Asignaturas.siglasToIdentificador(siglas)))
			Gestion.aviso(alYaMat);
		
		else if(!GestionErrores.comprobarRequisitos((Alumno)Gestion.mapaAlumnos.get(dni), Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas))))
			Gestion.aviso(noRequisitos);
		
		else { ((Alumno)Gestion.mapaAlumnos.get(dni)).matricula(new Asignatura(Asignaturas.siglasToIdentificador(siglas)));
		Gestion.aviso("OK");}
	}
	
	public static void asignarGrupo(String[] comando)throws IOException{
		if(comando.length!=5) {
			Gestion.aviso(nComandos);
			return;
		}
		String dni = comando[1].trim(), siglas = comando[2].trim();
		int grupo;
		char tipoGrupo;
		try{
			grupo = Integer.parseInt(comando[4].trim());
			tipoGrupo = comando[3].trim().charAt(0);
		} catch (Exception e){
			Gestion.aviso(nComandos);
			return;
		}
		if(Gestion.mapaAlumnos.get(dni)==null)
			Gestion.aviso(alInex);
		
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas))==null)
			Gestion.aviso(asInex);
		
		else if(!((Alumno)Gestion.mapaAlumnos.get(dni)).comprobarMatricula(Asignaturas.siglasToIdentificador(siglas)))
			Gestion.aviso(alNoMat);
		
		else if(!GestionErrores.comprobarTipoGrupo(tipoGrupo))
			Gestion.aviso(tGruInc);
		
		else if(!Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas)).comprobarGrupo( grupo, tipoGrupo))
			Gestion.aviso(gInex);
		
		else if(!GestionErrores.comprobarSolapeAlumno((Alumno)Gestion.mapaAlumnos.get(dni), Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas)),
				Gestion.mapaAsignaturas, tipoGrupo, grupo))
			Gestion.aviso(solape);
		
		else if(GestionErrores.comprobarGrupo(Asignaturas.siglasToIdentificador(siglas), tipoGrupo, (Alumno)Gestion.mapaAlumnos.get(dni)))
			Gestion.aviso("El alumno ya esta matriculado en un grupo de tipo "+tipoGrupo+" de esta asignatura");
	
		else {
			((Alumno)Gestion.mapaAlumnos.get(dni)).asignarGrupo(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas)),
		tipoGrupo, grupo);
		Gestion.aviso("OK");
		}
	}
	
	
	public static void evaluarAsignatura(String[] comando) throws IOException{
		if(comando.length!=4){
			Gestion.aviso(nComandos);
			return;
		}
		File notas = new File(comando[3].trim());
		String siglas = comando[1].trim(), cursoAcademico = comando[2].trim();
		if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas))==null)
			Gestion.aviso(asInex);
		
		else if(!notas.exists())
			Gestion.aviso(fNotas);
		
		else if(!GestionErrores.comprobarEvaluacionAlumno(Gestion.mapaAlumnos, cursoAcademico, Asignaturas.siglasToIdentificador(siglas)))
			Gestion.aviso(yaEvaluada);
		
		else 
			GestionErrores.comprobarFicheroNotas(Gestion.mapaAlumnos, notas, Asignaturas.siglasToIdentificador(siglas),	cursoAcademico);
	}
	
	
	public static void obtenerExpediente(String[] comando) throws IOException{
		if(comando.length!=3) {
			Gestion.aviso(nComandos);
			return;
		}
		String dni = comando[1], fichero = comando[2];
		if(Gestion.mapaAlumnos.get(dni)==null)
			Gestion.aviso(alInex);
		
		else if(!GestionErrores.comprobarExpediente(((Alumno)Gestion.mapaAlumnos.get(dni)).getAsignaturasSuperadas()))
			Gestion.aviso(eVacio);
		
		else {
			((Alumno)Gestion.mapaAlumnos.get(dni)).expediente(fichero, Gestion.mapaAsignaturas);
			Gestion.aviso("OK");
		}
	}
}