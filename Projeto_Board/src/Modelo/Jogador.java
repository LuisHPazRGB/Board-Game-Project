package Modelo;

public class Jogador
{
    private String nome; //Nome atribuido ao jogador
    private Peca peca; //Peça atribuida ao jogador

    public Jogador(String nome ,Peca peca)
    {
        this.nome = nome;
        this.peca = peca;
    }

    public String getNome()
    {
        return nome;
    }

    public Peca getPeca()
    {
        return peca;
    }
}
