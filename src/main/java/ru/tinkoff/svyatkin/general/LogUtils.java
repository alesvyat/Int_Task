package ru.tinkoff.svyatkin.general;

public class LogUtils {
	
	public static void reportException(Exception e) {
		try {
			String method = e.getStackTrace()[0].getMethodName();
			String eClass = e.getClass().getSimpleName();
			int eLine = e.getStackTrace()[0].getLineNumber();
			String eMessage = e.getMessage();
			StackTraceElement[] ste = e.getStackTrace();
			String eStackTrace = "";
			for (int i = 0; i < ste.length; i++) {
				eStackTrace += "\t\t" + ste[i].toString() + "\n";
			}
			
			Globals.sAssert.fail(method + " threw " + eClass + " in line " + eLine + ": " + eMessage + "\n Stack trace:\n" + eStackTrace);
		} catch (Exception e2) {
			Globals.sAssert.fail("Some exception appeared in reportExeption method");
		}
	}
}