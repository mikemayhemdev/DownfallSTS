package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class GetLatest extends AbstractBronzeCard {

    public final static String ID = makeID("GetLatest");

    //stupid intellij stuff skill, self, uncommon

    public GetLatest() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("GetLatest.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard qCardGet = SpaghettiCode.getRandomEncode();
        qCardGet.freeToPlayOnce = true;
        atb(new MakeTempCardInHandAction(qCardGet, true));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}