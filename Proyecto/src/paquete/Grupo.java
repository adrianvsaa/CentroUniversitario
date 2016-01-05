package paquete;

import java.util.Calendar;
import java.util.Comparator;
import java.text.SimpleDateFormat;

public class Grupo implements Comparable<Grupo>{
	private int identificador;
	private char tipo;
	private char dia;
	private Calendar horaEntrada = Calendar.getInstance();
	private Calendar horaSalida= Calendar.getInstance();
	
	/**
	 * Constructor completo del grupo
	 * @param identificador variable tipo int
	 * @param tipo variable tipo char
	 * @param dia variable tipo char
	 * @param horaEntrada variable tipo String 
	 * @param horaSalida variable tipo String
	 */
	
	public Grupo(int identificador, char tipo, char dia, String horaEntrada, String horaSalida){
		this.identificador = identificador;
		this.tipo = tipo;
		this.dia = dia;
		this.horaEntrada.set(2015, 5, 23, Integer.parseInt(horaEntrada), 0, 0);
		this.horaSalida.set(2015, 5, 23, Integer.parseInt(horaSalida), 0, 0);
	}
	
	/**
	 * Constructor con identificador y tipo
	 * @param identificador del grupo
	 * @param tipo del grupo
	 */
	
	public Grupo(int identificador, char tipo){
		this.identificador = identificador;
		this.tipo = tipo;
	}
	
	/**
	 * Metodo que retorna el identificador
	 * @return identificador variable tipo int
	 */
	
	public int getIdentificador(){
		return identificador;
	}
	
	/**
	 * Metodo que retorna el tipo
	 * @return tipo variable tipo char
	 */
	
	public char getTipo(){
		return tipo;
	}
	
	/**
	 * Metodo que retorna el string para imprimir en el fichero de personas
	 * @return string variable tipo String correspondiente a persona
	 */
	
	public String salidaPersona(){
		return tipo+" "+Integer.toString(identificador);
	}
	
	/**
	 * Metodo que retorna el string para imprimir en el fichero de asignaturas
	 * @param tipo variable tipo char que corresponde a un tipo
	 * @return string variable tipo String que corresponde a asignatura
	 */
	
	public String salidaAsignaturas(char tipo){
		if(tipo==this.tipo){
			SimpleDateFormat aux = new SimpleDateFormat("HH");
			return identificador+" "+dia+" "+aux.format(horaEntrada.getTime())+" "+aux.format(horaSalida.getTime());
		}
		else
			return "";
	}
	
	/**
	 * Metodo que retorna la hora de salida
	 * @return horaSalida hora de salida del grupo
	 */
	
	public int getHoraSalida(){
		SimpleDateFormat aux = new SimpleDateFormat("HH");
		return Integer.parseInt(aux.format(horaSalida.getTime()));
	}
	
	/**
	 * Metodo que retorna la hora de entrada
	 * @return horaEntrada hora de entrada del grupo
	 */
	
	public int getHoraEntrada(){
		SimpleDateFormat aux = new SimpleDateFormat("HH");
		return Integer.parseInt(aux.format(horaEntrada.getTime()));
	}
	
	/**
	 * Metodo que retorna el dia 
	 * @return dia en el que es el grupo
	 */
	
	public char getDia(){
		return dia;
	}
	
	 /**
	  * Metodo que retorna la duracion de un grupo
	  * @return duracion periodo de tiempo que dura el grupo
	  */
	
	public int getDuracion(){
		SimpleDateFormat aux = new SimpleDateFormat("HH");
		return Integer.parseInt(aux.format(horaSalida.getTime()))-Integer.parseInt(aux.format(horaEntrada.getTime()));
	}
	
	/**
	 * Metodo que compara los grupos por el dia en el que es
	 */
	
	public int compareTo(Grupo g){
		if(getDia()=='L'&&(g.getDia()=='M'||g.getDia()=='X'||g.getDia()=='J'||g.getDia()=='V'))
			return -1;
		else if(getDia()=='M'&&g.getDia()=='L')
			return 1;
		else if(getDia()=='M'&&(g.getDia()=='X'||g.getDia()=='J'||g.getDia()=='V'))
			return -1;
		else if(getDia()=='X'&&(g.getDia()=='M'||g.getDia()=='L'))
			return 1;
		else if(getDia()=='X'&&(g.getDia()=='J'||g.getDia()=='V'))
			return -1;
		else if(getDia()=='J'&&(g.getDia()=='M'||g.getDia()=='L'||g.getDia()=='X'))
			return 1;
		else if(getDia()=='J'&&g.getDia()=='V')
			return -1;
		else if(getDia()=='V'&&g.getDia()=='L'||g.getDia()=='M'||g.getDia()=='X'||g.getDia()=='J')
			return 1;
		else 
			return 0;
			
	}

}

class ComparadorHora implements Comparator<Grupo>{
	/**
	 * Metodo que compara los grupos por hora de entrada
	 */
	public int compare(Grupo g1, Grupo g2){
		if(g1.getHoraEntrada()<g2.getHoraEntrada())
			return -1;
		else
			return 1;
	}
}
