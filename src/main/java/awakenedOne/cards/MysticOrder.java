package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.RageExhaustPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;

public class MysticOrder extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(MysticOrder.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public MysticOrder() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, ID+".png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new RageExhaustPower(p, this.magicNumber), this.magicNumber));
    }


    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}