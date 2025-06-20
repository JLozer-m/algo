package iut.sae.algo.sobriete.etu95;

public class Recherche {
    /**
     * On recherche le nombre d'occurrences du mot aiguille dans une grille de caractères botteDeFoin,
     * en tenant compte de ses inverses (sauf si le mot est un palindrome et des 4 directions 
     *
     * @param botteDeFoin la grille sous forme de chaîne, chaque ligne étant séparée par un saut de ligne '\n'
     * @param aiguille    le mot à rechercher dans la grille
     * @return le nombre d'occurrences du mot (et de son inverse si ce n’est pas un palindrome), ou -1 en cas d'erreur, ou 0 si aucun mot n’est trouvé
     */
    public static int chercheMot(String botteDeFoin, String aiguille) {
        if (botteDeFoin == null || aiguille == null || aiguille.isEmpty()) return -1;

        String[] lignes = botteDeFoin.split("\n");
        int nbLignes = lignes.length;
        if (nbLignes == 0) return 0;
        int nbColonnes = lignes[0].length();

        for (int i = 1; i < nbLignes; i++) {
            if (lignes[i].length() != nbColonnes) return -1;
        }

        int longueurMot = aiguille.length();
        if (longueurMot == 1) {
            char c = aiguille.charAt(0);
            int count = 0;
            for (int i = 0; i < nbLignes; i++) {
                for (int j = 0; j < nbColonnes; j++) {
                    if (lignes[i].charAt(j) == c) count++;
                }
            }
            return count;
        }

        boolean palindrome = estPalindrome(aiguille);
        int total = 0;

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                total += chercheDepuisCase(lignes, i, j, aiguille, palindrome);
            }
        }

        return total;
    }

    /**
     * Cherche le mot à partir d'une case donnée dans toutes les directions possibles.
     *
     * @param grille     la grille de caractères
     * @param x          la ligne de départ
     * @param y          la colonne de départ
     * @param mot        le mot à chercher
     * @param palindrome indique si le mot est un palindrome
     * @return le nombre d'occurrences du mot à partir de cette case
     */
    private static int chercheDepuisCase(String[] grille, int x, int y, String mot, boolean palindrome) {
        int count = 0;
        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = {1, 0, 1, -1, -1, 0, -1, 1};
        int directions = palindrome ? 4 : 8;

        for (int d = 0; d < directions; d++) {
            if (correspond(grille, x, y, mot, dx[d], dy[d])) {
                count++;
            }
        }
        return count;
    }

    /**
     * Vérifie si le mot correspond à la grille à partir d'une position donnée dans une direction donnée.
     *
     * @param grille la grille de caractères
     * @param i      la ligne de départ
     * @param j      la colonne de départ
     * @param mot    le mot à chercher
     * @param dx     le déplacement en ligne
     * @param dy     le déplacement en colonne
     * @return true si le mot correspond, false sinon
     */
    private static boolean correspond(String[] grille, int i, int j, String mot, int dx, int dy) {
        int n = mot.length();
        int lignes = grille.length;
        int colonnes = grille[0].length();

        for (int k = 0; k < n; k++) {
            int ni = i + dx * k;
            int nj = j + dy * k;
            if (ni < 0 || ni >= lignes || nj < 0 || nj >= colonnes) return false;
            if (grille[ni].charAt(nj) != mot.charAt(k)) return false;
        }
        return true;
    }

    /**
     * Vérifie si le mot est un palindrome.
     *
     * @param mot le mot à vérifier
     * @return true si le mot est un palindrome, false sinon
     */
    private static boolean estPalindrome(String mot) {
        int gauche = 0, droite = mot.length() - 1;
        while (gauche < droite) {
            if (mot.charAt(gauche++) != mot.charAt(droite--)) return false;
        }
        return true;
    }
}
