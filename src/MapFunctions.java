import java.io.*;
import java.util.Scanner;

//与地图相关的各种操作
class MapFunctions {
    //将文件中的地图读取并转化为数组返回
    static String[][][] mapReading() throws FileNotFoundException {
        //按行将地图信息写入数组
        String[][] MapRead = new String[5][13];
        for (int i = 0; i <= 4; i++) {
            Scanner scanner = new Scanner(new File("map/" + i + ".txt"));
            for (int j = 0; j <= 12; j++) {
                MapRead[i][j] = scanner.nextLine() + "\n";
            }
        }
        //通过切割字符串，将每行地图信息按个体写入数组
        String[][][] Map = new String[5][13][13];
        traverseMap(MapRead,Map);
        return Map;
    }

    private static void traverseMap(String[][] mapRead,String[][][] map) {
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 12; j++) {
                for (int k = 0; k <= 12; k++) {
                    map[i][j][k] = mapRead[i][j].substring(k,k + 1);
                }
            }
        }
    }

    //打印当前楼层的地图
    static void mapPrint(int floor,String[][][] Map) {
        for (int j = 0; j <= 12; j++) {
            for (int k = 0; k <= 12; k++) {
                switch (Map[floor][j][k]) {
                    case "a":
                        PRINT.P("\u3000",PRINT.WHITE); break;
                    case "b":
                        PRINT.P("墙",PRINT.BLACK,PRINT.BLACK_BACKGROUND); break;
                    case "c":
                        PRINT.P("绿",PRINT.GREEN); break;
                    case "d":
                        PRINT.P("红",PRINT.RED); break;
                    case "e":
                        PRINT.P("黑",PRINT.BLACK); break;
                    case "f":
                        PRINT.P("蝠",PRINT.BLACK); break;
                    case "F":
                        PRINT.P("大",PRINT.BLACK); break;
                    case "g":
                        PRINT.P("骷",PRINT.BLACK); break;
                    case "h":
                        PRINT.P("兵",PRINT.BLACK); break;
                    case "i":
                        PRINT.P("上",PRINT.WHITE); break;
                    case "j":
                        PRINT.P("下",PRINT.WHITE); break;
                    case "k":
                        PRINT.P("商",PRINT.RED); break;
                    case "l":
                        PRINT.P("商",PRINT.BLUE); break;
                    case "m":
                        PRINT.P("攻",PRINT.RED); break;
                    case "n":
                        PRINT.P("防",PRINT.BLUE); break;
                    case "o":
                        PRINT.P("瓶",PRINT.RED); break;
                    case "p":
                        PRINT.P("瓶",PRINT.BLUE); break;
                    case "q":
                        PRINT.P("瓶",PRINT.YELLOW); break;
                    case "r":
                        PRINT.P("钥",PRINT.YELLOW); break;
                    case "s":
                        PRINT.P("门",PRINT.WHITE,PRINT.YELLOW_BACKGROUND); break;
                    case "t":
                        PRINT.P("钥",PRINT.BLUE); break;
                    case "u":
                        PRINT.P("门",PRINT.WHITE,PRINT.BLUE_BACKGROUND); break;
                    case "v":
                        PRINT.P("钥",PRINT.RED); break;
                    case "w":
                        PRINT.P("门",PRINT.WHITE,PRINT.RED_BACKGROUND); break;
                    case "x":
                        PRINT.P("魔",PRINT.BLACK); break;
                    case "y":
                        PRINT.P("宝",PRINT.WHITE,PRINT.CYAN_BACKGROUND); break;
                    case "z":
                        PRINT.P("石",PRINT.BLACK); break;
                    case "B":
                        PRINT.P("勇",PRINT.RED,PRINT.CYAN_BACKGROUND); break;
                }
            }
            System.out.println();
        }
    }

    //游戏存档
    static void mapSave(int turns,String[][][] map,int floor,int[] position,
                        int healthPoint,int attackPoint,int defencePoint,int money,int yellowKeyNumber,int blueKeyNumber,int redKeyNumber) throws FileNotFoundException {
        for (int i = 0; i <= 4; i++) {
            // 创建一个 File 实例
            File mapSave = new File("map/save" + turns + i + ".txt");
            // 创建一个文件
            PrintWriter output = new PrintWriter(mapSave);
            //储存地图数据
            for (int j = 0; j <= 12; j++) {
                for (int k = 0; k <= 12; k++) {
                    output.print(map[i][j][k]);
                }
                output.println();
            }
            //储存勇者位置信息（楼层、与横纵坐标，与各项属性值（共7项））
            output.println(floor + " " + position[0] + " " + position[1]);
            output.print(healthPoint + " " + attackPoint + " " + defencePoint + " " + money + " " + yellowKeyNumber + " " + blueKeyNumber + " " + redKeyNumber);
            // 关闭文件
            output.close();
        }
    }

    //游戏读档
    static String[][][] mapLoad(int turns) throws FileNotFoundException {
        String[][][] loadedInformation = new String[6][13][13];

        //按行将地图信息写入数组前5组
        String[][] MapRead = new String[6][13];
        for (int i = 0; i <= 4; i++) {
            Scanner scanner = new Scanner(new File("map/save" + turns + i + ".txt"));
            for (int j = 0; j <= 12; j++) {
                MapRead[i][j] = scanner.nextLine() + "\n";
            }
            for (int j = 0; j <= 9; j++) {
                MapRead[5][j] = String.valueOf(scanner.nextInt());
            }
            scanner.close();//关闭对文件的扫描，否则将无法删除被读过的档
        }
        //通过切割字符串，将每行地图信息按个体写入数组前5组
        traverseMap(MapRead,loadedInformation);
        //将勇者位置信息和勇者属性值写入数组第6组（共10个信息）
        System.arraycopy(MapRead[5],0,loadedInformation[5][0],0,10);
        return loadedInformation;
    }

    //勇者上下楼时，位置变为新一层向下/上楼梯口的位置
    static int[] changeFloorPosition(boolean upOrDown,int floor) {
        int[] FloorPosition = new int[2];
        if (upOrDown) {
            switch (floor) {
                case 1:
                    FloorPosition[0] = 1;
                    FloorPosition[1] = 6;
                    break;
                case 2:
                    FloorPosition[0] = 7;
                    FloorPosition[1] = 6;
                    break;
                case 3:
                    FloorPosition[0] = 6;
                    FloorPosition[1] = 11;
                    break;
                case 4:
                    FloorPosition[0] = 11;
                    FloorPosition[1] = 6;
                    break;
            }
        } else {
            switch (floor) {
                case 0:
                    FloorPosition[0] = 2;
                    FloorPosition[1] = 6;
                    break;
                case 1:
                    FloorPosition[0] = 7;
                    FloorPosition[1] = 6;
                    break;
                case 2:
                    FloorPosition[0] = 6;
                    FloorPosition[1] = 11;
                    break;
                case 3:
                    FloorPosition[0] = 11;
                    FloorPosition[1] = 6;
                    break;
            }
        }
        return FloorPosition;
    }

    //删除悔棋用存档
    static void savesDelete(int turnsMax) {
        for (int i = turnsMax; i >= 1; i--) {
            for (int j = 0; j <= 4; j++) {
                File mapDelete = new File("map/save" + i + j + ".txt"); mapDelete.delete();
            }
        }
    }
}