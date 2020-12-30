package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BurnOut extends AbstractBronzeCard {

    public final static String ID = makeID("BurnOut");

    //stupid intellij stuff attack, all_enemy, rare

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    public BurnOut() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        cardsToPreview = new Burn();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int statusCount = 0;

        for (int i = 0; i < p.drawPile.size(); ) {
            AbstractCard c = p.drawPile.group.get(i);
            if (c.type == CardType.STATUS) {
                statusCount++;
                p.drawPile.removeCard(c);
                p.limbo.addToTop(c);
                c.targetDrawScale = 0.5F;
                c.setAngle(0, true);
                c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                atb(new ExhaustSpecificCardAction(c, p.limbo));
                atb(new WaitAction(0.1F));
            } else {
                i++;
            }
        }

        for (int i = 0; i < p.discardPile.size(); ) {
            AbstractCard c = p.discardPile.group.get(i);
            if (c.type == CardType.STATUS) {
                statusCount++;
                p.discardPile.removeCard(c);
                p.limbo.addToTop(c);
                c.targetDrawScale = 0.5F;
                c.setAngle(0, true);
                c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                atb(new ExhaustSpecificCardAction(c, p.limbo));
                atb(new WaitAction(0.1F));
            } else {
                i++;
            }
        }

        for (int i = 0; i < p.hand.size(); ) {
            AbstractCard c = p.hand.group.get(i);
            if (c.type == CardType.STATUS) {
                statusCount++;
                p.hand.removeCard(c);
                p.limbo.addToTop(c);
                c.targetDrawScale = 0.5F;
                c.setAngle(0, true);
                c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                atb(new ExhaustSpecificCardAction(c, p.limbo));
                atb(new WaitAction(0.1F));
            } else {
                i++;
            }
        }

        if (statusCount > 0) {
            for (int i = 0; i < statusCount; i++) {
                atb(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
            }
        }

        shuffleIn(new Burn(), 3);
    }


    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player != null) {
            this.rawDescription = cardStrings.DESCRIPTION;

            int statusCount = 0;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.type == CardType.STATUS) {
                    statusCount++;
                }
            }

            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c.type == CardType.STATUS) {
                    statusCount++;
                }
            }

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type == CardType.STATUS) {
                    statusCount++;
                }
            }

            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + statusCount;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];

            this.initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}