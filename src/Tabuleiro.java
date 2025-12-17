public class Tabuleiro {
    private static final int SIZE = 3;
    private Boolean[][] board = new Boolean[SIZE][SIZE];
    private boolean[] jogadas = new boolean[SIZE * SIZE];


    public int getJogadasFeitas() {
        int count = 0;
        for (boolean j : jogadas) if (j) count++;
        return count;
    }

    public boolean jogar(int pos, boolean jogador) {
        if (pos < 0 || pos > 8 || jogadas[pos]) return false;
        int linha = pos / SIZE;
        int coluna = pos % SIZE;
        board[linha][coluna] = jogador;
        jogadas[pos] = true;
        return true;
    }

    public void desfazerJogada(int pos) {
        int linha = pos / SIZE;
        int coluna = pos % SIZE;
        board[linha][coluna] = null;
        jogadas[pos] = false;
    }

    public Boolean[][] getBoard() {
        return board;
    }

    public boolean[] getJogadas() {
        return jogadas;
    }

    public void imprimir() {
        System.out.println("\nTabuleiro:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int pos = i * SIZE + j;
                if (board[i][j] == null) System.out.print(" " + pos + " ");
                else System.out.print(" " + (board[i][j] ? "O" : "X") + " ");
                if (j < SIZE - 1) System.out.print("|");
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("---+---+---");
        }
        System.out.println();
    }

    public Boolean checarVencedor() {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return board[i][0];
            if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return board[0][i];
        }
        if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return board[0][0];
        if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return board[0][2];
        return null;
    }

    public boolean tabuleiroCheio() {
        for (boolean j : jogadas) if (!j) return false;
        return true;
    }

    public boolean verificaFimDeJogo() {
        Boolean vencedor = checarVencedor();
        if (vencedor != null) {
            if (vencedor) {
                System.out.println("Jogador (O) venceu!");
            } else {
                System.out.println("Máquina (X) venceu!");
            }
            return true;
        } else if (tabuleiroCheio()) {
            System.out.println("Empate!");
            return true;
        }
        return false;
    }

}
