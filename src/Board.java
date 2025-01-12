public class Board{
    public static final String RESET = "\u001B[0m";  // Text Reset
    public static final String BLUE = "\u001B[0;34m";    // BLUE
    private static char[][] computerBoard;
    private static char[][] playerBoard;
    private static int rows,columns;


    //Constructor que inicializa los tableros con valores predeterminados ('-').
    public Board(int rows, int columns){
        Board.rows = rows;
        Board.columns = columns;
        computerBoard = new char[rows][columns];
        playerBoard = new char[rows][columns];
        setDefaultBoard();
    }
    //Completa el tablero con el valor de cada posición por defecto ('-').
    public static void setDefaultBoard (){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                computerBoard[i][j] = '-';
                playerBoard[i][j] = '-';
            }
        }
    }
    //Da un valor pseudo-aleatorio entre un rango de números.
    public static int randomize(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }
    //Coloca todos los barcos del ordenador según los tipos y cantidades especificados.
    public static void insertShips(int boats, int ships, int battleships, int aircraftCarriers){
        setShip('P', 5, aircraftCarriers, 1);
        setShip('Z', 4, battleships, 0);
        setShip('L', 1, boats, 0);
    }
    //Invoca la validación para colocar el barco en el tablero y lo coloca.
    public static void setShip(char kindOfShip, int size, int quantity, int position){
        int numberOfShips = quantity;
        while (numberOfShips > 0) {
            int row = randomize(0, rows - 1);
            int col = randomize(0, columns - 1);
            System.out.println("row: "+row+" col: "+col);
            System.out.println("number of ships: "+numberOfShips);
            if(!validateBox(size,position, row, col)) {
            // position 0 = horizontal, 1 = vertical
                if (position == 0) {
                    for (int i = 0; i < size; i++) {
                        computerBoard[row][col + i] = kindOfShip;
                    }
                } else {
                    for (int i = 0; i < size; i++) {
                        computerBoard[row + i][col] = kindOfShip;
                    }
                }
                numberOfShips--;
            }
        }
    }
    // Valída si hay espacio suficiente para colocar el barco, respetando que exista una distancia de dos posiciones entre barcos, tanto en horizontal como en vertical.
    public static boolean validateBox(int size, int position, int row, int col) {
        if (position == 0) {
            if(col + size  > columns-1){
                System.out.println("No hay espacio suficiente para colocar el barco");
                return false;
            }
            // Disponibilidad de las posiciones del tamaño del barco hacia la derecha en horizontal y si guarda una distancia de dos posiciones entre barcos, tanto en horizontal como en vertical
            for(int i = 0 ; i < size + 2; i++) {
                if (col + i < columns -1  && computerBoard[row][col + i] != '-') return false;
            }
            // Comprueba si hay dos posiciones libre hacia la izquierda en horizontal
            for (int i = 1 ; i <= 2; i++) {
                    if (col - i >= 0 && computerBoard[row][col-i] != '-') return false;
            }

            // Distancia mínima libre hacia abajo en vertical
            if(row < rows-1){
                for(int i = 1; i <= 2; i++){
                    if (row + i < rows  && computerBoard[row+i][col] != '-') return false;
                }
            }
            // Comprueba si hay dos posiciones libre hacia arriba en vertical
            if(row > 0){
                for(int i = 1; i <= 2; i++){
                    if (row - i >= 0  && computerBoard[row-i][col] != '-' ) return false;
                }
            }
        } else {
            if(row + size  > rows-1){
                return false;
            }
            // Disponibilidad de las posiciones del tamaño del barco hacia abajo en vertical y si guarda una distancia de dos posiciones entre barcos
            for(int i = 0 ; i < size + 2 ; i++){
                if(row + i < rows && computerBoard[row + i][col] != '-') return false;
            }
            // Comprueba si hay dos posiciones libre hacia arriba en vertical
            for(int i = 1; i <= 2; i++){
                if(row - i >= 0 && computerBoard[row-i][col] != '-') return false;
            }

            // Comprueba si hay dos posiciones libre hacia la derecha en horizontal
            if(col < columns-1){
                for(int i = 1; i <= 2; i++){
                    if(col + i < columns && computerBoard[row][col+i] != '-') return false;
                }
            }
            // Comprueba si hay dos posiciones libre hacia la izquierda en horizontal
            if(col > 0){
                for(int i = 1; i <= 2; i++){
                    if(col - i >= 0 && computerBoard[row][col-i] != '-') return false;
                }
            }
        }
        return  true;
    }
    //Imprime el tablero del jugador
    public static void printPlayerBoard(int userShoots, String user){
        System.out.println();
        System.out.println("#################################################");
        String charactersHeader = ( user.equals("Cobarde") ) ? "###############" : "############";
        System.out.println(" "+charactersHeader+" Tablero de "+user+" "+charactersHeader);
        System.out.println("#################################################");
        System.out.println();
        System.out.printf("Disparos restantes: %d\n", userShoots);
        System.out.print("   ");
        for(int i = 0; i < columns; i++){
            System.out.printf("  %d ", i);
        }
        System.out.println();
        System.out.print("   ");
        for(int i = 0; i < columns; i++){
            System.out.print("+---");
        }
        System.out.println("+");
        for(int i = 0; i < rows; i++){
                System.out.printf(" %C ", 65+i);
            for(int j = 0; j < columns; j++){
                System.out.printf("| %C ", playerBoard[i][j]);

            }
            System.out.println('|');
            System.out.print("   ");
            for (int k = 0; k < columns; k++) {
                System.out.print("+---");
            }
            System.out.println("+");
        }
        System.out.println();
    }
    //Imprime el tablero del ordenador.
    public static void printComputerBoard(int userShoots){
        System.out.println();
        System.out.println("#################################################");
        System.out.println("############### Tablero enemigo  ################");
        System.out.println("#################################################");
        System.out.println();
        System.out.printf("Disparos restantes: %d\n", userShoots);
        System.out.print("   ");
        for(int i = 0; i < columns; i++){
            System.out.printf("  %d ", i);
        }
        System.out.println();
        System.out.print("   ");
        for(int i = 0; i < columns; i++){
            System.out.print("+---");
        }
        System.out.println("+");
        for(int i = 0; i < rows; i++){
            System.out.printf(" %C ", 65+i);
            for(int j = 0; j < columns; j++){
                System.out.printf("| %C ", computerBoard[i][j]);

            }
            System.out.println('|');
            System.out.print("   ");
            for (int k = 0; k < columns; k++) {
                System.out.print("+---");
            }
            System.out.println("+");
        }
        System.out.println();
    }
    //Invoca el controlador de disparos y actualiza el tablero.
    public static String shoot(int row, int col){
        String playerShoot = "";
        if(computerBoard[row][col] == '-'){
            playerBoard[row][col] = 'A';
            computerBoard[row][col] = 'O';
            playerShoot =
                    "\n \n"+
                    "   ____        _____    __    __     ____   \n" +
                    "  (    )      / ___ \\   ) )  ( (    (    )  \n" +
                    "  / /\\ \\     / /   \\_) ( (    ) )   / /\\ \\  \n" +
                    " ( (__) )   ( (  ____   ) )  ( (   ( (__) ) \n" +
                    "  )    (    ( ( (__  ) ( (    ) )   )    (  \n" +
                    " /  /\\  \\    \\ \\__/ /   ) \\__/ (   /  /\\  \\ \n" +
                    "/__(  )__\\    \\____/    \\______/  /__(  )__\\ \n"+
                    "      Inténtalo de nuevo, ¡Ánimos soldado!"+
                    "\n \n";
        } else if(computerBoard[row][col] == 'L' || computerBoard[row][col] == 'B' || computerBoard[row][col] == 'Z' || computerBoard[row][col] == 'P'){
            playerBoard[row][col] = 'X';
            computerBoard[row][col] = 'X';
            playerShoot =
                    "\n \n"+
                    "            )                (         )  \n" +
                            "  *   )  ( /(    (     (     )\\ )   ( /(  \n" +
                            "` )  /(  )\\())   )\\    )\\   (()/(   )\\()) \n" +
                            " ( )(_))((_)\\  (((_)((((_)(  /(_)) ((_)\\  \n" +
                            "(_(_())   ((_) )\\___ )\\ _ )\\(_))_    ((_) \n" +
                            "|_   _|  / _ \\((/ __|(_)_\\(_)|   \\  / _ \\ \n" +
                            "  | |   | (_) || (__  / _ \\  | |) || (_) |\n" +
                            "  |_|    \\___/  \\___|/_/ \\_\\ |___/  \\___/\n "+
                            "        Sigue así, ¡Vamos a hundirlos!"+
                    "\n \n";
        }
        return playerShoot;
    }
    //Comprueba si la casilla ya fue disparada.
    public static Boolean repeatedShot (int row, int col){
        return computerBoard[row][col] == 'O' || computerBoard[row][col] == 'X';
    }
    //Comprueba si quedan barcos sin hundir en el tablero del ordenador.
    public static boolean checkShipsAlive(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if(computerBoard[i][j] == 'L' || computerBoard[i][j] == 'B' || computerBoard[i][j] == 'Z' || computerBoard[i][j] == 'P'){
                    return true;
                }
            }
        }
        return false;
    }
}

