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
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.ghostflames.SearingGhostflame;

public class SpectralSpark extends AbstractHexaCard {

    public final static String ID = makeID("NaughtySpirit");

    private static final int MAGIC = 3;

    public SpectralSpark() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBurn = burn = MAGIC;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "NaughtySpirit.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        burn(m, burn);

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
                            NoDiscardField.noDiscard.set(c, true);
                            atb(new RetractAction());
                        }
                    });
                }
            }
        });
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;

        if (GhostflameHelper.activeGhostFlame instanceof CrushingGhostflame) {
            if(GhostflameHelper.activeGhostFlame.getActiveFlamesTriggerCount() == 1){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            }
        }

        if(GhostflameHelper.activeGhostFlame.charged) this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(2);
        }
    }
}