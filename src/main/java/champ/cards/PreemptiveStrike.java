package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.cards.red.PerfectedStrike.countCards;

public class PreemptiveStrike extends AbstractChampCard {

    public final static String ID = makeID("ModFinisherStrike");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public PreemptiveStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 0;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        finisher();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        if (AbstractDungeon.player.hasPower(CounterPower.POWER_ID)){
            this.baseDamage += AbstractDungeon.player.getPower(CounterPower.POWER_ID).amount;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        if (AbstractDungeon.player.hasPower(CounterPower.POWER_ID)){
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
        if (!(p.stance instanceof DefensiveStance || p.stance instanceof UltimateStance)){
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