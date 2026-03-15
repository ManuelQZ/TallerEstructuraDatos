package Escenario4;

import java.util.*;

public class SistemaProductos {


    static class Producto {
        String codigo;
        String nombre;
        double precio;

        public Producto(String codigo, String nombre, double precio) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.precio = precio;
        }

        @Override
        public String toString() {
            return "[" + codigo + "] " + nombre + " - $" + precio;
        }
    }


    // HashMap: Buscar producto por código
    static HashMap<String, Producto> mapaCodigo = new HashMap<>();

    // TreeMap: Mostrar productos ordenados por precio → O(log n)
    static TreeMap<Double, List<Producto>> mapaPrecios = new TreeMap<>();


    // Insertar nuevo producto
    static void insertarProducto(Producto p) {
        mapaCodigo.put(p.codigo, p);
        mapaPrecios.computeIfAbsent(p.precio, k -> new ArrayList<>()).add(p);
    }

    // Buscar producto por código
    static Producto buscarPorCodigo(String codigo) {
        return mapaCodigo.get(codigo);
    }

    // Mostrar productos ordenados por precio
    static List<Producto> mostrarOrdenadosPorPrecio() {
        List<Producto> ordenados = new ArrayList<>();
        for (List<Producto> lista : mapaPrecios.values()) {
            ordenados.addAll(lista);
        }
        return ordenados;
    }

    // MEDICIÓN DE TIEMPO Y MEMORIA

    static void medirRendimiento(int n) {
        mapaCodigo.clear();
        mapaPrecios.clear();

        Random rand = new Random(42);

        System.out.println("\n--- Prueba con N = " + n + " productos ---");

        //  Inserción
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            String cod = "P" + String.format("%07d", i);
            String nom = "Producto" + i;
            double precio = Math.round(rand.nextDouble() * 999 + 1);
            insertarProducto(new Producto(cod, nom, precio));
        }
        long fin = System.currentTimeMillis();
        System.out.println("Inserción de " + n + " productos:         " + (fin - inicio) + " ms");

        // Búsqueda por código
        inicio = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String cod = "P" + String.format("%07d", rand.nextInt(n));
            buscarPorCodigo(cod);
        }
        fin = System.currentTimeMillis();
        System.out.println("1000 búsquedas por código:         " + (fin - inicio) + " ms");

        // Mostrar ordenados por precio
        inicio = System.currentTimeMillis();
        mostrarOrdenadosPorPrecio();
        fin = System.currentTimeMillis();
        System.out.println("Mostrar ordenados por precio:      " + (fin - inicio) + " ms");

        // --- Uso de memoria aproximado
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoriaUsada = (runtime.totalMemory() - runtime.freeMemory()) / 1024;
        System.out.println("Memoria usada (aprox):             " + memoriaUsada + " KB");
    }

    public static void main(String[] args) {

        System.out.println("\n=== DEMO FUNCIONAL ===");
        insertarProducto(new Producto("P001", "Laptop",    2500.0));
        insertarProducto(new Producto("P002", "Audífonos",  120.0));
        insertarProducto(new Producto("P003", "Teclado",     75.0));
        insertarProducto(new Producto("P004", "Monitor",    450.0));
        insertarProducto(new Producto("P005", "Mouse",       35.0));

        System.out.println("\n1) Buscar por código 'P003':");
        System.out.println("   " + buscarPorCodigo("P003"));

        System.out.println("\n2) Productos ordenados por precio:");
        for (Producto p : mostrarOrdenadosPorPrecio()) {
            System.out.println("   " + p);
        }

        System.out.println("\n3) Insertar nuevo producto 'P006':");
        insertarProducto(new Producto("P006", "Webcam", 90.0));
        System.out.println("   Insertado: " + buscarPorCodigo("P006"));

        int[] tamanos = {100, 1_000, 10_000, 100_000};
        for (int n : tamanos) {
            medirRendimiento(n);
        }
    }
}