%{
	#include<stdio.h>
%}

%%
[!@#$%^&*]+ printf("\n\t Special Character: \t%s", yytext);
char|int|float|double|void|if|else|class|import|public printf("\n\t Keyword: \t%s", yytext);
[0-9]+ printf("\n\t Constants: \t%s", yytext);
[,.'"]+ printf("\n\t Punctuation Chars: \t%s", yytext);
[a-zA-Z]+[a-zA-Z0-9]* printf("\n\t Identifiers: \t%s", yytext);
\{|\}|\(|\)|\[|\] printf("\n\t Brackets: \t%s", yytext);
; printf("\n\t Terminator: \t%s", yytext);
%%

int main(){
	yyin=fopen("count.java", "r");
	yylex();
	return 0;
}

int yywrap(){
	return 1;
}
