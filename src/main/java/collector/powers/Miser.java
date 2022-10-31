package collector.powers;

import collector.CollectorMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class Miser extends AbstractPower {
    public static final String POWER_ID = CollectorMod.makeID("Miser");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public Miser(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;
        this.loadRegion("retain");
        this.description = DESCRIPTIONS[0] + this.amount;
    }

    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount;

    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            Iterator var2 = AbstractDungeon.player.hand.group.iterator();

            while (var2.hasNext()) {
                AbstractCard c = (AbstractCard) var2.next();
                if (!c.isEthereal && (c.cost < amount)) {
                    c.retain = true;
                }
            }
        }

    }
}
