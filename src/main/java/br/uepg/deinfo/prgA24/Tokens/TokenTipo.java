package br.uepg.deinfo.prgA24.Tokens;

public enum TokenTipo {
    //Palavras reservadas
    PROGRAM,
    INT,
    THEN,
    END,
    IF,
    ENDPROGRAM,
    FUNCTION,
    ENDFUNCTION, 
    DECLARE,
    ENDDECLARE,
    RETURN,
    PRINT,
    CALL,

    //simbolos
    PONTOVIRGULA, 
    VIRGULA,
    ABREPARENTESES,
    FECHAPARENTESES,

    //operadores
    SOMA,
    SUBTRACAO,
    MULTIPLICACAO,
    DIVISAO,
    IGUAL,
    MENOR,
    MAIOR,
    ATRIBUICAO,
    
    //TIPOS
    NUMERO,
    VARIAVEL
}
