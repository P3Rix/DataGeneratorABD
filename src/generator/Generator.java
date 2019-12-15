package generator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class Generator {
	int nCol;
	int nRows;
	String tableName;
	String[] colNames;
	String[] types;
	Object[] values;
	Object[][] resValues;
	Scanner sc = new Scanner(System.in);
	Path path = Paths.get("/home/peri/Descargas/random-name-master/names.txt");
	ArrayList<String> names;
	
	public Generator(int nCol, int nRows, String tableName) {
		this.nCol = nCol;
		this.nRows = nRows;
		colNames = new String[nCol];
		types = new String[nCol];
		values = new Object[nCol];
		resValues = new Object[nRows][nCol];
		this.tableName = tableName;
		
		generateNamesFile();
	}
	
	public void defineTypes() {
		int selection;
		for(int i = 0; i < nCol; i++) {
			System.out.println("Introduce el tipo de dato para la columna " + i + ":\n1.int\n2.float\n3.bool\n4.TimeStamp\n5.Varchar\n6.VarCharName");
			selection = sc.nextInt();
			
			switch(selection) {
				case 1:
					types[i] = "int";
					//values[i] = generateRandomInt();
					break;
				case 2:
					types[i] = "float";
					//values[i] = generateRandomFloat();
					break;
				case 3:
					types[i] = "bool";
					//values[i] = generateRandomBool();
					break;
				case 4:
					types[i] = "timestamp";
					//values[i] = generateRandomTimeStamp();
					break;
				case 5:
					types[i] = "string";
					//values[i] = generateRandomName();
					break;
				case 6:
					types[i] = "string";
					//values[i] = generateRandomName();
					break;
				default:
					i--;	
			}
			
		}
		
		System.out.println("N columnas: " + nCol);
		
		/*for(int i = 0; i < nCol; i++) {
			System.out.print("Tipo: " + types[i]);
			System.out.println(" Valor: " + values[i]);
		}*/
	}
	
	public void generateData() {
		for(int i = 0; i < nRows; i++) {
			for(int j = 0; j < nCol; j++) {
				switch(types[j]) {
					case "int":
						resValues[i][j] = generateRandomInt();
						break;
					case "float":
						resValues[i][j] = generateRandomFloat();
						break;
					case "bool":
						resValues[i][j] = generateRandomBool();
						break;
					case "timestamp":
						resValues[i][j] = generateRandomTimeStamp();
						break;
					case "string":
						resValues[i][j] = generateRandomName();
						break;
					default:
						j--;
				}
			}
		}
		
		for(int i = 0; i < nRows; i++) {
			//System.out.println("Fila: " + i);
			for(int j = 0; j < nCol; j++) {
				//System.out.println("Columna: " + j + " valor: " + resValues[i][j]);
			}
			//System.out.println();
		}
	}
	
	private String generateSQLFileString() {
		String res = "";
		for(int i = 0; i < nRows; i++) {
			res += "insert into " + tableName + " values (";
			for(int j = 0; j < nCol - 1; j++) {
				//System.out.println("Columna: " + j + " valor: " + resValues[i][j]);
				res += "\'" + resValues[i][j] + "\', ";
			}
			res += "\'" + resValues[i][nCol - 1] + "\');";
			res += "\n";
			//System.out.println();
		}
		
		System.out.println(res);
		
		return res;
	}
	
	public void generateSQLFile() throws FileNotFoundException {
		String sqlQuerys = generateSQLFileString();
		try (PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "/inserts.sql")) {
		    out.println(sqlQuerys);
		    System.out.println("Archivo guardado en: "+ System.getProperty("user.dir"));
		}
	}
	
	public int generateRandomInt() {
		return (int) (Math.random() * 100);
	}
	
	public float generateRandomFloat() {
		return (float) (Math.random() * 100);
	}
	
	public boolean generateRandomBool() {
		if(Math.random() > 0.5) {
			return true;
		} else {
			return false;
		}
	}
	
	public Timestamp generateRandomTimeStamp() {
		long now = System.currentTimeMillis();
		long x = ((long) (Math.random() * 100000));
		now -= x;
		Timestamp time = new Timestamp(now);
		
		return time;
	}
	
	public void generateNamesFile() {
		names = new ArrayList<String>();
		try(Stream<String> lines = Files.lines(path)) {
			lines.forEach(line->names.add(line));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String generateRandomName() {
		int index = names.size() - 1;
		int i = (int) (Math.random() * index);
		return names.get(i);
	}
}
