package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.RetractAction;
import theHexaghost.patches.NoDiscardField;

public class SoulSteal extends AbstractHexaCard {

    public final static String ID = makeID("SoulSteal");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    public SoulSteal() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.LIGHTNING);
        blck();
        if (GhostflameHelper.getPreviousGhostFlame().charged) {
            atb(new RetractAction());
            NoDiscardField.noDiscard.set(this, true);
            exhaust = false;
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    exhaust = true;
                    isDone = true;
                }
            });
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = GhostflameHelper.getPreviousGhostFlame().charged ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeBlock(UPG_BLOCK);
        }
    }
}