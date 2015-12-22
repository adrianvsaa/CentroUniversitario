package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class Asignaturas {
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
	
	public static void añadir(String[] instruccion){
		
	}
	
	public static void imprimir() throws IOException{
		BufferedWriter salida = new BufferedWriter(new FileWriter(fichero));
		Set<Integer> keys = Gestion.mapaAsignaturas.keySet();
		boolean ponerAsterisco = false;
		for(int key:keys){
			if(ponerAsterisco)
				salida.write("\n*\n"+Gestion.mapaAsignaturas.get(key).salidaFichero());
			else
				salida.write(Gestion.mapaAsignaturas.get(key).salidaFichero());
			ponerAsterisco = true;
		}
		salida.close();
	}
	
	public static void asignarCoordinador(String[] comando) throws IOException{
		if(Gestion.mapaProfesores.get(comando[1])==null){
			Gestion.aviso("Profesor inexistente");
			return;
		}
		if(Gestion.mapaAsignaturas.get(Asignaturas.siglasToIdentificador(comando[2]))==null){
			Gestion.aviso("Asignatura Inexistente");
			return;
		}
		if(!GestionErrores.comprobarTitularidad((Profesor)Gestion.mapaProfesores.get(comando[1]))){
			Gestion.aviso("Profesor no titular");
			return;
		}
		if(!GestionErrores.comprobarCoordinadorDos(comando[1], Gestion.mapaAsignaturas)){
			Gestion.aviso("Profesor ya es coordinador de dos materias");
			return;
		}
		Gestion.mapaAsignaturas.get(siglasToIdentificador(comando[2])).setCoordinador(comando[1]);
		Gestion.aviso("OK");
		
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
}
