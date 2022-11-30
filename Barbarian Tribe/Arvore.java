/*
 * Aluna Ana Carolina Xavier, turma 14
 */
import java.util.LinkedList;
import java.util.Queue;

public class Arvore {
    public class NodoArvore {
        private NodoArvore pai;
        private String nome;
        private LinkedList<NodoArvore> filhos;
        private int terras;
        private int nFilhos;

        public NodoArvore(Integer terras, String nome) {
            this.terras = terras;
            this.nome = nome;
            pai = null;
            filhos = new LinkedList<>();
            nFilhos = 0;
        }

        // --> Adiciona "subArvore"; filho.
        public void addSubtree(NodoArvore f) {
            if (f != null) {
                if (nFilhos == filhos.size()) {
                    filhos.add(f);
                    nFilhos++;
                    f.pai = this;
                }
            }
        }

        // --> Consulta subtree; filho.
        public NodoArvore getSubtree(int idx) {
            if (idx >= 0 && idx < nFilhos) {
                return filhos.get(idx);
            }
            return null;
        }

        // --> Retorna tamanho da subtree/número de filhos.
        public int getSubtreeSize() {
            return nFilhos;
        }
    }

    private NodoArvore root;
    private int nElementos;

    // --> Construtor.
    public Arvore() {
        root = null;
        nElementos = 0;
    }
    //-----------------------------------------------------------------
    // METODOS BASICOS
    // --> Retorna o nível onde se encontra o filho/pai informado.
    public int nivel(String nome){
        // --> Cria auxiliar.
        NodoArvore aux = pesquisaNodo(nome, root);
        // --> "niveis" irá contar quantos "andares" irá percorrer.
        int niveis = 0;
        // --> Enquanto o auxiliar não chegar na raiz, continuará buscando.
        while(aux != root){
            // --> A cada nível, o auxiliar recebe o pai de onde passou.
            //     --> Andando por nível.
            aux = aux.pai;
            niveis++;
        }
        return niveis;
    }
    // --> Adiciona Nodo na árvore.
    //     --> Nome do filho, quantidade de terras e nome do pai.
    public boolean addNodo(String nome, Integer terras, String pai){
        // --> Cria o nodo.
        NodoArvore nodo = new NodoArvore(terras, nome);
        // --> Caso ainda não há a raiz.
        // --> Após criação do novo nodo, adiciona na árvore e seu pai é ele mesmo.
        if(pai == null){
            if(root != null){
                nodo.addSubtree(root);
                root.pai = nodo;
            }
            // --> Define-o como root e número de elementos aumenta.
            root = nodo;
            nElementos++;
            return true;
        }
        else{
            // --> Caso já exista a raiz é necessário criar filho.
            //     --> Encontra pai.
            NodoArvore aux = pesquisaNodo(pai, root);
            //     --> Caso encontrar o pai.
            if(aux != null){
                // --> Adiciona filho ao pai.
                // --> Define seu pai (encontrado em aux).
                // --> Aumenta número de elementos.
                aux.addSubtree(nodo);
                nodo.pai = aux;
                nElementos++;
                return true;
            }else{
                // --> Se não, não é possível adicionar filho.
                return false;
            }
        }
    }
    // --> Pesquisar/percorrer Nodo na árvore.
    public NodoArvore pesquisaNodo(String nome, NodoArvore ref) {
        // --> Se não existir, retorna null
        if(ref == null){
            return null;
        }
        // --> Caso o nome informado seja igual o da referencia, retorna a referencia
        if(nome.equals(ref.nome)){
            return ref;
        }else{
            // --> Se não, cria nodo auxiliar
            NodoArvore aux = null;
            // --> A partir do aux (estando nulo)
            // --> Percorre a quantidade de vezes igual o tamanho da subtree (filhos)
            // -- Recursão :)
            for(int i = 0; i < ref.getSubtreeSize() && aux == null; i++){
                aux = pesquisaNodo(nome, ref.getSubtree(i));
            }
            // --> Retorna o aux pesquisado
            return aux;
        }
    }
    //-----------------------------------------------------------------
    // METODOS POSITIONS
    // --> Métodos para percorrer a árvore.
    // --> Utilizando filas (com auxilio de interface Queue).
    public LinkedList<String> positionsWidth(){
        LinkedList<String> lst = new LinkedList<>();
        // --> Caso a árvore não estiver vazia
        if(root != null){
            // --> Cria a fila para os nodos, e adiciona a raiz inicialmente.
            Queue<NodoArvore> fila = new LinkedList<>();
            fila.add(root);
            // --> Enquanto a fila não está vazia.
            while(!(fila.isEmpty())){
                // --> Então nodo auxiliar é retirado da fila
                // --> É colocado o nome na lista
                NodoArvore aux = fila.remove();
                lst.add(aux.nome);
                // --> Adiciona filhos na fila.
                for(int i = 0; i < aux.getSubtreeSize(); i++){
                    fila.add(aux.getSubtree(i));
                }
            }
        }
        return lst;
    }
    //-----------------------------------------------------------------
    // METODOS ESTATISTICAS
    // --> Aqui será realizada a organização da leitura do arquivo.
    //     --> l1 = primeira linha do arquivo, onde há as terras do pai
    //     --> l2 = segunda linha onde há o nome do pai, do filho e a qtd de terras do filho.
    public void arquivoOrganiza(String l1, String l2){
        // --> Linha 1 convertida para inteiro e atribuida ao terras do pai.
        int terraPai = Integer.parseInt(l1);
        // --> Vetorzinho que armazena linha 2
        String[] data = l2.split(" ");
        // --> Variaveis que iniciarão a arvore.
        // --> idx = 0 é o pai, idx = 1 é o filho e idx = 2 é as terras do filho
        String nPai = data[0];
        String nFilho = data[1];
        // --> Converte String para int
        int terraFilho = Integer.parseInt(data[2]);

        // --> Adiciona essas linhas na arvore
        addNodo(nPai, terraPai, null);
        addNodo(nFilho, terraFilho, nPai);
    }
    // --> Realiza a distribuição das terras do pai para os filhos.
    public void atribuiTerras(){
        // --> Fornece os nomes em ordem de nivel
        LinkedList<String> lst = positionsWidth();

        // --> Percorre todos os elementos atribuindo terras
        for(int i = 0; i < lst.size(); i++){
            NodoArvore aux = pesquisaNodo(lst.get(i), root);
            if(aux != root){
                // --> Valor de terras a ser distribuido aos filhos
                int atribuicao = (aux.pai.terras/aux.pai.getSubtreeSize());
                aux.terras += atribuicao;
            }
        }
    }
    // --> Realiza a criação de uma lista e coloca nela elementos do ultimo nivel da árvore
    public LinkedList<String> ultimoNivel(){
        // --> Listas de apoio
        LinkedList<String> lst = positionsWidth();
        LinkedList<String> lstFinal = new LinkedList<>();

        // --> Adiciona o último elemento da lista original na final
        lstFinal.add(lst.get(lst.size()-1));

        // --> Retorna o nível onde está o elemento 0 da lista final
        int UltimoNivel = nivel(lstFinal.get(0));

        // -> Percorre a lista
        for(int  i = 1; i < lst.size(); i++) {
            // --> Se o nível do elemento da lista for diferente do da lista final
            if (nivel(lst.get(lst.size()-(i+1))) != UltimoNivel) {
                // --> Então finaliza o looping
                break;
                // --> Se não adiciona o elemento como último na lista final
            }else{
                lstFinal.add(lst.get(lst.size()-(i+1)));
            }
        }
        return lstFinal;
    }
    // --> Retorna o barbaro com a maior quantidade de terras.
    public NodoArvore barbaroMaisTerras(){
        // --> Lista auxiiar com elementos de último nível
        LinkedList<String> lst = ultimoNivel();
        // --> Nodo auxiliar para comparação
        NodoArvore atual = pesquisaNodo(lst.get(0), root);

        // --> Percorre a lista
        for(int i = 1; i < lst.size(); i++){
            // --> Encontra cada nodo/barbaro
            NodoArvore aux = pesquisaNodo(lst.get(i), root);
            // --> Verifica se as terras do barbaro é maior do que do atual com mais terras
            if(aux != root){
                if(aux.terras > atual.terras){
                    // --> Se sim, barbaro agora é atual com mais terras
                    atual = aux;
                }
            }
        }
        return atual;
    }

    //-----------------------------------------------------------------
    // MÉTODOS AUXILIARES
    // --> Imprimir árvore.
    public void imprimeArvore(NodoArvore ref){
        if(ref != null){
            System.out.print(ref.nome + "; \n");
            for(int i = 0; i < ref.getSubtreeSize(); i++){
                imprimeArvore(ref.getSubtree(i));
            }
        }
    }
    // --> Auxiliar de impressão
    public void imprimeArvore(){
        if(root == null){
            System.out.print("A árvore está vazia");
        }else{
            imprimeArvore(root);
        }
        System.out.println();
    }
    // --> Imprime o barbaro com maior número de terras.
    public String imprimeBarbaroMaisTerras(){
        NodoArvore aux = barbaroMaisTerras();
        return "Barbaro com mais terras: " + aux.nome + " com o total de " + aux.terras + " terras :)";
    }
    //-----------------------------------------------------------------
}