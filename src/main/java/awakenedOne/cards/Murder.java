package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class Murder extends AbstractAwakenedCard {
    public final static String ID = makeID(Murder.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public Murder() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 4;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));

        int i;
        if (Settings.FAST_MODE) {
            this.addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));

            for(i = 0; i < 5; ++i) {
                this.addToTop(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        } else {
            this.addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));

            for(i = 0; i < 5; ++i) {
                this.addToTop(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        }

        if (m != null) {
            this.addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

    }

    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if (strength != null) {
            strength.amount *= this.magicNumber;
        }

        super.applyPowers();
        if (strength != null) {
            strength.amount /= this.magicNumber;
        }

    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if (strength != null) {
            strength.amount *= this.magicNumber;
        }

        super.calculateCardDamage(mo);
        if (strength != null) {
            strength.amount /= this.magicNumber;
        }

    }

    public void upp() {
        this.exhaust = false;
    }
}