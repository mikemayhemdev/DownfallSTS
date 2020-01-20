package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UnleashSpirits extends AbstractHexaCard {

    public final static String ID = makeID("UnleashSpirits");

    //stupid intellij stuff ATTACK, ALL_ENEMY, RARE

    private static final int DAMAGE = 8;

    public UnleashSpirits() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageRandomEnemyAction(makeInfo(), AbstractGameAction.AttackEffect.FIRE));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int i = 0;
                for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                    if (c.isEthereal) {
                        i++;
                    }
                }
                for (int q = 0; q < i; q++) {
                    addToTop(new DamageRandomEnemyAction(makeInfo(), AbstractGameAction.AttackEffect.FIRE));
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}