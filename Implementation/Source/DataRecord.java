import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class DataRecord {
    private static HashMap<String, String[]> fieldNames = new HashMap<>(Map.of(
            "Session", new String[]{
                "Date et heure actuelles (JJ-MM-AAAA HH:MM:SS)",
                "Date de début du service (JJ-MM-AAAA)",
                "Date de fin du service (JJ-MM-AAAA)",
                "Heure du service (HH:MM)",
                "Récurrence hebdomadaire du service (quels jours il est offert à la même heure)",
                "Capacité maximale (maximum 30 inscriptions)",
                "Numéro du professionnel (9 chiffres)",
                "Code du service (7 chiffres)",
                "Code de la séance (7 chiffres)",
                "Frais du service (en $ : 5 chiffres dont 2 décimales)",
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
                "Nom"
            }));
    String[] data;

    private static LinkedList<Integer> existingIds = new LinkedList<>();

    DataRecord(String[] data) {
        this.data = data;
    }

    public static LinkedList<Integer> getExistingIds() {
        return existingIds;
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

    public int generateId() {
        int id = (int) Math.floor(Math.random() * 1000000000);
        for (int existingId : existingIds) {
            if (existingId == id) {
                id = (int) Math.floor(Math.random() * 1000000000);
            }
        }
        existingIds.add(id);
        return id;
    }
}