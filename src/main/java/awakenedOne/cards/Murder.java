package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class Murder extends AbstractAwakenedCard {
    public final static String ID = makeID(Murder.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public Murder() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = 4;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(Murder.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i=0; i<this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
            this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    //        int i;
//        if (Settings.FAST_MODE) {
//            this.addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));
//
//            for(i = 0; i < 5; ++i) {
//                this.addToTop(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
//            }
//        } else {
//            this.addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));
//
//            for(i = 0; i < 5; ++i) {
//                this.addToTop(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
//            }
//        }
//
//        if (m != null) {
//            this.addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
//        }

//    public void applyPowers() {
//        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
//        if (strength != null) {
//            strength.amount *= this.magicNumber;
//        }
//
//        super.applyPowers();
//        if (strength != null) {
//            strength.amount /= this.magicNumber;
//        }
//
//    }
//
//    public void calculateCardDamage(AbstractMonster mo) {
//        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
//        if (strength != null) {
//            strength.amount *= this.magicNumber;
//        }
//
//        super.calculateCardDamage(mo);
//        if (strength != null) {
//            strength.amount /= this.magicNumber;
//        }
//
//    }

    public void upp() {
        this.exhaust = false;
    }
}