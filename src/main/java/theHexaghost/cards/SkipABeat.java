package theHexaghost.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.actions.ExtinguishCurrentFlameAction;
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.powers.BurnPower;

public class SkipABeat extends AbstractHexaCard {

    public final static String ID = makeID("Kindle");

    public SkipABeat() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "SkipABeat.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded){
            if (!GhostflameHelper.activeGhostFlame.charged)  atb(new ChargeCurrentFlameAction());
        } else {
            atb(new ExtinguishCurrentFlameAction());
            atb(new ChargeCurrentFlameAction());
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;

        if (GhostflameHelper.activeGhostFlame instanceof CrushingGhostflame) {
            if(GhostflameHelper.activeGhostFlame.getActiveFlamesTriggerCount() == 1){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            }
        }

        if (GhostflameHelper.activeGhostFlame instanceof InfernoGhostflame) {
            if(GhostflameHelper.activeGhostFlame.getActiveFlamesTriggerCount() + this.costForTurn >= 3){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            }
        }

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}