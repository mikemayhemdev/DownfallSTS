package automaton;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TestDisplayPanel extends EasyInfoDisplayPanel {

    public TestDisplayPanel() {
        super(500, 500, 200);
    }

    @Override
    public String getTitle() {
        return "THIS_IS_TEST";
    }

    @Override
    public String getDescription() {
        return "Player hp is: " + AbstractDungeon.player.currentHealth;
    }
}
