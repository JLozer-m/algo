package iut.sae.algo.simplicite.etu35;

public class Recherche {      
    private static final int[][] Deplacements = {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
    };

    public static int chercheMot(String botteDeFoin, String aiguille) {
        if (botteDeFoin == null || aiguille == null || aiguille.isEmpty()) {
            return -1;
        }
        if (botteDeFoin.isEmpty()) {
            return 0;
        }

        String[] lignes = botteDeFoin.split("\n", -1);
        int nbLignes = lignes.length;
        int nbColonne = lignes[0].length();

        if (nbColonne == 0) {
            return 0;
        }
        for (String ligne : lignes) {
            if (ligne.length() != nbColonne) {
                return -1;
            }
        }        
        int longueurMot = aiguille.length();
        char premiereLettre = aiguille.charAt(0);
        int compteur = 0;

        if (longueurMot == 1) {
            // cas particulier donc on compte simplement le nombre d'occurrences de la lettre
            for (int ligne = 0; ligne < nbLignes; ligne++) {
                for (int colonne = 0; colonne < nbColonne; colonne++) {
                    if (lignes[ligne].charAt(colonne) == premiereLettre) {
                        compteur++;
                    }
                }
            }
            return compteur;
        }

        // si toutes les lettres sont identiques
        boolean motUniforme = true;
        for (int i = 1; i < longueurMot; i++) {
            if (aiguille.charAt(i) != premiereLettre) {
                motUniforme = false;
            }
        }

        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonne; colonne++) {
                if (lignes[ligne].charAt(colonne) == premiereLettre) {
                    for (int[] deplacement : Deplacements) {
                        int derniereLigne = ligne + (longueurMot - 1) * deplacement[0];
                        int derniereColonne = colonne + (longueurMot - 1) * deplacement[1];
                        if (derniereLigne >= 0 && derniereLigne < nbLignes && derniereColonne >= 0 && derniereColonne < nbColonne) {
                            int l = ligne;
                            int c = colonne;
                            boolean correspondbien = true;
                            for (int inidice = 1; inidice < longueurMot; inidice++) {
                                l += deplacement[0];
                                c += deplacement[1];
                                if (lignes[l].charAt(c) != aiguille.charAt(inidice)) {
                                    correspondbien = false;
                                }
                            }
                            if (correspondbien) {
                                // Si le mot est uniforme, on ne compte que les occurrences
                                if (motUniforme) {
                                    if ((deplacement[0] > 0) || (deplacement[0] == 0 && deplacement[1] > 0)) {
                                        compteur++;
                                    }
                                } else {
                                    compteur++;
                                }
                            }
                        }

                    }
                }
            }
        }
        return compteur;
    }

}
