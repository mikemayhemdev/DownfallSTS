package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.shuffleIn;

public class Planeswalk extends AbstractAwakenedCard {
    public final static String ID = makeID(Planeswalk.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Planeswalk() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(Planeswalk.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(magicNumber));
        shuffleIn(new VoidCard());
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}