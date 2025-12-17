import java.util.Scanner;

public class Humano implements Jogador {
    private boolean simbolo;
    private Scanner scanner;

    public Humano(boolean simbolo) {
        this.simbolo = simbolo;
    }

    public void jogar(Tabuleiro tabuleiro) {
        while (true) {
            System.out.print("Sua jogada (0-8): ");
            int pos = scanner.nextInt();
            if (tabuleiro.jogar(pos, simbolo)) break;
            System.out.println("Jogada inválida. Tente novamente.");
        }
        tabuleiro.imprimir();
    }

    @Override
    public String getTipo() {
        return "Humano";
    }
}

