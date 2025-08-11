package awakenedOne.cards;

import awakenedOne.powers.EclipseEmbracePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class EclipseEmbrace extends AbstractAwakenedCard {
    public final static String ID = makeID(EclipseEmbrace.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public EclipseEmbrace() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(EclipseEmbrace.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new EclipseEmbracePower(1));
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}