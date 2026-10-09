package excecoes;

// Exceção customizada para jogadas inválidas (ex: posição já ocupada)
public class MovimentoInvalido extends Exception
{
    public MovimentoInvalido(String mensagem)
    {
        super(mensagem); // Repassa a mensagem de erro para a classe pai (Exception)
    }
}