package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import hermit.powers.Bruise;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class ThornWhip extends AbstractCollectorCard {
    public final static String ID = makeID(ThornWhip.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 11, 1, , , ,

    public ThornWhip() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = 6;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot(new DamageAction(monster, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                int unblockedDamage = Math.max(damage - monster.currentBlock, 0); // Changed `m` to `monster`
                if (unblockedDamage > 0) {
                    applyToEnemy(monster, new DoomPower(monster, unblockedDamage));
                }
            }
        }
    }


    public void upp() {
        upgradeDamage(2);
    }
}
