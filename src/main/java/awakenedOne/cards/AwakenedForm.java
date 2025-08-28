package awakenedOne.cards;

import awakenedOne.powers.ReverseRitualPower;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.RitualPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.*;

public class AwakenedForm extends AbstractAwakenedCard {
    public final static String ID = makeID(AwakenedForm.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , 2, 1

    public AwakenedForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
        tags.add(BaseModCardTags.FORM);
        loadJokeCardImage(this, makeBetaCardPath(AwakenedForm.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) awaken(true);
        applyToSelfTop(new CuriosityPower(p, magicNumber));
        applyToSelfTop(new RitualPower(p, secondMagic, true));
    }

    @Override
    public void upp() {
        upgradeSecondMagic(1);
    }
}