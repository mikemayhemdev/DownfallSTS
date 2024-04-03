package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.patches.NoDiscardField;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.RetractAction;

public class SpectralSpark extends AbstractHexaCard {

    public final static String ID = makeID("SpectralSpark");

    private static final int MAGIC = 5;

    public SpectralSpark() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBurn = burn = MAGIC;
//        magicNumber = baseMagicNumber = 5;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "SpectralSpark.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        burn(m, burn);
        atb(new RetractAction());

        AbstractCard c = this;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (GhostflameHelper.activeGhostFlame.charged) {
                    addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            NoDiscardField.noDiscard.set(c, true);
                        }
                    });
                }
            }
        });
    }

    public void triggerOnGlowCheck() {
        this.glowColor = GhostflameHelper.activeGhostFlame.charged ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(3);
        }
    }
}