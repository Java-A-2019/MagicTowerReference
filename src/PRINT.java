//用便于理解的方式彩打
class PRINT {
    static final int WHITE = 30;             // 白色
    //static final int WHITE_BACKGROUND = 40;  // 白色背景
    static final int RED = 31;               // 红色
    static final int RED_BACKGROUND = 41;    // 红色背景
    static final int GREEN = 32;             // 绿色
    //static final int GREEN_BACKGROUND = 42;  // 绿色背景
    static final int YELLOW = 33;            // 黄色
    static final int YELLOW_BACKGROUND = 43; // 黄色背景
    static final int BLUE = 34;              // 蓝色
    static final int BLUE_BACKGROUND = 44;   // 蓝色背景
    //static final int MAGENTA = 35;           // 品红（洋红）
    //static final int MAGENTA_BACKGROUND = 45;// 品红背景
    //static final int CYAN = 36;              // 蓝绿
    static final int CYAN_BACKGROUND = 46;   // 蓝绿背景
    static final int BLACK = 37;             // 黑色
    static final int BLACK_BACKGROUND = 47;  // 黑色背景
    //static final int BOLD = 1;       // 粗体
    //static final int ITATIC = 3;     // 斜体
    //static final int UNDERLINE = 4;  // 下划线
    //static final int REVERSE = 7;    // 反转

    private static String FMT(String txt, int... codes) {
        StringBuilder sb = new StringBuilder();
        for (int code : codes) {
            sb.append(code).append(";");
        }
        String _code = sb.toString();
        if (_code.endsWith(";")) {
            _code = _code.substring(0, _code.length() - 1);
        }
        return (char) 27 + "[" + _code + "m" + txt + (char) 27 + "[0m";
    }

    //打印不换行
    static void P(String txt, int... codes) {
        System.out.print(FMT(txt, codes));
    }
    //打印并换行与默认打印红色

    /*//打印并换行
    private static void PN(String txt, int... codes) {
        System.out.println(FMT(txt, codes));
    }

    //默认打印红色文字
    static void PN(String txt) {
        System.out.println(FMT(txt, RED));
    }*/
}