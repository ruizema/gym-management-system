public class Session {
    private static String[] fieldNames = {
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
    };
    private String[] data;

    public Session(String[] data) {
        this.data = data;
    }

    public static String[] getFieldNames() {
        return fieldNames;
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < fieldNames.length; i++) {
            output += fieldNames[i] + ": " + data[i] + "\n";
        }
        return output + "\n";
    }
}
