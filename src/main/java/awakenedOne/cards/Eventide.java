package awakenedOne.cards;

import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.defect.NewRipAndTearAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class Eventide extends AbstractAwakenedCard {
    public final static String ID = makeID(Eventide.class.getSimpleName());

    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1
    public Eventide() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 8;
        loadJokeCardImage(this, makeBetaCardPath(Eventide.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2; ++i) {
            this.addToBot(new NewRipAndTearAction(this));
        }
        Wiz.atb(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true, false));

    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    public void upp() {
        upgradeDamage(2);
    }
}