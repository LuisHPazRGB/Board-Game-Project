package jogo;

import excecoes.MovimentoInvalido;
import excecoes.PosicaoInvalida;
import modelo.Casa;
import modelo.Jogador;
import modelo.Peca;
import modelo.Tabuleiro;

import java.util.Scanner;

public class JogoDaVelha
{
    private Tabuleiro tabuleiro;
    private Jogador[] jogadores;
    private int indiceAtual; //0 ou 1: de quem é a vez
    private Scanner scanner;
    private int[] vitorias = new int[2];
    private int empates = 0;

    public JogoDaVelha(String nome1, String nome2)
    {
        tabuleiro = new Tabuleiro();
        jogadores = new Jogador[] {
            new Jogador(nome1, new Peca('X')),
            new Jogador(nome2, new Peca('O'))
        };
        indiceAtual = 0;
        scanner = new Scanner(System.in);
    }

    //Loop inicial para dar inicio ao jogo
    public void iniciar()
    {
        System.out.println("=== JOGO DA VELHA ===");
        Jogador vencedor = null;

        while (vencedor == null && !tabuleiro.estaCheio())
        {
            tabuleiro.formarTabuleiro();
            Jogador atual = jogadores[indiceAtual];
            System.out.println("\nVez de " + atual.getNome() + " (" + atual.getPeca().getSimbolo() + ")");

            try
            {
                int[] posicao = lerPosicao();
                jogar(atual, posicao[0], posicao[1]);

                if (verificarVitoria(atual.getPeca().getSimbolo()))
                {
                    vencedor = atual;
                }
                else
                {
                    indiceAtual = 1 - indiceAtual; //Alterna o jogador
                }
            }
            catch (PosicaoInvalida | MovimentoInvalido e)
            {
                System.out.println("Erro: " + e.getMessage() + " Tente novamente.");
            }
        }

        tabuleiro.formarTabuleiro();
        if (vencedor != null)
        {
            System.out.println("\nParabens, " + vencedor.getNome() + "! Voce venceu!");
            vitorias[indiceAtual]++;//Soma a vitória de cada jogador
        }
        else
        {
            System.out.println("\nDeu velha! Empate.");
            empates++;//soma a quantidade de empates
        }
        mostrarPlacar();
    }

    //Analisa a posição da jogada
    private int[] lerPosicao() throws PosicaoInvalida
    {
        //Previne quebra do sistema caso o usuario digite letras ao inves de números
        try
        {
            System.out.print("Linha (0-2): ");
            int linha = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Coluna (0-2): ");
            int coluna = Integer.parseInt(scanner.nextLine().trim());
            return new int[] { linha, coluna };
        }
        catch (NumberFormatException e)
        {
            throw new PosicaoInvalida("Digite apenas numeros inteiros.");//Retorna como erro de posição invalida
        }
    }


    private void jogar(Jogador jogador, int linha, int coluna) throws PosicaoInvalida, MovimentoInvalido
    {
        Casa casa = tabuleiro.getCasa(linha, coluna); //Lança PosicaoInvalida se fora dos limites
        if (!casa.estaVazia())
        {
            throw new MovimentoInvalido("A casa (" + linha + "," + coluna + ") ja esta ocupada.");
        }
        casa.setPeca(jogador.getPeca());
    }

    //Verifica se as posições das peças do jogador atendem as condições de vitoria ou não
    private boolean verificarVitoria(char s) throws PosicaoInvalida
    {
        for (int i = 0; i < 3; i++)
        {
            if (ocupa(i, 0, s) && ocupa(i, 1, s) && ocupa(i, 2, s)) return true; //Linha
            if (ocupa(0, i, s) && ocupa(1, i, s) && ocupa(2, i, s)) return true; //Coluna
        }
        if (ocupa(0, 0, s) && ocupa(1, 1, s) && ocupa(2, 2, s)) return true; //Diagonal principal
        return ocupa(0, 2, s) && ocupa(1, 1, s) && ocupa(2, 0, s);           //Diagonal secundária
    }

    private boolean ocupa(int linha, int coluna, char s) throws PosicaoInvalida
    {
        Casa c = tabuleiro.getCasa(linha, coluna);
        return !c.estaVazia() && c.getPeca().getSimbolo() == s;
    }

    //Mostra o placar de cada jogador e de empates
    private void mostrarPlacar()
    {
    
System.out.println("\n--- PLACAR ---");
        System.out.println(jogadores[0].getNome() + " (X): " + vitorias[0]);
        System.out.println(jogadores[1].getNome() + " (O): " + vitorias[1]);
        System.out.println("Empates: " + empates);
    }

    //Atua como um reset do estado do jogo reiniciando a partida
    public boolean jogarNovamente()
    {
        System.out.print("\nJogar novamente? (S/N): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        if (resposta.equals("s"))
        {
            tabuleiro.inicializar(); //Limpa o tabuleiro
            indiceAtual = 0;         //X volta a começar
            return true;
        }
        return false;
    }
}
