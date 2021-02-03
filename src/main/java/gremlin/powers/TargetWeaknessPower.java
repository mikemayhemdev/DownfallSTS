package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import gremlin.GremlinMod;

public class TargetWeaknessPower extends AbstractGremlinPower implements DamageConditionalGivePower {
    static final String POWER_ID = getID("TargetWeakness");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/target_weakness.png"));

    public TargetWeaknessPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1]);
    }

    @Override
    public float atDamageConditionalGive(float damage, AbstractCard ca, AbstractMonster mo, DamageInfo.DamageType type) {
        if(mo != null && mo.hasPower(WeakPower.POWER_ID)){
            return damage + this.amount;
        }
        return damage;
    }

    @Override
    public float atFinalDamageConditionalGive(float damage, AbstractCard ca, AbstractMonster mo, DamageInfo.DamageType type) {
        return damage;
    }
}

