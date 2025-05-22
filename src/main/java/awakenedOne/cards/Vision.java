package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.actions.DuplicatePowerAction;

import static awakenedOne.AwakenedOneMod.makeID;

public class Vision extends AbstractAwakenedCard {
    public final static String ID = makeID(Vision.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Vision() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DuplicatePowerAction(p, 1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}