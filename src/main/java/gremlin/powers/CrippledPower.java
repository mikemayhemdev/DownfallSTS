package gremlin.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gremlin.GremlinMod;

public class CrippledPower extends AbstractGremlinPower implements HealthBarRenderPower {
    public static final String POWER_ID = getID("Crippled");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/crippled.png"));

    private AbstractCreature source;

    public CrippledPower(AbstractCreature owner, AbstractCreature source) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;

        this.img = IMG;
        this.type = PowerType.DEBUFF;
        this.amount = -1;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = strings.DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean player) {
        if (!player) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.flashWithoutSound();
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.LoseHPAction(this.owner, this.source, getHealthBarAmount(), AbstractGameAction.AttackEffect.POISON));
            }
        }
    }

    @Override
    public int getHealthBarAmount() {
        if (owner.hasPower(WeakPower.POWER_ID)) {
            return owner.getPower(WeakPower.POWER_ID).amount;
        }
        return 0;
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }
}

