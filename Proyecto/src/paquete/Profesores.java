package paquete;

import java.io.IOException;

public class Profesores implements Constantes{
	public static void obtenerCalendario(String[] instruccion)throws IOException{
		if(instruccion.length!=3){
			Gestion.aviso(nComandos);
		}
		else if(Gestion.mapaProfesores.get(instruccion[1].trim())==null){
			Gestion.aviso(prInex);
		}
		else if(!GestionErrores.comprobarDocencia((Profesor)Gestion.mapaProfesores.get(instruccion[1].trim()))){
			Gestion.aviso(noAsig);
		}
		else {
			((Profesor)Gestion.mapaProfesores.get(instruccion[1].trim())).obtenerCalendario(instruccion[2].trim(), Gestion.mapaAsignaturas);
			Gestion.aviso("OK");
		}
	}
	
	public static void asignarCargaDocente(String[] comando)throws IOException{
		if(comando.length!=5){
			Gestion.aviso(nComandos);
		}
		else if(Gestion.mapaProfesores.get(comando[1])==null){
			Gestion.aviso(prInex);
		}
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2]))==null){
			Gestion.aviso(asInex);
		}
		else if(!GestionErrores.comprobarTipoGrupo(comando[3])){
			Gestion.aviso(tGruInc);
		}
		else if(!GestionErrores.comprobarExistenciaGrupo(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])), 
				Integer.parseInt(comando[4]),comando[3].toCharArray()[0])){
			Gestion.aviso(gInex);
		}
		else if(!GestionErrores.comprobarAsignacionGrupo(Gestion.mapaProfesores, Asignaturas.siglasToIdentificador(comando[2]), 
				Integer.parseInt(comando[4]),comando[3].toCharArray()[0])){
			Gestion.aviso(gAsig);
		}
		else if(!GestionErrores.comprobarNumeroHoras((Profesor)Gestion.mapaProfesores.get(comando[1]), Gestion.mapaAsignaturas, Gestion.mapaAsignaturas.get(
				Asignaturas.siglasToIdentificador(comando[2])).getGrupo(Integer.parseInt(comando[4]),comando[3].toCharArray()[0]).getDuracion())){
			Gestion.aviso(hoSupMax);
		}
		else if(!GestionErrores.comprobarSolapeProfesor(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2])),
				(Profesor)Gestion.mapaProfesores.get(comando[1]), Integer.parseInt(comando[4]), comando[3].toCharArray()[0], Gestion.mapaAsignaturas)){
			Gestion.aviso(solape);
		}
		else{
			((Profesor)Gestion.mapaProfesores.get(comando[1])).addDocencia(Asignaturas.siglasToIdentificador(comando[2]), Integer.parseInt(comando[4]),
				comando[3].toCharArray()[0]);
			Gestion.aviso("OK");	
		}
	}
}
