/*
    Ana Carolina Xavier :')
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class Fenicios {

    // representa o mar no mapa
    private static final char MAR = '.';
    // representa um obstáculo no mapa
    private static final char OBSTACULO = '*';

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        char[][] mapa = lerMapa("src\\Casos\\caso06.txt");
        int distanciaTotal = calcularDistanciaTotal(mapa);  // p/ calcula a distância total percorrida
        System.out.println("Distância total percorrida: " + distanciaTotal);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Tempo de execução " + totalTime + " em milissegundos");
    }

    private static char[][] lerMapa(String nomeArquivo) {
        char[][] mapa = null;
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine();
            String[] dimensoes = linha.split(" ");
            // número de linhas do mapa
            int linhas = Integer.parseInt(dimensoes[0]);
            // número de colunas do mapa
            int colunas = Integer.parseInt(dimensoes[1]);

            // cria uma matriz para armazenar o mapa
            mapa = new char[linhas][colunas];
            for (int i = 0; i < linhas; i++) {
                linha = br.readLine();
                for (int j = 0; j < colunas; j++) {
                    // armazena os caracteres do mapa na matriz
                    mapa[i][j] = linha.charAt(j);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapa;
    }

    private static int calcularDistanciaTotal(char[][] mapa) {
        int distanciaTotal = 0;
        // encontra a posição do primeiro porto (porto 1)
        int[] posicaoAtual = encontrarPorto(mapa, 1);

        // percorre os portos de 2 a 9
        for (int porto = 2; porto <= 9; porto++) {
            // encontra a posição do porto atual
            int[] posicaoFinal = encontrarPorto(mapa, porto);
            // calcula a distância entre os portos
            int distancia = calcularDistancia(mapa, posicaoAtual, posicaoFinal);

            if (distancia == -1) {
                System.out.println("Não foi possível encontrar um caminho para o porto " + porto);
                continue;
            }

            // acumula a distância percorrida
            distanciaTotal += distancia;
            // atualiza a posição atual para a próxima iteração
            posicaoAtual = posicaoFinal;
        }

        // adiciona a distância de volta ao porto 1
        int[] posicaoInicial = encontrarPorto(mapa, 1);
        int distanciaVolta = calcularDistancia(mapa, posicaoAtual, posicaoInicial);
        if (distanciaVolta == -1) {
            System.out.println("Não foi possível encontrar um caminho de volta ao porto 1 :(");
            return -1;
        }
        distanciaTotal += distanciaVolta;

        return distanciaTotal;
    }


    private static int[] encontrarPorto(char[][] mapa, int numeroPorto) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (Character.isDigit(mapa[i][j]) && Character.getNumericValue(mapa[i][j]) == numeroPorto) {
                    // retorna a posição do porto encontrado
                    return new int[]{i, j};
                }
            }
        }
        // retorna null se o porto não for encontrado
        return null;
    }

    private static int calcularDistancia(char[][] mapa, int[] posicaoInicial, int[] posicaoFinal) {
        int linhas = mapa.length;
        int colunas = mapa[0].length;
        // matriz para marcar as posições visitadas
        boolean[][] visitado = new boolean[linhas][colunas];
        // movimentos possíveis: cima, baixo, esquerda, direita
        int[][] movimentos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // fila para armazenar as posições a serem visitadas
        Queue<int[]> fila = new ArrayDeque<>();
        // adiciona a posição inicial à fila
        fila.add(posicaoInicial);
        // marca a posição inicial como visitada
        visitado[posicaoInicial[0]][posicaoInicial[1]] = true;

        // distância percorrida até o momento
        int distancia = 0;

        // enquanto a fila não estiver vazia
        while (!fila.isEmpty()) {
            // obtém o tamanho atual da fila
            //  --> número de posições a serem visitadas neste nível
            int tamanhoFila = fila.size();

            for (int i = 0; i < tamanhoFila; i++) {
                // remove a primeira posição da fila
                int[] posicaoAtual = fila.poll();
                int x = posicaoAtual[0];
                int y = posicaoAtual[1];

                if (x == posicaoFinal[0] && y == posicaoFinal[1]) {
                    // se a posição atual é igual à posição final, retorna a distância percorrida
                    return distancia;
                }

                for (int[] movimento : movimentos) {
                    // calcula a nova posição x
                    int novoX = x + movimento[0];
                    // calcula a nova posição y
                    int novoY = y + movimento[1];

                    // verifica se a nova posição (novoX, novoY) está dentro dos limites da matriz mapa
                    // e se a nova posição satisfaz as seguintes condições:
                    // --> a nova posição no mapa é igual a MAR ou é a posição final
                    //     que representa o destino final do percurso
                    // --> a posição não foi visitada anteriormente
                    // --> a posição não é um obstáculo no mapa.
                    // > caso todas as condições acima forem verdadeiras, a nova posição (novoX, novoY)
                    // é adicionada à fila (fila.add) e marcada como visitada (visitado[novoX][novoY] = true).
                    if (novoX >= 0 && novoX < linhas && novoY >= 0 && novoY < colunas
                            && (mapa[novoX][novoY] == MAR || (novoX == posicaoFinal[0] && novoY == posicaoFinal[1]))
                            && !visitado[novoX][novoY] && !ehObstaculo(mapa, novoX, novoY)) {
                        // adiciona a nova posição à fila
                        fila.add(new int[]{novoX, novoY});
                        // marca a nova posição como visitada
                        visitado[novoX][novoY] = true;
                    }
                }
            }
            // incrementa a distância percorrida
            distancia++;
        }
        return -1;
    }

    private static boolean ehObstaculo(char[][] mapa, int x, int y) {
        // verifica se a posição (x, y) é um obstáculo
        return mapa[x][y] == OBSTACULO;
    }
}

