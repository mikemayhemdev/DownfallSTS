package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.GremlinMod;

public class FuriousPower extends AbstractGremlinPower implements OnGainedBlockModifierPower{
    static final String POWER_ID = getID("Furious");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/furious.png"));

    public FuriousPower(AbstractCreature owner) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = -1;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = strings.DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public int onGainedBlockModifier(float blockAmount) {
        flash();
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        if(blockAmount >= 10){
            effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
                DamageInfo.createDamageMatrix(MathUtils.floor(blockAmount), true),
                DamageInfo.DamageType.THORNS, effect));
        return 0;
    }
}

