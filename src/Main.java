import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250,300,1500, 200,600,130};
    public static int[] heroesDamage = {10, 15, 20,0,3,15,5,13};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic","Medic", "Golem","lucky","Berserk","Thor"};
    public static int roundNumber = 0;
    static Random random = new Random();

    static boolean randStun = random.nextBoolean();

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        golemWork();
        thorWork();
        bossHits();
        heroesHit();
        medicWork();
        luckyWork();
        berserkWork();
        printStatistics();
        bossDamage = 50;
    }

    private static void thorWork() {
        if (heroesHealth[7] > 0 && randStun){
            bossDamage = 0;
            System.out.println("Boss stunned");
        }
    }

    private static void berserkWork() {
        int randBlock = random.nextInt(30);
        if (heroesHealth[6] > 0){
            heroesHealth[6] += randBlock;
            bossHealth -= randBlock;
            System.out.println("Berserk is block on " + randBlock);
        }
    }

    private static void luckyWork() {
        boolean randSlope = random.nextBoolean();
        if (heroesHealth[5] > 0 && randSlope){
            heroesHealth[5] += bossDamage;
            System.out.println("Lucky is evaded");
        }
    }

    private static void golemWork() {
        int golemDamageing = bossDamage / 5;
        if (heroesHealth[4] > 0 && !randStun){
                bossDamage -= bossDamage / 5;
                heroesHealth[4] -= golemDamageing * (heroesAttackType.length - 2);
                System.out.println("Golem absorbed damage: " + bossDamage);
        }
    }

    private static void medicWork() {
        int health = 30; /*random.nextInt(100);*/
        if (heroesHealth[3] > 0){
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] <= 100 && heroesHealth[i] > 0 && heroesHealth[i] != 3){
                    heroesHealth[i] += health;
                    System.out.println("Medic health " + heroesAttackType[i] + " "+ health);
                    break;
                }
            }
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int healthOfCurrentHero : heroesHealth) {
            if (healthOfCurrentHero > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        if (roundNumber == 0) {
            System.out.println("BEFORE START -------------");
        } else {
            System.out.println("ROUND " + roundNumber + " -------------");
        }
        /*String value;
        if (bossDefence == null) {
            value = "No defence";
        } else {
            value = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + "; damage: "
                + bossDamage + "; defence: "
                + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " +
                    heroesHealth[i] + "; damage: " + heroesDamage[i]);
        }
    }
}
