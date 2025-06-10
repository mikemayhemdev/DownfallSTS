package awakenedOne.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import java.util.Iterator;

public class TheTower extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(TheTower.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public TheTower() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = 6;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        cardsToPreview = new VoidCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var2.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var2.next();
            if (!mo.isDeadOrEscaped()) {
                this.addToBot(new VFXAction(new ExplosionSmallEffect(mo.hb.cX, mo.hb.cY), 0.1F));
            }
        }

       this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
//
//        if (ConjureAction.refreshedthisturn == true) {
//            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
//                if ((!monster.isDead) && (!monster.isDying) && !monster.halfDead) {
//                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(monster.hb.cX, monster.hb.cY), 0.1F));
//                }
//            }
//            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
//            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
//                if ((!monster.isDead) && (!monster.isDying) && !monster.halfDead) {
//                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(monster.hb.cX, monster.hb.cY), 0.1F));
//                }
//            }
//            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
//        }

    }


    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }


    public static int countCards() {
        int statusCount = 0;
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof AbstractSpellCard || c instanceof VoidCard || c instanceof IntoTheVoid) {
                statusCount++;
            }
        }
        return statusCount;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }


//    public void triggerOnGlowCheck() {
//        this.glowColor = ConjureAction.refreshedthisturn ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
//    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}