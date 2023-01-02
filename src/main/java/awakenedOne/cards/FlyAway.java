package awakenedOne.cards;

import awakenedOne.cardmods.FlyingModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class FlyAway extends AbstractAwakenedCard {
    public final static String ID = makeID(FlyAway.class.getSimpleName());
    // intellij stuff skill, self, common, , , 8, 3, , 

    public FlyAway() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
        CardModifierManager.addModifier(this, new FlyingModifier());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(3);
    }
}