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
			salida.write(((Alumno)lista.get(i)).stringExpediente());
			salida.write(System.getProperty("line.separator"));
		}
		salida.close();
		Gestion.aviso("OK");
		return;
	}
	
	public static void matricular(String[] comando) throws IOException{
		if(comando.length!=3)
			Gestion.aviso(nComandos);
		
		else if(Gestion.mapaAlumnos.get(comando[1].trim())==null)
			Gestion.aviso(alInex);
		
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2]))==null)
			Gestion.aviso(asInex);
		
		else if(((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).comprobarMatricula(Asignaturas.siglasToIdentificador(comando[2])))
			Gestion.aviso(alYaMat);
		
		else if(!GestionErrores.comprobarRequisitos((Alumno)Gestion.mapaAlumnos.get(comando[1]), Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2]))))
			Gestion.aviso(noRequisitos);
		
		else { ((Alumno)Gestion.mapaAlumnos.get(comando[1])).matricula(new Asignatura(Asignaturas.siglasToIdentificador(comando[2])));
		Gestion.aviso("OK");}
	}
	
	public static void asignarGrupo(String[] comando)throws IOException{
		if(comando.length!=5)
			Gestion.aviso(nComandos);
		
		else if(Gestion.mapaAlumnos.get(comando[1].trim())==null)
			Gestion.aviso(alInex);
		
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2]))==null)
			Gestion.aviso(asInex);
		
		else if(!((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).comprobarMatricula(Asignaturas.siglasToIdentificador(comando[2])))
			Gestion.aviso(alNoMat);
		
		else if(!GestionErrores.comprobarTipoGrupo(comando[3].trim()))
			Gestion.aviso(tGruInc);
		
		else if(!Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])).comprobarGrupo(Integer.parseInt(comando[4].trim()),
				comando[3].trim().toCharArray()[0]))
			Gestion.aviso(gInex);
		
		else if(!GestionErrores.comprobarSolapeAlumno((Alumno)Gestion.mapaAlumnos.get(comando[1].trim()), Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])),
				Gestion.mapaAsignaturas,comando[3].trim().toCharArray()[0], Integer.parseInt(comando[4].trim())))
			Gestion.aviso(solape);
		
		else if(Alumnos.comprobarGrupo(Asignaturas.siglasToIdentificador(comando[2]), comando[3].trim().charAt(0), (Alumno)Gestion.mapaAlumnos.get(comando[1].trim())))
			Gestion.aviso("El alumno ya esta matriculado en un grupo de tipo "+comando[3].trim().toCharArray()[0]+" de esta asignatura");
	
		else {
			((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).asignarGrupo(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])),
		comando[3].trim().charAt(0), Integer.parseInt(comando[4].trim()));
		Gestion.aviso("OK");
		}
	}
	
	
	public static void evaluarAsignatura(String[] comando) throws IOException{
		if(comando.length!=4){
			Gestion.aviso(nComandos);
			return;
		}
		File notas = new File(comando[3].trim());
		if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[1].trim()))==null)
			Gestion.aviso(asInex);
		
		else if(!notas.exists())
			Gestion.aviso(fNotas);
		
		else if(!GestionErrores.comprobarEvaluacionAlumno(Gestion.mapaAlumnos, comando[2], Asignaturas.siglasToIdentificador(comando[1].trim())))
			Gestion.aviso(yaEvaluada);
		
		else 
			GestionErrores.comprobarFicheroNotas(Gestion.mapaAlumnos, comando[3].trim(), Asignaturas.siglasToIdentificador(comando[1].trim()),
				comando[2].trim());
	}
	
	
	public static void obtenerExpediente(String[] comando) throws IOException{
		if(comando.length!=3)
			Gestion.aviso(nComandos);
		
		else if(Gestion.mapaAlumnos.get(comando[1].trim())==null)
			Gestion.aviso(alInex);
		
		else if(!GestionErrores.comprobarExpediente(((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).getAsignaturasSuperadas()))
			Gestion.aviso(eVacio);
		
		else {
			((Alumno)Gestion.mapaAlumnos.get(comando[1].trim())).expediente(comando[2].trim(), Gestion.mapaAsignaturas);
			Gestion.aviso("OK");
		}
	}
}