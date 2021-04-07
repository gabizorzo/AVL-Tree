/* ARVORES DE PESQUISA 
Algoritmos e Estruturas de Dados
Turma 128
Gabriela Zorzo e Morgana Weber */

public class Interface {
    public void executa() {
        AVLTree t = new AVLTree();
        t.add(10);
        t.add(5);
        t.add(1);
        t.add(3);
        t.add(24);
        t.add(12);
        t.add(33);
        t.add(7);
        t.add(99);
        t.add(0);

        System.out.println("Pai de 7: " + t.getParent(7));
        System.out.println("33 esta na arvore:  " + t.contains(33));
        System.out.println("55 esta na arvore: " + t.contains(55));
        System.out.println("Altura da arvore: " + t.height());
        System.out.println("Esta balanceada? " + t.isBalanced());
        System.out.println("A arvore tem " + t.size() + " elementos.");
        System.out.println("A arvore esta vazia? " + t.isEmpty());
        System.out.println("Caminhamento pre-fixado: " + t.positionsPre());
        System.out.println("Caminhamento central: " + t.positionsCentral());
        System.out.println("Caminhamento pos-fixado: " + t.positionsPos());
        System.out.println("Caminhamento por largura: " + t.positionsWidth());

    }
}
