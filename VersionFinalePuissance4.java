import java.util.Scanner;

class VersionFinalePuissance4 {
    private static final Scanner input = new Scanner(System.in);
    /* les structures de donnees sont : int[][] grille et int couleur */
    private static final int VIDE = 0;
    private static final int JAUNE = 1;
    private static final int ROUGE = 2;

    public static void main(String[] args) {
        int[][] grille = new int[6][7];

        initialise(grille);
        affiche(grille);

        int couleurJoueur = JAUNE;
        boolean gagne;
        do {
            demandeEtJoue(grille, couleurJoueur);
            affiche(grille);
            gagne = estCeGagne(grille, couleurJoueur);
            /* on change la couleur pour la couleur de l'autre joueur : */
            // si on est en couleur jaune, alors on change en couleur rouge,
            // sinon, on change pas la couleur
            if (couleurJoueur == JAUNE) {
                couleurJoueur = ROUGE;
            } else {
                couleurJoueur = JAUNE;
            }
        } while (!gagne && !plein(grille));

        if (gagne) {
            // attention, on a changé la couleur pour la couleur de l'autre joueur !
            // cela signifie que si c'est le tour du O, alors c'est X qui a gagné, et inversement.
            if (couleurJoueur == JAUNE) {
                System.out.println("Le joueur O a gagné");
            } else {
                System.out.println("Le joueur X a gagné");
            }
        } else {
            System.out.println("Match nul !");
        }

    }

    //    =========================================================================================

    /* teste si la grille courante est pleine */
    // idee : il suffit de verifier si les cases de la ligne 0 sont pleines ou pas.
    // Si chaque case est pleine, alors la grille est pleine. Sinon, colonne pas pleine.
    static boolean plein(int[][] grille) {
        for (int cellule : grille[0]) {
            if (cellule == VIDE) {
                return false;
            }
        }
        // Sinon, la grille est pleine
        return true;
    }
    //    =========================================================================================

    // i pour la ligne courante
    // j pour la colonne courante

    /**
     * Video5
     **/
    /* compte le nb de pions d'une mm couleur (soit jaune soit rouge) */
    // dir pour direction
    static int compte(int[][] grille, int ligneDepart, int colonneDepart, int dirLigne, int dirColonne) {
        int count = 0;
        int i = ligneDepart;
        int j = colonneDepart;

        // on part de la case (ligneDepart,colonneDepart) et on parcourt la grille dans la direction donnée par
        // (dirLigne,dirColonne) tant qu'on trouve des pions de la mm couleur que le pion de depart :
        while ((i >= 0 && i < grille.length) &&
                (j >= 0 && j < grille[0].length) &&
                grille[i][j] == grille[ligneDepart][colonneDepart]) {
            ++count;
            i += dirLigne;
            j += dirColonne;
        }
        return count;
    }


    /* verifie si le joueur gagne ou pas */
    static boolean estCeGagne(int[][] grille, int couleurJoueur) {
        for (int ligne = 0; ligne < grille.length; ++ligne) {
            for (int colonne = 0; colonne < grille[0].length; ++colonne) {
                // la couleur de la case = la ij-ième composante (= la composante courante)
                int couleurCase = grille[ligne][colonne];

                // si la case qui contient un pion de la mm couleur que la couleur du joueur,
                // alors on compte les pions de la mm couleur dans quatre directions :
                if (couleurCase == couleurJoueur) {
                    final int ligneMax = grille.length - 4;
                    final int colonneMax = grille[0].length - 4;
                    if (
                        //en diagonale, vers le haut et la droite :
                            ((ligne >= 3 && colonne <= colonneMax) && compte(grille, ligne, colonne, -1, +1) >= 4)
                                    ||
                                    // horizontalement, vers la droite :
                                    (colonne <= colonneMax && compte(grille, ligne, colonne, 0, +1) >= 4)
                                    ||
                                    // en diagonale, vers le bas et la droite :
                                    ((ligne <= ligneMax && colonne <= colonneMax) && compte(grille, ligne, colonne, +1, +1) >= 4)
                                    ||
                                    // verticalement, vers le bas :
                                    ((ligne <= ligneMax) && compte(grille, ligne, colonne, 1, 0) >= 4)
                    ) {
                        return true;
                    }
                }
            }
        }
        // si on a parcouru toute la grille mais pas trouvé de "au moins quatre pions alignés",
        // alors le joueur de la couleur correspondante n'a pas gagné, du moins pas encore :
        return false;
    }


    //    =========================================================================================

    /**
     * Video4
     **/
    /* il faut poser la question puis recuperer la reponse  (dans do) */
    static void demandeEtJoue(int[][] grille, int couleurJoueur) {
        boolean valide;
        //On pose d'abord la question. Si le coup n'est pas valide, alors on répète jusqu'au coup soit valide.
        do {
            System.out.print("Joueur ");
            demandeQuelJoueur(couleurJoueur);
//            int colonne = demandeColonneInBounds(grille);

            // les indices des tableaux commencent par 0 en Java :
//            --colonne;
            valide = joueVer2(grille, couleurJoueur);
            if (!valide) {
                System.out.println(" > Ce coup n'est pas valide");
            }
        } while (!valide);
    }

    /* cette meth pourrait etre utile dans l'affichage du jeu et du joueur */
    static void demandeQuelJoueur(int couleurJoueur) {
        if (couleurJoueur == JAUNE) {
            System.out.println("X :");
        } else {
            System.out.println("O :");
        }
    }

    //    =======================================================================

    /* initialiser l'ensemble des cases de la grille à rien (i.e. VIDE) */
    static void initialise(int[][] grille) { // on s'attend pas à retourner une grille
        for (int i = 0; i < grille.length; ++i) {
            for (int j = 0; j < grille[0].length; ++j) {
                grille[i][j] = 0;
            }
        }
    }

    /* affiche les contenus de chaque case de la grille */
    // affiche O pour une case rouge, X pour une case jaune
    static void affiche(int[][] grille) {
        for (int[] ligne : grille) {
            System.out.print(" |");
            for (int cellule : ligne) {
                switch (cellule) {
                    case VIDE -> System.out.print(' ');
                    case JAUNE -> System.out.print('X');
                    case ROUGE -> System.out.print('O');
                }
                System.out.print('|');
            }
            System.out.println();
        }
        afficheBas(grille);
    }

    /* affiche ==1=2=3=4=5=6=7== qui se trouve en bas de la grille */
    static void afficheBas(int[][] grille) {
        System.out.print('=');
        for (int i = 1; i <= grille[0].length; ++i) {
            System.out.print("=" + i);
        }
        System.out.println("==");
    }

    //====================================================================
    static boolean joueVer2(int[][] grille, int couleur) {

        int colonne = demandeColonneInBounds(grille);
        // on teste d'abord si la col est pleine.
        // si la col est pleine, alors le coup n'est pas valide <-- return false
        // :
        if (grille[0][colonne] != VIDE) {
            System.out.println("Le coup n'est pas valide car la colonne est pleine.");
            return false;
        }
        // sinon, on sait qu'il existe au moins une case vide donc le coup est forcement valide <-- return true
        // on parcourt la col en partant du bas jusqu'a trouver une case vide
        // :
        int ligne = grille.length - 1;
        while (grille[ligne][colonne] != VIDE) {
            --ligne;
        }
        System.out.println("Le coup est valide");
        // on remplit la case vide trouvée
        grille[ligne][colonne] = couleur;
        return true;
    }

    static int demandeColonneInBounds(int[][] grille) {
        int colonne;
        do {
            System.out.print("Entrez la colonne : ");
            colonne = input.nextInt();
            if (colonne < 0 || colonne > 6) {
                System.out.println("La grille a au moins 1 col (indice 0) et au plus " + grille[0].length +
                        " colonnes (indice 6). Reentrez la colonne.");
            }
        } while (colonne < 0 || colonne > 6);
        return colonne;
    }

}