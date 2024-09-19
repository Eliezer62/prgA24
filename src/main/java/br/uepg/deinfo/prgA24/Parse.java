package br.uepg.deinfo.prgA24;

import br.uepg.deinfo.prgA24.Tokens.TabelaSimbolos;
import br.uepg.deinfo.prgA24.Tokens.TokenTipo;

public class Parse 
{
    TabelaSimbolos tb;
    private int posicaoAtual;
    TokenTipo[] linhaAtual;
    public Parse(TabelaSimbolos tb)
    {
        this.tb = tb;
        posicaoAtual = 0;
    }

    // Início da análise
    public void analisar() 
    {
        linhaAtual = tb.get();
        expect(TokenTipo.PROGRAM);
        parseDeclareSection();
        parseBeginSection();
        expect(TokenTipo.ENDPROGRAM);
        expect(TokenTipo.PONTOVIRGULA);
    }

    private void parseDeclareSection() {
        expect(TokenTipo.DECLARE);
        parseDeclarations();
        expect(TokenTipo.ENDDECLARE);
    }

    private void parseDeclarations() {
        if (check(TokenTipo.INT)) {
            parseDeclaration();
            expect(TokenTipo.PONTOVIRGULA);
            parseDeclarations();
        }
    }

    private void parseDeclaration() {
        expect(TokenTipo.INT);
        expect(TokenTipo.VARIAVEL);
    }

    private void parseBeginSection() {
        expect(TokenTipo.BEGIN);
        parseStatements();
        expect(TokenTipo.END);
    }

    private void parseStatements() {
        if (check(TokenTipo.VARIAVEL) || check(TokenTipo.PRINT) || check(TokenTipo.IF) || check(TokenTipo.CALL)) 
        {
            parseStatement();
            expect(TokenTipo.PONTOVIRGULA);
            parseStatements();
        }
    }

    private void parseStatement() {
        if (check(TokenTipo.VARIAVEL)) 
        {
            parseAssignment();
        } 
        else if (check(TokenTipo.PRINT)) 
        {
            parsePrint();
        } 
        else if (check(TokenTipo.IF)) 
        {
            parseIf();
        } 
        else if (check(TokenTipo.CALL)) 
        {
            parseCall();
        }
    }

    private void parseAssignment() {
        expect(TokenTipo.VARIAVEL);
        expect(TokenTipo.ATRIBUICAO);
        parseExpression();
    }

    private void parsePrint() {
        expect(TokenTipo.PRINT);
        expect(TokenTipo.ABREPARENTESES);
        expect(TokenTipo.CADEIA);
        expect(TokenTipo.FECHAPARENTESES);
    }

    private void parseIf() {
        expect(TokenTipo.IF);
        expect(TokenTipo.ABREPARENTESES);
        parseCondition();
        expect(TokenTipo.FECHAPARENTESES);
        expect(TokenTipo.THEN);
        parseStatements();
        expect(TokenTipo.END);
    }

    private void parseCall() {
        expect(TokenTipo.CALL);
        expect(TokenTipo.VARIAVEL);
        expect(TokenTipo.ABREPARENTESES);
        parseExpressionList();
        expect(TokenTipo.FECHAPARENTESES);
    }

    private void parseExpressionList() {
        if (check(TokenTipo.VARIAVEL) || check(TokenTipo.NUMERO)) {
            parseExpression();
            if (check(TokenTipo.VIRGULA)) {
                expect(TokenTipo.VIRGULA);
                parseExpressionList();
            }
        }
    }

    private void parseExpression() {
        parseTerm();
        if (check(TokenTipo.SOMA) || check(TokenTipo.SUBTRACAO))
        {
            advance();
            parseExpression();
        }
    }

    private void parseTerm() {
        parseFactor();
        if (check(TokenTipo.MULTIPLICACAO) || check(TokenTipo.DIVISAO)) 
        {
            advance(); // Consumir operador
            parseTerm();
        }
    }

    private void parseFactor() {
        if (check(TokenTipo.NUMERO))
            expect(TokenTipo.NUMERO);
        
        else if(check(TokenTipo.VARIAVEL))
            expect(TokenTipo.VARIAVEL);

        else if (check(TokenTipo.ABREPARENTESES)) 
        {
            expect(TokenTipo.ABREPARENTESES);
            parseExpression();
            expect(TokenTipo.FECHAPARENTESES);
        }
    }

    private void parseCondition() {
        parseExpression();
        if (check(TokenTipo.MENOR) || check(TokenTipo.MAIOR)) 
        {
            advance(); // Consumir operador relacional
            parseExpression();
        }
    }


    private void expect(TokenTipo tipo) {
        if (!check(tipo)) {
            throw new RuntimeException("Erro de sintaxe: esperado " + tipo);
        }
        advance();
    }

    private boolean check(TokenTipo tipo) {
        return linhaAtual[posicaoAtual] == tipo;
    }


    private void advance() 
    {
        if(posicaoAtual < linhaAtual.length)
            posicaoAtual++;

        else
        {
            posicaoAtual = 0;
            linhaAtual = tb.get();
        }
    }
}
