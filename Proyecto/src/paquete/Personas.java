package paquete;

import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public abstract class Personas implements Constantes{
	private static File fichero = new File("personas.txt");
	public static void poblar() throws FileNotFoundException{
		if(!fichero.exists()){
					
				}
		else {
			Scanner entrada = new Scanner(new FileInputStream(fichero));
			while(entrada.hasNextLine()){
				String auxiliar = entrada.nextLine().trim();
				String dni = entrada.nextLine().trim();
				String nombre = entrada.nextLine().trim();
				String apellidos = entrada.nextLine().trim();
				Calendar fechaNacimiento = Gestion.stringToCalendar(entrada.nextLine());
				if(auxiliar.trim().equals("alumno")){
					Calendar fechaIngreso = Gestion.stringToCalendar(entrada.nextLine().trim());
					String asignaturasSuperadas = entrada.nextLine().trim();
					String docenciaRecibida = entrada.nextLine().trim();
					Alumno a = new Alumno(dni.trim(), nombre.trim(), apellidos.trim(), fechaNacimiento, fechaIngreso, docenciaRecibida, 
							asignaturasSuperadas);
					Gestion.mapaAlumnos.put(dni, a);
				}
				else{
					String categoria = entrada.nextLine().trim();
					String departamento = entrada.nextLine().trim();
					String horas = entrada.nextLine().trim();
					String docenciaImpartida = entrada.nextLine().trim();
					Profesor p = new Profesor(dni.trim(), nombre.trim(), apellidos.trim(), fechaNacimiento, categoria, departamento, 
							Integer.parseInt(horas), docenciaImpartida);
					Gestion.mapaProfesores.put(dni, p);
				}
				if(entrada.hasNext())
					entrada.nextLine();
				}
			entrada.close();
		}
	}
	
	public static void añadir(String instruccion, LinkedHashMap<String, Persona> mapa) throws IOException{
		String[] token = instruccion.trim().split("\"");
		String[] comando1 = token[0].trim().split("\\s+");
		Persona p;
		if(token.length!=7&&token.length!=5){
			Gestion.aviso(nComandos);
			return;
		}
		if(!GestionErrores.comprobarDNI(comando1[2])){
			Gestion.aviso("DNI incorrecto");
			return;
		}	
			if(comando1[1].equals("alumno")){
				String[] comando2 = token[4].trim().split("\\s+");
				Calendar fecha = Gestion.stringToCalendar(comando2[0]);
				if(comando1.length!=3||comando2.length!=2||token.length!=5){
					Gestion.aviso(nComandos);
					return;
				}
				if(!GestionErrores.comprobarFecha(fecha)){
					Gestion.aviso(fechaIncorrecta);
					return;
				}
				Calendar fechaIngreso = Gestion.stringToCalendar(comando2[1]);
				if(!GestionErrores.comprobarFechaIngreso(fechaIngreso, fecha)){
					Gestion.aviso(fechaIncorrecta);
					return;
				}
				if(mapa.get(comando1[2])!=null){
					Gestion.aviso(alExi);
					return;
				}
				p = new Alumno(comando1[2], token[1], token[3], fecha, fechaIngreso);
				Gestion.aviso("OK");
			}
			else{
				if(token.length!=7||token[4].trim().split("\\s+").length!=2||token[6].trim().split("\\s+").length!=1){
					Gestion.aviso(nComandos);
					return;
				}
				Calendar fecha = Gestion.stringToCalendar(token[4].trim().split(" ")[1]);
				if(!GestionErrores.comprobarFecha(fecha)){
					Gestion.aviso(fechaIncorrecta);
					return;
				}
				if(!GestionErrores.comprobarHorasAsignables(Integer.parseInt(token[6].trim()), token[4].trim().trim().split("\\s+")[0])){
					Gestion.aviso(hoInc);
					return;
				}
				if(mapa.get(comando1[2])!=null){
					Gestion.aviso(prExi);
					return;
				}
				p =  new Profesor(comando1[2], token[1].trim(), token[3].trim(), fecha, token[4].trim().split("\\s+")[0], 
						token[5].trim(), Integer.parseInt(token[6].trim()));
				Gestion.aviso("OK");
			}
		mapa.put(p.getDNI(), p);
	}
	
	public static void imprimir() throws IOException{
		BufferedWriter salida = new BufferedWriter(new FileWriter(fichero));
		Set<String> keys = Gestion.mapaAlumnos.keySet();
		boolean ponerAsterisco = false;
		for(String key:keys){
			if(ponerAsterisco)
				salida.write("\n*\n"+"alumno\n"+((Alumno)Gestion.mapaAlumnos.get(key)).stringFichero());
			else
				salida.write("alumno\n"+((Alumno)Gestion.mapaAlumnos.get(key)).stringFichero());
			ponerAsterisco = true;
		}
		keys = Gestion.mapaProfesores.keySet();
		for(String key:keys) {
			if(ponerAsterisco)
				salida.write("\n*\n"+"profesor\n"+((Profesor)Gestion.mapaProfesores.get(key)).salidaFichero());
			else
				salida.write("profesor\n"+((Profesor)Gestion.mapaProfesores.get(key)).salidaFichero());
			ponerAsterisco = true;
		}
		salida.close();
	}
	
	
	public static void mostrarPantalla(){
		DefaultListModel modelo = new DefaultListModel();
		Set<String> keys = Gestion.mapaAlumnos.keySet();
		JPanel panel;
		JFrame ventana =  new JFrame();
		for(String key:keys){
			modelo.addElement(key);
			modelo.addElement(Gestion.mapaAlumnos.get(key).getApellidos()+", "+Gestion.mapaAlumnos.get(key).getNombre());
			modelo.addElement("Fecha de nacimiento: "+Gestion.mapaAlumnos.get(key).getFechaNacimiento());
			modelo.addElement("Fecha de ingreso: "+((Alumno)Gestion.mapaAlumnos.get(key)).getFechaIngreso());
			modelo.addElement("Asignaturas superadas:");
			((Alumno)Gestion.mapaAlumnos.get(key)).stringGrafico(modelo);
			modelo.addElement(" ");
		}
		keys  = Gestion.mapaProfesores.keySet();
		for(String key:keys){
			modelo.addElement(key);
			modelo.addElement(Gestion.mapaProfesores.get(key).getApellidos()+", "+Gestion.mapaProfesores.get(key).getNombre());
			modelo.addElement("Fecha de nacimiento: "+Gestion.mapaProfesores.get(key).getFechaNacimiento());
			modelo.addElement("Departamento: "+((Profesor)Gestion.mapaProfesores.get(key)).getDepartamento());
			modelo.addElement("Categoria: "+((Profesor)Gestion.mapaProfesores.get(key)).getCategoria());
			modelo.addElement("Horas asignables: "+((Profesor)Gestion.mapaProfesores.get(key)).getHorasAsignables());
			((Profesor)Gestion.mapaProfesores.get(key)).stringGrafico(modelo);
			modelo.addElement(" ");
		}
		JList listaAlumnos = new JList(modelo);
		listaAlumnos.setLayout(new BoxLayout(listaAlumnos, BoxLayout.Y_AXIS));
		JScrollPane impresion = new JScrollPane(listaAlumnos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(300, 200, 1080, 720);
		ventana.add(impresion);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
}
