/**
 * 
 */
package vista;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import controller.BotigaImportExportData;
import controller.BotigaImportExportJSON;
import controller.BotigaImportExportText;
import controller.ClientsDao;
import controller.ProductesDao;
import funciones.UtilConsole;
import model.Persistable;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 * Logger log = Logger.getLogger("logs");
 */
public class IniciVistaController {
	 
	public static void main(String[] args) throws SecurityException, IOException {
		
		Logger log = Logger.getLogger("logs");
		FileHandler fh = new FileHandler("res/log.txt", true);
		fh.setFormatter(new SimpleFormatter());
		log.addHandler(fh);
		log.setLevel(Level.ALL);
		log.info("inicio");
		
		int opcion = 0;
		ProductesDao pd = new ProductesDao();
		ClientsDao cd = new ClientsDao();
		BotigaImportExportText ImpExText = new BotigaImportExportText();
		BotigaImportExportData ImpExData = new BotigaImportExportData();
		BotigaImportExportJSON ImpExJSON = new BotigaImportExportJSON();
		
		Scanner sc = new Scanner(System.in);
		int[] array = new int[5];
		array[0] = 0;
		array[1] = 1;
		array[2] = 2;
		array[3] = 3;
		array[4] = 4;
		try {
			do {
				//menu
				System.out.println("0. Salir");
				System.out.println("1. Productes");
				System.out.println("2. Clients");
				System.out.println("3. Proveïdors");
				System.out.println("4. Probocar NumberFormatException");
				//opcion = UtilConsole.pedirInt();
				opcion = sc.nextInt();
				//segun lo que se escoge, se llama al submenu del controlador que toca
				if (array[opcion] == 1) {
					ProductesVistaController.inicio(pd, ImpExText, ImpExData, ImpExJSON);
				}else  if (array[opcion] == 2){
					ClientsVistaController.inicio(cd);
				}else  if (array[opcion] == 3){
					//TODO 
				}else  if (array[opcion] == 4){
					System.out.println("Si escibes texto se produce el error, si solo hay numeros no.");
					String input = sc.next();
					int num = Integer.parseInt(input);
					System.out.println(num);
				}
				log.fine("Fue todo bien");
			} while (array[opcion] != 0);	
		} catch (IndexOutOfBoundsException ex) {
			log.log(Level.SEVERE, "Error al pedir el valor, solo del 0 al 3!", ex);
		} catch (InputMismatchException ex) {
			log.log(Level.SEVERE, "Error al pedir el valor, tiene que ser un int!", ex);
		} catch (NumberFormatException ex) {
			log.log(Level.SEVERE, "Error al pedir el valor, tiene que ser un int!", ex);
		}
	}
	
	/**
	 * 
	 */
	public static void imprimir(Persistable<?> p) {
		for (Object obj : p.getMap().values()) {
			System.out.println(obj);
		}
	}
	
}
