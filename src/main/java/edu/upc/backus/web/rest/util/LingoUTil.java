package edu.upc.backus.web.rest.util;

import com.lindo.Lingd14;

public class LingoUTil {

	// Load the Lingo JNI interface
	static {
		// System.loadLibrary("Lingj90");
		System.loadLibrary("Lingj14");
	}

	// Create a new Lingo object, which we will use to interface with Lingo
	private Lingd14 lng = null;

	// Stores the Lingo JNI environment pointer
	Object pLngEnv;

	int nLastIterationCount;

	public LingoUTil() {
		lng = new Lingd14();
	}

	public boolean executeLingoFile(String lingoFile, String logFile) {
		lingoFile += "\\BACKUS_FOR-SUM-BIN-EXEL.lg4";
		logFile += "\\BACKUS_FOR-SUM-BIN-EXEL.log";
		System.out.println("Process lingo " + lingoFile);
		// Cargando Librerias Lingo
		try {

			// Create the Lingo environment.
			// We do this here in the constructor so as not to repeat this
			// for each subsequent solve, improving performance.
			// Be sure to delete the Lingo environment on exit.
			pLngEnv = lng.LScreateEnvLng();
			if (pLngEnv == null) {
				System.out.println("***Unable to create Lingo environment***");
				return false;
			}

			int nErr = Lingd14.LSopenLogFileLng(pLngEnv, logFile);
			if (pLngEnv == null) {
				System.out.println("***Unable to create Lingo environment***");
				return false;
			}

			// construct the script
			// echo input to log file
			String sScript = "SET ECHOIN 1" + "\n";

			// load the model from disk
			sScript = sScript + "TAKE " + lingoFile + "\n";

			// view the formulation
			sScript = sScript + "LOOK ALL" + "\n";

			// solve
			sScript = sScript + "GO" + "\n";

			// exit script processor
			sScript = sScript + "QUIT" + "\n";
			
			System.out.println("** script to Lingo =>"+sScript);

			nErr = lng.LSexecuteScriptLng(pLngEnv, sScript);
			System.out.println("LSexecuteScriptLng="+nErr);
			if (nErr != 0) {
				System.out.println("***LSexecuteScriptLng error***: ");
				return false;
			}
			
			nErr = lng.LSclearPointersLng(pLngEnv);
			System.out.println("LSclearPointersLng="+nErr);
			if (nErr != 0) {
				System.out.println("***LScloseLogFileLng() error***: ");
				return false;
			}
			nErr = lng.LSdeleteEnvLng(pLngEnv);
			System.out.println("LSdeleteEnvLng="+nErr);
			
			System.out.println("------ Fin script lingo ----");
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

}
