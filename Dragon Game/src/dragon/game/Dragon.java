package dragon.game;

public class Dragon {

    // Atributes
    private String name;
    private int HP = 100;

    // For leveling system.
    private int level = 1;
    private int XP = 0;

    // Battle Variables (Changed based on level.)
    private double defenceModifier = 1.4 * level; // Decides how much damage taken is muffeled. (Example .2)
    private double damageModifier = 1.2 * level; // Decides how much damage given is multiplied. (Example: 1.2)
    private double accuracy = 80.0 + level; // Chance to hit.

    // Attacks
    final static int CLAW = 10;
    final static int BITE = 15;
    final static int FIRE_BREATH = 15; // Rember to add fire effect. (2 Damage per round???)
    final static int TAIL_WHIP = 20;

    // Constructor (Accepts only name)
    public Dragon(String name) {
        this.name = name;
    }

    // Constructor (Accepts name and level)
    public Dragon(String name, int level) {
        this.name = name;
        this.level = level;
    }

    // levelCheck (Before each fight uses the number of XP make sure the correct level has been assigned)
    public void levelCheck(int XP) {
        level = (XP / 100) + 1;
    }

    // Attack Method (Takes in target and deals damage according to the attack used.)
    public void attack(int type, Dragon target) {
        // Health Reduction
        target.setHP((int) (target.getHP() - ((type * damageModifier) / defenceModifier)));

        // Doesn't allow for overkill
        if (target.getHP() < 0) {
            target.setHP(0);
        }

        // Checks to see if dragon is dead
        deadCheck(target);

        // Prints out health information to the console.
        debug(target);
    }

    // deadCheck Method (Checks after each move if either player has died.)
    public boolean deadCheck(Dragon dragon) {
        if (dragon.getHP() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Degbug Method (Prints out health and attack used for debugging purposes only.)
    public void debug(Dragon dragon) {
        System.out.println(dragon.getName() + " has " + dragon.getHP() + " health left.");
    }

    // Get and Set for HP.
    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    // Get and Set for Name.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
