import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GramAnalysis {

    /* 文件类，引用打开的测试文件 */
    public static File file;
    /* 随机读写文件类，可以用seek()自定义文件读写指针的位置 */
    public static RandomAccessFile accessFile;
    /* 符号栈S */
    public static char S[] = new char[1005];
    /* 符号栈栈顶 */
    public static int top = 0;
    /* 输入串T，inS用于过渡输入的内容 */
    public static String inS;
    public static char T[] = new char[1005];
    /* 优先关系矩阵，l代表大于，s代表小于，e代表等于，n代表无关系 */
    public static char priorityMatrix[][] = new char[][]{
            {'l','s','s','s','l','l'},
            {'l','l','s','s','l','l'},
            {'l','l','n','n','l','l'},
            {'s','s','s','s','e','n'},
            {'l','l','n','n','l','l'},
            {'s','s','s','s','n','t'}};

    public static void main(String[] args) throws IOException {
        /* 将文件内容读入并放在T中 */
        file = new File(args[0]);
        accessFile = new RandomAccessFile(file, "r");
        inS = accessFile.readLine();
        int inSLength = inS.length();
        char tmp[] = inS.toCharArray();
        for(int i = 0;i < inSLength;i++){
            T[i] = tmp[i];
        }
        /* 给符号栈和输入串添加左界符和右界符 */
        S[0] = '#';
        T[inSLength] = '#';
        /* 边读入分析 */
        for(int i = 0;i < inSLength + 1;i++){
            /* 如果左优先级大于右，则暂停读入 */
            if(analyseProcess(i) == 1){
                i--;
            }
        }
        System.exit(0);
    }
    /* 符号与优先关系矩阵位置号映射函数，+->0 *->1 i ->2 (->3 )->4 #->5 */
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
                /* 无法识别的符号，输出E，程序终止 */
                System.out.println("E");
                System.exit(0);
        }
        return 6;
    }
    /* 得到最靠近栈顶的终结符的位置 */
    public static int makeSureTheTop(){
        int tmp = top;
        while(S[tmp] == 'N'){
            tmp--;
        }
        return tmp;
    }
    /* 主分析过程 */
    public static int analyseProcess(int i){
        /* 读入符号在右 */
        int right = reflect(T[i]);
        int termninalTop = makeSureTheTop();
        /* 最靠近栈顶终结符在左 */
        int left = reflect(S[termninalTop]);
        /* 如果左小于或等于右，则入栈，并打印I{T[i]} */
        if(priorityMatrix[left][right] == 's' || priorityMatrix[left][right] == 'e'){
            S[++top] = T[i];
            System.out.println("I" + T[i]);
        }
        /* 如果左大于右，则归约 */
        else if(priorityMatrix[left][right] == 'l'){
            while(priorityMatrix[left][right] == 'l'){
                /* 如果栈顶是i */
                if(S[top] == 'i'){
                    S[top] = 'N';
                    System.out.println("R");
                }
                /* 如果栈顶是(N)或N + N或N * N */
                else if(S[top] == ')' || S[top - 1] == '+' || S[top - 1] == '*'){
                    top -= 2;
                    S[top] = 'N';
                    System.out.println("R");
                }
                /* 失败的归约 */
                else{
                    System.out.println("RE");
                    System.exit(0);
                }
                /* 更新right和left */
                right = reflect(T[i]);
                termninalTop = makeSureTheTop();
                left = reflect(S[termninalTop]);
                /* 两个#相遇，程序终止 */
                if(priorityMatrix[left][right] == 't'){
                    System.exit(0);
                }
            }
            return 1;
        }
        /* 无法识别的符号 */
        else{
            System.out.println("E");
            System.exit(0);
        }
        return 0;
    }
}
