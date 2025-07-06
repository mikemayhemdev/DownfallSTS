package awakenedOne.cards;

import awakenedOne.powers.DarkIncantationRitualPower;
import awakenedOne.ui.OrbitingSpells;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class MazalethForm extends AbstractAwakenedCard {
    public final static String ID = makeID(MazalethForm.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public MazalethForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(BaseModCardTags.FORM);
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath(MazalethForm.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            awakenedthiscombat = true;
            OrbitingSpells.upgradeall();
        }
        applyToSelf(new DarkIncantationRitualPower(magicNumber));
    }

    public void upp() {
    }

}