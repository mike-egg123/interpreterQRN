#include <stdio.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>

//存放当前读进的字符
char nowChar;

//存放单词的字符串
char token[50];

//存放当前读入的整型数值
int num;

//文件指针，用来引用当前输入的文件
FILE *fd;

//从fd中读入一个字符
void myGetchar(){
    nowChar = (char)fgetc(fd);
}

//清空token
void clearToken(){
    for(int i = 0;i < 30;i++){
        token[i] = 0;
    }
}

//判断当前字符是否为空格
bool isSpace(){
    if(nowChar == ' '){
        return true;
    }
    return false;
}

//判断当前字符是否为换行
bool isNewline(){
    if(nowChar == '\r\n'){
        return true;
    }
    return false;
}

//判断当前字符是否为制表符
bool isTab(){
    if(nowChar == '\t'){
        return true;
    }
    return false;
}

//判断当前字符是否为字母
bool isLetter(){
    if(isalpha(nowChar)){
        return true;
    }
    return false;
}

//判断当前字符是否为数字
bool isDigit(){
    if(isdigit(nowChar)){
        return true;
    }
    return false;
}

//判断当前字符是否为冒号
bool isColon(){
    if(nowChar == ':'){
        return true;
    }
    return false;
}

//判断当前字符是否为逗号
bool isComma(){
    if(nowChar == ','){
        return true;
    }
    return false;
}

//判断当前字符是否为等号
bool isEqu(){
    if(nowChar == '='){
        return true;
    }
    return false;
}

//判断当前字符是否为加号
bool isPlus(){
    if(nowChar == '+'){
        return true;
    }
    return false;
}

//判断当前字符是否为星号
bool isStar(){
    if(nowChar == '*'){
        return true;
    }
    return false;
}

//判断当前字符是否为左括号
bool isLpar(){
    if(nowChar == '('){
        return true;
    }
    return false;
}

//判断当前字符是否为右括号
bool isRpar(){
    if(nowChar == ')'){
        return true;
    }
    return false;
}

//将当前读到的字符拼接到token后面
void catToken(){
    strcat(token, &nowChar);
}

//fd回退一格
void retract(){
    ungetc(nowChar, fd);
}

//判断是否是保留字
bool reserver(){
    if(strcmp(token, "BEGIN") == 0){
        printf("Begin\n");
        return true;
    }
    else if(strcmp(token, "END") == 0){
        printf("End\n");
        return true;
    }
    else if(strcmp(token, "FOR") == 0){
        printf("For\n");
        return true;
    }
    else if(strcmp(token, "IF") == 0){
        printf("If\n");
        return true;
    }
    else if(strcmp(token, "THEN") == 0){
        printf("Then\n");
        return true;
    }
    else if(strcmp(token, "ELSE") == 0){
        printf("Else\n");
        return true;
    }
    else{
        return false;
    }
}

//将token中的字符串转化成十进制数字并存到num中
void transNum(){
    char *stop;
    num = strtol(token, &stop, 10);
}

//错误处理
void error(){
    printf("Unknown");
    exit(0);
}

//主分析函数
void getsym(){
    clearToken();
    myGetchar();
    while(isSpace() || isNewline() || isTab()){
        myGetchar();
    }
    if(isLetter()){
        while(isLetter() || isDigit()){
            catToken();
            myGetchar();
        }
        retract();
        if(!reserver()){
            printf("Ident(%s)\n", token);
        }
    }
    else if(isDigit()){
        while(isDigit()){
            catToken();
            myGetchar();
        }
        retract();
        transNum();
        printf("Int(%d)\n", num);
    }
    else if(isColon()){
        myGetchar();
        if(isEqu()){
            printf("Assign\n");
        }
        else{
            retract();
            printf("Colon\n");
        }
    }
    else if(isPlus()){
        printf("Plus\n");
    }
    else if(isStar()){
        printf("Star\n");
    }
    else if(isComma()){
        printf("Comma\n");
    }
    else if(isLpar()){
        printf("LParenthesis\n");
    }
    else if(isRpar()){
        printf("RParenthesis\n");
    }
    else{
        if(feof(fd)){
            return;
        }
        error();
    }
}
int main(int argc, char *argv[]) {
    fd = fopen(argv[1], "r");
    while(!feof(fd)){
        getsym();
    }
    fclose(fd);
    return 0;
}
