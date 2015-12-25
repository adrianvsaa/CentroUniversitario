package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class Asignaturas implements Constantes{
	private static File fichero = new File("asignaturas.txt");
	
	public static void poblar() throws FileNotFoundException{
		if(!fichero.exists()){
			
		}
		else{
			Scanner entrada = new Scanner(new FileInputStream(fichero));
			while(entrada.hasNextLine()){
				String identificador = entrada.nextLine().trim();
				String nombre = entrada.nextLine().trim();
				String siglas = entrada.nextLine().trim();
				String curso = entrada.nextLine().trim();
				String coordinador = entrada.nextLine().trim();
				String preRequisitos = entrada.nextLine().trim();
				String gruposA = entrada.nextLine().trim();
				String gruposB = entrada.nextLine().trim();
				Gestion.mapaAsignaturas.put(Integer.parseInt(identificador.trim()), new Asignatura(Integer.parseInt(identificador.trim()), nombre,
						siglas, Integer.parseInt(curso.trim()), coordinador.trim(), preRequisitos.trim(), gruposA.trim(), gruposB.trim()));
				if(entrada.hasNext())
					entrada.nextLine();
			}
			entrada.close();
		}
	}
	
	public static void añadir(String instruccion) throws IOException{
		String[] token = instruccion.trim().split("\"");
		if(token.length!=8){
			Gestion.aviso(nComandos);
			return;
		}
		String nombre = token[1];
		String[] comando1 = token[0].trim().split("\\s+"), comando2 = token[2].trim().split(" ");
		if(comando1.length!=2||comando2.length!=3||comando2.length!=2){
			Gestion.aviso(nComandos);
			return;
		}
		String coordinador, siglas = comando2[0];
		if(comando2.length==2)
			coordinador = "";
		else
			coordinador = comando2[2].trim();
		int id, curso;
		try { 
		id = Integer.parseInt(comando1[1]);
		curso = Integer.parseInt(comando2[1].trim());
		} catch(Exception e){
			Gestion.aviso(nComandos);
			return;
		}
		
		if(Gestion.mapaAsignaturas.get(id)!=null)
			Gestion.aviso(asExi);
		
		else if(Gestion.mapaProfesores.get(coordinador)==null)
			Gestion.aviso(prInex);
		
		else if(Gestion.mapaAsignaturas.get(siglasToIdentificador(siglas))!=null)
			Gestion.aviso(siExis);
	
		else {
			Gestion.mapaAsignaturas.put(id, new Asignatura(id, nombre, siglas, curso, coordinador, token[3], token[5], token[7]));
			Gestion.aviso("OK");
		}
		
		
	}
	
	public static void imprimir() throws IOException{
		BufferedWriter salida = new BufferedWriter(new FileWriter(fichero));
		Set<Integer> keys = Gestion.mapaAsignaturas.keySet();
		boolean ponerAsterisco = false;
		for(int key:keys){
			if(ponerAsterisco)
				salida.write("\n*\n"+Gestion.mapaAsignaturas.get(key).stringFichero());
			else
				salida.write(Gestion.mapaAsignaturas.get(key).stringFichero());
			ponerAsterisco = true;
		}
		salida.close();
	}
	
	public static void asignarCoordinador(String[] comando) throws IOException{
		if(comando.length!=3){
			Gestion.aviso(nComandos);
			return;
		}
		String dni = comando[1].trim(), siglas = comando[2].trim();
		
		if(Gestion.mapaProfesores.get(dni)==null)
			Gestion.aviso(prInex);
		
		else if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(siglas))==null)
			Gestion.aviso(asInex);
		
		else if(!GestionErrores.comprobarTitularidad((Profesor)Gestion.mapaProfesores.get(dni)))
			Gestion.aviso(noTit);
	
		else if(!GestionErrores.comprobarCoordinadorDos(dni, Gestion.mapaAsignaturas))
			Gestion.aviso(coorDos);
		
		else {
			Gestion.mapaAsignaturas.get(siglasToIdentificador(siglas)).setCoordinador(dni);
			Gestion.aviso("OK");
		}
		
		
	}
	
	public static int siglasToIdentificador(String siglas){
		LinkedHashMap<Integer, Asignatura> mapaAsignaturas = Gestion.mapaAsignaturas;
		Set<Integer> keys = mapaAsignaturas.keySet();
		Asignatura a=null;
		for(int key:keys){
			if(mapaAsignaturas.get(key).getSiglas().equals(siglas)){
				a = mapaAsignaturas.get(key);
				break;
			}
		}
		if(a==null)
			return 0;
		return a.getIdentificador();
	}
	
	public static boolean comprobarHorario(Set<Integer> keys, int horaEntrada, int horaSalida, char dia, Persona p){
		boolean opcion = true;
		LinkedHashMap<Integer, Asignatura> mapa;
		if(p instanceof Alumno)
			mapa = ((Alumno)p).getDocenciaRecibida();
		else
			mapa = ((Profesor)p).getDocenciaImpartida();
		for(int key:keys){
			ArrayList<Grupo> grupos = Gestion.mapaAsignaturas.get(key).getGrupos();
			ArrayList<Grupo> gruposPersona = mapa.get(key).getGrupos();
			for(int i=0; i<gruposPersona.size(); i++){
				for(int j=0; j<grupos.size(); j++){
					if(gruposPersona.get(i).getIdentificador()==grupos.get(j).getIdentificador()&&gruposPersona.get(i).getTipo()==
							grupos.get(j).getTipo()){
						if(grupos.get(j).getDia()!=dia)
							continue;
						else{
							if(grupos.get(j).getHoraEntrada()==horaEntrada||horaEntrada-grupos.get(j).getHoraSalida()<0){
								opcion = false;
								break;
							}
						}
					}
				}
			}
			if(!opcion)
				break;		
		}
		return opcion;
	}
}
