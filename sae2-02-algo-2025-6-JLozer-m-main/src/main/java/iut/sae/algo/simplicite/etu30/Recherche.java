package iut.sae.algo.simplicite.etu30;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        int cpt = 0;
        if (aiguille == null || botteDeFoin == null) {
            return -1;
        }
        String tabBotte[][] = convetirEnMatrice(botteDeFoin);
        String tabAiguille[] = new String[aiguille.length()];
        if (tabBotte.length == 0){
            return -1;
        }
        for (int i = 0; i < tabAiguille.length; i++){
            tabAiguille[i] = String.valueOf(aiguille.charAt(i));
        }
        cpt = cpt + chercherHorizontalement(tabBotte, tabAiguille); 
        cpt = cpt + chercherVerticalement(tabBotte, tabAiguille); 
        cpt = cpt + chercherEnDiagonale(tabBotte, tabAiguille);         
        
        return cpt;
    }
    
public static int chercherHorizontalement(String botte[][], String aiguille[]){
    int cpt = 0;
    // gauche → droite
    for (int i = 0; i < botte.length; i++){
        for (int j = 0; j <= botte[i].length - aiguille.length; j++) {
            boolean trouve = true;
            for (int a = 0; a < aiguille.length; a++) {
                if (!botte[i][j + a].equals(aiguille[a])) {
                    trouve = false;
                    break;
                }
            }
            if (trouve) cpt++;
        }
    }
    // droite → gauche
    for (int i = 0; i < botte.length; i++){
        for (int j = botte[i].length - 1; j >= aiguille.length - 1; j--) {
            boolean trouve = true;
            for (int a = 0; a < aiguille.length; a++) {
                if (!botte[i][j - a].equals(aiguille[a])) {
                    trouve = false;
                    break;
                }
            }
            if (trouve) cpt++;
        }
    }
    return cpt;
}

public static int chercherVerticalement(String botte[][], String aiguille[]){
    int cpt = 0;
    // haut → bas
    for (int i = 0; i < botte[0].length; i++){
        for (int j = 0; j <= botte.length - aiguille.length; j++) {
            boolean trouve = true;
            for (int a = 0; a < aiguille.length; a++) {
                if (!botte[j + a][i].equals(aiguille[a])) {
                    trouve = false;
                    break;
                }
            }
            if (trouve) cpt++;
        }
    }
    // bas → haut
    for (int i = 0; i < botte[0].length; i++){
        for (int j = botte.length - 1; j >= aiguille.length - 1; j--) {
            boolean trouve = true;
            for (int a = 0; a < aiguille.length; a++) {
                if ((j - a) < 0 || !botte[j - a][i].equals(aiguille[a])) {
                    trouve = false;
                    break;
                }
            }
            if (trouve) cpt++;
        }
    }
    return cpt;
}

public static int chercherEnDiagonale(String botte[][], String aiguille[]){
    int cpt = 0;
    for (int i = 0; i <= botte.length - aiguille.length ; i++){
        for (int j = 0; j <= botte[0].length - aiguille.length; j++) {
            boolean trouve = true;
            for (int a = 0; a < aiguille.length; a++) {
                if (!botte[i + a][j + a].equals(aiguille[a])) {
                    trouve = false;
                    break;
                }
            }
            if (trouve) {
                cpt++;
            }
        }
    }
    for (int i = aiguille.length - 1; i < botte.length ; i++){
        for (int j = 0; j <= botte[0].length - aiguille.length; j++) {
            boolean trouve = true;
            for (int a = 0; a < aiguille.length; a++) {
                if ((i - a) < 0 || (j + a) >= botte[0].length || !botte[i - a][j + a].equals(aiguille[a])) {
                    trouve = false;
                    break;
                }
            }
            if (trouve) {
                cpt++;
            }
        }
    }
    for (int i = 0; i <= botte.length - aiguille.length ; i++){
        for (int j = aiguille.length - 1; j < botte[0].length ; j++){
            boolean trouve = true;
            for (int b = 0; b < aiguille.length; b++) {
                if ((i + b) >= botte.length || (j - b) < 0 || !botte[i + b][j - b].equals(aiguille[b])) {
                    trouve = false;
                    break;
                }
            }
            if (trouve) {
                cpt++;
            }
        }
    }
    for (int i = aiguille.length - 1; i < botte.length ; i++){
        for (int j = aiguille.length - 1; j < botte[0].length ; j++){
            boolean trouve = true;
            for (int b = 0; b < aiguille.length; b++) {
                if ((i - b) < 0 || (j - b) < 0 || !botte[i - b][j - b].equals(aiguille[b])) {
                    trouve = false;
                    break;
                }
            }
            if (trouve) {
                cpt++;
            }
        }
    }
    return cpt;
}
 

    public static String[][] convetirEnMatrice(String botteString){
        String[] lignes = botteString.split("\n");
        int nbLignes = lignes.length;
        int nbColonnes = lignes[0].length();
        for (String ligne : lignes) {
        if (ligne.length() != nbColonnes) {
            return new String[0][0];
        }
    }
        String botte[][] = new String[nbLignes][nbColonnes];
        
            for (int i = 0; i < botte.length; i++){
                for(int j = 0; j < botte[i].length ;j++){
                    botte[i][j] = String.valueOf(lignes[i].charAt(j));
                    }      
                }
            
        return botte;
        }
}

