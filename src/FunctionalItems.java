class FunctionalItems {
    //捡到钥匙
    static int pickUpKey(int keyNumber) {
        keyNumber += 1;
        return keyNumber;
    }

    //捡到血瓶
    static int pickUpBottle(int bottleSize, int healthPoint) {
        healthPoint += bottleSize;
        return healthPoint;
    }

    //捡到宝石
    static int pickUpGem(int ability) {
        ability += 2;
        return ability;
    }

    //花钱进商店
    static int purchaseInStore(int money) {
        money -= 20;
        System.out.println("勇者感觉钱包被掏空_(xз」∠)_");
        return money;
    }

    //在商店里增加能力
    static int enhanceAbilityInStore(int ability) {
        ability += 3;
        return ability;
    }
}
