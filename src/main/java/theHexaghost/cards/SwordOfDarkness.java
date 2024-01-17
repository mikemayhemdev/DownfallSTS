package theHexaghost.cards;

import automaton.actions.ScryBlockStatusAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;

public class SwordOfDarkness extends AbstractHexaCard {

    public final static String ID = makeID("SwordOfDarkness");

    //sword of night

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 2;

    public SwordOfDarkness() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 2;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "SwordOfDarkness.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        if (!GhostflameHelper.activeGhostFlame.charged) {
//            atb(new GainEnergyAction(1));
//        }
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        atb(new ScryBlockStatusAction(magicNumber, 0, true));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = !GhostflameHelper.activeGhostFlame.charged ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(UPG_DAMAGE);
        }
    }
}