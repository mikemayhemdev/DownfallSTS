package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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
        exhaust = true;
        tags.add(ChampMod.FINISHER);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
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
    public void triggerOnGlowCheck() {
        glowColor = (upgraded && bcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
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