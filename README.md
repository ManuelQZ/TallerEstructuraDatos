# Taller de Escenarios de Estructuras de Datos en Java

Este repositorio contiene cuatro escenarios independientes que demuestran el uso de distintas estructuras de datos en Java (`HashMap`, `TreeMap`, `PriorityQueue`, `ArrayDeque`, etc.) para resolver problemas comunes de gestión, priorización y búsqueda eficiente. Cada escenario se encuentra en su propia carpeta dentro del proyecto.

## Estructura del Proyecto

```
/
├── Escenario1/       # Gestión de pacientes con prioridad
├── Escenario2/       # Gestión de productos con múltiples vistas
├── Escenario3/       # Gestión de solicitudes FIFO con cancelación
└── Escenario4/       # Gestión de productos con medición de rendimiento
```

---

## Escenario 1 – Atención de Pacientes por Prioridad

**Carpeta:** `Escenario1`

Simula un sistema de triage en un hospital. Los pacientes se registran con su estado de salud (`grave` o `normal`) y se almacenan en un `HashMap` para búsqueda rápida por documento, mientras que una `PriorityQueue` los ordena automáticamente (primero los graves, y por orden de llegada en caso de empate). La clase `Paciente` implementa `Comparable` para definir la prioridad.

---

## Escenario 2 – Gestión de Productos con Múltiples Vistas

**Carpeta:** `Escenario2`

Administra un catálogo de productos utilizando varias estructuras en paralelo para optimizar diferentes operaciones:
- `HashMap` por código → búsqueda O(1).
- `TreeMap` por precio → productos ordenados por precio.
- `ArrayDeque` como lista de inserción al inicio.
- `HashMap` por categoría → filtrado rápido.

Incluye una medición de tiempo y memoria para distintos volúmenes de datos (100, 1.000, 10.000, 100.000 productos).

---

## Escenario 3 – Gestión de Solicitudes FIFO con Cancelación

**Carpeta:** `Escenario3`

Implementa una cola de solicitudes (FIFO) usando `ArrayDeque`, complementada con un `HashMap` que permite cancelar una solicitud específica sin recorrer toda la cola. Las solicitudes tienen estado (`PENDIENTE`, `ATENDIDA`, `CANCELADA`) y se pueden atender por orden de llegada o cancelar mediante su código.

---

## Escenario 4 – Sistema de Productos con Medición de Rendimiento

**Carpeta:** `Escenario4`

Similar al Escenario 2 pero más simplificado: utiliza un `HashMap` para búsqueda por código y un `TreeMap` para mantener los productos ordenados por precio. Se realizan pruebas de rendimiento midiendo tiempos de inserción, búsqueda y recorrido ordenado para distintos tamaños de muestra (100 a 100.000 productos), incluyendo una estimación del consumo de memoria.

---

Cada escenario contiene su propio `main` para ejecutar demostraciones funcionales y, en algunos casos, pruebas de rendimiento. Los códigos están comentados para facilitar su comprensión.
