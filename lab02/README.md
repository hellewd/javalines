# Pilas y filas

Tareas:
    1. Realizar Latex de informe
    2. Realizar ppt de presentación
    3. Anotar cada observación del informe

Resolución:
Implementar un simulador web que verifique el contenido de páginas en formato XHTML.
    - Uso e implementación de las estructuras de datos, Stack & Queue.
    - Comprensión del concepto de interfaz en java mediante adaptadores.
    - Analísis empírico y teórico BIG-O.

Procedimiento:
Tomar una URL, leer el archivo que conduce y enfila todos los enlaces "href" que contenga para explorarlos más tarde.

A su vez; crear:
    - Pila construida internamente con sólo un único objeto de tipo queue.
        (Hacer una pila utiliza queue)
    - Fila construida internamente con sólo dos objetos de tipo stack.
        ()

Desafío:
Consiste en comprobar cómo estas implementaciones afectan el rendimiento del validador a medida que N aumenta.

Deberá programar las interfaces genéricas Pila<T> y Cola<T>, y luego cuatro clases que implementen dichas interfces.

Interfaces:
Pila<T>
    Métodos:
        Leer PDF.
Cola<T>
    Métodos:
        Leer PDF.

A su vez, programar cuatro clases:
    1. PilaPrinceton
    2. ColaPrinceton
    3. PilaConCola
    4. ColaConPilas

Web Crawler:
Debe inspirarse en la clase WebCrawler de Princetor.
    - Recibir pila y cola a ser usadas internamente.
    - Recibir un documento XHTML en formato String, debe utilizar la pila para verificar si el XHTML es válido.
        Puede revisar la siguiente información para comprobarlo:
            <!DOCTYPE>
            El atributo xmlns
            Cada etiqueta acompañada con un cierre.
    - Recibir un URL, leer el contenido y validarlo.

Analisis empírico Offline: 
    Deberá implementar una clase experimento.java que siga el método empírico de Doubling Ratio.
    Evaluación de la Pila (Experimento 1).
    Evaluación de la Cola (Experimento 2).
    Reporte experimental
Análisis teórico:
    Generar una tabla de CSV y calcule las estadísticas y el ratio.
Análisis EXCEL:
    Generar un archivo analisis.xlsx
    
Nota:
    Analizar páginas simples.
