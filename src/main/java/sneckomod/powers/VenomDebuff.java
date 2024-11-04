package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;

public class VenomDebuff extends AbstractPower implements CloneablePowerInterface, HealthBarRenderPower {
    public static final String POWER_ID = SneckoMod.makeID("VenomDebuff");

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/MudShield84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/MudShield32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public VenomDebuff(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (getDebuffCount() >= 3) {
                this.addToBot(new DamageAction(owner, new DamageInfo(owner, amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.POISON));
            }
        }
    }

    @Override
    public Color getColor() {
        return (getDebuffCount() >= 3) ? Color.CYAN.cpy() : Color.CYAN.cpy(); // todo: make the color vanish if possible
    }

    private int getDebuffCount() {
        int debuffCount = 0;
        for (AbstractPower power : owner.powers) {
            if ((power.type == PowerType.DEBUFF))
            {
                debuffCount++;
            }
        }
        return debuffCount;
    }

    @Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VenomDebuff(owner, amount);
    }
}
