package awakenedOne.cards;

import awakenedOne.powers.DarkIncantationRitualPower;
import awakenedOne.powers.ReverseRitualPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class CawCaw extends AbstractAwakenedCard {
    public final static String ID = makeID(CawCaw.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , 2, 1

    public CawCaw() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelfTop(new CuriosityPower(p, secondMagic));
        applyToSelf(new ReverseRitualPower(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}