package paquete;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.DefaultListModel;

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
	
	public void addDocencia(int identificador, int identificadorGrupo, char tipoGrupo){
		if(docenciaImpartida.get(identificador)!=null){
			docenciaImpartida.get(identificador).addGrupo(identificadorGrupo, tipoGrupo);
		}
		else
			docenciaImpartida.put(identificador, new Asignatura(identificador, identificadorGrupo, tipoGrupo));
		return;
	}
	
	public String getCategoria(){
		return categoria;
	}
	
	public int getHorasAsignables(){
		return horasAsignables;
	}
	
	public String getDepartamento(){
		return departamento;
	}
	
	public LinkedHashMap<Integer, Asignatura> getDocenciaImpartida(){
		return docenciaImpartida;
	}
	
	public int getHorasImpartidas(LinkedHashMap<Integer, Asignatura> mapaAsignaturas){
		int retorno=0;
		Set<Integer> keys = docenciaImpartida.keySet();
		for(int key:keys){
			ArrayList<Grupo> gruposProfesor = docenciaImpartida.get(key).getGrupos();
			ArrayList<Grupo> grupos = mapaAsignaturas.get(key).getGrupos();
			for(int i = 0; i<gruposProfesor.size(); i++){
				for(int j=0; j<grupos.size(); j++){
					if(grupos.get(j).getIdentificador()==gruposProfesor.get(i).getIdentificador()&&gruposProfesor.get(i).getTipo()==grupos.get(j).getTipo()){
						retorno += grupos.get(j).getDuracion();
					}
				}
			}
		}
		return retorno;
	}
	
	public void obtenerCalendario(String fichero, LinkedHashMap<Integer, Asignatura> mapaAsignaturas) throws IOException{
		File archivo = new File(fichero);
		BufferedWriter salida = new BufferedWriter(new FileWriter(archivo));
		salida.write("Dia\t Hora\t Asignatura\t Tipo grupo\t Id grupo\n");
		Set<Integer> keys = docenciaImpartida.keySet();
		for(int key:keys){
			ArrayList<Grupo> gruposProfesor = docenciaImpartida.get(key).getGrupos();
			ArrayList<Grupo> grupos = mapaAsignaturas.get(key).getGrupos();
			for(int i = 0; i<gruposProfesor.size(); i++){
				for(int j=0; j<grupos.size(); j++){
					if(grupos.get(j).getIdentificador()==gruposProfesor.get(i).getIdentificador()&&gruposProfesor.get(i).getTipo()==grupos.get(j).getTipo()){
						salida.write(grupos.get(j).getDia()+"; "+grupos.get(j).getHoraEntrada()+" ;"+mapaAsignaturas.get(key).getSiglas()+"; "+
					grupos.get(j).getTipo()+"; "+grupos.get(j).getIdentificador()+"\n");
					}
				}
			}
		}
		salida.close();
	}
	
	public String toString(){
		return super.toString()+"\n"+categoria+"\n"+departamento+"\n"+Integer.toString(horasAsignables)+"\n"+docenciaImpartida+"\n";
	}
	
	public String salidaFichero(){
		String auxiliarDocencia = "";
		Set<Integer> keys = docenciaImpartida.keySet();
		boolean ponercoma = false;
		for(int key:keys){
			if(ponercoma)
				auxiliarDocencia += "; ";
			auxiliarDocencia += docenciaImpartida.get(key).stringPersona();
			ponercoma = true;
		}
		return super.toString()+"\n"+categoria+"\n"+departamento+"\n"+horasAsignables+"\n"+auxiliarDocencia;
	}
	
	public void stringGrafico(DefaultListModel modelo){
		Set<Integer> keys = docenciaImpartida.keySet();
		modelo.addElement("Docencia Impartida");
		for(int key: keys){
			modelo.addElement(Gestion.mapaAsignaturas.get(key).getNombre());
		}
	}
	
	public boolean comprobarAsignacion(int identificador, int idGrupo, char tipoGrupo){
		if(docenciaImpartida.get(identificador)==null)
			return true;
		if(docenciaImpartida.get(identificador).comprobarGrupo(idGrupo, tipoGrupo))
			return false;
		return true;
	}
	
	public boolean comprobarHorario(int horaEntrada, int horaSalida, char dia, LinkedHashMap<Integer, Asignatura> mapaAsignaturas, Profesor p){
		Set<Integer> keys = docenciaImpartida.keySet();
		return Asignaturas.comprobarHorario(keys, horaEntrada, horaSalida, dia, p);
	}
	
	public boolean comprobarDocenciaVacia(){
		if(docenciaImpartida.size()==0)
			return true;
		else
			return false;
	}
}
