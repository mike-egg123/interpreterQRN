import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class WordAnalysis {
    public static File file;
    public static RandomAccessFile accessFile;


    public static char nowChar;
    public static int num;
    public static int tokenOff;
    public static int pos;
    public static Boolean isFileEnd = false;
    public static char[] token = new char[50];
    public static void main(String[] args) throws IOException {
        file = new File(args[0]);
        accessFile = new RandomAccessFile(file, "r");
        while(!isFileEnd){
            getsym();
        }
        accessFile.close();
    }
    //从文件中获取一个字符，放入nowChar中
    public static void myGetchar() throws IOException {
        int tmp = accessFile.read();
        if(tmp == -1){
            nowChar = 0;
            isFileEnd = true;
        }
        else
            nowChar = (char)tmp;
        pos++;
    }
    //清空token
    public static void clearToken(){
        tokenOff = 0;
        for(int i = 0;i < 50;i++){
            token[i] = 0;
        }
    }
    //计算token的长度
    public static int tokenLen(){
        int i;
        for(i = 0;i < 50;i++){
            if(token[i] == 0)
                break;
        }
        return i;
    }
    //判断当前字符是否为空格
    public static Boolean isSpace(){
        return nowChar == ' ';
    }
    //判断当前字符是否为换行
    public static Boolean isNewline(){
        return nowChar == '\n' || nowChar == '\r';
    }
    //判断当前字符是否为制表符
    public static Boolean isTab(){
        return nowChar == '\t';
    }
    //判断当前字符是否为字母
    public static Boolean isLetter(){
        return (nowChar >= 'a' && nowChar <= 'z') || (nowChar >= 'A' && nowChar <= 'Z');
    }
    //判断当前字符是否为数字
    public static Boolean isDigit(){
        return (nowChar >= '0' && nowChar <= '9');
    }
    //判断当前字符是否为冒号
    public static Boolean isColon(){
        return nowChar == ':';
    }
    //判断当前字符是否为逗号
    public static Boolean isComma(){
        return nowChar == ',';
    }
    //判断当前字符是否为等号
    public static Boolean isEqu(){
        return nowChar == '=';
    }
    //判断当前字符是否为加号
    public static Boolean isPlus(){
        return nowChar == '+';
    }
    //判断当前字符是否为星号
    public static Boolean isStar(){
        return nowChar == '*';
    }
    //判断当前字符是否为左括号
    public static Boolean isLpar(){
        return nowChar == '(';
    }
    //判断当前字符是否为右括号
    public static Boolean isRpar(){
        return nowChar == ')';
    }
    //将当前读到的字符拼接到token后面
    public static void catToken(){
        token[tokenOff] = nowChar;
        tokenOff++;
    }
    //文件指针回退一格
    public static void retract() throws IOException {
        pos--;
        accessFile.seek(pos);
    }
    //判断token是否是保留字
    public static boolean reserver(){
        String tmp = new String(token,0,tokenLen());
        switch (tmp) {
            case "BEGIN":
                System.out.println("Begin");
                return true;
            case "END":
                System.out.println("End");
                return true;
            case "FOR":
                System.out.println("For");
                return true;
            case "IF":
                System.out.println("If");
                return true;
            case "THEN":
                System.out.println("Then");
                return true;
            case "ELSE":
                System.out.println("Else");
                return true;
            default:
                return false;
        }
    }
    //将token中的字符串转化成十进制数字并存到num中
    public static void transNum(){
        String tmp = new String(token,0,tokenLen());
        num = Integer.parseInt(tmp);
    }
    //错误处理
    public static void error(){
        System.out.println("Unknown");
        System.exit(0);
    }
    //主分析函数
    public static void getsym() throws IOException {
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
                String tmp = new String(token,0,tokenLen());
                System.out.println("Ident(" + tmp + ")");
            }
        }
        else if(isDigit()){
            while(isDigit()){
                catToken();
                myGetchar();
            }
            retract();
            transNum();
            System.out.println("Int(" + num + ")");
        }
        else if(isColon()){
            myGetchar();
            if(isEqu()){
                System.out.println("Assign");
            }
            else{
                retract();
                System.out.println("Colon");
            }
        }
        else if(isPlus())
            System.out.println("Plus");
        else if(isStar())
            System.out.println("Star");
        else if(isComma())
            System.out.println("Comma");
        else if(isLpar())
            System.out.println("LParenthesis");
        else if(isRpar())
            System.out.println("RParenthesis");
        else{
            if (isFileEnd){
                System.exit(0);
            }
            error();
        }
    }

}
