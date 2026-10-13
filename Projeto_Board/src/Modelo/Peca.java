package Modelo;

public class Peca
{
    private char simbolo;

    //Construtor
    public Peca(char simbolo)
    {
        this.simbolo = simbolo;
    }

    //Metodo getter para acessar de forma segura
    public char getSimbolo()
    {
        return simbolo;
    }

}
