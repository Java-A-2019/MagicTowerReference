class Fighting {

    //与怪物战斗
    static int[] fight(int healthPoint, int attackPoint, int defencePoint, int money, int heal, int atk, int def, int mon) {
        int[] result = new int[3];
        final int oldHealthPoint = healthPoint; int healthLoss;
        money += mon;
        System.out.println("勇者脑补起了战斗场面");
        while (heal > 0) {
            System.out.println("勇者和怪物相互攻击！");
            healthLoss = Math.max((atk - defencePoint), 0); healthPoint = healthPoint - healthLoss;
            healthLoss = Math.max((attackPoint - def), 0); heal = heal - healthLoss;
            System.out.println("勇者还剩" + Math.max(healthPoint,0) + "血，怪物还剩" + Math.max(heal,0) + "血");
            if (healthPoint <= 0) {//勇者打不过！
                money -= mon;
                healthPoint = oldHealthPoint;
                result[2] = 1;//勇者把梦到的失败结局告诉自己
            }
        }
        result[0] = healthPoint; result[1] = money;
        System.out.println("胜负已分！");
        return result;
    }

    //打怪反应
    static boolean canBeat(int result) {
        if (result == 1) {
            System.out.println("勇者脑补发现他打不过怪物，竟然就退缩了！"); return false;
        } else {
            System.out.println("勇者脑补发现他打得过怪物，勇者A了上去！"); return true;
        }

    }
}