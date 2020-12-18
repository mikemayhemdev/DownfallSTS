package automaton;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TestDisplayPanel extends EasyInfoDisplayPanel {

    public TestDisplayPanel() {
        super(500, 500, 200);
    } // NOTE: X, Y, Width are all multipled by settings.scale on constructor, so use values like this.

    @Override
    public String getTitle() {
        return "THIS_IS_TEST";
    }

    @Override
    public String getDescription() {
        return "Player hp is: " + AbstractDungeon.player.currentHealth;
    }
}
