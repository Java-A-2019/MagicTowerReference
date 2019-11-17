//对查询性指令发出反应
class OrderPrint {
    //-help，查询帮助文档
    static void helpPrint() {
        System.out.println("欢迎来到魔塔游戏:D\n" +
                "这是一款地下城冒险游戏，你要操控勇者打怪提升属性，击败魔王获得胜利!\n" +
                "\n" +
                "操作方式:\n" +
                "你可以输入wsad对应上下左右，输入后回车以确认操作\n" +
                "输入-f得到当前属性下攻击怪物的对应损血，血量不足则无法攻击\n" +
                "输入-help可以重新看这段提示\n" +
                "输入-restart投胎转世，输入-exit离开人世\n" +
                "输入-undo吃下后悔药，输入-redo吐出上一口后悔药\n" +
                "\n" +
                "地图物品说明:\n" +
                "墙:无法通过的墙壁\n" +
                "上、下:进入上一层、下一层的楼梯\n" +
                "钥、门:拾取钥匙后可开对应颜色的门，否则无法通过门\n" +
                "攻、防:攻击宝石和防御宝石，拾取可以增加2点对应属性\n" +
                "小、中、大:不同型号的血瓶，可以恢复50、100、250点生命值\n" +
                "商:红色为攻击力商店、蓝色为防御力商店，可花费20金获取3点对应属性\n" +
                "商店仅在一楼开设，下楼路很长，所以也可以输入指令-buyAtk购买攻击力、-buyDef购买防御力" +
                "\n" +
                "属性说明:\n" +
                "health:单位生命值，你将无法做出会清空生命值的操作\n" +
                "attack:单位攻击力，每回合能造成确定伤害\n" +
                "defence:单位防御力，每回合所受伤害为敌人攻击力减去自身防御力\n" +
                "money:金钱，杀死怪物掉落，可以去商店购买属性，20金可增加3点属性\n" +
                "\n" +
                "战斗方式:\n" +
                "战斗为回合制，勇者与怪物互相攻击直到一方死亡\n" +
                "一轮中受到的伤害为对方攻击力减去己方防御力（防御大于攻击则不造成伤害）\n" +
                "**勇者很聪明，不会去送死，如果打不过就不能打怪\n" +
                "\n" +
                "获胜方式:\n" +
                "拾取第五层的最终宝物即可获胜\n");
    }

    //-f，查询当前楼层的怪物信息、击败它的损血(输入楼层、勇者属性)
    static void fPrint(String[][] mapCurrentFloor,int healthPoint,int attackPoint,int defencePoint) {
        //打印头表
        System.out.println("怪物简称\t\t怪物名称\t\t血量\t攻击\t防御\t击败损血");
        //检测现在有哪些怪物
        boolean c = false; boolean d = false; boolean e = false; boolean f = false; boolean F = false;
        boolean g = false; boolean h = false; boolean x = false; boolean z = false;
        for (int i = 0; i <= 12; i++) {
            for (int j = 0; j <= 12; j++) {
                switch (mapCurrentFloor[i][j]) {
                    case "c":
                        c = true; break;
                    case "d":
                        d = true; break;
                    case "e":
                        e = true; break;
                    case "f":
                        f = true; break;
                    case "F":
                        F = true; break;
                    case "g":
                        g = true; break;
                    case "h":
                        h = true; break;
                    case "x":
                        x = true; break;
                    case "z":
                        z = true; break;
                }
            }
        }
        //有哪些怪物打印哪些信息
        if (c) fightPlanGreen(healthPoint,attackPoint,defencePoint);
        if (d) fightPlanRed(healthPoint,attackPoint,defencePoint);
        if (e) fightPlanBlack(healthPoint,attackPoint,defencePoint);
        if (f) fightPlanBat(healthPoint,attackPoint,defencePoint);
        if (F) fightPlanBigBat(healthPoint,attackPoint,defencePoint);
        if (g) fightPlanSkeleton(healthPoint,attackPoint,defencePoint);
        if (h) fightPlanSkeletonSoldier(healthPoint,attackPoint,defencePoint);
        if (x) fightPlanBoss(healthPoint,attackPoint,defencePoint);
        if (z) fightPlanStone(healthPoint,attackPoint,defencePoint);
        if (!(c || d || e || f || F || g || h || x || z)) System.out.println("当前楼层怪物已全部清除~");
    }

    private static void fightPlanGreen(int healthPoint,int attackPoint,int defencePoint) {
        fightPlan("绿","绿史莱姆",50,20,1,healthPoint,attackPoint,defencePoint);
    }

    private static void fightPlanRed(int healthPoint,int attackPoint,int defencePoint) {
        fightPlan("红","红史莱姆",80,30,1,healthPoint,attackPoint,defencePoint);
    }

    private static void fightPlanBlack(int healthPoint,int attackPoint,int defencePoint) {
        fightPlan("黑","黑史莱姆",200,45,15,healthPoint,attackPoint,defencePoint);
    }

    private static void fightPlanBat(int healthPoint,int attackPoint,int defencePoint) {
        fightPlan("蝙","蝙蝠",100,35,5,healthPoint,attackPoint,defencePoint);
    }

    private static void fightPlanBigBat(int healthPoint,int attackPoint,int defencePoint) {
        fightPlan("大","大蝙蝠",200,60,25,healthPoint,attackPoint,defencePoint);
    }

    private static void fightPlanSkeleton(int healthPoint,int attackPoint,int defencePoint) {
        fightPlan("骷","骷髅人",120,70,0,healthPoint,attackPoint,defencePoint);
    }

    private static void fightPlanSkeletonSoldier(int healthPoint,int attackPoint,int defencePoint) {
        fightPlan("兵","骷髅士兵",200,100,5,healthPoint,attackPoint,defencePoint);
    }

    private static void fightPlanBoss(int healthPoint,int attackPoint,int defencePoint) {
        fightPlan("魔","魔王",350,150,25,healthPoint,attackPoint,defencePoint);
    }

    private static void fightPlanStone(int healthPoint,int attackPoint,int defencePoint) {
        fightPlan("石","石头人",70,60,50,healthPoint,attackPoint,defencePoint);
    }

    private static void fightPlan(String shortName,String name,int heal,int atk,int def,int healthPoint,int attackPoint,int defencePoint) {
        int originHealth = healthPoint; int originHeal = heal; int healthLoss;
        if (attackPoint <= def)
            System.out.println(shortName + "\t\t\t" + name + "\t\t" + originHeal + "\t\t" + atk + "\t\t" + def + "\t\t" + "无法攻击");
        else {
            while (heal > 0) {
                healthLoss = Math.max((atk - defencePoint),0); healthPoint = healthPoint - healthLoss;
                healthLoss = Math.max((attackPoint - def),0); heal = heal - healthLoss;
            }
            int needHealth = originHealth - healthPoint;
            if (needHealth > originHealth)
                System.out.println(shortName + "\t\t\t" + name + "\t\t" + originHeal + "\t\t" + atk + "\t\t" + def + "\t\t" + "打不过");
            else
                System.out.println(shortName + "\t\t\t" + name + "\t\t" + originHeal + "\t\t" + atk + "\t\t" + def + "\t\t" + needHealth);
        }
    }
}