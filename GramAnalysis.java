import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GramAnalysis {

    //文件类，引用打开的测试文件
    public static File file;
    //随机读写文件类，可以用seek()自定义文件读写指针的位置
    public static RandomAccessFile accessFile;
    //符号栈
    public static char S[] = new char[1005];
    //符号栈栈顶
    public static int top = 0;
    //输入串
    public static String inS;
    public static char T[] = new char[1005];
    //优先关系矩阵
    public static char priorityMatrix[][] = new char[][]{
            {'l','s','s','s','l','l'},
            {'l','l','s','s','l','l'},
            {'l','l','n','n','l','l'},
            {'s','s','s','s','e','n'},
            {'l','l','n','n','l','l'},
            {'s','s','s','s','n','t'}};

    public static void main(String[] args) throws IOException {
        file = new File(args[0]);
        accessFile = new RandomAccessFile(file, "r");
        inS = accessFile.readLine();
        int inSLength = inS.length();
        char tmp[] = inS.toCharArray();
        for(int i = 0;i < inSLength;i++){
            T[i] = tmp[i];
        }
        S[0] = '#';
        T[inSLength] = '#';
        for(int i = 0;i < inSLength + 1;i++){
            if(analyseProcess(i) == 1){
                i--;
            }
        }
        System.exit(0);
    }
    public static int reflect(char word){
        switch (word){
            case '+':
                return 0;
            case '*':
                return 1;
            case 'i':
                return 2;
            case '(':
                return 3;
            case ')':
                return 4;
            case '#':
                return 5;
            default:
                System.out.println("E");
                System.exit(0);
        }
        return 6;
    }
    public static int makeSureTheTop(){
        int tmp = top;
        while(S[tmp] == 'N'){
            tmp--;
        }
        return tmp;
    }
    public static int analyseProcess(int i){
        int right = reflect(T[i]);
        int termninalTop = makeSureTheTop();
        int left = reflect(S[termninalTop]);
        if(priorityMatrix[left][right] == 's' || priorityMatrix[left][right] == 'e'){
            S[++top] = T[i];
            System.out.println("I" + T[i]);
        }
        else if(priorityMatrix[left][right] == 'l'){
            while(priorityMatrix[left][right] == 'l'){
                if(S[top] == 'i'){
                    S[top] = 'N';
                    System.out.println("R");
                }
                else if(S[top] == ')' || S[top - 1] == '+' || S[top - 1] == '*'){
                    top -= 2;
                    S[top] = 'N';
                    System.out.println("R");
                }
                else{
                    System.out.println("RE");
                    System.exit(0);
                }
                right = reflect(T[i]);
                termninalTop = makeSureTheTop();
                left = reflect(S[termninalTop]);
                if(priorityMatrix[left][right] == 't'){
                    System.exit(0);
                }
            }
            return 1;
        }
        else{
            System.out.println("E");
            System.exit(0);
        }
        return 0;
    }
}
