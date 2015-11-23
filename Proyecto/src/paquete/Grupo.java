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
		String[] auxiliar = horaEntrada.split(":");
		this.horaEntrada.set(2015, 5, 23, Integer.parseInt(auxiliar[0]), Integer.parseInt(auxiliar[1]), 0);
		auxiliar = horaSalida.split(":");
		this.horaSalida.set(2015, 5, 23, Integer.parseInt(auxiliar[0]), Integer.parseInt(auxiliar[1]), 0);
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
	
	public String toString(){
		SimpleDateFormat aux = new SimpleDateFormat("HH:mm");
		return Integer.toString(identificador)+tipo+dia+aux.format(horaEntrada.getTime())+aux.format(horaSalida.getTime());
	}
}
