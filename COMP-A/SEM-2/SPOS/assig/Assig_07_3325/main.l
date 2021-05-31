%{
	int lines,words,chars;
%}

%%
[" "]+	{words++;chars+=yyleng;}
[\n]" "*	{lines++; words++;chars+=yyleng;}
.	{chars++;}
%%

int yywrap() {
	return 1;
}
int main() {
	yyin = fopen("count.java", "r");
	yylex();
	printf("No of lines %d\nNo of words %d\nNo of chars %d\n",lines+1,words+1,chars);
	return 0;
}
