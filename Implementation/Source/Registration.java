public class Registration {
    private static String[] fieldNames = {
            "Date et heure actuelles (JJ-MM-AAAA HH:MM:SS)",
            "Date à laquelle le service sera fourni (JJ-MM-AAAA)",
            "Numéro du professionnel (9 chiffres)",
            "Numéro du membre (9 chiffres)",
            "Code du service (7 chiffres)",
            "Code de la séance (7 chiffres)",
            "Commentaires (100 caractères) (facultatif)."
    };
    private String[] data;

    public Registration(String[] data) {
        this.data = data;
    }
}
