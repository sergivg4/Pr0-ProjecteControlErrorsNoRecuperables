/**
 * 
 */
package vista;

import java.util.ArrayList;

import controller.BotigaImportExportData;
import controller.BotigaImportExportJSON;
import controller.BotigaImportExportText;
import controller.ProductesDao;
import funciones.UtilConsole;
import model.Pack;
import model.Producte;
import model.ProducteAbstract;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class ProductesVistaController {

	public static void inicio(ProductesDao pd, BotigaImportExportText ImpExText, BotigaImportExportData ImpExData, BotigaImportExportJSON ImpExJSON) {
		
		//Cargar datos
		pd.obrirFitxer();
		
		int opcion;
		do {
			// menu
			System.out.println("0. Salir");
			System.out.println("1. Afegir");
			System.out.println("2. Buscar");
			System.out.println("3. Eliminar");
			System.out.println("4. Modificar");
			System.out.println("5. Mostrar");
			System.out.println("6. Importar");
			System.out.println("7. Exportar");
			opcion = UtilConsole.pedirInt();
			// segun lo que se escoge, se llama al submenu del controlador que toca
			if (opcion == 1) {
				afegirProducte(pd, false, 0);
			} else if (opcion == 2) {
				System.out.println(comprobarId(pd));
			}else if (opcion == 3) {
				pd.eliminar(comprobarId(pd).getId());
			}else if(opcion == 4) {
				int id = comprobarId(pd).getId();
				afegirProducte(pd, true, id);
			} else if (opcion == 5) {
				IniciVistaController.imprimir(pd);
			}else if(opcion == 6) {
				pd.importarMenu(ImpExText, ImpExData, ImpExJSON);
			} else if (opcion == 7) {
				menuExportar(pd, ImpExText, ImpExData, ImpExJSON);
			}
		} while (opcion != 0);
			//Guardar datos
			pd.guardarFitxer();
	}

	/**
	 * 
	 */
	
	private static void menuExportar(ProductesDao pd, BotigaImportExportText ImpExText, BotigaImportExportData ImpExData, BotigaImportExportJSON ImpExJSON) {
		int opcion;
		do {
			// menu
			System.out.println("Exportar en format:");
			System.out.println("0. Salir");
			System.out.println("1. Format text");
			System.out.println("2. Format data");
			System.out.println("3. Format JSON");
			opcion = UtilConsole.pedirInt();
			// segun lo que se escoge, se llama al submenu del controlador que toca
			if (opcion == 1) {
				ImpExText.exportar("res/datos.txt", pd);
				opcion = 0;
			} else if (opcion == 2) {
				ImpExData.exportar("res/datos.dat", pd);
				opcion = 0;
			}else if (opcion == 3) {
				ImpExJSON.exportar("res/datos.json", pd);
				opcion = 0;
			}
		} while (opcion != 0);
	}
	
	private static ProducteAbstract comprobarId(ProductesDao pd) {
		int id = -1;
		ProducteAbstract p = null;
		do {
			IniciVistaController.imprimir(pd);
			System.out.println("Introduce la id: ");
			id = UtilConsole.pedirInt();
			p = (ProducteAbstract)pd.buscar(id);
			if (p == null) {
				System.out.println("id erroneo");
			}
		} while (p == null);
		return p;
	}

	/**
	 * 
	 */
	private static void afegirProducte(ProductesDao pd, boolean mod, int id) {

		ProducteAbstract prod = null;
		boolean crear = false;
		int opcion = 0;
		do {
			// submenu
			System.out.println("Que quieres crear?");
			System.out.println("1. Producte");
			System.out.println("2. Pack");
			opcion = UtilConsole.pedirInt();
			if (opcion == 1 || opcion == 2) {
				crear = true;
			}

		} while (!crear);
		if (!mod) {
			System.out.println("id");
			id = UtilConsole.pedirInt();
		}
		System.out.println("nom");
		String nom = UtilConsole.pedirString();
		if (opcion == 1) {
			System.out.println("Stock");
			int stock = UtilConsole.pedirInt();
			System.out.println("Preu");
			double preu = UtilConsole.pedirDouble();
			prod = new Producte(id, nom, stock, preu);
		} else if (opcion == 2) {
			System.out.println("Productes");
			ArrayList<Producte> prodsTemp = new ArrayList<>();
			int ids = -1;
			do {
				System.out.println("Escribe una id o 0 para salir");
				ids = UtilConsole.pedirInt();
				ProducteAbstract p = (Producte)pd.buscar(ids);
				if (p != null) {
					if (p instanceof Producte) {
						prodsTemp.add((Producte) p);
					} else {
						System.out.println("El id no existe o no es un producto");
					}
				}
			} while (ids != 0);
			System.out.println("Escribe el descuento del pack");
			double descompte = UtilConsole.pedirDouble();
			prod = new Pack(id, nom, prodsTemp, descompte);
		}
		if (prod != null) {
			pd.guardar(prod);
			//System.out.println("GUARDANDO" + prod);
		} else {
			System.out.println("La creacion ha fallado!");
			return;
		}
	}	

}
