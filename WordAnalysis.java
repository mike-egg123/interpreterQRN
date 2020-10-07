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
    //���ļ��л�ȡһ���ַ�������nowChar��
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
    //���token
    public static void clearToken(){
        tokenOff = 0;
        for(int i = 0;i < 50;i++){
            token[i] = 0;
        }
    }
    //����token�ĳ���
    public static int tokenLen(){
        int i;
        for(i = 0;i < 50;i++){
            if(token[i] == 0)
                break;
        }
        return i;
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ�ո�
    public static Boolean isSpace(){
        return nowChar == ' ';
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ����
    public static Boolean isNewline(){
        return nowChar == '\n' || nowChar == '\r';
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ�Ʊ��
    public static Boolean isTab(){
        return nowChar == '\t';
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ��ĸ
    public static Boolean isLetter(){
        return (nowChar >= 'a' && nowChar <= 'z') || (nowChar >= 'A' && nowChar <= 'Z');
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ����
    public static Boolean isDigit(){
        return (nowChar >= '0' && nowChar <= '9');
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊð��
    public static Boolean isColon(){
        return nowChar == ':';
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ����
    public static Boolean isComma(){
        return nowChar == ',';
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ�Ⱥ�
    public static Boolean isEqu(){
        return nowChar == '=';
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ�Ӻ�
    public static Boolean isPlus(){
        return nowChar == '+';
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ�Ǻ�
    public static Boolean isStar(){
        return nowChar == '*';
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ������
    public static Boolean isLpar(){
        return nowChar == '(';
    }
    //�жϵ�ǰ�ַ��Ƿ�Ϊ������
    public static Boolean isRpar(){
        return nowChar == ')';
    }
    //����ǰ�������ַ�ƴ�ӵ�token����
    public static void catToken(){
        token[tokenOff] = nowChar;
        tokenOff++;
    }
    //�ļ�ָ�����һ��
    public static void retract() throws IOException {
        pos--;
        accessFile.seek(pos);
    }
    //�ж�token�Ƿ��Ǳ�����
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
    //��token�е��ַ���ת����ʮ�������ֲ��浽num��
    public static void transNum(){
        String tmp = new String(token,0,tokenLen());
        num = Integer.parseInt(tmp);
    }
    //������
    public static void error(){
        System.out.println("Unknown");
        System.exit(0);
    }
    //����������
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
