package paquete;

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
}
