/* ARVORES DE PESQUISA 
Algoritmos e Estruturas de Dados
Turma 128
Gabriela Zorzo e Morgana Weber */

import java.util.*;

public class AVLTree {
    //Classe interna nodo 
    public class Node {
        private Node left, right, parent;
        private Integer height = 1;
        private Integer value;
        public Integer element;

        private Node(Integer val) {
            this.value = val;
            parent = null;
            left = null;
            right = null;
        }
    }

    //Retorna a altura da árvore 
    //O(1)
    public int height() {
        return height(root);
    }

    //Classe interna altura 
    private int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    private int count; 
    private Node root; 

    //Método construtor
    public AVLTree() {
        count = 0;
        root = null;
    }

    //Limpa a árvore
    public void clear() {
        count = 0;
        root = null;
    }

    //Verifica se a árvore está vazia
    //O(1)
    public boolean isEmpty() {
        return (root == null);
    }

    //Retorna o total de elementos da árvore
    //O(1)
    public int size() {
        return count;
    }

    //Retorna o pai de element 
    //O(log n)
    public Integer getParent(Integer element) {
        Node aux = new Node(element);
        aux = getParent(root, aux);
        return aux.value;
    }

    //Classe interna para buscar o pai de um elemento 
    private Node getParent(Node root, Node node) {
        if (root == null || node == null) {
            return null;
        } else if ((root.right != null && root.right.value == node.value)
                || (root.left != null && root.left.value == node.value)) {
            return root;
        } else {
            Node found = getParent(root.right, node);
            if (found == null) {
                found = getParent(root.left, node);
            }
            return found;
        }
    }

    //Verifica se element está na árvore
    //O(log n)
    public boolean contains(Integer element) {
        Node aux = searchNodeRef(element, root);
        return (aux != null);
    }

    //Classe interna de busca por element
    private Node searchNodeRef(Integer element, Node target) {
        if (element == null || target == null)
            return null;

        int r = element.compareTo(target.value); 

        if (r == 0) 
            return target; 
        else if (r < 0)
            return searchNodeRef(element, target.left);
        else
            return searchNodeRef(element, target.right);
    }

    //Caminhamento pré-fixado
    //O(log n)
    public LinkedListOfInteger positionsPre() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPreAux(root, res);
        return res;
    }

    //Classe interna de caminhamento pré-fixado
    private void positionsPreAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            res.add(n.value); 
            positionsPreAux(n.left, res); 
            positionsPreAux(n.right, res); 
        }
    }

    //Caminhamento pós-fixado
    //O(log n)
    public LinkedListOfInteger positionsPos() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPosAux(root, res);
        return res;
    }

    //Classe interna de caminhamento pós-fixado
    private void positionsPosAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsPosAux(n.left, res); 
            positionsPosAux(n.right, res); 
            res.add(n.value); 
        }
    }

    //Caminhamento central
    //O(log n)
    public LinkedListOfInteger positionsCentral() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsCentralAux(root, res);
        return res;
    }

    //Classe interna de caminhamento central
    private void positionsCentralAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsCentralAux(n.left, res);
            res.add(n.value); 
            positionsCentralAux(n.right, res); 
        }
    }

    //Caminhamento por largura
    //O(log n)
    public LinkedListOfInteger positionsWidth() {
        Queue<Node> fila = new Queue<>();
        Node atual = null;
        LinkedListOfInteger res = new LinkedListOfInteger();
        if (root != null) {
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                atual = fila.dequeue();
                if (atual.left != null) {
                    fila.enqueue(atual.left);
                }
                if (atual.right != null) {
                    fila.enqueue(atual.right);
                }
                res.add(atual.value);
            }
        }
        return res;
    }

    //Método para adicionar element na árvore 
    //O(log n)
    public void add(Integer element) {
        root = insert(root, element, null);
        count++;
        print(root);
        System.out.println("--------------------------------------------------------------------");
    }

    //Classe interna para adicionar element 
    private Node insert(Node node, int value, Node parent) {
        if (node == null) {
            Node aux = new Node(value);
            aux.parent = parent;
            return aux;
        }

        if (value < node.value)
            node.left = insert(node.left, value, node);
        else
            node.right = insert(node.right, value, node);

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        if (balance > 1 && value < node.left.value)
            return rightRotate(node);

        if (balance < -1 && value > node.right.value)
            return leftRotate(node);

        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    //Rotaciona a árvore para a direita
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    //Rotaciona a árvore para a esquerda
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    //Verifica o balanceamento do nodo
    private int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    //Verifica se a árvore está balanceada
    //O(log n)
    public boolean isBalanced(){
        return isBalanced(root);
    }

    //Método interno para verificar se a árvore está balanceada
    private boolean isBalanced(Node node) {
        int leftH; 
        int rightH; 

        if (node == null)
            return true;

        leftH = height(node.left);
        rightH = height(node.right);

        if (Math.abs(leftH - rightH) <= 1 && isBalanced(node.left) && isBalanced(node.right))
            return true;

        return false;
    }

    //Método para imprimir a árvore conforme inserção de elementos 
    //Autor: Nehal J Wani
    //Disponível em: GitHub (AVLTree.java)
    public void print(Node root) {
        if (root == null) {
            System.out.println("(XXXXXX)");
            return;
        }

        int height = root.height, width = (int) Math.pow(2, height - 1);

        List<Node> current = new ArrayList<Node>(1), next = new ArrayList<Node>(2);
        current.add(root);

        final int maxHalfLength = 4;
        int elements = 1;

        StringBuilder sb = new StringBuilder(maxHalfLength * width);
        for (int i = 0; i < maxHalfLength * width; i++)
            sb.append(' ');
        String textBuffer;

        for (int i = 0; i < height; i++) {
            sb.setLength(maxHalfLength * ((int) Math.pow(2, height - 1 - i) - 1));

            textBuffer = sb.toString();

            for (Node n : current) {
                System.out.print(textBuffer);

                if (n == null) {
                    System.out.print("        ");
                    next.add(null);
                    next.add(null);
                } else {
                    System.out.printf("(%6d)", n.value);
                    next.add(n.left);
                    next.add(n.right);
                }

                System.out.print(textBuffer);
            }

            System.out.println();

            if (i < height - 1) {
                for (Node n : current) {
                    System.out.print(textBuffer);
                    if (n == null)
                        System.out.print("        ");
                    else
                        System.out.printf("%s      %s", n.left == null ? " " : "/", n.right == null ? " " : "\\");
                    System.out.print(textBuffer);
                }
                System.out.println();
            }

            elements *= 2;
            current = next;
            next = new ArrayList<Node>(elements);
        }
    }
}