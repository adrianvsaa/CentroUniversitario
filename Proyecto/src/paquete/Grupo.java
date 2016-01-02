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
	
	public Grupo(int identificador, char tipo, char dia, String horaEntrada, String horaSalida){
		this.identificador = identificador;
		this.tipo = tipo;
		this.dia = dia;
		this.horaEntrada.set(2015, 5, 23, Integer.parseInt(horaEntrada), 0, 0);
		this.horaSalida.set(2015, 5, 23, Integer.parseInt(horaSalida), 0, 0);
	}
	
	public Grupo(int identificador, char tipo){
		this.identificador = identificador;
		this.tipo = tipo;
	}
	public int getIdentificador(){
		return identificador;
	}
	
	public char getTipo(){
		return tipo;
	}
	
	public String salidaPersona(){
		return tipo+" "+Integer.toString(identificador);
	}
	
	public String salidaAsignaturas(char tipo){
		if(tipo==this.tipo){
			SimpleDateFormat aux = new SimpleDateFormat("HH");
			return identificador+" "+dia+" "+aux.format(horaEntrada.getTime())+" "+aux.format(horaSalida.getTime());
		}
		else
			return "";
	}
	
	public int getHoraSalida(){
		SimpleDateFormat aux = new SimpleDateFormat("HH");
		return Integer.parseInt(aux.format(horaSalida.getTime()));
	}
	
	public int getHoraEntrada(){
		SimpleDateFormat aux = new SimpleDateFormat("HH");
		return Integer.parseInt(aux.format(horaEntrada.getTime()));
	}
	
	public char getDia(){
		return dia;
	}
	
	public int getDuracion(){
		SimpleDateFormat aux = new SimpleDateFormat("HH");
		return Integer.parseInt(aux.format(horaSalida.getTime()))-Integer.parseInt(aux.format(horaEntrada.getTime()));
	}
	
	public String toString(){
		return Integer.toString(identificador)+" "+tipo;
	}
	
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
	public int compare(Grupo g1, Grupo g2){
		if(g1.getHoraEntrada()<g2.getHoraEntrada())
			return -1;
		else
			return 1;
	}
}
