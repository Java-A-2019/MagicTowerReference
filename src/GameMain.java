import java.io.FileNotFoundException;
import java.util.Scanner;

//游戏的主体程序
public class GameMain {

    public static void main(String[] args) throws FileNotFoundException {
        //启动游戏
        while (true) {
            //外层功能初始化
            boolean restart = true, redo = true; int[] result; int turns = 0, turnsMax = 0;
            Scanner input = new Scanner(System.in); String Input = null;
            StringBuilder sayings;
            String[][][] loadInformation;
            String[][][] Map = MapFunctions.mapReading();//从原始文件中读取地图，放入三维数组Map

            //游戏数据初始化
            int floor = 0;//出生在一楼
            int[] position = {10,6};//初始化勇者位置
            Map[floor][position[0]][position[1]] = "B";//将勇者位置写入地图
            int[] oldPosition; int oldFloor; boolean loaded, move;

            //创建勇者属性
            int healthPoint = 1000; int attackPoint = 10; int defencePoint = 10; int money = 20;
            int yellowKeyNumber = 0; int blueKeyNumber = 0; int redKeyNumber = 0;

            //游戏介绍与说明
            OrderPrint.helpPrint();
            //输出地图
            MapFunctions.mapPrint(floor,Map);
            MapFunctions.mapSave(turns,Map,floor,position,healthPoint,attackPoint,defencePoint,money,yellowKeyNumber,blueKeyNumber,redKeyNumber);
            //进入游戏循环
            while (restart) {
                //轮次内参数初始化
                loaded = false; move = true;
                sayings = new StringBuilder();
                //部分参数可能在玩家操作后改变，先保存旧位置用于复原
                oldPosition = position.clone();
                oldFloor = floor;
                //读取玩家的操作，对非法输入要求重试
                do {
                    try { Input = input.next(); redo = false; } catch (Exception e) {
                        sayings.append("无效操作，重新输入吧~可以输入-help查看所有指令\n"); input.nextLine();
                    }
                } while (redo);
                assert Input != null; switch (Input) {
                    //输入w、s、a、d-改变勇士位置
                    case "w":
                        position[0] -= 1; break;
                    case "s":
                        position[0] += 1; break;
                    case "a":
                        position[1] -= 1; break;
                    case "d":
                        position[1] += 1; break;
                    //-buyAtk,-buyDef远程购买攻击力和防御力
                    case "-buyAtk":
                        if (money >= 20) {
                            money = FunctionalItems.purchaseInStore(money);
                            attackPoint = FunctionalItems.enhanceAbilityInStore(attackPoint);
                        } else
                            sayings.append("口袋空空的勇者被拒之门外，去打些怪赚钱吧\n"); break;
                    case "-buyDef":
                        if (money >= 20) {
                            money = FunctionalItems.purchaseInStore(money);
                            defencePoint = FunctionalItems.enhanceAbilityInStore(defencePoint);
                        } else
                            sayings.append("口袋空空的勇者被拒之门外，去打些怪赚钱吧\n"); break;
                    //-help，打印help文档
                    case "-help":
                        turns -= 1;
                        OrderPrint.helpPrint(); break;
                    //f，打印怪物手册
                    case "-f":
                        turns -= 1;
                        OrderPrint.fPrint(Map[floor],healthPoint,attackPoint,defencePoint); break;
                    //-status，输出勇者属性
                    case "-status":
                        turns -= 1;
                        sayings.append("勇者生命值：").append(healthPoint).append(",攻击值：").append(attackPoint).append("，防御值：").append(defencePoint).append("，金钱：").append(money).append("，黄钥匙：").append(yellowKeyNumber).append("个，蓝钥匙：").append(blueKeyNumber).append("个，红钥匙：").append(redKeyNumber).append("个\n"); break;
                    //-exit，退出游戏
                    case "-exit":
                        MapFunctions.savesDelete(turnsMax);
                        sayings.append("退出游戏\n"); System.exit(0); break;
                    //-restart，回到启动游戏（设置continue种子）
                    case "-restart":
                        sayings.append("重新开始游戏\n"); restart = false; break;
                    //存档读档
                    case "-save":
                        turns -= 1;
                        MapFunctions.mapSave(-8,Map,floor,position,healthPoint,attackPoint,defencePoint,money,yellowKeyNumber,blueKeyNumber,redKeyNumber);
                        sayings.append("游戏进度已保存:D再次打开游戏输入load可以继续玩啦\n"); break;
                    case "-load":
                        turns -= 1;
                        //表明本轮已读档这一事实
                        sayings.append("勇者从存档中醒来，继续游戏吧~\n");
                        loaded = true; loadInformation = MapFunctions.mapLoad(-8);
                        //将load结果写入地图、勇者位置、属性
                        System.arraycopy(loadInformation,0,Map,0,5);
                        floor = Integer.parseInt(loadInformation[5][0][0]); position[0] = Integer.parseInt(loadInformation[5][0][1]); position[1] = Integer.parseInt(loadInformation[5][0][2]);
                        healthPoint = Integer.parseInt(loadInformation[5][0][3]); attackPoint = Integer.parseInt(loadInformation[5][0][4]); defencePoint = Integer.parseInt(loadInformation[5][0][5]); money = Integer.parseInt(loadInformation[5][0][6]);
                        yellowKeyNumber = Integer.parseInt(loadInformation[5][0][7]); blueKeyNumber = Integer.parseInt(loadInformation[5][0][8]); redKeyNumber = Integer.parseInt(loadInformation[5][0][9]); break;
                    case "-undo":
                        turns -= 1;
                        //防止刚开始游戏就悔棋
                        if (turns < 0) { turns += 1; sayings.append("勇者已经站在起点了，不需要喝后悔药\n"); }
                        //表明本轮已悔棋这一事实
                        else sayings.append("勇者喝下了后悔药，重新抉择吧\n");
                        loaded = true; loadInformation = MapFunctions.mapLoad(turns);
                        //将load结果写入地图、勇者位置、属性
                        System.arraycopy(loadInformation,0,Map,0,5);
                        floor = Integer.parseInt(loadInformation[5][0][0]); position[0] = Integer.parseInt(loadInformation[5][0][1]); position[1] = Integer.parseInt(loadInformation[5][0][2]);
                        healthPoint = Integer.parseInt(loadInformation[5][0][3]); attackPoint = Integer.parseInt(loadInformation[5][0][4]); defencePoint = Integer.parseInt(loadInformation[5][0][5]); money = Integer.parseInt(loadInformation[5][0][6]);
                        yellowKeyNumber = Integer.parseInt(loadInformation[5][0][7]); blueKeyNumber = Integer.parseInt(loadInformation[5][0][8]); redKeyNumber = Integer.parseInt(loadInformation[5][0][9]);
                        turns -= 1; break;
                    case "-redo":
                        turns += 1;//检测是否已经撤销悔棋到最新操作
                        if (turns - 1 >= turnsMax) {
                            turns -= 1; sayings.append("后悔药已经吐空了，继续冒险吧~\n");
                        } else sayings.append("勇者吐出了后悔药，胃里难受心中舒服\n");
                        loadInformation = MapFunctions.mapLoad(turns);
                        //将load结果写入地图、勇者位置、属性
                        System.arraycopy(loadInformation,0,Map,0,5);
                        floor = Integer.parseInt(loadInformation[5][0][0]); position[0] = Integer.parseInt(loadInformation[5][0][1]); position[1] = Integer.parseInt(loadInformation[5][0][2]);
                        healthPoint = Integer.parseInt(loadInformation[5][0][3]); attackPoint = Integer.parseInt(loadInformation[5][0][4]); defencePoint = Integer.parseInt(loadInformation[5][0][5]); money = Integer.parseInt(loadInformation[5][0][6]);
                        yellowKeyNumber = Integer.parseInt(loadInformation[5][0][7]); blueKeyNumber = Integer.parseInt(loadInformation[5][0][8]); redKeyNumber = Integer.parseInt(loadInformation[5][0][9]);
                        turns -= 1; break;
                    default:
                        sayings.append("无效操作，重新输入吧~可以输入-help查看所有指令\n");
                }
                //勇者的新位置与地图发生交互，不能移动或修改属性
                switch (Map[floor][position[0]][position[1]]) {
                    case "b"://撞墙,勇者无法前进
                        position = oldPosition.clone(); turns -= 1; break;
                    //遭遇各种怪物
                    case "c"://遭遇绿史莱姆
                        result = Fighting.fight(healthPoint,attackPoint,defencePoint,money,50,20,1,1);
                        healthPoint = result[0]; money = result[1]; move = Fighting.canBeat(result[2]); break;
                    case "d"://遭遇红史莱姆
                        result = Fighting.fight(healthPoint,attackPoint,defencePoint,money,80,30,1,2);
                        healthPoint = result[0]; money = result[1]; move = Fighting.canBeat(result[2]); break;
                    case "e"://遭遇黑史莱姆
                        result = Fighting.fight(healthPoint,attackPoint,defencePoint,money,200,45,15,5);
                        healthPoint = result[0]; money = result[1]; move = Fighting.canBeat(result[2]); break;
                    case "f"://遭遇蝙蝠
                        result = Fighting.fight(healthPoint,attackPoint,defencePoint,money,100,35,5,3);
                        healthPoint = result[0]; money = result[1]; move = Fighting.canBeat(result[2]); break;
                    case "F"://遭遇大蝙蝠
                        result = Fighting.fight(healthPoint,attackPoint,defencePoint,money,200,60,25,8);
                        healthPoint = result[0]; money = result[1]; move = Fighting.canBeat(result[2]); break;
                    case "g"://遭遇骷髅人
                        result = Fighting.fight(healthPoint,attackPoint,defencePoint,money,120,70,0,5);
                        healthPoint = result[0]; money = result[1]; move = Fighting.canBeat(result[2]); break;
                    case "h"://遭遇骷髅士兵
                        result = Fighting.fight(healthPoint,attackPoint,defencePoint,money,200,100,5,8);
                        healthPoint = result[0]; money = result[1];
                        move = Fighting.canBeat(result[2]); break;
                    case "x"://遭遇魔王！
                        result = Fighting.fight(healthPoint,attackPoint,defencePoint,money,350,150,25,20);
                        healthPoint = result[0]; money = result[1]; move = Fighting.canBeat(result[2]); break;
                    case "z"://遭遇石头人
                        result = Fighting.fight(healthPoint,attackPoint,defencePoint,money,70,60,50,8);
                        healthPoint = result[0]; money = result[1]; move = Fighting.canBeat(result[2]); break;
                    //上下楼
                    case "i"://上楼
                        floor += 1; position = MapFunctions.changeFloorPosition(true,floor); break;
                    case "j"://下楼
                        floor -= 1; position = MapFunctions.changeFloorPosition(false,floor); break;
                    //捡钥匙
                    case "r"://捡到黄钥匙
                        yellowKeyNumber = FunctionalItems.pickUpKey(yellowKeyNumber); sayings.append("天哪！一把金灿灿的黄钥匙\n"); break;
                    case "t"://捡到蓝钥匙
                        blueKeyNumber = FunctionalItems.pickUpKey(blueKeyNumber); sayings.append("天哪！一把黑黢黢的蓝钥匙\n"); break;
                    case "v"://捡到红钥匙
                        redKeyNumber = FunctionalItems.pickUpKey(redKeyNumber); sayings.append("天哪！一把血淋淋的红钥匙\n"); break;
                    //开门
                    case "s"://黄钥匙开黄门
                        if (yellowKeyNumber == 0) {move = false; sayings.append("需要黄钥匙才能开门");} else {
                            yellowKeyNumber -= 1; sayings.append("黄门开了！");
                        } break;
                    case "u"://蓝钥匙开蓝门
                        if (blueKeyNumber == 0) {move = false; sayings.append("需要蓝钥匙才能开门");} else {
                            blueKeyNumber -= 1; sayings.append("蓝门开了！");
                        } break;
                    case "w"://红钥匙开红门
                        if (redKeyNumber == 0) {move = false; sayings.append("红门纹丝不动，钥匙孔发出饥渴的喘息");} else {
                            redKeyNumber -= 1; sayings.append("钥匙孔很满足~~红门开了~~~");
                        } break;
                    //捡血瓶
                    case "o"://捡到小血瓶
                        healthPoint = FunctionalItems.pickUpBottle(50,healthPoint); sayings.append("勇者打开瓶子喝完，血色液体让他感到血气喷涌！\n生命值+50\n"); break;
                    case "p"://捡到中血瓶
                        healthPoint = FunctionalItems.pickUpBottle(100,healthPoint); sayings.append("勇者打开瓶子喝完，蓝瓶的就是好喝~\n生命值+100\n"); break;
                    case "q"://捡到大血瓶
                        healthPoint = FunctionalItems.pickUpBottle(250,healthPoint); sayings.append("勇者打开瓶子喝完，黄黄的奇怪液体让他心潮澎盘飘飘欲仙！！\n生命值狂加250！！\n"); break;
                    //捡宝石
                    case "m"://捡到攻击宝石
                        attackPoint = FunctionalItems.pickUpGem(attackPoint); sayings.append("勇者捡到了攻击宝石，力量涌来，胸口的彩虹纹封印若隐若现！\n攻击力+2\n"); break;
                    case "n"://捡到防御宝石
                        defencePoint = FunctionalItems.pickUpGem(defencePoint); sayings.append("勇者捡到了防御宝石，胸口碎大石技术更上一层楼！\n防御力+2\n"); break;
                    //商店买东西
                    case "k"://攻击商店
                        if (money >= 20) {
                            money = FunctionalItems.purchaseInStore(money);
                            attackPoint = FunctionalItems.enhanceAbilityInStore(attackPoint);
                        } else
                            sayings.append("口袋空空的勇者被拒之门外，去打些怪赚钱吧\n");
                        move = false; break;
                    case "l"://防御商店
                        if (money >= 20) {
                            money = FunctionalItems.purchaseInStore(money);
                            defencePoint = FunctionalItems.enhanceAbilityInStore(defencePoint);
                        } else
                            sayings.append("口袋空空的勇者被拒之门外，去打些怪赚钱吧\n");
                        move = false; break;
                    case "y"://拿到宝物，游戏胜利
                        sayings.append("你帮助勇者一路过关斩将拿到了宝物！游戏通关！\n");
                        sayings.append("你可以选择输入\"-exit\"关闭游戏，也可以继续看看这个世界\n"); break;
                }
                if (!move) position = oldPosition.clone();//勇者无法前进
                //将勇者之前的位置从地图中删除
                if (!loaded)
                    Map[oldFloor][oldPosition[0]][oldPosition[1]] = "a";
                //重新画上楼梯
                Map[0][2][6] = "i"; Map[1][7][6] = "i"; Map[2][6][11] = "i"; Map[3][11][6] = "i";
                Map[1][1][6] = "j"; Map[2][7][6] = "j"; Map[3][6][11] = "j"; Map[4][11][6] = "j";
                //将勇者改变后的位置写入地图
                Map[floor][position[0]][position[1]] = "B";
                //输出更新后的地图
                MapFunctions.mapPrint(floor,Map);
                //打印本轮发生的事情和勇者的状态
                System.out.print(sayings);
                System.out.println("勇者生命值：" + healthPoint + ",攻击值：" + attackPoint + "，防御值：" + defencePoint + "，金钱：" + money + "，黄钥匙：" + yellowKeyNumber + "个，蓝钥匙：" + blueKeyNumber + "个，红钥匙：" + redKeyNumber + "个");
                //轮次+1，记录出现过的最大轮次、并按论述将游戏存入新建存档
                turns += 1; turnsMax = Math.max(turns,turnsMax);
                MapFunctions.mapSave(turns,Map,floor,position,healthPoint,attackPoint,defencePoint,money,yellowKeyNumber,blueKeyNumber,redKeyNumber);
            }
        }
    }
}