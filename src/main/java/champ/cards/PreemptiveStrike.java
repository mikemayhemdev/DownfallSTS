package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static champ.ChampMod.loadJokeCardImage;

public class PreemptiveStrike extends AbstractChampCard {

    public final static String ID = makeID("PreemptiveStrike");

    public PreemptiveStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 0;
        isMultiDamage = true;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        postInit();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        loadJokeCardImage(this, "PreemptiveStrike.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        if (p.hasPower(CounterPower.POWER_ID)) {
        if (!dcombo()) addToTop(new ReducePowerAction(p, p, CounterPower.POWER_ID, p.getPower(CounterPower.POWER_ID).amount / 2));
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

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}