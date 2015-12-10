package paquete;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Alumno extends Persona{
	private Calendar fechaIngreso = Calendar.getInstance();
	private LinkedHashMap<Integer, Asignatura> docenciaRecibida = new LinkedHashMap<Integer, Asignatura>();
	private LinkedHashMap<Integer, Nota> asignaturasSuperadas = new LinkedHashMap<Integer, Nota>();
	
	public Alumno(String dni, String nombre, String apellidos, Calendar fechaNacimiento, Calendar fechaIngreso){
		super(dni, nombre, apellidos, fechaNacimiento);
		this.fechaIngreso = fechaIngreso;
	}
	
	
	public Alumno(String dni, String nombre, String apellidos, Calendar fechaNacimiento, Calendar fechaIngreso, String docenciaRecibida,
			String asignaturasSuperadas) {
		super(dni, nombre, apellidos, fechaNacimiento);
		this.fechaIngreso = fechaIngreso;
		if(asignaturasSuperadas.trim().length()!=0){
			String aux[] = asignaturasSuperadas.trim().split(";");
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
	
	public float calcularNotaExpediente() throws IOException{
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
		return aux/i;
	}
	
	public void matricula(Asignatura asignatura){
		docenciaRecibida.put(asignatura.getIdentificador(), asignatura);
		return;
	}
	
	public void asignarGrupo(Asignatura asignatura, char tipoGrupo, int idGrupo){
		docenciaRecibida.get(asignatura.getIdentificador()).addGrupo(idGrupo, tipoGrupo);
		return;
	}
	
	public void evaluarAsignatura(int idAsignatura, Nota nota){
		if(nota.getNota()>=5){
			asignaturasSuperadas.put(idAsignatura, nota);
			docenciaRecibida.remove(idAsignatura);
		}
		else
			docenciaRecibida.remove(idAsignatura);
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
	
	public LinkedHashMap<Integer, Nota> getAsignaturasSuperadas(){
		return asignaturasSuperadas;
	}
	
	public void expediente(String fichero, LinkedHashMap<Integer, Asignatura> mapaAsignaturas)throws IOException{
		File archivo = new File(fichero);
		BufferedWriter salida = new BufferedWriter(new FileWriter(archivo));
		Set<Integer> keys = asignaturasSuperadas.keySet();
		for(int key:keys){
			asignaturasSuperadas.get(key).setNombreAsignatura(mapaAsignaturas.get(key).getNombre());
			asignaturasSuperadas.get(key).setCursoAsignatura(mapaAsignaturas.get(key).getCurso());
		}
		List<Nota> lista = new LinkedList<Nota>(asignaturasSuperadas.values());
		Collections.sort(lista, new ComparadorNombre());
		Collections.sort(lista);
		for (int i = 0; i < lista.size(); i++) {
			salida.write(lista.get(i).getCursoAsignatura()+" "+lista.get(i).getNombreAsignatura()+" "+ lista.get(i).getNota()+" "
					+lista.get(i).getAnoAcademico()+"\n");
		}
		salida.close();
	}
	
	public boolean comprobarEvaluacion(String anoAcademico, int idAsignatura){
		if(asignaturasSuperadas.get(idAsignatura)==null)
			return true;
		if(asignaturasSuperadas.get(idAsignatura).getAnoAcademico().trim().equals(anoAcademico.trim()))
			return false;
		return true;
	}
	
	public boolean comprobarMatricula(int idAsignatura){
		if(docenciaRecibida.get(idAsignatura)==null)
			return false;
		else
			return true;
	}
	
	public boolean comprobarAprobado(int idAsignatura){
		if(asignaturasSuperadas.get(idAsignatura)!=null)
			return true;
		return false;
			
	}
	
	public boolean comprobarHorario(int horaEntrada, int horaSalida, char dia, LinkedHashMap<Integer, Asignatura> mapaAsignaturas){
		boolean opcion = true;
		Set<Integer> keys = docenciaRecibida.keySet();
		for(int key:keys){
			ArrayList<Grupo> grupos = mapaAsignaturas.get(key).getGrupos();
			ArrayList<Grupo> gruposAlumno = docenciaRecibida.get(key).getGrupos();
			for(int i=0; i<gruposAlumno.size(); i++){
				for(int j=0; j<grupos.size(); j++){
					if(gruposAlumno.get(i).getIdentificador()==grupos.get(j).getIdentificador()&&gruposAlumno.get(i).getTipo()==
							grupos.get(j).getTipo()){
						if(grupos.get(j).getDia()!=dia)
							continue;
						else{
							if(grupos.get(j).getHoraEntrada()==horaEntrada||horaEntrada-grupos.get(j).getHoraSalida()<0){
								opcion = false;
								break;
							}
						}
					}
				}
			}
			if(!opcion)
				break;		
		}
		return opcion;
	}
	
	public boolean comprobarGrupo(int idAsignatura, char tipoGrupo){
		if(docenciaRecibida.get(idAsignatura)==null)
			return false;
		if(docenciaRecibida.get(idAsignatura).comprobarGrupoTipo(tipoGrupo))
			return true;
		return false;
	}
}
