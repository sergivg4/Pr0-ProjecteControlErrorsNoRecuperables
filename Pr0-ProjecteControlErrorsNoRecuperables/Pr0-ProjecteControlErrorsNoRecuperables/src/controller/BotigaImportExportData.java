
package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import model.BotigaImportExportable;
import model.Pack;
import model.Persistable;
import model.Producte;
import model.ProducteAbstract;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class BotigaImportExportData implements BotigaImportExportable<ProducteAbstract>{

	private static Map<Integer, ProducteAbstract> productesMap = new HashMap<Integer, ProducteAbstract>();
	
	@Override
	public Map<Integer, ProducteAbstract> importar(String nomFitxer) {
		//pack para poder leer datos
		try(DataInputStream dis = new DataInputStream(new FileInputStream(nomFitxer))) {
				while (dis.available() > 0) {
					int id = dis.readInt();
					String nom = dis.readUTF();
					int stock = dis.readInt();
					double precio = dis.readDouble();
	//				System.out.println("Id: " + id);
	//				System.out.println("Nombre: " + nom);
	//				System.out.println("Stock: " + stock);
	//				System.out.println("Precio: " + precio);
					
					ProducteAbstract tempProd = new Producte( id, nom, stock, precio);
					productesMap.put(id, tempProd);
				}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return productesMap;
	}

	@Override
	public boolean exportar(String nomFitxer, Persistable<ProducteAbstract> dao) {
		
		//creamos el pack de objetos de escritura de datos
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(nomFitxer))){
			
			for (Map.Entry<Integer, ProducteAbstract> entry : dao.getMap().entrySet()) {
				ProducteAbstract tempProd = entry.getValue();
				if (tempProd instanceof Producte) {
					Producte tempProd2 = (Producte)dao.buscar(tempProd.getId());
					dos.writeInt(tempProd2.getId());
					dos.writeUTF(tempProd2.getNom());
					dos.writeInt(tempProd2.getStock());
					dos.writeDouble(tempProd2.getPrecio());
				}else if(tempProd instanceof Pack) {
					//Todavía no se puede exportar packs
				}
			}
			System.out.println("Datos exportados en: " + nomFitxer);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
}
