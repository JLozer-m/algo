package iut.sae.algo.sobriete.etu41;

public class Recherche {
    public static int chercheMot(String botteDeFoin, String aiguille) {

        if (aiguille==null || botteDeFoin == null) {
            return -1;
        }
        if (botteDeFoin.length() == 0 || aiguille.length() == 0) {
            return 0;
        }
        
        int cols = 0;
        int i = 0;

        while (i<botteDeFoin.length() && botteDeFoin.charAt(i) != '\n') {
            cols = cols + 1;
            i++;
        }
        
        // grille régulière
        int lignes = 1;
        int colActuelle = 0;
        for (i = 0; i<botteDeFoin.length(); i++) {
            if (botteDeFoin.charAt (i)=='\n') {
                if (colActuelle != cols) {
                    return -1;
                }
                lignes = lignes + 1;
                colActuelle = 0;
            } else {
                colActuelle++;
            }
        }
        if (colActuelle != cols) {
            return -1;
        }
        
        int cpt=0;
        
        if (aiguille.length() == 1) {
            for (i = 0; i < botteDeFoin.length(); i = i + 1) {
                if (botteDeFoin.charAt(i)!='\n' && botteDeFoin.charAt(i) == aiguille.charAt(0)) {
                    cpt++;
                }
            }
            return cpt;
        }
        
        boolean estLuDouble = true;
        for (i=0; i < aiguille.length() / 2; i = i + 1) {
            if (aiguille.charAt(i) != aiguille.charAt(aiguille.length() - 1 - i)) {
                estLuDouble = false;
                break;
            }
        }
        
        // Recherche dans toutes les directions
        int dx, dy;
        int nbDirections;
        if (estLuDouble) {
            nbDirections = 4;
        } else {
            nbDirections = 8;
        }
        
        for (int ligne = 0; ligne<lignes; ligne = ligne + 1) {
            for (int col = 0; col < cols; col++) {
                for (int dir=0; dir < nbDirections; dir++) {
                    
                    if (dir == 0) {
                        dx = 0; dy = 1;// Est
                    } else if (dir == 1) {
                        dx = 1; dy = 1;// SE
                    } else if (dir == 2) {
                        dx = 1; dy = 0;  // S
                    } else if (dir == 3) {
                        dx = 1; dy = -1;// SW
                    } else if (dir == 4) {
                        dx = 0; dy = -1;// W
                    } else if (dir == 5) {
                        dx = -1; dy = -1;// NW
                    } else if (dir == 6) {
                        dx = -1; dy = 0;// N
                    } else {
                        dx = -1; dy = 1; //NE
                    }
                    
                    // limites
                    if (ligne + dx *(aiguille.length() - 1) < 0 || ligne+dx * (aiguille.length() - 1)>=lignes || col + dy*(aiguille.length() - 1)< 0 || col + dy * (aiguille.length()-1) >= cols) {
                        continue;
                    }
                    
                    // Trouver mot
                    for (i = 0; i<aiguille.length(); i = i + 1) {
                        if (botteDeFoin.charAt((ligne + i * dx)*(cols + 1) + (col + i * dy))!=aiguille.charAt(i)) {
                            break;
                        }
                    }
                    
                    if (i == aiguille.length()) {
                        cpt = cpt + 1;
                    }
                }
            }
        }
        
        return cpt;
    }
}
