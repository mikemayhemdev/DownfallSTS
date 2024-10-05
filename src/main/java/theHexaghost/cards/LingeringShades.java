package theHexaghost.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.actions.AllEtherealToHandAction;
import theHexaghost.actions.RetractAction;

public class LingeringShades extends AbstractHexaCard{

    public final static String ID = makeID("LingeringShades");

    public LingeringShades() {
        super(ID, 1, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        baseBurn = burn = 12;
        HexaMod.loadJokeCardImage(this, "LingeringShades.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RetractAction());
        burn(m, burn);
        atb(new AllEtherealToHandAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(5);
        }
    }

}
