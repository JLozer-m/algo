package iut.sae.algo.simplicite.etu56;

public class Recherche {
    /**
     * Cherche une aiguille dans une botte de foin
     * @param botteDeFoin la botte de foin dans laquelle chercher l'aiguille
     * @param aiguille l'aiguille à chercher dans la botte de foin
     * @return nombre d'occurrences de l'aiguille dans la botte de foin, -1 si une erreur est détectée
     */
    public static int chercheMot(String botteDeFoin, String aiguille) {

            if (checkErreurs(botteDeFoin, aiguille)) {
                return -1;
            };

            int cpt = 0;
            int[][] facteurs = {{1,-1},{1,0},{1,1},{0,1}}; //directions sens de lecture : haut droite, droite, bas droite, bas...
            String reversed = reverseString(aiguille);
            String[][] tabBotteDeFoin = tablifier(botteDeFoin);
            int lignes = tabBotteDeFoin.length;
            int colonnes = tabBotteDeFoin[0].length;

            for (int ligne = 0; ligne < lignes; ligne++) {
                for (int colonne = 0; colonne < colonnes; colonne++) {
                    if (aiguille.length()==1) {
                        if (tabBotteDeFoin[ligne][colonne].equals(aiguille)) {
                            cpt++;
                        }
                    } else {
                        for (int i = 0; i < 4; i++) { //check dans les 4 directions de lecture
                            if (check(ligne,colonne,facteurs[i][0],facteurs[i][1],aiguille,tabBotteDeFoin)) {
                                cpt++;
                            }
                        }
                    }
                    
                    if (!reversed.equals(aiguille)) {
                        for (int i = 0; i < 4; i++) { //check dans les 4 directions de lecture mais avec la chaine renversée (donc au final les 4 autres directions)
                            if (check(ligne,colonne,facteurs[i][0],facteurs[i][1],reversed,tabBotteDeFoin)) {
                                cpt++;
                            }
                        }
                    }
                }
            }
            return cpt;
    }

    /**
     * Transforme une chaîne de caractères en tableau de caractères
     * @param txt la chaine de caractères
     * @return un tableau de caractères
     */
    public static String[][] tablifier(String txt){
        String [] split = txt.split("\n");
        int lignes = split.length;
        int colonnes = split[0].length();

        String[][] tableau = new String[lignes][colonnes];

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                tableau[i][j] = ""+split[i].charAt(j);
            }
        }
        return tableau;
    }

    /**
     * Affiche un tableau de caractères (ne sert plus vraiment mais c'était utile)
     * @param tab le tableau de caractères
     */
    public static void afficherTableau(String[][] tab){
        for (String[] ligne : tab) {
            for (String car : ligne) {
            System.out.print(car);
            }
            System.out.print("\n");
        }
    }

    /**
     * Vérifie si l'aiguille est présente dans la botte de foin
     * @param xstart la coordonnée de départ en x
     * @param ystart la coordonnée de départ en y
     * @param factX facteur de multiplication pour l'axe x (1 pour avancer, -1 pour reculer)
     * @param factY facteur de multiplication pour l'axe y (1 pour avancer, -1 pour reculer)
     * @param aiguille l'aiguille à chercher
     * @param tab le tableau de caractères représentant la botte de foin
     * @return true si l'aiguille est présente, false sinon
     */
    public static boolean check(int xstart, int ystart, int factX, int factY, String aiguille, String[][] tab ){ //factX et factY permettent de choisir le sens du check
        int lignes = tab.length;
        int colonnes = tab[0].length;

        int avancement = 0; // index de la lettre de la chaine analysée qui correspond à la même lettre du mot recherché
        boolean motcorrect = true; 

        //du moment qu'on est dans les bornes du tableau, que la chaine de caractère analysée est <= au mot recherché et qu'elle correspond.
        while (xstart+(avancement*factX)<lignes && xstart+(avancement*factX)>=0 && ystart+(avancement*factY)>=0 && ystart+(avancement*factY)<colonnes && motcorrect && avancement<aiguille.length()) {
            
            if ((""+aiguille.charAt(avancement)).equals( tab[xstart+(avancement*factX)][ystart+(avancement*factY)] )) { //le caractère suivant (factX et factY font varier la direction)
                avancement++;
            } else {
                motcorrect = false;
            }           
        }
        if (avancement-1 != aiguille.length()-1) {
            motcorrect=false;
        } 

        return motcorrect;
    }

    /**
     * Renverse une chaîne de caractères
     * @param chaine chaine de caractères
     * @return la chaîne de caractères renversée
     */
    public static String reverseString(String chaine){
        String reversed = "";
        for (int i = chaine.length()-1; i >= 0; i--) {
            reversed+=chaine.charAt(i);
        }
        return reversed;
    }

    /**
     * Vérifie si la botte de foin et l'aiguille sont valides
     * @param botte la botte de foin
     * @param aiguille l'aiguille à chercher dans la botte de foin
     * @return true si une erreur est détectée, false sinon
     */
    public static boolean checkErreurs(String botte, String aiguille){
        if (aiguille == null || botte == null) {
            return true;
        }

        String[] tabLignes = botte.split("\n");
        for (int i = 0; i < tabLignes.length; i++) { //vérifie qu'on a bien partout la même longueur de ligne...
            if (i!=0 && tabLignes[i].length()!=tabLignes[i-1].length()) {
                return true;
            }
        }
        return false;
        
    }
}
