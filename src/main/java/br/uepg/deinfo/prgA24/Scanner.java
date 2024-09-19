package br.uepg.deinfo.prgA24;

import br.uepg.deinfo.prgA24.Exception.TokenInvalido;
import br.uepg.deinfo.prgA24.Tokens.TabelaSimbolos;
import br.uepg.deinfo.prgA24.Tokens.TokenTipo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner 
{
    private HashMap<String, TokenTipo> tokens;
    /**
     * @param String src Endereço do arquivo e le o arquivo
     * @return
     */

    java.util.Scanner sc;

    public Scanner(File arquivo)
    {
        //Tokens scanner
        tokens = new HashMap<>();
        tokens.put("Program", TokenTipo.PROGRAM);
        tokens.put("begin", TokenTipo.BEGIN);
        tokens.put("int", TokenTipo.INT);
        tokens.put("then", TokenTipo.THEN);
        tokens.put("end", TokenTipo.END);
        tokens.put("if", TokenTipo.IF);
        tokens.put("endProgram", TokenTipo.ENDPROGRAM);
        tokens.put("function", TokenTipo.FUNCTION);
        tokens.put("endFunction", TokenTipo.ENDFUNCTION);
        tokens.put("declare", TokenTipo.DECLARE);
        tokens.put("endDeclare", TokenTipo.ENDDECLARE);
        tokens.put("return", TokenTipo.RETURN);
        tokens.put("print", TokenTipo.PRINT);
        tokens.put("call", TokenTipo.CALL);

        //Simbolos
        tokens.put(";", TokenTipo.PONTOVIRGULA);
        tokens.put(",", TokenTipo.VIRGULA);
        tokens.put("(", TokenTipo.ABREPARENTESES);
        tokens.put(")", TokenTipo.FECHAPARENTESES);

        //Operadores
        tokens.put("+", TokenTipo.SOMA);
        tokens.put("-", TokenTipo.SUBTRACAO);
        tokens.put("*", TokenTipo.MULTIPLICACAO);
        tokens.put("/", TokenTipo.DIVISAO);
        tokens.put("=", TokenTipo.DIVISAO);
        tokens.put("<", TokenTipo.MENOR);
        tokens.put(">", TokenTipo.MAIOR);
        tokens.put(":", TokenTipo.ATRIBUICAO);
        tokens.put("STRING", TokenTipo.CADEIA);

        try 
        {
            sc = new java.util.Scanner(arquivo);
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Arquivo não encontrado");
        }
    }
    private TokenTipo getToken(String token) throws TokenInvalido
    {
        if(tokens.containsKey(token))
            return tokens.get(token);
        else
        {
            Pattern var = Pattern.compile("^[a-zA-Z_$][a-zA-Z_$0-9]*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = var.matcher(token);
            if(matcher.find()) return TokenTipo.VARIAVEL;

            Pattern num = Pattern.compile("[0-9^]+", Pattern.CASE_INSENSITIVE);
            Matcher numero = num.matcher(token);
            if(numero.find()) return TokenTipo.NUMERO;
            else
                throw new TokenInvalido(token);
        }
    }


    public TabelaSimbolos getTabela() throws TokenInvalido
    {
        TabelaSimbolos ts = new TabelaSimbolos();
        while (sc.hasNextLine()) 
        {
            String linha = sc.nextLine();
            linha = linha.replaceAll("\"[^\"]*\"", "STRING");
            Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            
            String[] tokens = tokenizer.tokenize(linha);
            TokenTipo[] tipos = new TokenTipo[tokens.length];
            int index = 0;
            for (String token : tokens) 
            {
                tipos[index] = getToken(token);
                index++;
            }
            ts.push(tipos);
        }

        return ts;
    }
    
}
