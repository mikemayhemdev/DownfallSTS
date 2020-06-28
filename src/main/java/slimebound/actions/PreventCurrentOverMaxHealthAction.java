package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;

public class PreventCurrentOverMaxHealthAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private AbstractPlayer p;


    public PreventCurrentOverMaxHealthAction(int amount) {

        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;

    }


    public void update() {
        if (p.currentHealth > p.maxHealth - this.amount) {

            p.currentHealth = p.maxHealth - this.amount;
            p.healthBarUpdatedEvent();

        }

        this.isDone = true;


    }
}



