import java.io.FileNotFoundException;
import java.util.Scanner;

import generator.Generator;

public class MainSection {
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws FileNotFoundException {
		int nCol;
		int nRows;
		String tableName;
		
		System.out.println("Introduce el número de columnas: ");
		nCol = sc.nextInt();
		System.out.println("Introduce el número de filas: ");
		nRows = sc.nextInt();
		System.out.println("Introduce el nombre de la tabla: ");
		tableName = sc.next();
		Generator generador = new Generator(nCol, nRows, tableName);
		
		generador.defineTypes();
		generador.generateData();
		generador.generateSQLFile();
	}
}
