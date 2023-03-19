
package controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import model.BotigaImportExportable;
import model.Persistable;
import model.Producte;
import model.ProducteAbstract;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**@author Sergi Valenzuela García
 * M03-UF4 
 * 10 mar 2023
 */
public class BotigaImportExportJSON implements BotigaImportExportable<ProducteAbstract>{
	
	@Override
	public Map<Integer, ProducteAbstract> importar(String nomFitxer) {
		
		HashMap<Integer, ProducteAbstract> productesMap = new HashMap<Integer, ProducteAbstract>();

		try (JsonReader getLocalJsonFile = new JsonReader(new FileReader(nomFitxer))) {
			
			Type mapTokenType = new TypeToken<Map<Integer, Producte>>(){}.getType();

			productesMap = new Gson().fromJson(getLocalJsonFile, mapTokenType);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productesMap;
	}

	@Override
	public boolean exportar(String nomFitxer, Persistable<ProducteAbstract> dao) {
		
		FileWriter fw = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			fw = new FileWriter(nomFitxer);
			gson.toJson(dao.getMap(), fw);
			System.out.println("Datos exportados en: " + nomFitxer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
