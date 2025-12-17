public class Heuristica extends Maquina {
    
    public Heuristica(boolean simbolo) {
        super(simbolo);
    }

    @Override
    public void jogar(Tabuleiro tabuleiro) {

        int[][] casosBloqueio = {{0,1,2}, {0,3,6}, {2,5,8}, {6,7,8}, {0,4,8}, {2,4,6}};

        for (int i = 0; i < 9; i++) {
            if (!tabuleiro.getJogadas()[i]) {
                tabuleiro.getBoard()[i/3][i%3] = simbolo;
                if (tabuleiro.checarVencedor() != null) {
                    System.out.println("Vitória na posição " + i);
                    return;
                }
                tabuleiro.getBoard()[i/3][i%3] = null;
            }
        }

        for (int[] caso : casosBloqueio) {
            if (tabuleiro.getJogadas()[caso[0]] && !tabuleiro.getJogadas()[caso[1]] && tabuleiro.getJogadas()[caso[2]] &&
                tabuleiro.getBoard()[caso[0]/3][caso[0]%3] == tabuleiro.getBoard()[caso[2]/3][caso[2]%3]) {
                tabuleiro.jogar(caso[1], simbolo);
                return;
            }
        }

        if (!tabuleiro.getJogadas()[4]) {
            tabuleiro.jogar(4, simbolo);
            System.out.println("Máquina (Heuristica) jogou no CENTRO");
            tabuleiro.imprimir();
            return;
        }

        int[] cantos = {0, 2, 6, 8};
        for (int canto : cantos) {
            if (!tabuleiro.getJogadas()[canto]) {
                tabuleiro.jogar(canto, simbolo);
                System.out.println("Máquina (Heuristica) jogou no CANTO " + canto);
                tabuleiro.imprimir();
                return;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (!tabuleiro.getJogadas()[i]) {
                tabuleiro.jogar(i, simbolo);
                System.out.println("Máquina (Heuristica) jogou na posição: " + i);
                tabuleiro.imprimir();
                return;
            }
        }
        
        System.out.println("Máquina (Heuristica): nenhuma jogada possível - empate!");
    }

    @Override
    public String getTipo() {
        return "Heurística";
    }
}