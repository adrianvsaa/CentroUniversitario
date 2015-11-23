package paquete;

import java.util.Calendar;

public class Profesor extends Persona{
	private String categoria;
	private String departamento;
	private int horasAsignables;
	
	public Profesor(String dni, String nombre, String apellidos, Calendar fechaNacimiento, String categoria, String departamento, int horasAsignables) {
		super(dni, nombre, apellidos, fechaNacimiento);
		this.categoria = categoria;
		this.departamento = departamento;
		this.horasAsignables = horasAsignables;
		
	}

}
