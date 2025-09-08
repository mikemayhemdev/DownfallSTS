package awakenedOne.cards;

import awakenedOne.powers.CursedStrength;
import awakenedOne.powers.DarknessFallsPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class DarknessFalls extends AbstractAwakenedCard {
    public final static String ID = makeID(DarknessFalls.class.getSimpleName());

    // intellij stuff power, self, rare, , , , , ,

    public DarknessFalls() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        baseSecondMagic = secondMagic = 1;
        loadJokeCardImage(this, makeBetaCardPath(DarknessFalls.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DarknessFallsPower(p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CursedStrength(p, secondMagic)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}