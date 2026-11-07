package engine;

import excecoes.ConfiguracaoInvalida;
import modelo.Jogador;
import modelo.Peca;
import modelo.Tabuleiro;
import regras.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLoader
{
    public static GameEngine carregarJogo(String caminhoArquivo) throws ConfiguracaoInvalida
    {
        try
        {
            //le o texto inteiro do ficheiro JSON
            String jsonContent = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
            JSONObject config = new JSONObject(jsonContent);

            // 1. Configurar Tabuleiro
            JSONObject board = config.getJSONObject("board");
            int linhas = board.getInt("rows");
            int colunas = board.getInt("columns");
            if (linhas <= 0 || colunas <= 0)
            {
                throw new ConfiguracaoInvalida("Dimensões do tabuleiro inválidas.");
            }
            Tabuleiro tabuleiro = new Tabuleiro(linhas, colunas);

            // 2. Configurar Jogadores e ALEATORIZAR
            JSONObject players = config.getJSONObject("players");
            int playerCount = players.getInt("count");
            JSONArray symbols = players.getJSONArray("symbols");

            if (symbols.length() != playerCount)
            {
                throw new ConfiguracaoInvalida("Número de símbolos não corresponde à quantidade de jogadores.");
            }

            // Cria os nomes (Jogador 1, Jogador 2, etc.) e embaralha a lista
            List<String> nomesSorteio = new ArrayList<>();
            for (int i = 1; i <= playerCount; i++)
            {
                nomesSorteio.add("Jogador " + i);
            }
            Collections.shuffle(nomesSorteio); // O sorteio acontece aqui!

            // O jogador que ficou no índice 0 após embaralhar recebe o símbolo do índice 0 do JSON
            List<Jogador> jogadores = new ArrayList<>();
            for (int i = 0; i < playerCount; i++)
            {
                String nome = nomesSorteio.get(i);
                char simbolo = symbols.getString(i).charAt(0);

                // 1. Cria o jogador primeiro (se o seu construtor de Jogador exigir a peça logo de cara,
                // você pode precisar adicionar um metodo setPeca() na classe Jogador)
                Jogador novoJogador = new Jogador(nome);

                // 2. Cria a peça passando o jogador como dono.
                // Como não tem "tipo" no JSON da Velha/Trilha, usamos "COMUM" como padrão.
                Peca novaPeca = new Peca(simbolo, novoJogador, "COMUM");

                // 3. Associa a peça ao jogador
                novoJogador.setPeca(novaPeca);

                jogadores.add(novoJogador);
            }

            // 3. Configurar Regras de Movimento
            JSONObject rules = config.getJSONObject("rules");
            String movementType = rules.getString("placement");
            RegraDeMovimento regraMovimento;
            if (movementType.equals("EMPTY_POSITION"))
            {
                regraMovimento = new RegraPosicaoVazia();
            }
            else
            {
                throw new ConfiguracaoInvalida("Regra de movimento não suportada: " + movementType);
            }

            // 4. Configurar Regras de Captura
            RegraDeCaptura regraCaptura = null;
            if (rules.has("captureRule"))
            {
                String captureType = rules.getString("captureRule");
            }

            // 5. Configurar Condições de Vitória
            List<CondicaoDeVitoria> condicoesVitoria = new ArrayList<>();
            JSONObject winConf = rules.getJSONObject("winCondition");
            String winType = winConf.getString("type");

            if (winType.equals("ALIGNMENT"))
            {
                condicoesVitoria.add(new CondicaoVitoriaAlinhamento(winConf.getInt("quantity")));
            }
            else if (winType.equals("ELIMINATION"))
            {
                condicoesVitoria.add(new CondicaoVitoriaPecasInsuficientes(winConf.getInt("minPieces")));
            }
            else
            {
                throw new ConfiguracaoInvalida("Condição de vitória não suportada pela Engine: " + winType);
            }

            // Retorna a Engine montada
            return new GameEngine(tabuleiro, jogadores, regraMovimento, regraCaptura, condicoesVitoria);

        }
        catch (ConfiguracaoInvalida e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ConfiguracaoInvalida("Erro ao processar ficheiro JSON: " + e.getMessage());
        }
    }
}