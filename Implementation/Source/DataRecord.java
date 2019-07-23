import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Map;

public abstract class DataRecord {
    private static HashMap<String, String[]> fieldNames = new HashMap<>(Map.of(
            "Service", new String[] {
                "Nom du service (20 caractères)",
                "Code du service (7 chiffres)",
            },
            "Session", new String[]{
                "Date et heure actuelles (JJ-MM-AAAA HH:MM:SS)",
                "Date de début du service (JJ-MM-AAAA)",
                "Date de fin du service (JJ-MM-AAAA)",
                "Heure du service (HH:MM)",
                "Récurrence hebdomadaire du service (D,L,Ma,Me,J,V,S) (quels jours il est offert à la même heure)",
                "Capacité maximale (maximum 30 inscriptions)",
                "Numéro du professionnel (9 chiffres)",
                "Code du service (7 chiffres)",
                "Code de la séance (7 chiffres)",
                "Frais du service (xxx.xx) (en $, 5 chiffres dont 2 décimales)",
                "Commentaires (100 caractères) (facultatif).",
            }, "Registration", new String[]{
                "Date et heure actuelles (JJ-MM-AAAA HH:MM:SS)",
                "Date à laquelle le service sera fourni (JJ-MM-AAAA)",
                "Numéro du professionnel (9 chiffres)",
                "Numéro du membre (9 chiffres)",
                "Code du service (7 chiffres)",
                "Code de la séance (7 chiffres)",
                "Commentaires (100 caractères) (facultatif).",
            }, "Confirmation", new String[]{
                "Date et heure actuelles (JJ-MM-AAAA HH:MM:SS)",
                "Numéro du professionnel (9 chiffres)",
                "Numéro du membre (9 chiffres)",
                "Code du service (7 chiffres)",
                "Code de la séance (7 chiffres)",
                "Commentaires (100 caractères) (facultatif).",
            }, "Account", new String[]{
                "Courriel",
                "Nom du client(25 lettres)",
                "Adresse du client (25 caractères)",
                "Ville du client (14 caractères)",
                "Province du client (2 lettres)",
                "Code postal du client (6 caractères)",
                "Status du client (M ou P) (Membre ou Professionnel)",
            }));
    String[] data;

    DataRecord(String[] data) {
        this.data = data;
    }

    public static String[] getFieldNames(String className) {
        return fieldNames.get(className);
    }

    public String toString(String className) {
        String output = "\n";
        for (int i = 0; i < fieldNames.get(className).length; i++) {
            output += fieldNames.get(className)[i] + ": " + data[i] + "\n";
        }
        return output;
    }
    
    public boolean validation (String dataType , String field , int i) {
    	// parse what is in parenthesis
    	String[] parse = getFieldNames(dataType)[i].split("[()]+");
    	String[] restriction = parse[1].split("[ ]+");
    	
    	switch (restriction[1]) {
	        case "chiffres":
	        	 // check if only int       	
	        	try {
	        		 	int result = Integer.parseInt(field);
	        		  } catch (NumberFormatException e) {
	        		    return false;
	        		  }
	        	 // check if length is less than max
	        	if ( field.length()<= Integer.parseInt(restriction[0]) )
	        	{
	        		return true;
	        	}
	        	return false;
	           
	        case "lettres":
	        	if(field.matches("[a-zA-Z]+") == true) {
	        		if(field.length() <= Integer.parseInt(restriction[0])) {
	        			return true;
	        		}
	        	}
	        	return false;
	        case "caractères":
	        	if(field.length() <= Integer.parseInt(restriction[0])) {
	        		return true;
	        	}
	        	return false;
	        	
	        
    	 }
	        	
    	switch (restriction[0]) {
    		case "M":
    			if(field == "M" || field == "P" ) {
    				return true;
    			}
    			return false;
    		case "JJ-MM-AAAA":
    			if (restriction[1] == "HH:MM:SS" )
    				return isValidDateTime(field);
    			else
    				return isValidDate(field);

    			
    		case "xxx.xx":
    			try {
	                Double num = Double.parseDouble(field);
	            } catch (NumberFormatException e) {
	                return false;
	            }
	        
	        	String[] parts = field.split(".");
	        	if (parts[0].length() == 3 && parts[1].length() == 2) {
	        		return true;
	        	}
	        	else 
	        		return false;
    		case "HH:MM":
    			return isValidTime(field);
    			
    		case "D" :
    			String[] jours = field.split(",");
    			if( jours.length > 7)
    				return false;
    		 
    		case "maximum":
    			try {
        		 	int result = Integer.parseInt(field);
        		  } catch (NumberFormatException e) {
        		    return false;
        		  }
    			
    			if ( Integer.parseInt(field) <= 30)
    				return true;
    				else 
    					return false;
    			
    			
    	}
    	
    	return true;
	
    }
    
    public static boolean isValidDateTime(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    public static boolean isValidTime(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
