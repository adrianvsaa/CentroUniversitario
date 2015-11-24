package paquete;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Grupo {
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
	
	public String toString(){
		SimpleDateFormat aux = new SimpleDateFormat("HH:mm");
		return Integer.toString(identificador)+" "+tipo;
	}
}
