package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.BurnAction;
import theHexaghost.actions.ExtinguishCurrentFlameAction;
import theHexaghost.patches.NoDiscardField;

public class SpectralSpark extends AbstractHexaCard {

    public final static String ID = makeID("SpectralSpark");

    //stupid intellij stuff SKILL, ENEMY, UNCOMMON

    private static final int MAGIC = 3;

    public SpectralSpark() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new BurnAction(m, magicNumber));
        AbstractCard c = this;
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (GhostflameHelper.activeGhostFlame.charged) {
                    addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            c.baseMagicNumber++;
                            if (c.upgraded) c.baseMagicNumber++;
                            c.applyPowers();
                            NoDiscardField.noDiscard.set(c, true);
                        }
                    });
                    addToTop(new ExtinguishCurrentFlameAction());
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}