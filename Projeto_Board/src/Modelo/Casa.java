package Modelo;

public class Casa
{
    private Peca peca; //Cada casa pode conter uma peça ou ser null

    //Verifica se a celula está vazia
    public boolean estaVazia()
    {
        return peca == null;
    }

    //Retorna a peça contida na casa
    public Peca getPeca()
    {
        return peca;
    }

    //Posiciona uma peça na casa
    public void setPeca(Peca peca)
    {
        this.peca = peca;
    }
}
