package paquete;

import java.util.Calendar;

public class Alumno extends Persona{
	private Calendar fechaIngreso = Calendar.getInstance();
	private float notaExpediente;
	
	public Alumno(String dni, String nombre, String apellidos, Calendar fechaNacimiento, Calendar fechaIngreso) {
		super(dni, nombre, apellidos, fechaNacimiento);
		this.fechaIngreso = fechaIngreso;
	}
	
	
}
