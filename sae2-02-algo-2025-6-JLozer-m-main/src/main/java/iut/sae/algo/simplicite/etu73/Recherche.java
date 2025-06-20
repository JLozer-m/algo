package iut.sae.algo.simplicite.etu73;

public class Recherche {

    /**
     * Recherche le nombre d'occurrences d'une aiguille dans une botte de foin.
     * @param botteDeFoin
     * @param aiguille
     * @return
     */
    public static int chercheMot(String botteDeFoin, String aiguille) {
        //cas d'erreur
        if (botteDeFoin == null || aiguille == null || aiguille.isEmpty()) {
            return -1;
        }
        if (botteDeFoin.isEmpty()) {
            return 0;
        }

        //Découpage de la botte de foin en lignes
        String[] lignes = botteDeFoin.split("\n");
        int nbLignes = lignes.length;
        int nbColonnes = lignes[0].length();

        //Vérification que toutes les lignes ont la même longueur
        for (int i = 0; i < lignes.length; i++) {
            if (lignes[i].length() != nbColonnes) {
                return -1;
            }
        }

        int total = 0;
        String aiguilleInverse = new StringBuilder(aiguille).reverse().toString();

        //Parcours de chaque case de la grille
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                // 8 directions
                total+=compteDirection(lignes, i, j, aiguille, 0, 1);//droite
                total+=compteDirection(lignes, i, j, aiguilleInverse, 0, 1);// gauche (inverse)
                total+=compteDirection(lignes, i, j, aiguille, 1, 0);//bas
                total+=compteDirection(lignes, i, j, aiguilleInverse, 1, 0);//haut (inverse)
                total+=compteDirection(lignes, i, j, aiguille, 1, 1);//diagonale bas-droite
                total+=compteDirection(lignes, i, j, aiguilleInverse, 1, 1);// diagonale haut-gauche (inverse)
                total+=compteDirection(lignes, i, j, aiguille, 1, -1);//diagonale bas-gauche
                total+=compteDirection(lignes, i, j, aiguilleInverse, 1, -1);//diagonale haut-droite (inverse)
            }
        }
        return total;
    }

    //Compte le nombre d'occurrences de l'aiguille dans une direction donnée.
    private static int compteDirection(String[] lignes, int ligneDepart, int colonneDepart, String aiguille, int dLigne, int dColonne) {
        int longueurAiguille = aiguille.length();
        int nbLignes = lignes.length;
        int nbColonnes = lignes[0].length();

        for (int k = 0; k < longueurAiguille; k++) {
            int ligneCourante = ligneDepart + dLigne * k;
            int colonneCourante = colonneDepart + dColonne * k;
            if (ligneCourante < 0 || ligneCourante >= nbLignes || colonneCourante < 0 || colonneCourante >= nbColonnes) {
                return 0;
            }
            if (lignes[ligneCourante].charAt(colonneCourante) != aiguille.charAt(k)) {
                return 0;
            }
        }
        return 1;
    }
}
