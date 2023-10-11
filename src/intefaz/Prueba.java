package intefaz;

import dominio.Soliflips;
import java.util.*;

public class Prueba {

    public static String traductor(String casilla) {
        return String.valueOf(casilla.charAt(0));
    }
    
 
    public static void main(String[] args) {
        Scanner lectorMenu = new Scanner(System.in);
        boolean jugar = true;

        while (jugar) {
            System.out.println("¿Desea jugar una partida de Soliflips?");
            String respuesta = lectorMenu.nextLine().toUpperCase();

            switch (respuesta) {
                case "SI" -> {
                    Soliflips juego = new Soliflips();
                    Menu menuJuego = new Menu(juego);
                    menuJuego.mostrarMenu();
                }
                case "NO" -> jugar = false;
                default -> System.out.println("Respuesta inválida. Por favor, ingrese SI o NO.");
            }
        }
        
        System.out.println("Gracias por jugar a Soliflips");  
    }
 
}
