package paquete;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Set;

public class Profesor extends Persona{
	private String categoria;
	private String departamento;
	private int horasAsignables;
	private LinkedHashMap<Integer, Asignatura> docenciaImpartida = new LinkedHashMap<Integer, Asignatura>();
	
	public Profesor(String dni, String nombre, String apellidos, Calendar fechaNacimiento, String categoria, String departamento, int horasAsignables){
		super(dni, nombre, apellidos, fechaNacimiento);
		this.categoria = categoria;
		this.departamento = departamento;
		this.horasAsignables = horasAsignables;
	}
	
	public Profesor(String dni, String nombre, String apellidos, Calendar fechaNacimiento, String categoria, String departamento, int horasAsignables, 
			String docenciaImpartida) {
		super(dni, nombre, apellidos, fechaNacimiento);
		this.categoria = categoria;
		this.departamento = departamento;
		this.horasAsignables = horasAsignables;
		if(docenciaImpartida.length()>0){
			String aux[] = docenciaImpartida.split(";");
			for(int i=0; i<aux.length; i++) {
				String aux2[] = aux[i].trim().split(" ");
				if(this.docenciaImpartida.get(Integer.parseInt(aux2[0]))!=null){
					this.docenciaImpartida.get(Integer.parseInt(aux2[0])).addGrupo(Integer.parseInt(aux2[2]), aux2[1].toCharArray()[0]);
				}
				else {
					this.docenciaImpartida.put(Integer.parseInt(aux2[0]), new Asignatura(Integer.parseInt(aux2[0]), Integer.parseInt(aux2[2]),
							aux2[1].toCharArray()[0]));
				}
			}
		}
		
	}
	
	public String salidaFichero(){
		String auxiliarDocencia = "";
		Set<Integer> keys = docenciaImpartida.keySet();
		boolean ponercoma = false;
		for(int key:keys){
			if(ponercoma)
				auxiliarDocencia += "; ";
			auxiliarDocencia += docenciaImpartida.get(key).salidaPersona();
			ponercoma = true;
		}
		return super.toString()+"\n"+categoria+"\n"+departamento+"\n"+horasAsignables+"\n"+auxiliarDocencia;
	}
	
	public String toString(){
		return super.toString()+"\n"+categoria+"\n"+departamento+"\n"+Integer.toString(horasAsignables)+"\n"+docenciaImpartida+"\n";
	}

}
