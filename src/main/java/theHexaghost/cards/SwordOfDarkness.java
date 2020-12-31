package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;

public class SwordOfDarkness extends AbstractHexaCard {

    public final static String ID = makeID("SwordOfDarkness");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 4;

    public SwordOfDarkness() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!GhostflameHelper.activeGhostFlame.charged) {
            atb(new GainEnergyAction(1));
        }
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = !GhostflameHelper.activeGhostFlame.charged ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}