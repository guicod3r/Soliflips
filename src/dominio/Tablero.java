package dominio;

public class Tablero {

    //Atributos
    private int col;
    private int row;
    private int nivel;
    private String[][] tablero;
    private static int cantMovs;
    private static String[] movimientos;

    //Constructores
    public Tablero(int row, int col, int nivel) {
        this.row = row;
        this.col = col;
        this.nivel = nivel;
        this.tablero = generarTablero(row, col, nivel);
        this.movimientos = new String[row * col];
    }

    //Getters and Setters
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String[][] getTablero() {
        return tablero;
    }

    public void setTablero(String[][] tablero) {
        this.tablero = tablero;
    }

    //Métodos
    //verifica que las coordenadas esten dentro del tablero
    public static boolean posValida(int i, int j, String[][] mat) {
        return i >= 0 && i < mat.length && j >= 0 && j < mat[0].length;
    }

    //genera el tablero predeterminado
    public static String[][] tableroPredeterminado() {
        String[][] tablero = {{"|A", "|A", "-R", "/A", "|R", "-R"}, {"-R", "/A", "/A", "|A", "-R", "-R"}, {"-R", "-R", "|A", "-R", "/R", "-R"}, {"\\R", "-R", "|R", "-R", "|R", "\\R"}, {"|A", "|R", "\\R", "/R", "/R", "|A", "/A", "\\A"}};
        mostrarTablero(tablero);
        return tablero;
    }

    //general simbolos aleatorios
    public static String generarSimbolos() {
        String simbolo;
        int numAleatotio = (int) (Math.random() * 4) + 1;
        simbolo = switch (numAleatotio) {
            case 1 ->
                "/" + "R";
            case 2 ->
                "\\" + "R";
            case 3 ->
                "|" + "R";
            case 4 ->
                "-" + "R";
            default ->
                "?";
        };
        //System.out.println(simbolo);
        return simbolo;
    }

    //generar tablero aleatorio
    public static String[][] generarTablero(int row, int col, int nivel) {
        String[][] tab = new String[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                tab[i][j] = generarSimbolos();
            }
        }

        //genera par de coordenadas para modificar acorde al nivel y verifica que no esten repetidas
        for (int g = 0; g < nivel; g++) {
            int x;
            int y;
            boolean repetido;
            boolean fuera;
            int[] modificadas = new int[nivel * 2];

            do {
                x = (int) (Math.random() * (row - 1)) + 1;
                y = (int) (Math.random() * (col - 1)) + 1;
                repetido = false;
                fuera = false;

                if (!(posValida(x, y, tab))) {
                    fuera = true;
                }

                for (int i = 0; i < modificadas.length && !repetido; i += 2) {
                    if (x == modificadas[i] && y == modificadas[i + 1]) {
                        repetido = true;
                    }
                }

            } while (repetido || fuera);

            modificadas[g * 2] = x;
            modificadas[g * 2 + 1] = y;
            String simbolo = String.valueOf(tab[x][y].charAt(0));
            switch (simbolo) {
                case "/" ->
                    movDiagonalCreciente(x, y, tab);
                case "\\" ->
                    movDiagonalDecreciente(x, y, tab);
                case "|" ->
                    movimientoVertical(y, tab);
                case "-" ->
                    movimientoHorizontal(x, tab);
                default ->
                    System.out.println("no cambio ninguga dureccion de color");
            }
        }
        return tab;
    }

    //traductor de simbolo
    public static String traductorSimbolo(String casilla) {
        String simbolo = String.valueOf(casilla.charAt(0));
        switch (simbolo) {
            case "/" ->
                simbolo = "/";
            case "\\" ->
                simbolo = "\\";
            case "|" ->
                simbolo = "|";
            case "-" ->
                simbolo = "-";
            default ->
                simbolo = "?";
        }
        return simbolo;
    }

    //traductor de color
    public static String traductorColor(String casilla) {
        String colorRojo = "\u001B[31m";
        String colorAzul = "\u001B[34m";
        String resetColor = "\u001B[0m";

        String color;
        if (String.valueOf(casilla.charAt(1)).equals("R")) {
            color = colorRojo + String.valueOf(casilla.charAt(0)) + resetColor;
        } else {
            color = colorAzul + String.valueOf(casilla.charAt(0)) + resetColor;
        }

        return color;
    }

    //mostrar tablero por pantalla
    public static void mostrarTablero(String tablero[][]) {
        String resetColor = "\u001B[0m";
        System.out.print("  ");
        for (int j = 0; j < tablero[0].length; j++) {
            System.out.print(" " + (j + 1) + "  ");
        }
        System.out.println("");
        for (int i = 0; i < tablero.length; i++) {
            System.out.print(" ");
            System.out.print("+");
            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print("---+");
            }
            System.out.println("");
            System.out.print((i + 1));
            System.out.print("|");
            for (int j = 0; j < tablero[0].length; j++) {
                String colorSimbolo = String.valueOf(traductorColor(tablero[i][j]));
                //String direccionSimbolo = traductorSimbolo(tablero[i][j]);
                System.out.print(" " + colorSimbolo + /*direccionSimbolo +*/ " " + resetColor + "|");
            }
            System.out.println("");
        }
        System.out.print(" " + "+");
        for (int j = 0; j < tablero[0].length; j++) {
            System.out.print("---+");
        }
        System.out.println("");
    }

    //Cambia el color de la matriz
    private static String colorOpuesto(String simbolo) {
        String color = String.valueOf(simbolo.charAt(1));

        String colorOpuesto;
        if (color.equals("R")) {
            colorOpuesto = "A";
        } else {
            colorOpuesto = "R";
        }

        String nuevoSimbolo = String.valueOf(simbolo.charAt(0)) + colorOpuesto;
        return nuevoSimbolo;
    }

    //----------------MOVIMIENTOS----------------//
    private static void movimientoVertical(int col, String[][] tablero) {
        System.out.print("coord iniciales" + "col" + col + "-");
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (j == col) {
                    tablero[i][j] = colorOpuesto(tablero[i][j]);
                }
            }
        }
        System.out.print("coord finales" + "col" + col + "-");
        System.out.println("Entre a vertical");
    }

    private static void movimientoHorizontal(int row, String[][] tablero) {

        System.out.print("coord iniciales" + "row" + row + "-");
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (i == row) {
                    tablero[i][j] = colorOpuesto(tablero[i][j]);
                }
            }
        }
        System.out.print("coord finales" + "row" + row + "-");
        System.out.println("Entre a horizontal");
    }

    private static void movDiagonalCreciente(int row, int col, String[][] tablero) {
        System.out.print("coord iniciales" + "row" + row + "col" + col + "-");
        //Se posiciona en el extremo superior de la diagonal
        while (posValida(row, col, tablero)) {
            row--;
            col++;
        }
        row++;
        col--;
        System.out.print("coord extremo" + "row" + row + "col" + col + "-");

        while (posValida(row, col, tablero)) {
            tablero[row][col] = colorOpuesto(tablero[row][col]);
            row++;
            col--;
            System.out.print("coord finales" + "row" + row + "col" + col + "-");
        }

        System.out.println("Entre a creciente");
    }

    private static void movDiagonalDecreciente(int row, int col, String[][] tablero) {
        System.out.print("coord iniciales" + "row" + row + "col" + col + "-");

        //Se posiciona en el extremo superior de la diagonal
        while (posValida(row, col, tablero)) {
            row--;
            col--;
        }
        row++;
        col++;
        System.out.print("coord extremo" + "row" + row + "col" + col + "-");

        while (posValida(row, col, tablero)) {
            tablero[row][col] = colorOpuesto(tablero[row][col]);
            row++;
            col++;
            System.out.print("coord finales" + "row" + row + "col" + col + "-");
        }
        System.out.println("Entre a decreciente");
    }

    //metodo que lleva a cabo los movimeintos
    public static String[][] movimiento(int row, int col, String[][] tablero) {

        if (posValida(row, col, tablero)) {
            String simbolo = String.valueOf(tablero[row][col].charAt(0));
            switch (simbolo) {
                case "/" ->
                    movDiagonalCreciente(row, col, tablero);
                case "\\" ->
                    movDiagonalDecreciente(row, col, tablero);
                case "|" ->
                    movimientoVertical(col, tablero);
                case "-" ->
                    movimientoHorizontal(row, tablero);
                default ->
                    System.out.println("No se cambió ninguna dirección de color");
            }
            registrarMov(row, col);
            mostrarTablero(tablero);
        } else {
            System.out.println("Coordenadas no válidas.");
        }
        return tablero;
    }

    private static void registrarMov(int row, int col) {
        if (cantMovs < movimientos.length) {
            movimientos[cantMovs] = row + " " + col;
            cantMovs++;
        } else {
            // Elimina el movimiento más antiguo
            for (int i = 0; i < cantMovs - 1; i++) {
                movimientos[i] = movimientos[i + 1];
            }
            movimientos[cantMovs - 1] = row + " " + col;
        }
    }

    public void deshacerMov() {
        if (cantMovs > 0) {
            String[] coordenadas = movimientos[cantMovs - 1].split(" ");
            int row = Integer.parseInt(coordenadas[0]);
            int col = Integer.parseInt(coordenadas[1]);

            String simbolo = String.valueOf(tablero[row][col].charAt(0));
            switch (simbolo) {
                case "/" ->
                    movDiagonalCreciente(row, col, tablero);
                case "\\" ->
                    movDiagonalDecreciente(row, col, tablero);
                case "|" ->
                    movimientoVertical(col, tablero);
                case "-" ->
                    movimientoHorizontal(row, tablero);
                default ->
                    System.out.println("No se cambió ninguna dirección de color");
            }

            movimientos[cantMovs - 1] = null;
            cantMovs--;
            
            //System.out.println("Movimiento deshecho.");
            //mostrarTablero(tablero);
        }else{
            System.out.println("No ha ingresado ningun movimiento previo");
        }
    }
}
