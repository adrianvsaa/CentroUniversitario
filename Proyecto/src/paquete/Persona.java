package paquete;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public abstract class Persona {
	private String dni;
	private String nombre;
	private String apellidos;
	private Calendar fechaNacimiento = Calendar.getInstance();
	
	public Persona(String dni, String nombre, String apellidos, Calendar fechaNacimiento){
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String toString(){
		SimpleDateFormat aux = new SimpleDateFormat("HH:mm");
		return dni+"\n"+nombre+"\n"+apellidos+"\n"+aux.format(fechaNacimiento.getTime());
	}
}
