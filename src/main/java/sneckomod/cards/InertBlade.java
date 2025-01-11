package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

public class InertBlade extends AbstractSneckoCard {

    public static final String ID = makeID("InertBlade");

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 3;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;
    private static final int ENERGY_GAIN = 1;
    private static final int UPG_ENERGY = 1;
    private static final int COST = 0;

    private int energypayout;

    public InertBlade() {
        super(ID, COST, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        energypayout = ENERGY_GAIN;
        SneckoMod.loadJokeCardImage(this, "InertBlade.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        // Check the card's costForTurn and apply conditional effects
        if (this.costForTurn >= 1) {
            addToBot(new DrawCardAction(p, magicNumber));
        }
        if (this.costForTurn >= 2) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        }
        if (this.costForTurn >= 3) {
            addToBot(new GainEnergyAction(energypayout));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPG_MAGIC);
            energypayout += UPG_ENERGY;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
