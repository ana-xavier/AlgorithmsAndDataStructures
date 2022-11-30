/*
* Aluna Ana Carolina Xavier, turma 14
*
* A entrada recebida é uma lista de linhas onde a primeira linha diz a quantidade
* de terras do primeiro guerreiro. Cada uma das linhas seguintes descreve um pai p, um
* filho f e a quantidade de terras que o filho f conquistou/comprou na sua vida
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {

        // --> Leitura do arquivo
        Path path1 = Paths.get("caso0.txt");
        // Demais casos: caso1.txt, caso2.txt, caso3.txt, caso4.txt
        try (BufferedReader leitor = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            // --> Cria árvore
            Arvore arvore = new Arvore();

            // --> Variaveis de auxilio
            String line, aux[];

            // --> Duas primerias linhas
            arvore.arquivoOrganiza(leitor.readLine(), leitor.readLine());

            // --> Enquanto ainda haver linhas, realiza suas leituras e adiciona os guerreiros na árvore
            while ((line = leitor.readLine()) != null) {
                aux = line.split(" ");
                arvore.addNodo(aux[1], Integer.parseInt(aux[2]), aux[0]);
            }
            // --> Atribui as terras
            arvore.atribuiTerras();

            // --> Imprime árvore
            arvore.imprimeArvore();

            // --> Imprime o bárbaro com mais terras no último nível
            System.out.println(arvore.imprimeBarbaroMaisTerras());

        } catch (IOException e) {
            System.err.format("Houve um problema amigo :( ", e);
        }
    }
}

