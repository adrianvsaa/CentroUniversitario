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

public class Asignaturas implements Constantes{
	private static File fichero = new File("asignaturas.txt");
	
	/**
	 * Metodo que abre el fichero asignaturas y con el contenido de ese fichero crea el mapa de asignaturas
	 * @throws FileNotFoundException excepcion en caso de no encontrar el archivo
	 */
	
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
	
	/**
	 * Metodo que recibe una instruccion la desgloba entre parametros comprueba si los parametros son correctos y si la 
	 * instruccion se puede ejecutar en ese caso añade una asignatura al mapa
	 * @param instruccion variable que contiene la operacion que se desea ejecutar
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
	public static void anadir(String instruccion) throws IOException{
		String[] token = instruccion.trim().split("\"");
		if(token.length!=8){
			Gestion.aviso(nComandos);
			return;
		}
		String nombre = token[1];
		String[] comando1 = token[0].trim().split("\\s+"), comando2 = token[2].trim().split(" ");
		if(comando1.length!=2||comando2.length!=3){
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
			Gestion.aviso(siExis+" o identidicador ya en uso");
	
		else {
			Gestion.mapaAsignaturas.put(id, new Asignatura(id, nombre, siglas, curso, coordinador, token[3], token[5], token[7]));
			Gestion.aviso("OK");
		}
		
		
	}
	
	/**
	 * Metodo que imprime el mapa de asignaturas en su correspondiente fichero con su correspondiente formato
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
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
	
	/**
	 * Metodo que recibe unos parametros de una instruccion comprueba si los datos son correctos y en caso de ser correctos 
	 * y en caso de ser correctos llama al metodo setCoordinador del objeto asignatura 
	 * @param comando variable que contiene la operacion que se desea ejecutar
	 * @throws IOException excepcion en caso de error en salida de datos
	 */
	
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
	
	/**
	 * parametro que recibe un string correspondiente con lo que deberian ser las siglas de una asignatura, comprueba que eso sea correcto
	 * en caso de serlo retorno el identificador correspondiente a la asignatura con esas siglas en otro caso retorna 0
	 * @param siglas parametro tipo String con las siglas de una asignatura
	 * @return identificador
	 */
	
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
