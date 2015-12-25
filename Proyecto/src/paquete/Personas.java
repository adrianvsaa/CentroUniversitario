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
				Calendar fechaNacimiento = Gestion.stringToCalendar(entrada.nextLine().trim());
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
		String[] token;
		String[] comando1;
		String[] comando2;
		try{
		token = instruccion.trim().split("\"");
		comando1 = token[0].trim().split("\\s+");
		comando2 = token[4].trim().split("\\s+");
		} catch (Exception e){
			Gestion.aviso(nComandos);
			return;
		}
		Persona p;
		if(token.length!=7&&token.length!=5){
			Gestion.aviso(nComandos);
			return;
		}
		String perfil = comando1[1].trim(), dni = comando1[2].trim(), nombre = token[1].trim(), apellidos = token[3].trim(),
				fecha1 = comando2[0].trim();
		if(!GestionErrores.comprobarDNI(dni)){
			Gestion.aviso("DNI incorrecto");
			return;
		}	
			if(perfil.equals("alumno")){
				if(comando1.length!=3||comando2.length!=2||token.length!=5){
					Gestion.aviso(nComandos);
					return;
				}
				Calendar fecha = Gestion.stringToCalendar(fecha1);
				String fecha2 = comando2[1].trim();
				if(!GestionErrores.comprobarFecha(fecha)){
					Gestion.aviso(fechaIncorrecta);
					return;
				}
				Calendar fechaIngreso = Gestion.stringToCalendar(fecha2);
				if(!GestionErrores.comprobarFechaIngreso(fechaIngreso, fecha)){
					Gestion.aviso(fechaIncorrecta);
					return;
				}
				if(mapa.get(dni)!=null){
					Gestion.aviso(alExi);
					return;
				}
				p = new Alumno(dni, nombre, apellidos, fecha, fechaIngreso);
				Gestion.aviso("OK");
			}
			else{
				if(token.length!=7||token[4].trim().split("\\s+").length!=2||token[6].trim().split("\\s+").length!=1){
					Gestion.aviso(nComandos);
					return;
				}
				String categoria = comando2[1], departamento = token[5].trim();
				int horasAsignables;
				try{
					horasAsignables = Integer.parseInt(token[6].trim());
				} catch(Exception e){
					Gestion.aviso(nComandos);
					return;
				}
				Calendar fecha = Gestion.stringToCalendar(token[4].trim().split("\\s+")[1]);
				if(!GestionErrores.comprobarFecha(fecha)){
					Gestion.aviso(fechaIncorrecta);
					return;
				}
				if(!GestionErrores.comprobarHorasAsignables(horasAsignables, categoria)){
					Gestion.aviso(hoInc);
					return;
				}
				if(mapa.get(dni)!=null){
					Gestion.aviso(prExi);
					return;
				}
				p =  new Profesor(dni, nombre, apellidos, fecha, categoria, 
						departamento, horasAsignables);
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
		Set<String> keys = Gestion.mapaAlumnos.keySet();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3, 1, 1 ));
		JFrame ventana =  new JFrame();
		for(String key:keys){
			panel.add(new JLabel("DNI"));
			panel.add(new JLabel(key));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Nombre:"));
			panel.add(new JLabel(Gestion.mapaAlumnos.get(key).getNombre()));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Apellidos:"));
			panel.add(new JLabel(Gestion.mapaAlumnos.get(key).getApellidos()));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Perfil:"));
			panel.add(new JLabel("Alumno"));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Fecha de nacimiento:"));
			panel.add(new JLabel(Gestion.mapaAlumnos.get(key).getFechaNacimiento()));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Fecha de Ingreso:"));
			panel.add(new JLabel(((Alumno)Gestion.mapaAlumnos.get(key)).getFechaIngreso()));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Asignaturas matriculado:"));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			LinkedHashMap<Integer, Asignatura> mapa1 = ((Alumno)Gestion.mapaAlumnos.get(key)).getDocenciaRecibida();
			Set<Integer> keys1 = mapa1.keySet();
			for(int key1 : keys1){
				panel.add(new JLabel(" "));
				panel.add(new JLabel(Gestion.mapaAsignaturas.get(key1).getNombre()));
				panel.add(new JLabel(" "));
			}
			panel.add(new JLabel("Asignaturas superadas:"));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			LinkedHashMap<Integer, Nota> mapa2 = ((Alumno)Gestion.mapaAlumnos.get(key)).getAsignaturasSuperadas();
			keys1 = mapa2.keySet();
			for(int key1 : keys1){
				panel.add(new JLabel(" "));
				panel.add(new JLabel(Gestion.mapaAsignaturas.get(key1).getNombre()));
				panel.add(new JLabel(Float.toString(mapa2.get(key1).getNota())));
			}
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
		}
		keys = Gestion.mapaProfesores.keySet();
		for(String key : keys){
			panel.add(new JLabel("DNI"));
			panel.add(new JLabel(key));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Nombre:"));
			panel.add(new JLabel(Gestion.mapaProfesores.get(key).getNombre()));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Apellidos"));
			panel.add(new JLabel(Gestion.mapaProfesores.get(key).getApellidos()));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Fecha de nacimiento:"));
			panel.add(new JLabel(Gestion.mapaProfesores.get(key).getFechaNacimiento()));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Perfil:"));
			panel.add(new JLabel("Profesor"));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Departamento:"));
			panel.add(new JLabel(((Profesor)Gestion.mapaProfesores.get(key)).getDepartamento()));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Categoria:"));
			panel.add(new JLabel(((Profesor)Gestion.mapaProfesores.get(key)).getCategoria()));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Horas Asignables:"));
			panel.add(new JLabel(Integer.toString(((Profesor)Gestion.mapaProfesores.get(key)).getHorasAsignables())));
			panel.add(new JLabel(" "));
			panel.add(new JLabel("Docencia Impartida:"));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			LinkedHashMap<Integer, Asignatura> mapa = ((Profesor)Gestion.mapaProfesores.get(key)).getDocenciaImpartida();
			Set<Integer> keys1 = mapa.keySet();
			for(int key1 : keys1){
				panel.add(new JLabel(" "));
				panel.add(new JLabel(Gestion.mapaAsignaturas.get(key1).getNombre()));
				panel.add(new JLabel(" "));
			}
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
			panel.add(new JLabel(" "));
		}
		JScrollPane impresion = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(300, 200, 1080, 720);
		ventana.add(impresion);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
}
