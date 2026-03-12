import java.time.LocalDateTime;
import java.util.*;

public class Paciente implements Comparable<Paciente> {
    private String documento;
    private String nombre;
    private String estadoSalud; // "grave" o "normal"
    private LocalDateTime horaLlegada;
    private long ordenLlegada; // Para desempatar por orden de llegada, nanosegundos

    // estructuras estáticas compartidas por todos los pacientes/operaciones
    private static Map<String, Paciente> registroMap = new HashMap<>();
    private static PriorityQueue<Paciente> colaAtencion = new PriorityQueue<>();

    public Paciente(String documento, String nombre, String estadoSalud, LocalDateTime horaLlegada, long ordenLlegada) {
        this.documento = documento;
        this.nombre = nombre;
        this.estadoSalud = estadoSalud;
        this.horaLlegada = horaLlegada;
        this.ordenLlegada = ordenLlegada;
    }

    // Getters necesarios
    public String getDocumento() { return documento; }
    public String getEstadoSalud() { return estadoSalud; }
    public LocalDateTime getHoraLlegada() { return horaLlegada; }
    public long getOrdenLlegada() { return ordenLlegada; }

    @Override
    public String toString() {
        return "Paciente[" + documento + " | " + nombre + " | " + estadoSalud + " | " + horaLlegada + "]";
    }

    @Override
    public int compareTo(Paciente o) {
        if (!estadoSalud.equals(o.estadoSalud))
            return estadoSalud.equals("grave") ? -1 : 1;
        return Long.compare(ordenLlegada, o.ordenLlegada);
    }

    // operaciones estáticas que antes residían en SistemaHospital
    public static void registrar(String doc, String nombre, String estado) {
        if (registroMap.containsKey(doc)) {
            System.out.println("Error: El paciente con documento " + doc + " ya está registrado.");
            return;
        }
        Paciente p = new Paciente(doc, nombre, estado, LocalDateTime.now(), System.nanoTime());
        registroMap.put(doc, p);
        colaAtencion.add(p);
        System.out.println("Registrado: " + p);
    }

    public static Paciente buscar(String doc) {
        return registroMap.get(doc);
    }

    public static Paciente atenderSiguiente() {
        Paciente p = colaAtencion.poll();
        if (p != null) registroMap.remove(p.getDocumento());
        return p;
    }

    public static void main(String[] args) {
        // demostración integrada dentro de la clase
        registrar("101", "Juan Perez", "normal");
        registrar("102", "Maria Lopez", "grave");
        registrar("101", "Pedro Gomez", "normal"); // duplicado

        System.out.println("\nBuscando paciente 102: " + buscar("102"));
        System.out.println("\n--- Atendiendo pacientes ---");
        System.out.println("Atendido: " + atenderSiguiente());
        System.out.println("Atendido: " + atenderSiguiente());
    }
}

class SistemaHospital {
    // HashMap para búsqueda O(1) y evitar duplicados
    private Map<String, Paciente> registroMap = new HashMap<>();
    // PriorityQueue para atención por prioridad (Paciente es Comparable)
    private PriorityQueue<Paciente> colaAtencion = new PriorityQueue<>();

    public SistemaHospital() {
        // la cola usa el orden natural definido en Paciente.compareTo
    }

    public void registrarPaciente(String doc, String nombre, String estado) {
        if (registroMap.containsKey(doc)) {
            System.out.println("Error: El paciente con documento " + doc + " ya está registrado.");
            return;
        }
        Paciente nuevo = new Paciente(doc, nombre, estado, LocalDateTime.now(), System.nanoTime());
        registroMap.put(doc, nuevo);
        colaAtencion.add(nuevo);
        System.out.println("Registrado: " + nuevo);
    }

    public Paciente buscarPaciente(String doc) {
        // Búsqueda inmediata O(1) [1]
        return registroMap.get(doc);
    }

    public Paciente atenderSiguiente() {
        // Extrae el de mayor prioridad O(log n) [4]
        Paciente p = colaAtencion.poll();
        if (p != null) {
            registroMap.remove(p.getDocumento());
        }
        return p;
    }


    public class Main {
        public static void main(String[] args) {
            SistemaHospital hospital = new SistemaHospital();

            // 1. Registro y validación de duplicados [7]
            hospital.registrarPaciente("101", "Juan Perez", "normal");
            hospital.registrarPaciente("102", "Maria Lopez", "grave");
            hospital.registrarPaciente("101", "Pedro Gomez", "normal"); // Intento de duplicado

            // 2. Búsqueda rápida [8]
            System.out.println("\nBuscando paciente 102: " + hospital.buscarPaciente("102"));

            // 3. Atención por prioridad (Atenderá primero a Maria por ser 'grave') [9]
            System.out.println("\n--- Atendiendo pacientes ---");
            System.out.println("Atendiendo a: " + hospital.atenderSiguiente());
            System.out.println("Atendiendo a: " + hospital.atenderSiguiente());
        }
    }
}