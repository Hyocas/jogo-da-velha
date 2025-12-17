public abstract class Maquina implements Jogador {
    protected boolean simbolo;

    public Maquina(boolean simbolo) {
        this.simbolo = simbolo;
    }

    protected void jogarPosicao(Tabuleiro tabuleiro, int posicao) {
        tabuleiro.jogar(posicao, simbolo);
        System.out.println("Máquina (" + getTipo() + ") jogou na posição: " + posicao);
        tabuleiro.imprimir();
    }
}
