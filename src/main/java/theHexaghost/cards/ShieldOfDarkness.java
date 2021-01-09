package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;

public class ShieldOfDarkness extends AbstractHexaCard {

    public final static String ID = makeID("ShieldOfDarkness");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;

    public ShieldOfDarkness() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!GhostflameHelper.activeGhostFlame.charged) {
            atb(new GainEnergyAction(1));
        }
        blck();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = !GhostflameHelper.activeGhostFlame.charged ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}