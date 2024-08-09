package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;

public class GhostflameInferno extends AbstractHexaCard {

    public final static String ID = makeID("InstantInferno");

    //Instant Inferno

    public GhostflameInferno() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        selfRetain = true;
        exhaust = true;
        baseBurn = burn = 12;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "GhostflameInferno.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new FireballEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.4F));
        burn(m, burn);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractGhostflame gf = GhostflameHelper.hexaGhostFlames.get(5);
                if (gf instanceof InfernoGhostflame) {
                    gf.forceCharge();
                }
                GhostflameHelper.activeGhostFlame = GhostflameHelper.hexaGhostFlames.get(5);
            }
        });
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;

        if (GhostflameHelper.activeGhostFlame instanceof InfernoGhostflame) {
            if(GhostflameHelper.activeGhostFlame.getActiveFlamesTriggerCount() + this.costForTurn >= 3){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            }
        }

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(9);
        }
    }
}