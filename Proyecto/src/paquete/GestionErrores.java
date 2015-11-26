package paquete;

import java.util.Calendar;
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
	
	public static boolean comprobarDNI(String dni){
		if(dni.length()!=9)
			return false;
		char[] auxiliar = dni.toCharArray();
		if(auxiliar[dni.length()-1]<'A'||auxiliar[dni.length()-1]>'Z')
			return false;
		try{
			String aux="";
			for(int i=0; i<dni.length()-1; i++)
				aux += auxiliar[i];
			Integer.parseInt(aux);
		} catch(Exception d){
			return false;
		}
		return true;
	}
	
	public static boolean comprobarFecha(Calendar fecha){
		Calendar fechaMinima = Calendar.getInstance();
		fechaMinima.set(1950, 1 , 1);
		Calendar fechaMaxima = Calendar.getInstance();
		fechaMaxima.set(2020, 1, 1);
		try{
			fecha.setLenient(false);
			fecha.getTime();
		} catch(Exception time){
			return false;
		}
		if((fecha.getTimeInMillis()-fechaMinima.getTimeInMillis())<0)
			return false;
		if((fechaMaxima.getTimeInMillis()-fecha.getTimeInMillis())<0)
			return false;
		return true;
	}
}
