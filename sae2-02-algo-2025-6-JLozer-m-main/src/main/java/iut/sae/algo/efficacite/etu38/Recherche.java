package iut.sae.algo.efficacite.etu38;

public class Recherche {

    // Fonction principale qui recherche toutes les occurrences du mot 'aiguille' dans la 'botteDeFoin'
    public static int chercheMot(String botteDeFoin, String aiguille) throws NullPointerException {
        if (botteDeFoin == null || aiguille == null) {
            return -1;
        }

        int tailleM = botteDeFoin.length();
        int tailleA = aiguille.length();

        // Cas trivial : chaîne vide ou mot vide
        if (tailleM == 0 || tailleA == 0) {
            return 0;
        }

        // Calcul du nombre de lignes et colonnes de la matrice (avant suppression des \n)
        int nombreColonnes = nombreColonnes(botteDeFoin);
        int nombreLignes = nombreLignes(botteDeFoin);

        // On enlève les retours à la ligne pour travailler sur une chaîne continue
        botteDeFoin = botteDeFoin.replace("\n", "");
        tailleM = botteDeFoin.length();

        // Vérifie la cohérence entre la taille de la chaîne et la grille (lignes x colonnes)
        if (nombreColonnes * nombreLignes != tailleM) {
            return -1;
        }

        // Si l'aiguille est plus grande que la grille dans toutes les directions, inutile de chercher
        if (tailleA > nombreColonnes && tailleA > nombreLignes) {
            return -1;
        }

        int resultat = 0;

        // Cas particulier : recherche d'un seul caractère
        if (tailleA == 1) {
            for (int i = 0; i < tailleM; i++) {
                if (botteDeFoin.charAt(i) == aiguille.charAt(0)) {
                    resultat++;
                }
            }
            return resultat;
        }

        // Vérifie si l'aiguille est un palindrome
        boolean palindrome = estPalindrome(aiguille, tailleA);

        // Parcours de tous les caractères de la grille
        for (int i = 0; i < tailleM; i++) {
            // Si la première lettre correspond, on teste toutes les directions
            if (botteDeFoin.charAt(i) == aiguille.charAt(0)) {
                resultat += testerToutesDirections(botteDeFoin, aiguille, i, nombreColonnes, nombreLignes, tailleA, palindrome);
            }
        }

        return resultat;
    }

    // Retourne le nombre de colonnes (basé sur la première ligne)
    public static int nombreColonnes(String botteDeFoin) {
        int nombreColonnes = 0;
        while (nombreColonnes < botteDeFoin.length() && botteDeFoin.charAt(nombreColonnes) != '\n') {
            nombreColonnes++;
        }
        return nombreColonnes;
    }

    // Retourne le nombre de lignes (en comptant les \n)
    public static int nombreLignes(String botteDeFoin) {
        int nombreLignes = 0;
        for (int i = 0; i < botteDeFoin.length(); i++) {
            if (botteDeFoin.charAt(i) == '\n') {
                nombreLignes++;
            }
        }
        return nombreLignes + 1; // Ajoute la dernière ligne sans '\n'
    }

    // Test dans la direction vers le bas
    public static int testVertical(String botteDeFoin, String aiguille, int indice, int nbColonnes, int nbLignes, int tailleA, int numLigne, int numColonne) {
        if ((numLigne) + tailleA > nbLignes) {
            return 0;
        }
        for (int i = 0; i < tailleA; i++) {
            if (botteDeFoin.charAt(indice) != aiguille.charAt(i)) {
                return 0;
            }
            indice += nbColonnes;
        }
        return 1;
    }

    // Test dans la direction vers le haut
    public static int testVerticalInverse(String botteDeFoin, String aiguille, int indice, int nbColonnes, int tailleA,int numLigne, int numColonne) {
        if ((numLigne) - tailleA + 1 < 0) {
            return 0;
        }
        for (int i = 0; i < tailleA; i++) {
            if (botteDeFoin.charAt(indice) != aiguille.charAt(i)) {
                return 0;
            }
            indice -= nbColonnes;
        }
        return 1;
    }

    // Test vers la droite
    public static int testHorizontal(String botteDeFoin, String aiguille, int indice, int nbColonnes, int tailleA, int numLigne, int numColonne) {
        if ((numColonne) + tailleA > nbColonnes) {
            return 0;
        }
        for (int i = 0; i < tailleA; i++) {
            if (botteDeFoin.charAt(indice) != aiguille.charAt(i)) {
                return 0;
            }
            indice++;
        }
        return 1;
    }

    // Test vers la gauche
    public static int testHorizontalInverse(String botteDeFoin, String aiguille, int indice, int nbColonnes, int tailleA, int numLigne, int numColonne) {
        if ((numColonne) - tailleA + 1 < 0) {
            return 0;
        }
        for (int i = 0; i < tailleA; i++) {
            if (botteDeFoin.charAt(indice) != aiguille.charAt(i)) {
                return 0;
            }
            indice--;
        }
        return 1;
    }

    // Diagonale descendante vers la droite
    public static int testDiagonaleBasDroite(String botteDeFoin, String aiguille, int indice, int nbColonnes, int nbLignes, int tailleA, int numLigne, int numColonne) {
        if (numLigne + tailleA > nbLignes || numColonne + tailleA > nbColonnes) {
            return 0;
        }
        for (int i = 0; i < tailleA; i++) {
            if (botteDeFoin.charAt(indice) != aiguille.charAt(i)) {
                return 0;
            }
            indice = indice + (nbColonnes + 1);
        }
        return 1;
    }

    // Diagonale montante vers la gauche
    public static int testDiagonaleHautGauche(String botteDeFoin, String aiguille, int indice, int nbColonnes, int nbLignes, int tailleA, int numLigne, int numColonne) {
        if (numLigne - tailleA + 1 < 0 || numColonne - tailleA + 1 < 0) {
            return 0;
        }
        for (int i = 0; i < tailleA; i++) {
            if (botteDeFoin.charAt(indice) != aiguille.charAt(i)) {
                return 0;
            }
            indice = indice - (nbColonnes + 1);
        }
        return 1;
    }

    // Diagonale descendante vers la gauche
    public static int testDiagonaleBasGauche(String botteDeFoin, String aiguille, int indice, int nbColonnes, int nbLignes, int tailleA, int numLigne, int numColonne) {
        if (numLigne + tailleA > nbLignes || numColonne - tailleA + 1 < 0) {
            return 0;
        }
        for (int i = 0; i < tailleA; i++) {
            if (botteDeFoin.charAt(indice) != aiguille.charAt(i)) {
                return 0;
            }
            indice = indice + (nbColonnes - 1);

        }
        return 1;
    }

    // Diagonale montante vers la droite
    public static int testDiagonaleHautDroite(String botteDeFoin, String aiguille, int indice, int nbColonnes, int nbLignes, int tailleA, int numLigne, int numColonne) {
        if (numLigne - tailleA + 1 < 0 || numColonne + tailleA > nbColonnes) {
            return 0;
        }
        for (int i = 0; i < tailleA; i++) {
            if (botteDeFoin.charAt(indice) != aiguille.charAt(i)) {
                return 0;
            }
            indice = indice - (nbColonnes - 1);
        }
        return 1;
    }

    // Vérifie si une chaîne est un palindrome (utile pour éviter les tests inverses inutiles)
    public static boolean estPalindrome(String aiguille, int tailleA) {
        for (int i = 0; i < tailleA / 2; i++) {
            if (aiguille.charAt(i) != aiguille.charAt(tailleA - i - 1)) {
                return false;
            }
        }
        return true;
    }

    // Fonction qui teste toutes les directions autour d'une position donnée
    private static int testerToutesDirections(String botteDeFoin, String aiguille, int indice, int nbColonnes, int nbLignes, int tailleA, boolean palindrome) {
        int nombreOccurence = 0;

        // Position (ligne, colonne) dans la grille
        int numLigne = indice / nbColonnes;
        int numColonne = indice % nbColonnes;

        // Directions "normales"
        nombreOccurence += testVertical(botteDeFoin, aiguille, indice, nbColonnes, nbLignes, tailleA,numLigne,numColonne);
        nombreOccurence += testHorizontal(botteDeFoin, aiguille, indice, nbColonnes, tailleA,numLigne,numColonne);
        nombreOccurence += testDiagonaleBasDroite(botteDeFoin, aiguille, indice, nbColonnes, nbLignes, tailleA,numLigne,numColonne);
        nombreOccurence += testDiagonaleBasGauche(botteDeFoin, aiguille, indice, nbColonnes, nbLignes, tailleA,numLigne,numColonne);

        // Tests inverses uniquement si l'aiguille n'est pas un palindrome
        if (!palindrome) {
            nombreOccurence += testVerticalInverse(botteDeFoin, aiguille, indice, nbColonnes, tailleA,numLigne,numColonne);
            nombreOccurence += testHorizontalInverse(botteDeFoin, aiguille, indice, nbColonnes, tailleA,numLigne,numColonne);
            nombreOccurence += testDiagonaleHautDroite(botteDeFoin, aiguille, indice, nbColonnes, nbLignes, tailleA,numLigne,numColonne);
            nombreOccurence += testDiagonaleHautGauche(botteDeFoin, aiguille, indice, nbColonnes, nbLignes, tailleA,numLigne,numColonne);
        }
        return nombreOccurence;
    }
}
