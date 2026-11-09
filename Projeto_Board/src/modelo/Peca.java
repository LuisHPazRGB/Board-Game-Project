package modelo;

public class Peca
{
    private char simbolo;
    private Jogador dono;
    private String tipo;

    //Construtor
    public Peca(char simbolo, Jogador dono, String tipo)
    {
        this.simbolo = simbolo;
        this.dono = dono;
        this.tipo = tipo;
    }

    //Metodo getter para acessar de forma segura
    public char getSimbolo()
    {
        return simbolo;
    }
    public Jogador getDono()
    {
        return dono;
    }
    public String getTipo()
    {
        return tipo;
    }

}
