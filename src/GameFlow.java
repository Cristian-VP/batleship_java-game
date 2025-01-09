import java.util.InputMismatchException;
import java.util.Scanner;

public class GameFlow {
    private static Scanner scanner;
    private static int tries;
    private static String user;

    public void createGame(){
        Board board = new Board(10,10);
        scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("       Bienvenido a Hundir la flota");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("       ¡Prepárate para la batalla!");
        System.out.println();
        System.out.println();
        System.out.print("¡Soldado dime el nombre tu madre!:");
        user = scanner.next();
        System.out.println();
        System.out.println("|                                         |");
        System.out.println("|=========================================|");
        System.out.println("|    Dificultad             Número        |");
        System.out.println("|                                         |");
        System.out.println("|    Cobarde                  1           |");
        System.out.println("|    Aventurero               2           |");
        System.out.println("|    Destructor               3           |");
        System.out.println("|=========================================|");
        System.out.println();
        System.out.print("Ahora, introduce la dificultad de tu partida:");
        Integer difficulty = intDificultValidator(scanner);
        System.out.println();
        System.out.println();
        switch (difficulty){
            case 1:
                System.out.println("####################################################");
                System.out.println("  Has seleccionado la dificultad Cobarde ");
                System.out.println("  Tienes 50 disparos para hundir 10 barcos de guerra");
                System.out.println("####################################################");
                System.out.println();

                Board.insertShips(5,3,0,0);
                tries = 50;
                break;
            case 2:
                System.out.println("###################################################");
                System.out.println(" Has seleccionado la dificultad Aventurero ");
                System.out.println(" Tienes 30 disparos para hundir 5 barcos de guerra");
                System.out.println("###################################################");
                System.out.println();

                Board.insertShips(2,1,1,1);
                tries = 30;
                break;
            case 3:
                System.out.println("##################################################");
                System.out.println(" Has seleccionado la dificultad Destructor. ");
                System.out.println(" Tienes 10 disparos para hundir 2 barcos de guerra.");
                System.out.println("###################################################");
                System.out.println();
                System.out.println();
                Board.insertShips(1,1,0,0);
                tries = 10;
                break;
            default:
                System.out.println("Dificultad no válida");
        }
        startGame(board);
    }
    /*
     jugarPartida(Tablero tablero, int intentos):
Muestra el tablero visible del jugador.
Solicita coordenadas de disparo al jugador.
Llama a disparar() en la clase Tablero.
Actualiza el estado del juego tras cada disparo (mostrar "Tocado" o "Agua").
Determina si el jugador gana o pierde.
     */

    public static void startGame(Board board) {
        do{
            Board.printPlayerBoard(tries, user);
            System.out.println("¡Alerta soldado! Es hora de atacar.");
            System.out.print("Introduce la primera coordenada (0-9):");
            int xCoordinate = intCoordinateValidator(scanner);
            System.out.print("Introduce la segunda coordenada (a-j):");
            String yCoordinate = charCoordinateValidator(scanner);
            System.out.printf("Disparando a la coordenada: (%d, %S)\n", xCoordinate, yCoordinate);
            System.out.printf("%s\n",Board.shoot(xCoordinate,convertCharToIntCoordinate(yCoordinate)));
            if(tries < 10) System.out.println("¡Espabila soldado! Te quedan menos de 10 disparos");
            tries--;
            if(tries == 0) {
                System.out.println("¡Has perdido! Te has quedado sin disparos");
                System.out.println(("Los barcos enemigos han ganado"));
                System.out.println("Tablero de barcos enemigos:");
                Board.printComputerBoard();
            }
            if(!Board.checkShipsAlive()){
                System.out.println("¡Has ganado! Has hundido todos los barcos");
                System.out.println(("Los barcos enemigos han ganado"));
                System.out.println("Tablero de barcos enemigos:");
                Board.printComputerBoard();
            }
        }while (Board.checkShipsAlive() && tries > 0);
    }

    public static int convertCharToIntCoordinate(String yCoordinate){
        return yCoordinate.charAt(0) - 65;
    }

    public static String charCoordinateValidator(Scanner input){
        try {
            String coordinate;
            coordinate = input.next().toLowerCase();
            if (coordinate.matches("[a-j]")) {
                System.out.println("Coordenada válida: "+coordinate);
                return coordinate.toUpperCase();
            }else if(coordinate.matches("[^a-zA-Z]")){
                System.out.println("Coordenada no válida, solo puedes ser alfabético");
            }else {
                System.out.println("Coordenada no válida, solo puedes elegir entre a y j");
            }
        } catch (InputMismatchException e) {
            System.out.println("Dato introducido no válido, solo puede ser alfabético");
            input.next();
        }
        System.out.println("Introduce una coordenada nuevamente (a-j):");
        return charCoordinateValidator(input);
    }

    public static int intCoordinateValidator(Scanner input){
        try {
            int coordinate;
            if (input.hasNextInt()) {
                coordinate = input.nextInt();
                if (coordinate >= 0 && coordinate <= 9) {
                    System.out.println("Coordenada válida: "+coordinate);
                    return coordinate;
                }else{
                    System.out.println("Coordenada no válida, solo puedes elegir entre 0 y 9");
                    input.next();
                }
            }else{
                input.next();
            }
        } catch (InputMismatchException e) {
            System.out.println("Dato introducido no válido, solo puede ser numérico");
            input.next();
        }
        System.out.println("Introduce una coordenada nuevamente (0-9):");
        return intCoordinateValidator(input);
    }

    public Integer intDificultValidator (Scanner input){
        try {
            Integer dificult;
            if (input.hasNextInt()) {
                dificult = input.nextInt();
                while (dificult < 1 || dificult > 3) {
                    System.out.println("Opción no válida, solo puedes elegir entre 1, 2 o 3");
                    System.out.print("Introduce nuevamente la dificultad:");
                    dificult = scanner.nextInt();
                }
                return dificult;
            }
            return null;
        } catch (InputMismatchException e) {
            scanner.next();
            System.out.println("Dato introducido no válido, solo puede ser numérico");
            return null;
        }
    }
}
