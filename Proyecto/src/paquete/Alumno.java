package paquete;


import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Set;
import java.io.IOException;
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
				if(aux2.length==1){
					this.docenciaRecibida.put(Integer.parseInt(aux2[0]), new Asignatura(Integer.parseInt(aux2[0])));
				}
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
	
	public void calcularNotaExpediente() throws IOException{
		if(!GestionErrores.comprobarExpediente(asignaturasSuperadas)){
			Gestion.editarArchivoAvisos(("El alumno"+super.getApellidos()+", "+super.getNombre()+" no tiene notas"));
		}
		Set<Integer> keys = asignaturasSuperadas.keySet();
		int i=0;
		float aux = 0;
		for(Integer key:keys){
			aux += asignaturasSuperadas.get(key).getNota();
			i++;
		}
		this.notaExpediente = aux/i;
		return;
	}
	
	public String salidaFichero(){
		SimpleDateFormat aux = new SimpleDateFormat("dd/MM/YYYY");
		String auxiliarSuperadas = "";
		Set<Integer> keys = asignaturasSuperadas.keySet();
		boolean ponercoma = false;
		for(int key:keys){
			if(ponercoma)
				auxiliarSuperadas += "; ";
			auxiliarSuperadas += key+" "+asignaturasSuperadas.get(key).toString();
			ponercoma =true;
		}
		ponercoma = false;
		String auxiliarDocencia = "";
		keys = docenciaRecibida.keySet();
		for(int key:keys){
			if(ponercoma)
				auxiliarDocencia += "; ";
			auxiliarDocencia += docenciaRecibida.get(key).salidaPersona();
			ponercoma = true;
		}
		return super.toString()+"\n"+aux.format(fechaIngreso.getTime())+"\n"+auxiliarSuperadas+"\n"+auxiliarDocencia;
	}
	
	public String toString(){
		SimpleDateFormat aux = new SimpleDateFormat("dd/MM/YYYY");
		return super.toString()+"\n"+aux.format(fechaIngreso.getTime())+"\n"+docenciaRecibida.values()+"\n"+asignaturasSuperadas+"\n";
	}
}
