package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class UnleashSpirits extends AbstractHexaCard {

    public final static String ID = makeID("UnleashSpirits");

    //stupid intellij stuff ATTACK, ALL_ENEMY, RARE

    private static final int DAMAGE = 6;

    public UnleashSpirits() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 0;
        exhaust = true;
        HexaMod.loadJokeCardImage(this, "UnleashSpirits.png");
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

    public void applyPowers() {
        baseMagicNumber = magicNumber = countCards()+1;
        super.applyPowers();
        this.rawDescription = DESCRIPTION+UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public int countCards(){
        int count=0;
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.isEthereal) {
                count++;
            }
        }
        return count;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }
}