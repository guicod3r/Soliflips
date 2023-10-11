package dominio;

import java.util.Scanner;

public class Soliflips {

    //Atributos
    private Tablero tablero;

    //Constructor
    public Soliflips() {
        this.tablero = null;
    }
    
    public Soliflips(int row, int col, int nivel) {
        this.tablero = new Tablero(row, col, nivel);
    }

    //Getters and Setters
    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    //metodo que inicia el juego
    public void inicio() {
        Scanner lectorPartida = new Scanner(System.in);

        System.out.println("Ingresar las coordenadas.");
        int row = lectorPartida.nextInt();
        int col = lectorPartida.nextInt();

        if (Tablero.posValida(row, col, tablero.getTablero())) {
            // Obtén el tablero actual
            String[][] tableroActual = tablero.getTablero();
            // Realiza el movimiento en el tablero
            tableroActual = Tablero.movimiento(row, col, tableroActual);
            // Actualiza el tablero en el objeto Soliflips
            tablero.setTablero(tableroActual);
            // Muestra el tablero actualizado
            Tablero.mostrarTablero(tableroActual);
        } else {
            System.out.println("Coordenadas no válidas. Inténtalo de nuevo.");
        }
    }

    //inicializar tableoro
    public void inicializarTablero(int row, int col, int nivel) {
        this.tablero = new Tablero(row, col, nivel);
    }
}
