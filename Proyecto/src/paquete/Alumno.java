package paquete;


import java.util.Calendar;
import java.util.LinkedHashMap;
import java.text.SimpleDateFormat;

public class Alumno extends Persona{
	private Calendar fechaIngreso = Calendar.getInstance();
	private float notaExpediente;
	private LinkedHashMap<Integer, Asignatura> docenciaRecibida = new LinkedHashMap<Integer, Asignatura>();
	private LinkedHashMap<Integer, Nota> asignaturasSuperadas = new LinkedHashMap<Integer, Nota>();
	
	
	public Alumno(String dni, String nombre, String apellidos, Calendar fechaNacimiento, Calendar fechaIngreso, String docenciaRecibida,
			String asignaturasSuperadas) {
		super(dni, nombre, apellidos, fechaNacimiento);
		this.fechaIngreso = fechaIngreso;
		if(asignaturasSuperadas.length()!=0){
			String aux[] = asignaturasSuperadas.split(";");
			for(int i= 0; i<aux.length; i++){
				String[] aux2 = aux[i].trim().split(" ");
				Nota n = new Nota(Float.parseFloat(aux2[2]), aux2[1].trim());
				this.asignaturasSuperadas.put(Integer.parseInt(aux2[0].trim()), n);
			}
		}
		if(docenciaRecibida.length()!=0){
			String aux[] = docenciaRecibida.split(";");
			for(int i=0; i<aux.length; i++) {
				String aux2[] = aux[i].trim().split(" ");
				if(aux2.length==1)
					this.docenciaRecibida.put(Integer.parseInt(aux2[0]), new Asignatura());
				else {
					if(this.docenciaRecibida.get(Integer.parseInt(aux2[0]))!=null){
						this.docenciaRecibida.get(Integer.parseInt(aux2[0])).addGrupo(Integer.parseInt(aux2[2]), aux2[1].toCharArray()[0]);
					}
					else {
						this.docenciaRecibida.put(Integer.parseInt(aux2[0]), new Asignatura(Integer.parseInt(aux2[0]), Integer.parseInt(aux2[2]),
								aux2[1].toCharArray()[0]));
					}
				}
			}
		}
		
	}
	
	public String toString(){
		SimpleDateFormat aux = new SimpleDateFormat("HH:mm");
		return super.toString()+aux.format(fechaIngreso.getTime());
	}
}
