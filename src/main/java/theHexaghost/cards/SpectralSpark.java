package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.ExtinguishCurrentFlameAction;
import downfall.patches.NoDiscardField;
import theHexaghost.powers.EnhancePower;

public class SpectralSpark extends AbstractHexaCard {

    public final static String ID = makeID("SpectralSpark");

    //stupid intellij stuff SKILL, ENEMY, UNCOMMON

    private static final int MAGIC = 1;

    public SpectralSpark() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    //    baseBurn = burn = MAGIC;
        magicNumber = baseMagicNumber =1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // burn(m, burn);
      //  AbstractCard c = this;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (GhostflameHelper.activeGhostFlame.charged) {
                    addToBot(new ApplyPowerAction(p, p, new EnhancePower(magicNumber)));
                    //addToTop(new ExtinguishCurrentFlameAction());
                    addToBot(new ExtinguishCurrentFlameAction());
                }
            }
        });
    }

    public void triggerOnGlowCheck() {
        this.glowColor = GhostflameHelper.activeGhostFlame.charged ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}