/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolesRojoNegro;

/**
 *
 * @author Octavio
 */
public class Arbol {

    Nodo raiz = null;

    Nodo rotarIzquierda(Nodo rota) {
        Nodo hijo = rota.derecho;
        Nodo hijoizq = hijo.izquierdo;
        hijo.izquierdo = rota;
        rota.derecho = hijoizq;

        return hijo;
    }

    Nodo rotarDerecha(Nodo rota) {
        Nodo hijo = rota.izquierdo;
        Nodo hijoder = hijo.derecho;
        hijo.derecho = rota;
        rota.izquierdo = hijoder;

        return hijo;

    }

    boolean esRojo(Nodo x) {
        if (x != null) {
            return x.color == Color.ROJO;
        } else {
            return false;
        }
    }

    void intercambioColor(Nodo uno, Nodo dos) {
        Color temp = uno.color;
        uno.color = dos.color;
        dos.color = temp;
    }

    void color(Nodo uno) {
        if (esRojo(uno)) {
            uno.color = Color.NEGRO;
        } else {
            uno.color = Color.ROJO;
        }
    }

    public void insertar(int dato) {
        Nodo nuevo = crearNodo(dato);

        if (raiz == null) {
            raiz = nuevo;
        } else {
            Nodo aux = raiz;
            Nodo ant = null;
            while (aux != null) {
                ant = aux;
                if (nuevo.dato > aux.dato) {
                    aux = aux.derecho;
                } else {
                    aux = aux.izquierdo;
                }
            }
            if (nuevo.dato > ant.dato) {
                ant.derecho = nuevo;
            } else {
                ant.izquierdo = nuevo;
            }
        }
        acomodar(nuevo); //Vamos a hacer las verificaciones con nuevo que no imcumpla nincuna regla
        //raiz.color = false;
    }

    void acomodar(Nodo nuevo) {
        //caso 1
        if ((esRojo(nuevo.derecho)) && (!esRojo(nuevo.izquierdo))) {
            //rota izquierda
            nuevo = rotarIzquierda(nuevo);
            intercambioColor(nuevo, nuevo.izquierdo);
        }
        //caso 2
        if ((esRojo(nuevo.izquierdo)) && (esRojo(nuevo.izquierdo.izquierdo))) {
            //rota derecha
            nuevo = rotarDerecha(nuevo);
            intercambioColor(nuevo, nuevo.derecho);
        }
        if ((esRojo(nuevo.izquierdo)) && (esRojo(nuevo.derecho))) {
            color(nuevo);
            nuevo.izquierdo.color = Color.NEGRO;
            nuevo.derecho.color = Color.NEGRO;
        }

    }

    public Nodo crearNodo(int dato) {
        Nodo nuevo = new Nodo();
        nuevo.dato = dato;
        nuevo.color = Color.ROJO;
        nuevo.izquierdo = null;
        nuevo.derecho = null;
        return nuevo;
    }

    public void recorrer(Recorrido tipo) {
        switch (tipo) {
            case INORDER ->
                inorder(raiz);
            case PREORDER ->
                preorder(raiz);
            case POSTORDER ->
                postorder(raiz);
            case HOJAS ->
                soloHojas(raiz);
        }
    }

    void inorder(Nodo r) {
        if (r != null) {
            inorder(r.izquierdo);
            System.out.print(r.dato + " ");
            inorder(r.derecho);
        }
    }

    void preorder(Nodo r) {
        if (r != null) {
            System.out.print(r.dato + " ");
            preorder(r.izquierdo);
            preorder(r.derecho);
        }
    }

    void postorder(Nodo r) {
        if (r != null) {
            postorder(r.izquierdo);
            postorder(r.derecho);
            System.out.print(r.dato + " ");
        }
    }

    void soloHojas(Nodo r) {
        if (r != null) {
            soloHojas(r.izquierdo);
            if ((r.izquierdo == null) && (r.derecho == null)) {
                System.out.print(r.dato + " ");
            }
            soloHojas(r.derecho);
        }
    }

    public Boolean buscar(int dato) {
        return buscarElemento(raiz, dato);
    }

    Boolean buscarElemento(Nodo r, int dato) {
        if (r != null) {
            if (r.dato == dato) {
                return true;
            } else if (dato < r.dato) {
                return buscarElemento(r.izquierdo, dato);
            } else {
                return buscarElemento(r.derecho, dato);
            }
        } else {
            return false;
        }
    }
    
    public void eliminar(int dato) {
        if (raiz != null) {
            Nodo aux = raiz;
            Nodo ant = raiz;
            boolean esIzq = true;
            
            while (aux.dato != dato) {
                ant = aux;
                if (dato < aux.dato) {
                    esIzq = true;
                    aux = aux.izquierdo;
                } else {
                    esIzq = false;
                    aux = aux.derecho;
                }
            }
            if ((aux.izquierdo == null) && (aux.derecho == null)) {
                if (aux == raiz) {
                    raiz = null;
                } else if (esIzq) {
                    ant.izquierdo = null;
                } else {
                    ant.derecho = null;
                }
            } else if (aux.derecho == null) {
                if (aux == raiz) {
                    raiz = aux.izquierdo;
                } else if (esIzq) {
                    ant.izquierdo = aux.izquierdo;
                } else {
                    ant.derecho = aux.izquierdo;
                }
            } else if (aux.izquierdo == null) {
                if (aux == raiz) {
                    raiz = aux.derecho;
                } else if (esIzq) {
                    ant.izquierdo = aux.derecho;
                } else {
                    ant.derecho = aux.derecho;
                }
            } else {
                Nodo reemplazar = obtenerReemplazar(aux);
                if (aux == raiz) {
                    raiz = reemplazar;
                } else if (esIzq) {
                    ant.izquierdo = reemplazar;
                } else {
                    ant.derecho = reemplazar;
                }
                reemplazar.izquierdo = aux.izquierdo;
            }
            acomodar(aux);
        }
    }

    Nodo obtenerReemplazar(Nodo rem) {
        Nodo rempapa = rem;
        Nodo remplazo = rem;
        Nodo aux = rem.derecho;
        
        while (aux != null) {
            rempapa = remplazo;
            remplazo = aux;
            aux = aux.izquierdo;
        }
        if (remplazo != rem.derecho) {
            rempapa.izquierdo = remplazo.derecho;
            remplazo.derecho = rem.derecho;
        }
        return remplazo;
    }
}

