/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArbolesRojoNegro;

import java.util.Random;

/**
 *
 * @author Octavio
 */
public class Main {
    public static void main(String[] args) {
        Random ran = new Random();
        Arbol Arbolito= new Arbol();
        /*21, 28, 15, 8, 2, 18, 19, 6, 0, 26, 21, 17, 30, 7, 13, 25, 16, 5, 32*/
        for (int i = 0; i < 100; i++) {
            Arbolito.insertar(ran.nextInt(56));
        }
        
        //Arbolito.eliminar(0);   
        
        Arbolito.recorrer(Recorrido.INORDER);
        System.out.println();
        System.out.println(Arbolito.buscar(5));
        System.out.println();
        System.out.println(Arbolito.buscar(0));
        
    }
}
