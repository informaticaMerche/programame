package conmapa;

import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/*
 Se acerca el verano y los aficionados a la naturaleza pasarán buena parte de él en campings, disfrutando del aire libre.

Un requisito imprescindible en los meses de calor es colocar la tienda de campaña bajo la sombra de un buen árbol para poder pasar frescos las horas de siesta. Pero, dependiendo de la zona, eso no siempre es fácil. En los campings nuevos, el número de árboles es escaso, y también lo es por tanto el número de parcelas aptas para tiendas.
Sabiendo que cada árbol proporciona sombra a las ocho parcelas adyacentes, ¿cuántas tiendas de campaña disfrutarán de sombra en un camping?

Entrada
El programa deberá procesar múltiples casos de prueba recibidos por la entrada estándar. Cada uno representa un camping formado por una cuadrícula de parcelas de igual tamaño en los que puede haber hueco para una tienda, o un árbol.

Cada caso de prueba comienza con dos números 1 ≤ c, f ≤ 50, indicando el número de columnas y de filas de la cuadrícula de parcelas. A continuación se indica el número a de árboles del camping.

Si hay árboles, en la siguiente línea aparece la posición de todos ellos, indicando para cada uno la columna (1…c) y la fila (1…f) que ocupan. En total, aparecerán 2×a números.

La entrada termina con una línea con tres ceros (camping con dimensiones nulas y sin árboles), que no debe procesarse.

Salida
Por cada caso de prueba el programa indicará, en una línea, el número de parcelas que disfrutarán de sombra.

Entrada de ejemplo
7 7 8
7 2 3 3 4 3 4 4 3 5 4 5 1 7 2 7
5 3 1
3 2
0 0 0
Salida de ejemplo
22
8
 */
/**
 *
 * @author merche
 */
public class Main {

    static BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
//    static BufferedReader lector;
    static GestoraParcelas gestora;
    
    public static void main(String[] args) throws IOException {
//        lector = new BufferedReader(new FileReader("entrada.txt"));
        //'S' SOMBRA  'A' arbol
        String linea = lector.readLine();
        while (!linea.equals("0 0 0")) {           
            String[] partes = linea.split(" ");
            int arboles = Integer.parseInt(partes[2]);
            if (arboles == 0) {
                System.out.println("0");
            } else {
                String infArboles = lector.readLine();
                System.out.println(caso(linea, infArboles));
            }
            linea = lector.readLine();
        }
        System.exit(0);

    }

    public static String caso(String linea, String infArboles) throws IOException {
        String[] partes = linea.split(" ");
        gestora = new GestoraParcelas(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]));
        GestoraParcelas.numParcelasSombreadas=0;
        int arboles = Integer.parseInt(partes[2]);
        String[] inArbolesPartes = infArboles.split(" ");
        int dato = 0;
        for (int num = 0; num < arboles; num++) {
            int col = Integer.parseInt(inArbolesPartes[dato]) - 1;
            dato++;
            int fil = Integer.parseInt(inArbolesPartes[dato]) - 1;
            dato++;
            Parcela parcela = new Parcela(col, fil);
            if (gestora.get(parcela)!=null && gestora.get(parcela) == 'S') {
                GestoraParcelas.numParcelasSombreadas--;
            }
            gestora.put(parcela,'A');
            gestora.actualizarSombras(parcela);       ;
        }
   

    return Integer.toString (GestoraParcelas.numParcelasSombreadas);

}



}
class GestoraParcelas extends HashMap<Parcela, Character> {

    int columnas;
    int filas;
    static int numParcelasSombreadas ;

    public GestoraParcelas(int columnas, int filas) {
        this.columnas = columnas;
        this.filas = filas;
        this.numParcelasSombreadas=0;
    }

    void actualizarSombras(Parcela p) {
        Parcela derecha = new Parcela(p.c + 1, p.f);        
        if (p.c + 1 < columnas) {
            incrementarSombras(derecha);
            
        }
        Parcela derechaAbajo = new Parcela(p.c + 1, p.f+1);
        if (p.c + 1 < columnas && p.f + 1 < filas) {
            incrementarSombras(derechaAbajo);
        }
        Parcela izquierdaArriba = new Parcela(p.c - 1, p.f - 1);
        if (p.c - 1 >= 0 && p.f - 1 >= 0) {
            incrementarSombras(izquierdaArriba);
        }
        Parcela izquierda = new Parcela(p.c - 1, p.f);
        if (p.c - 1 >= 0) {
            incrementarSombras(izquierda);
        }
        Parcela izquierdaAbajo = new Parcela(p.c - 1, p.f + 1);
        if (p.c - 1 >= 0 && p.f + 1 < filas) {
            incrementarSombras(izquierdaAbajo);
        }
        Parcela abajo = new Parcela(p.c, p.f + 1);
        if (p.f + 1 < filas) {
            incrementarSombras(abajo);
        }
        Parcela arriba = new Parcela(p.c, p.f - 1);
        if (p.f - 1 >= 0) {
            incrementarSombras(arriba);
        }
        Parcela derechaArriba = new Parcela(p.c + 1, p.f - 1);
        if (p.c + 1 < columnas && p.f - 1 >= 0 ) {
            incrementarSombras(derechaArriba);
        }
    }

    private void incrementarSombras(Parcela derecha) {
        if(this.put(derecha, 'S')==null){
            numParcelasSombreadas++;
        }
    }

}

class Parcela {

    int c;
    int f;

    public Parcela(int c, int f) {
        this.c = c;
        this.f = f;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.c;
        hash = 17 * hash + this.f;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Parcela other = (Parcela) obj;
        if (this.c != other.c) {
            return false;
        }
        if (this.f != other.f) {
            return false;
        }
        return true;
    }

}
