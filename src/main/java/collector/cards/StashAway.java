package collector.cards;

import collector.powers.ReservePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class StashAway extends AbstractCollectorCard {
    public final static String ID = makeID(StashAway.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public StashAway() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ReservePower(1));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int q = AbstractDungeon.player.getPower(ReservePower.POWER_ID).amount;
                applyToSelfTop(new ReservePower(q));
            }
        });
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}