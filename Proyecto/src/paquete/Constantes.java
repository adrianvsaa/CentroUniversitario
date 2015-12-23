package paquete;

public interface Constantes {
	//Constantes para comandos
	String insertaPersona = "IP\t\t--\t";
	String asignaCoordinador = "ACOORD\t--\t";
	String asignaCargaDocente = "ACDOC\t--\t";
	String matricularAlumno = "MAT\t\t--\t";
	String asignaGrupo = "AGRUPO\t--\t";
	String evaluarAsignatura = "EVALUA\t--\t";
	String calendarioProfesor = "CAlENP\t--\t";
	String expedienteAlumno = "EXP\t\t--\t";
	String cIncorrecto = "\t\t\tComando Incorrecto";
	String ordenaAlumnosNota = "OAXNOT\t--\t";
	String insertaAsignatura = "IASIG\t--\t";
	//Constantes generales
	String nComandos = "Numero de comandos incorrecto";
	String fechaIncorrecta = "Fecha Incorrecta";
	String solape = "Se genera solape";
	//Constantes para alumnos
	String alExi = "Alumno ya existente";
	String alInex = "Alumno inexistente";
	String alYaMat = "Ya es alumno de la asignatura indicada";
	String alNoMat = "Alumno no matriculado";
	String noRequisitos = "Alumno no cumple los requisitos";
	String eVacio = "Expediente vacio";

	//Constantes para profesores
	String prExi = "Profesor ya existente";
	String prInex = "Profesor inexistente";
	String noAsig = "Profesor sin asignaciones";
	String hoSupMax = "Numero de horas asignables superior al maximo";
	String hoInc = "Numero de horas incorrecto";
	//Constantes para Asignaturas
	String asInex = "Asignatura inexistente";
	String tGruInc = "Tipo de grupo incorrecto";
	String gInex = "Grupo inexistente"; 
	String yaEvaluada = "Asignatura ya evaluada en ese curso academico";
	String gAsig = "Grupo ya asignado";
	
	//Constantes ficheros
	String fNotas = "Fichero de notas inexistente";
	String fNotasLineas = "\n\t\t\tError en linea <";
	
}
