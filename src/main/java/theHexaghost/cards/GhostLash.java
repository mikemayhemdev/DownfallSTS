package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostLash extends AbstractHexaCard {

    public final static String ID = makeID("GhostLash");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    public GhostLash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        if (hasEthereal())
            addToTop(new DamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    private boolean hasEthereal() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.isEthereal && c != this)
                return true;
        }
        return false;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = hasEthereal() ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}