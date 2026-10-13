package main;

import jogo.JogoDaVelha;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Nome do Jogador 1 (X): ");
        String nome1 = entrada.nextLine().trim();
        System.out.print("Nome do Jogador 2 (O): ");
        String nome2 = entrada.nextLine().trim();

        if (nome1.isEmpty()) nome1 = "Jogador 1";
        if (nome2.isEmpty()) nome2 = "Jogador 2";

        JogoDaVelha jogo = new JogoDaVelha(nome1, nome2);
        jogo.iniciar();//Chama a função de iniciar o jogo

        do {
            jogo.iniciar();
        } while (jogo.jogarNovamente());

        System.out.println("Obrigado por jogar!");
    }
}
