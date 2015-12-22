package paquete;

import java.io.IOException;

public class Profesores {
	public static void obtenerCalendario(String[] instruccion)throws IOException{
		if(instruccion.length!=3){
			Gestion.aviso("Numero de comandos Incorrecto");
		}
		else if(Gestion.mapaProfesores.get(instruccion[1].trim())==null){
			Gestion.aviso("Profesor Inexistente");
		}
		else if(!GestionErrores.comprobarDocencia((Profesor)Gestion.mapaProfesores.get(instruccion[1].trim()))){
			Gestion.aviso("Profesor sin asignaciones");
		}
		else {
			((Profesor)Gestion.mapaProfesores.get(instruccion[1].trim())).obtenerCalendario(instruccion[2].trim(), Gestion.mapaAsignaturas);
			Gestion.aviso("OK");
		}
	}
	
	public static void asignarCargaDocente(String[] comando)throws IOException{
		if(comando.length!=5){
			Gestion.aviso("Numero de comandos incorrecto");
		}
		else if(Gestion.mapaProfesores.get(comando[1])==null){
			Gestion.aviso("Profesor inexistente");
		}
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2]))==null){
			Gestion.aviso("Asignatura Inexistente");
		}
		else if(!GestionErrores.comprobarTipoGrupo(comando[3])){
			Gestion.aviso("Tipo de grupo incorrecto");
		}
		else if(!GestionErrores.comprobarExistenciaGrupo(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])), 
				Integer.parseInt(comando[4]),comando[3].toCharArray()[0])){
			Gestion.aviso("Grupo Inexistente");
		}
		else if(!GestionErrores.comprobarAsignacionGrupo(Gestion.mapaProfesores, Asignaturas.siglasToIdentificador(comando[2]), 
				Integer.parseInt(comando[4]),comando[3].toCharArray()[0])){
			Gestion.aviso("Grupo ya asignado");
		}
		else if(!GestionErrores.comprobarNumeroHoras((Profesor)Gestion.mapaProfesores.get(comando[1]), Gestion.mapaAsignaturas, Gestion.mapaAsignaturas.get(
				Asignaturas.siglasToIdentificador(comando[2])).getGrupo(Integer.parseInt(comando[4]),comando[3].toCharArray()[0]).getDuracion())){
			Gestion.aviso("Horas asignables superior al maximo");
		}
		else if(!GestionErrores.comprobarSolapeProfesor(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])),
				(Profesor)Gestion.mapaProfesores.get(comando[1]), Integer.parseInt(comando[4]), comando[3].toCharArray()[0], Gestion.mapaAsignaturas)){
			Gestion.aviso("Se genera solape");
		}
		else{
			((Profesor)Gestion.mapaProfesores.get(comando[1])).addDocencia(Asignaturas.siglasToIdentificador(comando[3]), Integer.parseInt(comando[4]),
				comando[3].toCharArray()[0]);
			Gestion.aviso("OK");	
		}
	}
}
