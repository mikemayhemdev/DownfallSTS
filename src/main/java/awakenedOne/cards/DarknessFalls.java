package awakenedOne.cards;

import awakenedOne.powers.DarknessFallsPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class DarknessFalls extends AbstractAwakenedCard {
    public final static String ID = makeID(DarknessFalls.class.getSimpleName());

    // intellij stuff power, self, rare, , , , , ,

    public DarknessFalls() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagic = 2;
        this.secondMagic = this.baseSecondMagic;
        this.isInnate = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DarknessFallsPower(secondMagic)));
    }

    @Override
    public void upp() {
        this.isInnate = true;
    }
}