package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.actions.RetractAction;

public class BacktrackDefend extends AbstractHexaCard{
    public final static String ID = makeID("BacktrackDefend");
    public BacktrackDefend() {
        super(ID, 1, CardType.SKILL, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
        baseBlock = 14;
//        HexaMod.loadJokeCardImage(this, "BacktrackSmack.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new RetractAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);
//            upgradeDamage(2);
        }
    }
}
