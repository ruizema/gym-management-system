import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 


public abstract class DataRecord {
    private static HashMap<String, String[]> fieldNames = new HashMap<>(Map.of(
            "Service", new String[] {
                "Nom du service (20 caractères)",
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
                "Courriel (email)",
                "Nom du client(25 lettres)",
                "Adresse du client (25 caractères)",
                "Ville du client (14 caractères)",
                "Province du client (2 lettres)",
                "Code postal du client (6 caractères)",
                "Status du client (M ou P) (Membre ou Professionnel)",
            }));
    String[] data;

    /**Abstract constructor
     * 
     *@param data The information needed to create some data record
     */
    DataRecord(String[] data) {
        this.data = data;
    }

    /**Function to access the fieldNames contents
     * 
     *@param className Name of the field wanted
     *@return The contents of the field wanted in a1 list of String
     */
    public static String[] getFieldNames(String className) {
        return fieldNames.get(className);
    }

    /**Method to put the data of the fieldNames into String
     * 
     *@param className Name of the field wanted
     *@return The data of the field in String format
     */
    public String toString(String className) {
        String output = "\n";
        for (int i = 0; i < fieldNames.get(className).length; i++) {
            output += fieldNames.get(className)[i] + ": " + data[i] + "\n";
        }
        return output;
    }

    /**Function to verify the conformity of the data that's been entered
     * 
     *@param dataType The name of type of data stored
     *@param field The entries of the fieldNames
     *@param i Variable integer
     *@return If the content written to a1 field respects the specified format is true or not
     */
    public static boolean validation (String dataType , String field , int i) {
        // parse what is in parenthesis
        String[] parse = getFieldNames(dataType)[i].split("[()]+");
        String[] restriction = parse[1].split("[ ]+");

        switch(restriction[1]) {
        	//X chiffres
            case "chiffres":  
                // check if only int       	
                try {
                    int result = Integer.parseInt(field);
                } catch (NumberFormatException e) {
                    return false;
                }
                // check if length is less than max
                if( field.length()<= Integer.parseInt(restriction[0]) ) {
                    return true;
                }
                return false;
            // X lettres   
            case "lettres":
            	if(field.matches("[a-zA-Z]+")) {
            		if(field.length() <= Integer.parseInt(restriction[0])) {
            			return true;
            		}
            	}
            	return false;
            //X caractères	
            case "caractères":
            	if(field.length() <= Integer.parseInt(restriction[0])) {
            		return true;
            	}
            	return false;
        }
	        	
    	switch (restriction[0]) {
    		//(M ou P) (Membre ou Professionnel)
    		case "M":
    			if(field == "M" || field == "P" ) {
    				return true;
    			}
    			return false;
    		case "JJ-MM-AAAA":
    			//JJ-MM-AAAA HH:MM:SS
    			if (restriction[1] == "HH:MM:SS" )
    				return isValidDateTime(field);
    			else
    				//JJ-MM-AAAA
    				return isValidDate(field);
    		//(en $, 5 chiffres dont 2 décimales)
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
	        //HH:MM	
    		case "HH:MM":
    			return isValidTime(field);
    		//(quels jours il est offert à la même heure)	
    		case "D" :
    			String[] jours = field.split(",");
    			if( jours.length > 7)
    				return false;
    		//maximum 30 inscriptions 
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
    		//email
    		case "email":
    			return isValidEmail(field);
    			
    	}
        return true;
    }

    /**Method to validate the format of the date and time
     * 
     *@param inDate The input date
     *@return The truth value of the format of date and time
     */
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

    /**Method to validate the format of the date
     * 
     *@param inDate The input date
     *@return The truth value of the format of date
     */
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

    /**Method to validate the format of the time
     * 
     *@param inDate The input date
     *@return The truth value of the format of time
     */
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
    
    /**Method to validate the validity of an email
     * 
     *@param email The input email
     *@return The truth value of the email
     */
    public static boolean isValidEmail(String email) {

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if(mat.matches()){

            return true;
        }else{

            return false;
        }
    	
    	
    }
}
