/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

/**
 *
 * @author Usuario
 */
public class Tree {

    private Map<Integer, int[]> map;
    private List<Integer> listaArvoresDisponiveis;
    private List<Integer> listaArvoresEmCheck;
    private int INICIO;
    private int FIM;
    private List<int[]> dados;

    public Tree() {
        map = new HashMap<>();
        listaArvoresDisponiveis = new ArrayList<>();
        listaArvoresEmCheck = new ArrayList<>();
        dados = new ArrayList<>();
    }

    public String readFile(File f) throws FileNotFoundException, IOException {
        StringBuilder input = new StringBuilder();
        //String path = "data/dados.txt";
        //String path = "data/dados_ex.txt";
        //String path = "data/dados_ex2.txt";
        //File f = new File(path);
        BufferedReader bf = new BufferedReader(new FileReader(f));

        int max = -1;

        String line = bf.readLine();
        while (line != null) {
            //System.out.println(line);
            String[] split = line.split(" ");
            input.append(Arrays.toString(split)).append("\n");
            int arvore = Integer.valueOf(split[0]); //Math.max(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            max = arvore > max ? arvore : max;
            if (!listaArvoresDisponiveis.contains(arvore)) {
                listaArvoresDisponiveis.add(arvore);
            }
            dados.add(Arrays.asList(split).stream().mapToInt(Integer::parseInt).toArray());
            line = bf.readLine();
        }

        FIM = max;
        INICIO = 1;

        dados.sort((int[] o1, int[] o2) -> o1[0] > o2[0] ? 1 : 0);

        System.out.println("Arvores Disponiveis: " + listaArvoresDisponiveis.toString());
        System.out.println("Arvores em check: " + listaArvoresEmCheck.toString());
        System.out.println("FIM: " + FIM);
        System.out.println("INICIO: " + INICIO);
        System.out.println("\n\nDados\n\n");
        for (int[] e : dados) {
            System.out.print(Arrays.toString(e));
        }

        //System.out.println("\n\nArvores \n\n");
        //for (int i : listaArvoresDisponiveis) {
        //System.out.println(i);
        //}
        return input.toString();
    }

    public void prepareData() {
        System.out.println("Arvores Disponíveis: " + listaArvoresDisponiveis.toString());
        for (int arvore : listaArvoresDisponiveis) {
            System.out.println("arvore: " + arvore);
            int[] ligacoes = getEmptyArray();
            for (int[] e : dados) {
                if (e[0] == arvore) {  //ex: 1 2 5 pega o 1 e compara
                    System.out.println("e[1]: " + (e[1]) + "  ligacoes[e[1] - 1]: " + ligacoes[e[1] - 1] + "   e[2]: " + e[2]);
                    ligacoes[e[1] - 1] = e[2];
                }

                if (e[1] == arvore) {
                    System.out.println("e[0]: " + (e[0]) + "  ligacoes[e[0] - 1]: " + ligacoes[e[0] - 1] + "   e[2]: " + e[2]);
                    ligacoes[e[0] - 1] = e[2];
                }
            }
            map.put(arvore, ligacoes);
        }

    }

    private int[] getEmptyArray() {
        int[] array = new int[FIM];

        for (int i = 0; i < array.length; i++) {
            array[i] = -1;
        }

        return array;
    }

    public String findTree() {
        StringBuilder resultadoLigacoes = new StringBuilder();
        int nodo = listaArvoresDisponiveis.remove(0);
        listaArvoresEmCheck.add(nodo);
        int valotTotal = 0;

        while (!listaArvoresDisponiveis.isEmpty()) {
            System.out.println("Arvores Disponiveis: " + listaArvoresDisponiveis.toString());
            System.out.println("Arvores em check: " + listaArvoresEmCheck.toString());

            int arvoreOrigem = -1;
            int arvoreDestino = -1;
            int menor = Integer.MAX_VALUE;
            for (int n : listaArvoresEmCheck) {
                int[] ligacoes = map.get(n);
                for (int i = 0; i < ligacoes.length; i++) {
                    if (ligacoes[i] != -1 && ligacoes[i] < menor && !listaArvoresEmCheck.contains(i + 1)) {
                        arvoreOrigem = n;
                        arvoreDestino = i + 1;
                        menor = ligacoes[i];
                    }
                }
            }
            resultadoLigacoes.append(arvoreOrigem).append(" ---> ").append(arvoreDestino).append(" : ").append(menor).append("\n");
            System.out.println("Arvore alvo: " + arvoreDestino);
            System.out.println("Ligacao: " + menor);
            listaArvoresDisponiveis.remove(listaArvoresDisponiveis.indexOf(arvoreDestino));
            listaArvoresEmCheck.add(arvoreDestino);
            valotTotal += menor;
        }

        System.out.println("\n\n");
        resultadoLigacoes.append("RESULTADO: ").append(valotTotal);
        System.out.println(resultadoLigacoes.toString());

        return resultadoLigacoes.toString();
    }

    public void pritMap() {
        map.forEach((k, v) -> {
            System.out.println(k + " : " + Arrays.toString(v));
        });
    }

}
