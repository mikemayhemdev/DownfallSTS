package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import theHexaghost.GhostflameHelper;
import theHexaghost.powers.BurnPower;

@CardIgnore
public class DynamicBlow extends AbstractHexaCard {

    public final static String ID = makeID("DynamicBlow");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 15;
    private static final int UPG_MAGIC = 5;

    public DynamicBlow() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBurn = burn = MAGIC;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (GhostflameHelper.activeGhostFlame.charged) {
                    addToTop(new ApplyPowerAction(m, p, new BurnPower(m, burn), burn));
                } else {
                    addToTop(new DamageAction(m, makeInfo(), AttackEffect.BLUNT_HEAVY));
                }
            }
        });
    }

    public void triggerOnGlowCheck() {
        this.glowColor = GhostflameHelper.activeGhostFlame.charged ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeBurn(UPG_MAGIC);
        }
    }
}