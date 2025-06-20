package iut.sae.algo.efficacite.etu53;


public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        if (aiguille == null || aiguille.isEmpty() || botteDeFoin == null) {
            return -1;
        }

        if (botteDeFoin.isEmpty()) {
            return 0;
        }

        return rechercherMot(botteDeFoin, aiguille);
    }

    public static boolean estPalindrome(String mot) {
        int gauche = 0;
        int droite = mot.length() - 1;
        while (gauche < droite) {
            if (mot.charAt(gauche++) != mot.charAt(droite--)) {
                return false;
            }
        }
        return true;
    }

    public static int rechercherMot(String botteDeFoin, String mot) {
        if (mot.length() == 1) {
            int compteur = 0;
            for (int i = 0; i < botteDeFoin.length(); i++) {
                if (botteDeFoin.charAt(i) == mot.charAt(0)) {
                    compteur++;
                }
            }
            return compteur;
        }

        boolean palindrome = estPalindrome(mot);
        String motInverse = palindrome ? null : new StringBuilder(mot).reverse().toString();

        int nbLignes = 0;
        int nbColonnes = 0;
        int i = 0;
        while (i < botteDeFoin.length()) {
            int len = 0;
            while (i < botteDeFoin.length() && botteDeFoin.charAt(i) != '\n') {
                len++;
                i++;
            }
            nbLignes++;
            nbColonnes = Math.max(nbColonnes, len);
            i++;
        }

        char[][] grille = new char[nbLignes][nbColonnes];
        int ligne = 0;
        int colonne = 0;
        for (i = 0; i < botteDeFoin.length(); i++) {
            char c = botteDeFoin.charAt(i);
            if (c == '\n') {
                ligne++;
                colonne = 0;
            } else {
                grille[ligne][colonne++] = c;
            }
        }

        return rechercherDansGrille(grille, nbLignes, nbColonnes, mot, motInverse);
    }

    private static int rechercherDansGrille(char[][] grille, int nbLignes, int nbColonnes, String mot, String motInverse) {
        int compteur = 0;
        int longueurMot = mot.length();

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (verifie(grille, i, j, mot, 0, 1)) compteur++;
                if (motInverse != null && verifie(grille, i, j, motInverse, 0, 1)) compteur++;

                if (verifie(grille, i, j, mot, 1, 0)) compteur++;
                if (motInverse != null && verifie(grille, i, j, motInverse, 1, 0)) compteur++;

                if (verifie(grille, i, j, mot, 1, 1)) compteur++;
                if (motInverse != null && verifie(grille, i, j, motInverse, 1, 1)) compteur++;

                if (verifie(grille, i, j, mot, 1, -1)) compteur++;
                if (motInverse != null && verifie(grille, i, j, motInverse, 1, -1)) compteur++;
            }
        }
        return compteur;
    }

    private static boolean verifie(char[][] grille, int i, int j, String mot, int di, int dj) {
        int n = grille.length;
        int m = grille[0].length;

        for (int k = 0; k < mot.length(); k++) {
            int x = i + k * di;
            int y = j + k * dj;

            if (x < 0 || x >= n || y < 0 || y >= m || grille[x][y] != mot.charAt(k)) {
                return false;
            }
        }
        return true;
    }
}
