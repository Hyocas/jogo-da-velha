public class Minimax extends Maquina {

    public Minimax(boolean simbolo) {
        super(simbolo);
    }

    @Override
    public void jogar(Tabuleiro tabuleiro) {
        int melhorValor = Integer.MIN_VALUE;
        int melhorMovimento = -1;

        for (int i = 0; i < 9; i++) {
            if (!tabuleiro.getJogadas()[i]) {
                tabuleiro.jogar(i, simbolo);
                int valor = minimax(tabuleiro, false, 0);
                tabuleiro.desfazerJogada(i);

                if (valor > melhorValor) {
                    melhorValor = valor;
                    melhorMovimento = i;
                }
            }
        }

        jogarPosicao(tabuleiro, melhorMovimento);
    }

    private int minimax(Tabuleiro tabuleiro, boolean isMax, int depth) {
        Boolean vencedor = tabuleiro.checarVencedor();
        if (vencedor != null) {
            if (vencedor == simbolo) {
                return 10 - depth;
            } else {
                return depth - 10;
            }
        }
        if (tabuleiro.tabuleiroCheio()) return 0;

        int melhorValor;

        if (isMax) {
            melhorValor = Integer.MIN_VALUE;
        } else {
            melhorValor = Integer.MAX_VALUE;
        }

        for (int i = 0; i < 9; i++) {
            if (!tabuleiro.getJogadas()[i]) {
                tabuleiro.jogar(i, isMax ? simbolo : !simbolo);
                int valor = minimax(tabuleiro, !isMax, depth + 1);
                tabuleiro.desfazerJogada(i);
                
                if (isMax) {
                    melhorValor = Math.max(melhorValor, valor);
                } else {
                    melhorValor = Math.min(melhorValor, valor);
                }
            }
        }
        return melhorValor;
    }

    @Override
    public String getTipo() {
        return "Minimax";
    }
}