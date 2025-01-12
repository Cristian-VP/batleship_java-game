import java.net.UnknownServiceException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameFlow {
    private static Scanner scanner;
    private static int tries;
    private static String user;
    private static String enter;

    public void createGame(){
        Board board = new Board(10,10);
        scanner = new Scanner(System.in);
        String title =
                "            "+" ______     ______     ______   __         ______   \n" +
                "            "+"/\\  == \\   /\\  __ \\   /\\__  _\\ /\\ \\       /\\  ___\\  \n" +
                "            "+"\\ \\  __<   \\ \\  __ \\  \\/_/\\ \\/ \\ \\ \\____  \\ \\  __\\  \n" +
                "            "+" \\ \\_____\\  \\ \\_\\ \\_\\    \\ \\_\\  \\ \\_____\\  \\ \\_____\\\n" +
                "            "+"  \\/_____/   \\/_/\\/_/     \\/_/   \\/_____/   \\/_____/\n" +
                "            "+"                                                    \n" +
                "            "+"          ______     __  __     __     ______       \n" +
                "            "+"         /\\  ___\\   /\\ \\_\\ \\   /\\ \\   /\\  == \\      \n" +
                "            "+"         \\ \\___  \\  \\ \\  __ \\  \\ \\ \\  \\ \\  _-/      \n" +
                "            "+"          \\/\\_____\\  \\ \\_\\ \\_\\  \\ \\_\\  \\ \\_\\        \n" +
                "            "+"           \\/_____/   \\/_/\\/_/   \\/_/   \\/_/        ";
        System.out.println(title);
        System.out.println();
        System.out.println();
        System.out.println();
        String boat = "                                     |__\n" +
                "                                     |\\/\n" +
                "                                     ---\n" +
                "                                     / | [\n" +
                "                              !      | |||\n" +
                "                            _/|     _/|-++'\n" +
                "                        +  +--|    |--|--|_ |-\n" +
                "                     { /|__|  |/\\__|  |--- |||__/\n" +
                "                    +---------------___[}-_===_.'____               /\\\n" +
                "                ____`-' ||___-{]_| _[}-  |     |_[___\\==--          \\/   _\n" +
                " __..._____--==/___]_|__|_____________________________[___\\==--___,-----' .7\n" +
                "|                                                           PEPINACO BB-61/\n" +
                " \\_______________________________________________________________________|";
        System.out.println(boat);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("                         ¡Prepárate para la batalla!");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("-------------------- PRESIONA ENTER PARA CONTINUAR -------------------------");
        enter = scanner.nextLine();
        if(enter.isEmpty()) {
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
            System.out.print("Introduce el numero correspondiente a la dificultad de tu partida:");
            Integer difficulty = intDificultValidator(scanner);
            System.out.println();
            System.out.println();
            switch (difficulty) {
                case 1:
                    System.out.println("####################################################");
                    System.out.println("  Has seleccionado la dificultad Cobarde ");
                    System.out.println("  Tienes 50 disparos para hundir 10 barcos de guerra");
                    System.out.println("####################################################");
                    System.out.println();
                    Board.insertShips(10, 0, 0, 0);
                    user = "Cobarde ";
                    tries = 4;
                    break;
                case 2:
                    System.out.println("###################################################");
                    System.out.println(" Has seleccionado la dificultad Aventurero ");
                    System.out.println(" Tienes 30 disparos para hundir 5 barcos de guerra");
                    System.out.println("###################################################");
                    System.out.println();
                    Board.insertShips(10, 0, 0, 0);
                    user = "Aventurero";
                    tries = 30;
                    break;
                case 3:
                    System.out.println("##################################################");
                    System.out.println(" Has seleccionado la dificultad Destructor. ");
                    System.out.println(" Tienes 10 disparos para hundir 2 barcos de guerra.");
                    System.out.println("###################################################");
                    System.out.println();
                    System.out.println();
                    Board.insertShips(10, 0, 0, 0);
                    user = "Destructor";
                    tries = 10;
                    break;
                default :
                    System.out.println("Dificultad no válida");
            }
            startGame(board);
        }
    }

    public static void startGame(Board board) {
        do{
            System.out.println();
            Board.printPlayerBoard(tries, user);
            System.out.println("¡Alerta soldado! Es hora de atacar.");
            System.out.print("Introduce la primera coordenada (0-9):");
            int xCoordinate = intCoordinateValidator(scanner);
            String yCoordinate = charCoordinateValidator(scanner);
            System.out.printf("Disparando a la coordenada: (%d-%S)\n", xCoordinate, yCoordinate);
            if(Board.repeatedShot(convertCharToIntCoordinate(yCoordinate), xCoordinate)){
                System.out.println("Ya has disparado a esta coordenada ¡No desperdicies disparos!");
                tries--;
            }else{
                System.out.printf("%s\n", Board.shoot(convertCharToIntCoordinate(yCoordinate), xCoordinate));
                tries--;
            }
        }while (Board.checkShipsAlive() && tries > 0);
            if(tries == 0) {
                System.out.println("¡Has perdido! Te has quedado sin disparos");
                System.out.println(("Los barcos enemigos han ganado"));
                System.out.println("Tablero de barcos enemigos:");
                Board.printComputerBoard(tries);
            }
            if(!Board.checkShipsAlive()){
                System.out.println("¡Has ganado! Has hundido todos los barcos");
                System.out.println(("Los barcos enemigos no han podido con tu astucia"));
                System.out.println("#################################################");
                System.out.println("############### Tablero enemigo #################");
                System.out.println("#################################################");
                Board.printComputerBoard(tries);
            }
    }

    public static int convertCharToIntCoordinate(String yCoordinate){
        return yCoordinate.charAt(0) - 65;
    }

    public static String charCoordinateValidator(Scanner input){
        while(true){
            System.out.print("Introduce la segunda coordenada (a-j):");
            String coordinate = input.next().toLowerCase();;

            if(coordinate.matches("[a-j]")) return coordinate.toUpperCase();

            if(coordinate.matches("[^a-zA-Z]")){
                System.out.println("Dato no válida, solo puedes ser alfabético");
            }else {
                System.out.println("Coordenada no válida, solo puedes elegir entre a y j");
            }
        }
    }

    public static int intCoordinateValidator(Scanner input){
        int coordinate;
        while(true){
            if (input.hasNextInt()) {
                coordinate = input.nextInt();
                if (coordinate >= 0 && coordinate <= 9) {
                    return coordinate;
                }else{
                    System.out.println("Coordenada no válida, solo puedes elegir entre 0 y 9");
                }
            }else{
                System.out.println("Dato introducido no válido, solo puede ser numérico");
            }
            System.out.print("Introduce una coordenada nuevamente (0-9):");
            input.nextLine();
        }
    }

    public int intDificultValidator (Scanner input){
        try {
            int difficult = input.nextInt();
           if (difficult >= 1 && difficult <= 3){
                return difficult;
           }else{
                System.out.println("Opción no válida, solo puedes elegir entre 1, 2 o 3");
           }
        } catch (InputMismatchException e) {
            System.out.println("Dato introducido no válido, solo puede ser numérico");
        }
        System.out.println("Introduce una dificultad válida (1, 2 o 3):");
        input.nextLine();
        return intDificultValidator(input);
    }
}
