package iut.sae.algo.sobriete.etu32;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        if (botteDeFoin == null || aiguille == null || aiguille.isEmpty()) return -1;
        if (botteDeFoin.isEmpty()) return 0;

        int aiguilleLen = aiguille.length();

        // Cas particulier : aiguille d’un seul caractère
        if (aiguilleLen == 1) {
            char c = aiguille.charAt(0);
            int count = 0;
            for (int i = 0, n = botteDeFoin.length(); i < n; i++) {
                char ch = botteDeFoin.charAt(i);
                if (ch == c && ch != '\n') count++;
            }
            return count;
        }

        int largeur = -1, hauteur = 1, longueurLigne = 0;

        for (int i = 0, n = botteDeFoin.length(); i < n; i++) {
            char ch = botteDeFoin.charAt(i);
            if (ch == '\n') {
                if (largeur == -1) largeur = longueurLigne;
                else if (longueurLigne != largeur) return -1;
                longueurLigne = 0;
                hauteur++;
            } else {
                longueurLigne++;
            }
        }

        if (largeur == -1) largeur = longueurLigne;
        else if (longueurLigne != largeur) return -1;

        boolean palindrome = estPalindrome(aiguille);
        int total = 0;

        int[] dx = {1, -1, 0,  0,  1, -1,  1, -1};
        int[] dy = {0,  0, 1, -1,  1, -1, -1,  1};

        int stride = largeur + 1;

        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                for (int dir = 0; dir < 8; dir++) {
                    if (palindrome && (dir % 2 == 1)) continue;

                    int cx = x, cy = y, i = 0;

                    while (i < aiguilleLen) {
                        if (cx < 0 || cx >= largeur || cy < 0 || cy >= hauteur) break;
                        int idx = cy * stride + cx;
                        if (idx >= botteDeFoin.length() || botteDeFoin.charAt(idx) != aiguille.charAt(i)) break;
                        cx += dx[dir];
                        cy += dy[dir];
                        i++;
                    }

                    if (i == aiguilleLen) total++;
                }
            }
        }

        return total;
    }

    private static boolean estPalindrome(String s) {
        for (int i = 0, n = s.length(); i < n / 2; i++) {
            if (s.charAt(i) != s.charAt(n - 1 - i)) return false;
        }
        return true;
    }
}
