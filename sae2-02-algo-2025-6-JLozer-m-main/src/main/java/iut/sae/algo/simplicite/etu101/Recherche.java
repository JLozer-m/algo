package iut.sae.algo.simplicite.etu101;

/**
 * Classe utilitaire pour rechercher un mot (aiguille) dans une grille de lettres (botteDeFoin)
 * dans toutes les directions possibles.
 */

public class Recherche {

    /**
     * Cherche le nombre d'occurrences du mot 'aiguille' dans la grille 'botteDeFoin'.
     * @param botteDeFoin Grille de lettres sous forme de String, lignes séparées par '\n'
     * @param aiguille Mot à chercher
     * @return Nombre d'occurrences trouvées, ou -1 si la grille est invalide
     */
    public static int chercheMot(String botteDeFoin, String aiguille) {

        // Vérification des entrées
        if (botteDeFoin == null || aiguille == null) {
            return -1;
        }

        // Découpage de la grille en lignes
        String[] lignes = botteDeFoin.split("\n");
        int longueurAttendue = lignes[0].length();

        // Vérifie que toutes les lignes ont la même longueur
        for (String ligne : lignes) {
            if (ligne.length() != longueurAttendue) {
                return -1;
            }
        }

        int nbr = 0;
        boolean similaire = true;

        // Vérifie si tous les caractères de l'aiguille sont identiques
        char premier = aiguille.charAt(0);
        for (int i = 1; i < aiguille.length(); i++) {
            if (aiguille.charAt(i) != premier) {
                similaire = false;
            }
        }

        // Recherche selon la nature de l'aiguille
        if (aiguille.length() == 1)
            nbr += Horizontal(botteDeFoin, aiguille, lignes);
        else if (similaire) {
            // Si tous les caractères sont identiques, on ne compte pas les inverses
            nbr += Horizontal(botteDeFoin, aiguille, lignes);
            nbr += Vertical(botteDeFoin, aiguille, lignes);
            nbr += PremiereDiagonale(botteDeFoin, aiguille, lignes);
            nbr += SecondeDiagonale(botteDeFoin, aiguille, lignes);
        } else {
            // Sinon, on compte aussi les inverses
            nbr += Horizontal(botteDeFoin, aiguille, lignes);
            nbr += HorizontalInverse(botteDeFoin, aiguille, lignes);
            nbr += Vertical(botteDeFoin, aiguille, lignes);
            nbr += VerticalInverse(botteDeFoin, aiguille, lignes);
            nbr += PremiereDiagonale(botteDeFoin, aiguille, lignes);
            nbr += PremiereDiagonaleInverse(botteDeFoin, aiguille, lignes);
            nbr += SecondeDiagonale(botteDeFoin, aiguille, lignes);
            nbr += SecondeDiagonaleInverse(botteDeFoin, aiguille, lignes);
        }

        return nbr;
    }

    /**
     * Recherche le mot 'aiguille' dans chaque ligne de la grille, de gauche à droite.
     * @param botteDeFoin Grille de lettres sous forme de String (non utilisé ici)
     * @param aiguille Mot à chercher
     * @param lignes Tableau de chaînes représentant les lignes de la grille
     * @return Nombre d'occurrences trouvées horizontalement (gauche->droite)
     */
    public static int Horizontal(String botteDeFoin, String aiguille, String[] lignes) {
        int nbr = 0;
        for (String ligne : lignes) {
            int index = 0;
            while ((index = ligne.indexOf(aiguille, index)) != -1) {
                nbr++;
                index++;
            }
        }
        return nbr;
    }

    /**
     * Recherche le mot 'aiguille' dans chaque ligne de la grille, de droite à gauche (mot inversé).
     * @param botteDeFoin Grille de lettres sous forme de String (non utilisé ici)
     * @param aiguille Mot à chercher
     * @param lignes Tableau de chaînes représentant les lignes de la grille
     * @return Nombre d'occurrences trouvées horizontalement (droite->gauche)
     */
    public static int HorizontalInverse(String botteDeFoin, String aiguille, String[] lignes){
        int nbr = 0;
        for (String ligne : lignes) {
            String mot = "";
            for (int i = ligne.length() - 1; i >= 0; i--) {
                mot += ligne.charAt(i);
            }
            int index = 0;
            while ((index = mot.indexOf(aiguille, index)) != -1) {
                nbr++;
                index++;
            }
        }
        return nbr;
    }

    /**
     * Recherche le mot 'aiguille' dans chaque colonne de la grille, de haut en bas.
     * @param botteDeFoin Grille de lettres sous forme de String (non utilisé ici)
     * @param aiguille Mot à chercher
     * @param lignes Tableau de chaînes représentant les lignes de la grille
     * @return Nombre d'occurrences trouvées verticalement (haut->bas)
     */
    public static int Vertical(String botteDeFoin, String aiguille, String[] lignes) {
        int lignesCount = lignes.length;
        int colonnesCount = lignes[0].length();
        int nbr = 0;
        for (int col = 0; col < colonnesCount; col++) {
            String mot = "";
            for (int ligne = 0; ligne < lignesCount; ligne++) {
                mot += lignes[ligne].charAt(col);
            }
            int index = 0;
            while ((index = mot.indexOf(aiguille, index)) != -1) {
                nbr++;
                index++;
            }
        }
        return nbr;
    }

    /**
     * Recherche verticale bas->haut (inverse)
     */
    public static int VerticalInverse(String botteDeFoin, String aiguille, String[] lignes) {
        int lignesCount = lignes.length;
        int colonnesCount = lignes[0].length();
        int nbr = 0;
        for (int col = 0; col < colonnesCount; col++) {
            String mot = "";
            for (int ligne = lignesCount - 1; ligne >= 0; ligne--) {
                mot += lignes[ligne].charAt(col);
            }
            int index = 0;
            while ((index = mot.indexOf(aiguille, index)) != -1) {
                nbr++;
                index++;
            }
        }
        return nbr;
    }

    /**
     * Recherche diagonale principale (haut-gauche vers bas-droite)
     */
    public static int PremiereDiagonale(String botteDeFoin, String aiguille, String[] lignes) {
        int nbr = 0;
        int lignesCount = lignes.length;
        int colonnesCount = lignes[0].length();
        for (int debutLigne = 0; debutLigne <= lignesCount - aiguille.length(); debutLigne++) {
            for (int debutCol = 0; debutCol <= colonnesCount - aiguille.length(); debutCol++) {
                String mot = "";
                for (int i = 0; i < aiguille.length(); i++) {
                    mot += lignes[debutLigne + i].charAt(debutCol + i);
                }
                if (mot.equals(aiguille)) nbr++;
            }
        }
        return nbr;
    }

    /**
     * Recherche diagonale principale inverse (bas-droite vers haut-gauche)
     * (Ne cherche que sur la diagonale principale)
     */
    public static int PremiereDiagonaleInverse(String botteDeFoin, String aiguille, String[] lignes) {
        int lignesCount = lignes.length;
        int colonnesCount = lignes[0].length();
        int i = lignesCount - 1;
        int j = colonnesCount - 1;
        int nbr = 0;
        String mot = "";
        while (i >= 0 && j >= 0) {
            mot += lignes[i].charAt(j);
            i--;
            j--;
        }
        if (mot.equals(aiguille)) {
            nbr++;
        }
        return nbr;
    }

    /**
     * Recherche diagonale secondaire (haut-droite vers bas-gauche)
     * (Ne cherche que sur la diagonale secondaire principale)
     */
    public static int SecondeDiagonale(String botteDeFoin, String aiguille, String[] lignes) {
        int nbr = 0;
        String mot = "";
        int cpt = 0;
        while (cpt < lignes.length && 0 <= lignes[0].length()-1-cpt) {
            mot += lignes[cpt].charAt(lignes[0].length() - 1 - cpt);
            cpt++;
        }
        if (mot.equals(aiguille)) {
            nbr++;
        }
        return nbr;
    }

    /**
     * Recherche diagonale secondaire inverse (bas-gauche vers haut-droite)
     * (Ne cherche que sur la diagonale secondaire principale)
     */
    public static int SecondeDiagonaleInverse(String botteDeFoin, String aiguille, String[] lignes) {
        int nbr = 0;
        String mot = "";
        int lignesCount = lignes.length;
        int colonnesCount = lignes[0].length();
        int i = lignesCount - 1;
        int j = 0;
        while (i >= 0 && j < colonnesCount) {
            mot += lignes[i].charAt(j);
            i--;
            j++;
        }
        if (mot.equals(aiguille)) {
            nbr++;
        }
        return nbr;
    }
}
