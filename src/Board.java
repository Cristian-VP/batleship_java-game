public class Board{
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
        setShip('L', 1, boats, 0);
        setShip('B', 3, ships, randomize(0,1));
        setShip('Z', 4, battleships, randomize(0,1));
        setShip('P', 5, aircraftCarriers, randomize(0,1));
    }
    //Invoca la validación para colocar el barco en el tablero y lo coloca.
    public static void setShip(char kindOfShip, int size, int quantity, int position){
        while (quantity > 0) {
            int row = randomize(0, rows - 1);
            int col = randomize(0, columns - 1);

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
                quantity--;
            }
        }
    }
    // Valída si hay espacio suficiente para colocar el barco, respetando que exista una distancia de dos posiciones entre barcos, tanto en horizontal como en vertical.
    public static boolean validateBox(int size, int position, int row, int col) {
        if (position == 0) {
            if(col + size  > columns-1){
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
    public static void printPlayerBoard(){
        for(int i = -1; i < rows; i++){
            System.out.printf("  %d", i);
        }
        System.out.println();
        for(int i = 0; i < rows; i++){
                System.out.printf("  %C ", 65+1);
            for(int j = 0; j < columns; j++){
                System.out.printf("| %C ", playerBoard[i][j]);
            }
            System.out.println('|');
        }
    }
    //Imprime el tablero del ordenador.
    public static void printComputerBoard(){
        for(int i = -1; i < rows; i++){
            System.out.printf("  %d", i);
        }
        System.out.println();
        for(int i = 0; i < rows; i++){
            System.out.printf("  %C ", 65+1);
            for(int j = 0; j < columns; j++){
                System.out.printf("| %C ", computerBoard[i][j]);
            }
            System.out.println('|');
        }
    }
    //Invoca el controlador de disparos y actualiza el tablero.
    public static String shoot(int row, int col){
        String playerShoot = "";
        if(!repeatedShot(row, col)){
            if(computerBoard[row][col] == '-'){
                playerBoard[row][col] = 'A';
                computerBoard[row][col] = 'O';
                playerShoot = "Agüita!! Inténtalo de nuevo, ¡Ánimos soldado!";
            } else if(computerBoard[row][col] == 'L' || computerBoard[row][col] == 'B' || computerBoard[row][col] == 'Z' || computerBoard[row][col] == 'P'){
                playerBoard[row][col] = 'X';
                computerBoard[row][col] = 'X';
                playerShoot =  "Tocado!! Sigue así, ¡Vamos a hundirlos!";
            }
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

