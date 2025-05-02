package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.util.Wiz.atb;

public class LastDitchEffort extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(LastDitchEffort.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public LastDitchEffort() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (AbstractDungeon.player.currentHealth <= AbstractDungeon.player.maxHealth*0.5)
        atb(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}