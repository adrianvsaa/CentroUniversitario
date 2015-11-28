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
	
	public static boolean comprobarFechaIngreso(Calendar fechaIngreso, Calendar fecha){
		int edadMaxima = 65, edadMinima = 15;
		try{
			fechaIngreso.setLenient(false);
			fechaIngreso.getTime();
		}catch(Exception time2){
			return false;
		}
		double aux = (fechaIngreso.getTimeInMillis()/1000/60/60/24-fecha.getTimeInMillis()/1000/60/60/24)/365;
		if(aux<edadMinima||aux>edadMaxima)
			return false;
		return true;
	}
	
	public static boolean comprobarHorasAsignables(int horas, String tipoProfesor){
		if(horas<0)
			return false;
		if(tipoProfesor.equals("titular")){
			if(horas>20)
				return false;
		}
		else
			if(horas>15)
				return false;
		return true;
	}
}
