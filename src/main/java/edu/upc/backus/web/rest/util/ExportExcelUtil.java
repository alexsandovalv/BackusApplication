package edu.upc.backus.web.rest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.upc.backus.domain.ProgramaProduccion;

public class ExportExcelUtil {
	
	public static void writeToExcel(List<ProgramaProduccion> lpp, String filePath){
		
		//String filePath = System.getProperty("user.dir")+ "\\src\\main\\resources";
		String fileName = "\\BACKUSLATAS-1.xlsx";

		File file = new File(filePath + "\\" + fileName);
		System.out.println("File: " + file);
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);

			Workbook workbook = null;

			String fileExtension = fileName.substring(fileName.indexOf("."));

			if (fileExtension.equals(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (fileExtension.equals(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				System.out.println("Unsupported file to perform excel IO");
			}

			// ------------------------------------------------------------------
			// Plan de produccion
			// ------------------------------------------------------------------

			// variables
			int fila = 0;// fila
			Sheet s = workbook.getSheet("HOJA1"); // hoja a leerw
			Row r = null; // fila a escribir
			Cell pm = null; // producto + medida
			Cell c = null;

			System.out.println("---> Inicio de programa de produccion");

			Long idAnterior = new Long(0);
			// Double total = new Double(0);
			// fila = 13;
			for (int i = 13; i <= 24; i++) {
				for (int j = 1; j<=6 ; j++) {
					s.getRow(i).getCell(j).setCellValue(0);
				} 
			}
			
			for (int i = 0; i < lpp.size(); i++) {
				ProgramaProduccion pp = lpp.get(i);

				/*
				 * if (i > 0 && pp.getId().intValue() != idAnterior.intValue())
				 * { System.out.println(idAnterior + "-" + pp.getId());
				 * System.out.println("total: " + total);
				 * r.getCell(7).setCellValue(total); // fila += 1; total = new
				 * Double(0); }
				 */
				// Producto medida
				String productoMedida = pp.getProducto().getNombre() + " "
						+ pp.getMedidasLts().getCantidad();
				Double cantidad = pp.getCantidad().doubleValue() / 1000;
				String dia = pp.getDiaTrabajada();

				String replacePM = productoMedida.replace("\u00d1", "N");
				System.out.println("productoMedida: " + replacePM);

				if (replacePM.equalsIgnoreCase("CRISTAL 473.00")) {
					fila = 13;
				}
				if (replacePM.equalsIgnoreCase("CRISTAL 355.00")) {
					fila = 14;
				}
				if (replacePM.equalsIgnoreCase("CRISTAL 250.00")) {
					fila = 15;
				}
				if (replacePM.equalsIgnoreCase("PILSEN 473.00")) {
					fila = 16;
				}
				if (replacePM.equalsIgnoreCase("PILSEN 355.00")) {
					fila = 17;
				}
				if (replacePM.equalsIgnoreCase("CUSQUENA 355.00")) {
					fila = 18;
				}
				if (replacePM.equalsIgnoreCase("ICE 473.00")) {
					fila = 19;
				}
				if (replacePM.equalsIgnoreCase("ICE 355.00")) {
					fila = 20;
				}
				if (replacePM.equalsIgnoreCase("BARENA 355.00")) {
					fila = 21;
				}
				if (replacePM.equalsIgnoreCase("AGUA TONICA 250.00")) {
					fila = 22;
				}
				if (replacePM.equalsIgnoreCase("GUARANA 355.00")) {
					fila = 23;
				}
				if (replacePM.equalsIgnoreCase("MALTIN 269.00")) {
					fila = 24;
				}

				r = s.getRow(fila);

				System.out.println("fila: " + fila);

				pm = r.getCell(0);
				pm.setCellType(Cell.CELL_TYPE_STRING);
				pm.setCellValue(productoMedida);

				if (dia.equalsIgnoreCase("LUNES")) {
					c = r.getCell(1);
				} else if (dia.equalsIgnoreCase("MARTES")) {
					c = r.getCell(2);
				} else if (dia.equalsIgnoreCase("MIERCOLES")) {
					c = r.getCell(3);
				} else if (dia.equalsIgnoreCase("JUEVES")) {
					c = r.getCell(4);
				} else if (dia.equalsIgnoreCase("VIERNES")) {
					c = r.getCell(5);
				} else {
					c = r.getCell(6);
				}

				c.setCellType(Cell.CELL_TYPE_NUMERIC);
				c.setCellValue(cantidad);

				// total += cantidad;

				idAnterior = pp.getId();
				/*
				 * if (i == lpp.size() - 1) r.getCell(7).setCellValue(total);
				 */

			}


			// Writing File
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			outputStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
