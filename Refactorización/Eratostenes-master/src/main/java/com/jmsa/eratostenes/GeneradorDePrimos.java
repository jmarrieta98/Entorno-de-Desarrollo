/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmsa.eratostenes;

/**
 *
 * @author jmsa
 */
public class GeneradorDePrimos
{
    /**
     * Generar números primos de 1 a max
     *
     * @param max es el valor máximo
     * @return Vector de números primos
     */
    public static int[] generarPrimos(int max)
    {
        if (max >= 2)
        {
            // Declaraciones
            InicializarCriba inicializarCriba = new InicializarCriba(max).invoke();
            int dim = inicializarCriba.getDim();
            boolean[] esPrimo = inicializarCriba.getEsPrimo();
            // GeneradorDePrimos
            int cuenta = Criba(dim, esPrimo);
            // Rellenar el vector de números primos
            int[] primos = rellenarPrimos(dim, esPrimo, cuenta);
            return primos;
        } 
        else
        { // max < 2
            return new int[0];
            // Vector vacío
        }
    }

    public static int[] rellenarPrimos(int dim, boolean[] esPrimo, int cuenta) {
        int i;
        int j;
        int[] primos = new int[cuenta];
        for (i = 0, j = 0; i < dim; i++)
        {
            if (esPrimo[i])
            {
                primos[j++] = i;
            }
        }
        return primos;
    }

    public static int Criba(int dim, boolean[] esPrimo) {
        int i;
        int j;
        for (i = 2; i < Math.sqrt(dim) + 1; i++)
        {
            if (esPrimo[i])
            {
                // Eliminar los múltiplos de i
                for (j = 2 * i; j < dim; j += i)
                {
                    esPrimo[j] = false;
                }
            }
        }
        // ¿Cuántos primos hay?
        int cuenta = 0;
        for (i = 0; i < dim; i++)
        {
            if (esPrimo[i])
            {
                cuenta++;
            }
        }
        return cuenta;
    }

    public static class InicializarCriba {
        private int max;
        private int dim;
        private boolean[] esPrimo;

        public InicializarCriba(int max) {
            this.max = max;
        }

        public int getDim() {
            return dim;
        }

        public boolean[] getEsPrimo() {
            return esPrimo;
        }

        public InicializarCriba invoke() {
            int i;
            dim = max + 1;
            esPrimo = new boolean[dim];
            // Inicializar el array
            for (i = 0; i < dim; i++)
            {
                esPrimo[i] = true;
            }
            // Eliminar el 0 y el 1, que no son primos
            esPrimo[0] = esPrimo[1] = false;
            return this;
        }
    }
}
