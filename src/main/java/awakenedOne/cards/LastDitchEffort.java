package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;


public class LastDitchEffort extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(LastDitchEffort.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public LastDitchEffort() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 5;
        loadJokeCardImage(this, makeBetaCardPath(LastDitchEffort.class.getSimpleName() + ".png"));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.addToBot(new ApplyPowerAction(p, p, new ReboundPower(p), 1));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}