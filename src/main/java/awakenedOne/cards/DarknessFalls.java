package awakenedOne.cards;

import awakenedOne.powers.DarkEchoPower;
import awakenedOne.powers.DarknessFallsPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

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
        applyToSelf(new DarknessFallsPower(magicNumber, secondMagic));
    }

    @Override
    public void upp() {
        this.isInnate = true;
    }
}