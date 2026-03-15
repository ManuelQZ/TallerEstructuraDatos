package Escenario2;

import java.util.*;

public class GestionProductos {

    static class Producto {
        String codigo;
        String nombre;
        double precio;
        String categoria;

        Producto(String codigo, String nombre, double precio, String categoria) {
            this.codigo    = codigo;
            this.nombre    = nombre;
            this.precio    = precio;
            this.categoria = categoria;
        }

        @Override
        public String toString() {
            return "[" + codigo + "] " + nombre + " - $" + precio + " (" + categoria + ")";
        }
    }

    // Estructuras
    static HashMap<String, Producto>              porCodigo    = new HashMap<>();
    static TreeMap<Double, List<Producto>>        porPrecio    = new TreeMap<>();
    static ArrayDeque<Producto>                   listaInicio  = new ArrayDeque<>();
    static HashMap<String, List<Producto>>        porCategoria = new HashMap<>();

    // Buscar por código
    static Producto buscarPorCodigo(String codigo) {
        return porCodigo.get(codigo);
    }

    // Mostrar ordenados por precio
    static void mostrarOrdenadosPorPrecio() {
        for (Map.Entry<Double, List<Producto>> entry : porPrecio.entrySet()) {
            for (Producto p : entry.getValue()) {
                System.out.println(p);
            }
        }
    }

    // Insertar al inicio (ArrayDeque)
    static void insertarAlInicio(Producto p) {
        // Registrar en todas las estructuras
        porCodigo.put(p.codigo, p);

        porPrecio.computeIfAbsent(p.precio, k -> new ArrayList<>()).add(p);

        listaInicio.addFirst(p);

        porCategoria.computeIfAbsent(p.categoria, k -> new ArrayList<>()).add(p);
    }

    // Filtrar por categoría
    static List<Producto> filtrarPorCategoria(String categoria) {
        return porCategoria.getOrDefault(categoria, Collections.emptyList());
    }

    // Generador de productos
    static List<Producto> generarProductos(int cantidad) {
        String[] categorias = {"Electronica", "Ropa", "Hogar", "Alimentos", "Juguetes"};
        List<Producto> lista = new ArrayList<>();
        Random rand = new Random(42);
        for (int i = 0; i < cantidad; i++) {
            String codigo    = "COD" + i;
            String nombre    = "Producto_" + i;
            double precio    = Math.round((rand.nextDouble() * 1000) * 100.0) / 100.0;
            String categoria = categorias[rand.nextInt(categorias.length)];
            lista.add(new Producto(codigo, nombre, precio, categoria));
        }
        return lista;
    }

    // Medición
    static void medir(int cantidad) {
        porCodigo.clear();
        porPrecio.clear();
        listaInicio.clear();
        porCategoria.clear();

        List<Producto> productos = generarProductos(cantidad);

        Runtime runtime = Runtime.getRuntime();

        // Inserción
        runtime.gc();
        long memAntes = runtime.totalMemory() - runtime.freeMemory();
        long t1 = System.nanoTime();

        for (Producto p : productos) insertarAlInicio(p);

        long t2 = System.nanoTime();
        long memDespues = runtime.totalMemory() - runtime.freeMemory();

        System.out.printf("%-12s | Inserción    | Tiempo: %8.2f ms | Memoria: %6.2f MB%n",
                "n=" + cantidad,
                (t2 - t1) / 1_000_000.0,
                (memDespues - memAntes) / (1024.0 * 1024.0));

        // Búsqueda por código
        String codigoBuscar = "COD" + (cantidad / 2);
        t1 = System.nanoTime();
        buscarPorCodigo(codigoBuscar);
        t2 = System.nanoTime();
        System.out.printf("%-12s | Búsqueda     | Tiempo: %8.4f ms%n",
                "n=" + cantidad,
                (t2 - t1) / 1_000_000.0);

        // Mostrar ordenados por precio
        t1 = System.nanoTime();
        // Iteramos sin imprimir para no distorsionar la medición
        for (Map.Entry<Double, List<Producto>> e : porPrecio.entrySet()) {
            for (Producto p : e.getValue()) { String s = p.toString(); }
        }
        t2 = System.nanoTime();
        System.out.printf("%-12s | Ord. precio  | Tiempo: %8.2f ms%n",
                "n=" + cantidad,
                (t2 - t1) / 1_000_000.0);

        // Filtrar por categoría
        t1 = System.nanoTime();
        filtrarPorCategoria("Electronica");
        t2 = System.nanoTime();
        System.out.printf("%-12s | Filtro cat.  | Tiempo: %8.4f ms%n",
                "n=" + cantidad,
                (t2 - t1) / 1_000_000.0);

        System.out.println("─".repeat(65));
    }

    public static void main(String[] args) {
        System.out.println("=".repeat(65));
        System.out.println("        FASE 4 — Medición real con distintos volúmenes");
        System.out.println("=".repeat(65));
        System.out.printf("%-12s | %-12s | %s%n", "Volumen", "Operación", "Resultado");
        System.out.println("─".repeat(65));

        int[] volumenes = {100, 1_000, 10_000, 100_000};
        for (int n : volumenes) {
            medir(n);
        }
    }
}