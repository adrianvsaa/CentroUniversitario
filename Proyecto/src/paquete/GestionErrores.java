package paquete;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GestionErrores {
	public static boolean existeArchivo(String archivo) throws IOException{
		Scanner entrada= null;
		try{
			entrada = new Scanner(new FileInputStream(archivo));
		} catch(FileNotFoundException a){
			return false;
		}
		entrada.close();
		return true;
	}
	
	public static boolean comprobarExpediente(LinkedHashMap<Integer, Nota> asignaturasSuperadas){
		if(asignaturasSuperadas.size()>=0)
			return true;
		else
			return false;
	}
}
