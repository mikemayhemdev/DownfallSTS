package champ.cards;

import champ.powers.CounterPower;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PreemptiveStrike extends AbstractChampCard {

    public final static String ID = makeID("PreemptiveStrike");

    public PreemptiveStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 0;
        isMultiDamage = true;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        if (p.hasPower(CounterPower.POWER_ID)) {
            addToTop(new ReducePowerAction(p, p, CounterPower.POWER_ID, p.getPower(CounterPower.POWER_ID).amount / 2));
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        if (AbstractDungeon.player.hasPower(CounterPower.POWER_ID)) {
            this.baseDamage += AbstractDungeon.player.getPower(CounterPower.POWER_ID).amount;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        if (AbstractDungeon.player.hasPower(CounterPower.POWER_ID)) {
            this.baseDamage += AbstractDungeon.player.getPower(CounterPower.POWER_ID).amount;
        }
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!(AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID))) {
            cantUseMessage = EXTENDED_DESCRIPTION[1];
            return false;
        }
        return super.canUse(p, m);
    }


    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}