package intefaz;

import dominio.Soliflips;
import dominio.Tablero;
import java.util.*;

public class Menu {

    //atributos
    Tablero tablero;
    private Soliflips juego;

    //Constructor
    public Menu(Soliflips juego) {
        this.juego = juego;
    }
    
    boolean jugar = true;

    public void mostrarMenu() {
        Scanner lectorMenu = new Scanner(System.in);

        while (jugar) {
            System.out.println("------- Menu de Soliplips -------");
            System.out.println("----- Bienvenido a Soliflips -----");
            System.out.println(" a) Tomar datos del archivo ´datos.txt´ ");
            System.out.println(" b) Usar tablero predefinido ");
            System.out.println(" c) Usar tablero al azar");
            System.out.println(" x) salir");
            System.out.println("-----Seleccione una opcion-----");

            String opcion = lectorMenu.nextLine().toLowerCase();

            switch (opcion) {
                case "a" ->
                    leerArchivo();
                case "b" ->
                    Tablero.tableroPredeterminado();
                case "c" ->
                    tableroRandom();
                case "x" ->
                    jugar = false;
                default ->
                    System.out.println("Dato igresado inválido");
            }
        }
    }

    private void leerArchivo() {
        System.out.println("Ingrese 'archivo' para cargar datos desde un archivo:");
        jugar = false;
    }

    private void tableroRandom() {
        Scanner lector = new Scanner(System.in);
        System.out.println("Ingresar Row");
        int row = lector.nextInt();
        System.out.println("Ingresar Col");
        int col = lector.nextInt();
        System.out.println("Nivel");
        int nivel = lector.nextInt();
        
        String mat[][] = Tablero.generarTablero(row, col, nivel);
        Tablero.mostrarTablero(mat);
        Soliflips soliflips = new Soliflips(row, col, nivel);
        soliflips.inicio();
    }
}
