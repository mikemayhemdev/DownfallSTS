package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.HemorrhageDebuff;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static awakenedOne.util.Wiz.applyToSelf;
@Deprecated
@CardIgnore
public class WallOfBlood extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(WallOfBlood.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public WallOfBlood() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 12;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new HemorrhageDebuff(this.magicNumber));
    }


    @Override
    public void upp() {
        upgradeBlock(3);
    }
}