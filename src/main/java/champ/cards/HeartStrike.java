package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import champ.stances.BerserkerStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class HeartStrike extends AbstractChampCard {

    public final static String ID = makeID("HeartStrike");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = -1;

    public HeartStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.FINISHER);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!(AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID) ||AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID))) {
            cantUseMessage = EXTENDED_DESCRIPTION[1];
            return false;
        }
        return super.canUse(p, m);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        if (p.hasPower(ResolvePower.POWER_ID)) {
            this.baseDamage = p.getPower(ResolvePower.POWER_ID).amount;
            this.calculateCardDamage(m);
            dmg(m, AbstractGameAction.AttackEffect.SMASH);
        }
        finisher();
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
            baseDamage = Math.min(AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount, AbstractDungeon.player.currentHealth - 1);
        }
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
            this.rawDescription = cardStrings.DESCRIPTION;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}