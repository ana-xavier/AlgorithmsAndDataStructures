import java.util.ArrayList;
import java.util.Scanner;

public class JogoMacaquinhos {

    private int numRodadas;
    private ArrayList<Macaquinho> macaquinhos;
    public JogoMacaquinhos() {
        macaquinhos = new ArrayList<>();
    }

    public void ler(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String[] partes;
        partes = br.readLine().split(" ");
        numRodadas = Integer.parseInt(partes[1]);
        partes = br.readLine().split(" ");
        try {
            while (partes != null && partes.length > 0) {
                String linha = br.readLine();
                int id = Integer.parseInt(partes[1]);
                int pares = Integer.parseInt(partes[4]);
                int impares = Integer.parseInt(partes[7]);

                int qtdPares = 0;
                int qtdImpares = 0;
                for(int i = 11; i < partes.length; i++){
                    int valor = Integer.parseInt(partes[i]);
                    if(valor % 2 == 0) {
                        qtdPares++;
                    }else{
                        qtdImpares++;
                    }
                }

                Macaquinho macaquinho = new Macaquinho(id, pares, impares, qtdPares, qtdImpares);
                macaquinhos.add(macaquinho);
                if(linha == null){
                    break;
                }else{
                    partes = linha.split(" ");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro na leitura do arquivo");
            e.printStackTrace();
        }
    }

    public void jogar() {
        int par, impar;

        if(macaquinhos.isEmpty()){
            System.out.println("Não há macaquinhos :(");
            return;
        }
        for(int i = 0; i < numRodadas; i++){
            for(int j = 0; j < macaquinhos.size(); j++){
                par = macaquinhos.get(j).pares;
                impar = macaquinhos.get(j).impares;

                int auxP = macaquinhos.get(j).getPares();
                int auxI = macaquinhos.get(j).getImpares();

                macaquinhos.get(par).setQtdPares(auxP);
                macaquinhos.get(impar).setQtdImpares(auxI);

                macaquinhos.get(j).clear();
            }
        }

        int maxCocos = 0;
        int idCampeao = 0;
        for(int i = 0; i < macaquinhos.size(); i++){
            if((macaquinhos.get(i).getCocosTotal()) > maxCocos){
                maxCocos = macaquinhos.get(i).getCocosTotal();
                idCampeao = i;
            }
        }

        System.out.println("O macaquinho " + idCampeao + " é o campeão com " + maxCocos + " coquinhos");
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        JogoMacaquinhos jogo = new JogoMacaquinhos();
        jogo.ler("src\\casos\\caso1000.txt");
        jogo.jogar();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Tempo de execução " + totalTime + " em milissegundos");
    }
}
