package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.ExtinguishCurrentFlameAction;
import theHexaghost.patches.NoDiscardField;

public class SpectralSpark extends AbstractHexaCard {

    public final static String ID = makeID("SpectralSpark");

    //stupid intellij stuff SKILL, ENEMY, UNCOMMON

    private static final int MAGIC = 4;

    public SpectralSpark() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBurn = burn = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        burn(m, burn);
        AbstractCard c = this;
        if (GhostflameHelper.activeGhostFlame.charged) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    NoDiscardField.noDiscard.set(c, true);
                }
            });
            addToTop(new ExtinguishCurrentFlameAction());
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = GhostflameHelper.activeGhostFlame.charged ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(2);
        }
    }
}