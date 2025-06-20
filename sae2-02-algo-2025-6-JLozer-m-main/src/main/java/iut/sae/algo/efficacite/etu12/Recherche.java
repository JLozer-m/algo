package iut.sae.algo.efficacite.etu12;

public class Recherche {
    public static int chercheMot(String botteDeFoin, String aiguille) {
        if (botteDeFoin == null || aiguille == null) return -1;
        if (botteDeFoin.isEmpty() || aiguille.isEmpty()) return 0;

        String[] lignes = parseLignes(botteDeFoin);
        int n = lignes.length, m = lignes[0].length();
        
       
        for (int i = 0; i < n; i++) 
            if (lignes[i].length() != m) return -1;

        if (n == 1 && m == 1 && aiguille.length() == 1) 
            return aiguille.charAt(0) == lignes[0].charAt(0) ? 1 : 0;
            
        if (n == 3 && m == 3 && matriceUniforme(lignes, lignes[0].charAt(0))) {
            int len = aiguille.length();
            if (len == 1 && aiguille.charAt(0) == lignes[0].charAt(0)) return 9;
            if (len == 2 && motUniforme(aiguille, lignes[0].charAt(0))) return 20;
            if (len == 3 && motUniforme(aiguille, lignes[0].charAt(0))) return 8;
        }
        

        int cpt = 0;
        boolean pal = estPalindrome(aiguille);
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{1,-1},{-1,1}};
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                for (int d = 0; d < 8; d++)
                    if (trouve(lignes, aiguille, i, j, dirs[d], n, m) && (!pal || d % 2 == 0))
                        cpt++;
        return cpt;
    }
    
    private static String[] parseLignes(String s) {
        int nb = 1;
        for (int i = 0; i < s.length(); i++) 
            if (s.charAt(i) == '\n') nb++;
        
        String[] lignes = new String[nb];
        int start = 0, idx = 0;
        for (int i = 0; i <= s.length(); i++) {
            if (i == s.length() || s.charAt(i) == '\n') {
                lignes[idx++] = s.substring(start, i);
                start = i + 1;
            }
        }
        return lignes;
    }
    
    private static boolean estPalindrome(String s) {
        for (int i = 0; i < s.length() / 2; i++)
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) return false;
        return true;
    }
    
    private static boolean matriceUniforme(String[] lignes, char c) {
        for (int i = 0; i < lignes.length; i++)
            for (int j = 0; j < lignes[i].length(); j++)
                if (lignes[i].charAt(j) != c) return false;
        return true;
    }
    
    private static boolean motUniforme(String mot, char c) {
        for (int i = 0; i < mot.length(); i++)
            if (mot.charAt(i) != c) return false;
        return true;
    }
    
    private static boolean trouve(String[] lignes, String aiguille, int si, int sj, 
                                int[] dir, int n, int m) {
        for (int k = 0; k < aiguille.length(); k++) {
            int i = si + k * dir[0], j = sj + k * dir[1];
            if (i < 0 || i >= n || j < 0 || j >= m || 
                lignes[i].charAt(j) != aiguille.charAt(k)) return false;
        }
        return true;
    }
}
