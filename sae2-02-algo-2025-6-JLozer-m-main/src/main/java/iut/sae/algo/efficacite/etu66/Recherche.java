package iut.sae.algo.efficacite.etu66;

import java.util.HashMap;

public class Recherche{

    // Cherche le nombre d’occurrences du mot "aiguille" dans la chaîne "botteDeFoin"
    public static int chercheMot(String botteDeFoin, String aiguille) {
        // Vérifie les cas d'entrée invalide
        if(botteDeFoin == null || aiguille == null || !(regulier(botteDeFoin))) {
            return -1;
        }

        int nbOccurence = 0;
        int[] tabStat = stat(botteDeFoin);
        int nbLigne = tabStat[0];
        int nbSaut = tabStat[1];
        String[] tabMot = toTabH(botteDeFoin, nbLigne);

        // Si l’aiguille est un seul caractère, recherche uniquement horizontalement
        if (aiguille.length() == 1) {
            for(int i = 0; i < tabMot.length; i++) {
                nbOccurence += boyerMoore(tabMot[i], aiguille);
                // Recherche également la version inversée si non trouvée
                if (boyerMoore(tabMot[i], aiguille)  == 0) {
                    nbOccurence += boyerMoore(tabMot[i],reverse(aiguille));
                }
            }
        }else{
            // Recherche horizontale
            for(int i = 0; i < tabMot.length; i++) {
                nbOccurence += boyerMoore(tabMot[i], aiguille);
                if (boyerMoore(tabMot[i], aiguille)  == 0) {
                    nbOccurence += boyerMoore(tabMot[i],reverse(aiguille));
                }
            }
            // Recherche verticale
            tabMot = toTabV(botteDeFoin, nbSaut);
            for(int i = 0; i < tabMot.length; i++) {
                nbOccurence += boyerMoore(tabMot[i], aiguille);
                if (boyerMoore(tabMot[i], aiguille)  == 0) {
                    nbOccurence += boyerMoore(tabMot[i],reverse(aiguille));
                }
            }
            // Recherche diagonale si le texte est carré
            tabMot = toTabH(botteDeFoin, nbLigne);
            if (estCarre(tabMot)) {
                nbOccurence += boyerMoore(toPreDia(tabMot), aiguille);
                if (boyerMoore(toPreDia(tabMot), aiguille)  == 0) {
                    nbOccurence += boyerMoore(toPreDia(tabMot),reverse(aiguille));
                }
                nbOccurence += boyerMoore(toSecDia(tabMot), aiguille);
                if (boyerMoore(toSecDia(tabMot), aiguille)  == 0) {
                    nbOccurence += boyerMoore(toSecDia(tabMot),reverse(aiguille));
                }
            }
        }
        return nbOccurence;
    }

    // Vérifie que chaque ligne de texte a la même longueur (structure régulière)
    public static boolean regulier(String pfTxt) {
        int saut = 1;
        for (int i = 0; i<pfTxt.length(); i+=saut) {
            if (pfTxt.charAt(i) == '\n' && saut==1) {
                saut = i + 1 ;
            }
            if (pfTxt.charAt(i) != '\n' && saut > 1) {
                return false;
            }
        }
        return true;
    }

    // Renvoie un tableau avec le nombre de lignes et la taille d’une ligne
    public static int[] stat(String pfTxt) {
        int ligne = 1;
        int saut = 1;
        for (int i = 0; i<pfTxt.length(); i++) {
            if (pfTxt.charAt(i) == '\n') {
                ligne ++ ;
            }
            if (pfTxt.charAt(i) == '\n' && saut == 1) {
                saut = i + 1 ;
            }
        }
        int[] t = {ligne, saut};
        return t;
    }

    // Renverse une chaîne de caractères
    public static String reverse(String pfTxt) {
        char[] tab = new char[pfTxt.length()];
        for (int i = 0; i < pfTxt.length(); i++) {
            tab[i] = pfTxt.charAt(i);
        }
        for (int i = 0; i < tab.length /2; i++) {
            char tmp = tab[i];
            tab[i] = tab[tab.length - (i+1)];
            tab[tab.length - (i+1)] =tmp;
        }
        String nouveauMot = new String(tab);
        return nouveauMot;
    }

    // Convertit le texte en tableau de chaînes représentant les lignes horizontales
    public static String[] toTabH(String mot, int nbLigne) {
        int cpt = 0;
        String[] tab = new String[nbLigne];
        for(int i = 0; i < tab.length; i++) {
            tab[i] = "";
        }
        for(int i = 0; i < mot.length(); i++) {
            if (mot.charAt(i) == '\n') {
                cpt++;
            }else{
                tab[cpt] = tab[cpt] + mot.charAt(i);
            }
        }
        return tab;
    }

    // Convertit le texte en tableau de chaînes représentant les colonnes verticales
    public static String[] toTabV(String mot, int nbSaut) {
        String[] tab = new String[nbSaut -1];
        for(int i = 0; i < tab.length; i++) {
            tab[i] = "";
        }
        for (int i = 0; i < tab.length; i++) {
            for(int j = i; j < mot.length(); j+= nbSaut) {
                tab[i] = tab[i] + mot.charAt(j);
            }
        }
        return tab;
    }

    // Vérifie si la grille de texte est carrée (même nombre de lignes et colonnes)
    public static boolean estCarre(String[] tab) {
        return tab.length == tab[0].length();
    }

    // Extrait la diagonale première d'une grille carrée
    public static String toPreDia(String[] tab) {
        String diagonal = "";
        for (int i = 0; i < tab.length; i++) {
            diagonal = diagonal + tab[i].charAt(i);
        }
        return diagonal;
    }

    // Extrait la diagonale secondaire d'une grille carrée
    public static String toSecDia(String[] tab) {
        String diagonal = "";
        for (int i = 0; i < tab.length; i++) {
            diagonal = diagonal + tab[i].charAt(tab.length - (i+1));
        }
        return diagonal;
    }

    // Il est recommandé de se documenter sur l'algorithme de Boyer-Moore afin de comprendre le rôle des
    // méthodes qui vont suivres
    // Construit la table des caractères pour l’algorithme de Boyer-Moore (prétraitement)
    public static HashMap<Character, Integer>[] tableBM(String mot) {
        HashMap<Character, Integer>[] tab;
        HashMap<Character, Integer> dico = new HashMap<>();
        for (int i = 0; i < mot.length(); i++) {
            dico.put(mot.charAt(i), i);
        }
        tab = new HashMap[mot.length()];
        for (int i = 0; i < tab.length ; i ++) {
            tab[i] = new HashMap<>(dico);
        }
        return tab;
    }

    // Calcule le décalage à appliquer selon le caractère rencontré et la position
    public static int decalage(HashMap<Character, Integer>[] tab, int j, char c) {
        if (tab[j].containsKey(c)){
            return j - tab[j].get(c);
        }else {
            return j + 1;
        }
    }

    // Applique l’algorithme de Boyer-Moore pour rechercher un motif dans un texte
    public static int boyerMoore(String t, String m) {
        int nbOccurence = 0;
        HashMap<Character, Integer>[] dico = tableBM(m);
        int i = 0;
        while (i <= t.length() - m.length()){
            int k = 0;
            for (int j = m.length() - 1; j >= 0; j--) {
                if (t.charAt(i + j) != m.charAt(j)) {
                    k = decalage(dico, j, t.charAt(i + j));
                    break;
                }
            }
            if (k == 0) {
                k = 1;
                nbOccurence++;
            }
            i += k;
        }
        return nbOccurence;
    }
}
