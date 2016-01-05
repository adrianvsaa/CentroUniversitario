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
	
	/**
	 * Metodo que retorna el nombre de la persona
	 * @return nombre
	 */
	public String getNombre(){
		return nombre;
	}
	
	/**
	 * Metodo que retorna los apellidos de la persona
	 * @return apellidos
	 */
	
	public String getApellidos(){
		return apellidos;
	}
	
	/**
	 * Metodo que retorna el dni de la persona
	 * @return dni
	 */
	
	public String getDNI(){
		return dni;
	}
	
	/**
	 * Metodo que retorna la fecha de nacimiento en string
	 * @return fechaNacimiento
	 */
	
	public String getFechaNacimiento(){
		SimpleDateFormat aux = new SimpleDateFormat("dd/MM/YYYY");
		return aux.format(fechaNacimiento.getTime());
	}
	
	/**
	 * Metodo que retorna la persona como un String
	 */
	
	public String toString(){
		SimpleDateFormat aux = new SimpleDateFormat("dd/MM/YYYY");
		return dni+"\n"+nombre+"\n"+apellidos+"\n"+aux.format(fechaNacimiento.getTime());
	}
}
