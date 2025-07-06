package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Planeswalk extends AbstractAwakenedCard {
    public final static String ID = makeID(Planeswalk.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Planeswalk() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 3;
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(Planeswalk.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new GainEnergyAction(magicNumber));
        atb(new MakeTempCardInDiscardAction(new VoidCard(), 1));
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    public void upp() {
        upgradeBlock(3);
    }
}