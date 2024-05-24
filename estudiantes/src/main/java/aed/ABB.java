package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    private Nodo _raiz;
    private int _cardinal;

    private class Nodo {
        // Agregar atributos privados del Nodo
        private T _valor;
        private Nodo _padre;
        private Nodo _izquierda;
        private Nodo _derecha;

        // Crear Constructor del nodo
        Nodo(T e) {
            this._valor = e;
            this._izquierda = null;
            this._derecha = null;
            this._padre = null;
        }
    }

    public ABB() {
        this._raiz = null;
        this._cardinal = 0;
    }

    public int cardinal() {
        return this._cardinal;
    }

    public T minimo(){
        Nodo actual = this._raiz;
        while (actual._izquierda != null) {
            actual = actual._izquierda;
        };
        return actual._valor;
    }

    public T maximo(){
        Nodo actual = this._raiz;
        while (actual._derecha != null) {
            actual = actual._derecha;
        };
        return actual._valor;
    }

    public void insertar(T elem){
        if (this._raiz == null) {
            Nodo insertado = new Nodo(elem);
            this._raiz = insertado;
            this._cardinal++;
        } else if (!pertenece(elem)) {
            insertarNodo(this._raiz, elem);
            this._cardinal++;
        }
    }

    private void insertarNodo(Nodo raiz, T elem) {
        Nodo actual = raiz;
        if (actual._valor.compareTo(elem) > 0 && actual._izquierda == null) {
            Nodo insertado = new Nodo(elem);
            actual._izquierda = insertado;
            insertado._padre = actual;
        } else if (actual._valor.compareTo(elem) < 0 && actual._derecha == null) {
            Nodo insertado = new Nodo(elem);
            actual._derecha = insertado;
            insertado._padre = actual;
        } else if (actual._valor.compareTo(elem) > 0) {
            actual = actual._izquierda;
            insertarNodo(actual, elem);
        } else {
            actual = actual._derecha;
            insertarNodo(actual, elem);
        }
    }

    public boolean pertenece(T elem){
        return perteneceNodo(this._raiz, elem);
    }

    private boolean perteneceNodo(Nodo raiz, T elem) {
        Nodo actual = raiz;
        if (actual == null) {
            return false;
        } else if (actual._valor == elem) {
            return true;
        } else {
            if (actual._valor.compareTo(elem) > 0) {
                return perteneceNodo(actual._izquierda, elem);
            } else {
                return perteneceNodo(actual._derecha, elem);
            }
        }
    }

    public void eliminar(T elem){
        if (pertenece(elem)) {
            eliminarNodo(this._raiz, elem);
            this._cardinal--;
        }
    }

    private void eliminarNodo(Nodo raiz, T elem){
        Nodo actual = raiz;
        if (actual._valor.compareTo(elem) == 0 && actual._izquierda == null && actual._derecha == null) {
            Nodo antecesor = actual._padre;
            if (antecesor._izquierda == actual) {
                antecesor._izquierda = null;
            } else {
                antecesor._derecha = null;
            }
            actual._padre = null;
        } else if (actual._valor.compareTo(elem) == 0 && actual._izquierda == null) {
            Nodo antecesor = actual._padre;
            Nodo siguienteMayor = actual._derecha;
            if (antecesor._izquierda == actual) {
                antecesor._izquierda = siguienteMayor;
            } else {
                antecesor._derecha = siguienteMayor;
            }
            actual._padre = null;
            actual._derecha = null;
            siguienteMayor._padre = antecesor;
        } else if (actual._valor.compareTo(elem) == 0 && actual._derecha == null) {
            Nodo antecesor = actual._padre;
            Nodo siguienteMenor = actual._izquierda;
            if (antecesor._izquierda == actual) {
                antecesor._izquierda = siguienteMenor;
            } else {
                antecesor._derecha = siguienteMenor;
            }
            actual._padre = null;
            actual._izquierda = null;
            siguienteMenor._padre = antecesor;
        } else if (actual._valor.compareTo(elem) == 0) {
            Nodo antecesor = actual._padre;
            Nodo puente = actual._derecha;
            while (puente._izquierda != null) {
                puente = puente._izquierda;
            }
            Nodo antecesorPuente = puente._padre;
            Nodo sucesorPuente;
            if (puente._derecha != null) {
                sucesorPuente = puente._derecha;
            } else {
                sucesorPuente = null;
            }
            if (antecesor._izquierda == actual) {
                antecesor._izquierda = puente;
            } else {
                antecesor._derecha = puente;
            }
            antecesorPuente._izquierda = sucesorPuente;
            sucesorPuente._padre = antecesorPuente;
            puente._padre = antecesor;
            puente._izquierda = actual._izquierda;
            puente._derecha = actual._derecha;
            actual._padre = null;
            actual._derecha = null;
            actual._izquierda = null;
        } else if (actual._valor.compareTo(elem) > 0) {
            actual = actual._izquierda;
            eliminarNodo(actual, elem);
        } else {
            actual = actual._derecha;
            eliminarNodo(actual, elem);
        }
    }

    public String toString(){
        throw new UnsupportedOperationException("No implementada aun");
        // StringBuffer sbuffer = new StringBuffer();
        


        // sbuffer.append("{");
        

        // return sbuffer.toString();
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual = nodoMinimo(_raiz);

        private Nodo nodoMinimo(Nodo n) {
            while (n._izquierda != null) {
                n = n._izquierda;
            };
            return n;
        }

        public boolean haySiguiente() {            
            if (this._actual._derecha != null) {
                return true;
            }

            Nodo sucesor = this._actual._padre;
                while (sucesor != null) {
                    if (sucesor != null && sucesor._izquierda == this._actual) {
                        return true;
                    }
                    sucesor = sucesor._padre;
                }
            return false;
        }
    
        public T siguiente() {
            Nodo devolver = this._actual;
            if (this._actual._derecha != null) {
                    this._actual = this._actual._derecha;
                    return devolver._valor;
            }

            Nodo sucesor = this._actual._padre;
            while (sucesor != null) {
                if (sucesor != null && sucesor._izquierda == this._actual) {
                    this._actual = sucesor;
                    return devolver._valor;
                }
                sucesor = sucesor._padre;
            }
            return devolver._valor;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
