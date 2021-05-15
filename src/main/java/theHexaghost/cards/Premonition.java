package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.PremonitionAction;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theHexaghost.HexaMod;
import theHexaghost.actions.SacrificeAction2;

public class Premonition extends AbstractHexaCard {

    public final static String ID = makeID("Premonition");

    public Premonition() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new PremonitionAction(upgraded));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}