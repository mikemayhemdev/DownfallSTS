package saveData;

import java.util.ArrayList;

//gotta put anything you wanna save in here
public class EvilWithinSaveData {
    public boolean EVIL_MODE = false;

    public boolean RED_KEY_CONSUMED = false;
    public boolean GREEN_KEY_CONSUMED = false;
    public boolean BLUE_KEY_CONSUMED = false;

    public boolean KILLED_CLERIC = false;
    public boolean ENCOUNTERED_CLERIC = false;

    public ArrayList<String> UPCOMING_BOSSES = new ArrayList<>();

    public int MERCHANT_HEALTH = -1;
    public int MERCHANT_STRENGTH = -1;
    public int MERCHANT_SOULS  = -1;

    public boolean MERCHANT_DEAD = false;
    public boolean MERCHANT_ESCAPED = false;
}
