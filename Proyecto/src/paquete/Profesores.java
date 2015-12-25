package paquete;

import java.io.IOException;

public class Profesores implements Constantes{
	public static void obtenerCalendario(String[] instruccion)throws IOException{
		if(instruccion.length!=3){
			Gestion.aviso(nComandos);
			return; 
		}
		String dni = instruccion[1].trim(), fichero = instruccion[2].trim();
		if(Gestion.mapaProfesores.get(dni)==null){
			Gestion.aviso(prInex);
		}
		else if(!GestionErrores.comprobarDocencia((Profesor)Gestion.mapaProfesores.get(dni))){
			Gestion.aviso(noAsig);
		}
		else {
			((Profesor)Gestion.mapaProfesores.get(dni)).obtenerCalendario(fichero, Gestion.mapaAsignaturas);
			Gestion.aviso("OK");
		}
	}
	
	public static void asignarCargaDocente(String[] comando)throws IOException{
		if(comando.length!=5){
			Gestion.aviso(nComandos);
			return;
		}
		String dni = comando[1].trim(), siglas = comando[2].trim();
		int grupo;
		char tipoGrupo;
		try{
			grupo = Integer.parseInt(comando[4]);
			tipoGrupo = comando[3].trim().charAt(0);
		} catch (Exception e){
			Gestion.aviso(nComandos);
			return;
		}
		if(Gestion.mapaProfesores.get(dni)==null){
			Gestion.aviso(prInex);
		}
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas))==null){
			Gestion.aviso(asInex);
		}
		else if(!GestionErrores.comprobarTipoGrupo(tipoGrupo)){
			Gestion.aviso(tGruInc);
		}
		else if(!GestionErrores.comprobarExistenciaGrupo(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas)), grupo,tipoGrupo)){
			Gestion.aviso(gInex);
		}
		else if(!GestionErrores.comprobarAsignacionGrupo(Gestion.mapaProfesores, Asignaturas.siglasToIdentificador(siglas), grupo , tipoGrupo)){
			Gestion.aviso(gAsig);
		}
		else if(!GestionErrores.comprobarNumeroHoras((Profesor)Gestion.mapaProfesores.get(dni), Gestion.mapaAsignaturas, Gestion.mapaAsignaturas.get(
				Asignaturas.siglasToIdentificador(siglas)).getGrupo(grupo, tipoGrupo).getDuracion())){
			Gestion.aviso(hoSupMax);
		}
		else if(!GestionErrores.comprobarSolapeProfesor(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas)),
				(Profesor)Gestion.mapaProfesores.get(dni), grupo, tipoGrupo, Gestion.mapaAsignaturas)){
			Gestion.aviso(solape);
		}
		else{
			((Profesor)Gestion.mapaProfesores.get(dni)).addDocencia(Asignaturas.siglasToIdentificador(siglas), grupo, tipoGrupo);
			Gestion.aviso("OK");	
		}
	}
}
