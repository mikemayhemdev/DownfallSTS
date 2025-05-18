package awakenedOne.cards;

import awakenedOne.powers.MazalethFormPower;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class MazalethForm extends AbstractAwakenedCard {
    public final static String ID = makeID(MazalethForm.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public MazalethForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
        tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MazalethFormPower(1));
    }

    @Override
    public void upp() {
        isEthereal = false;
    }
}