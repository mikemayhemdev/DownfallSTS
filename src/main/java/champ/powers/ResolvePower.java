package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.relics.PowerArmor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

public class ResolvePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("ResolvePower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/PushThrough84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/PushThrough32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ResolvePower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    private int strengthGrantedByResolve = 0;

    /*
    @Override
    public int getHealthBarAmount() {
        return Math.min(owner.maxHealth, owner.currentHealth + amount);
    }

    @Override
    public Color getColor() {
        return Color.WHITE.cpy();
    }
    */

    @Override
    public void onRemove() {
        amount = 0;
        equivStrCheck();
    }

    @Override
    public void onInitialApplication() {
        equivStrCheck();
    }

    @Override
    public void stackPower(int stackAmount) {
        if (AbstractDungeon.player.hasRelic(PowerArmor.ID))
            if (amount + stackAmount > PowerArmor.CAP_RESOLVE_ETC)
                stackAmount = (PowerArmor.CAP_RESOLVE_ETC - amount);
        super.stackPower(stackAmount);
        equivStrCheck();
    }

    public void equivStrCheck() {
        if (adjustStrength) {
            int x = amount / 10;
            if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
                owner.getPower(StrengthPower.POWER_ID).amount += x - strengthGrantedByResolve;
                if (owner.getPower(StrengthPower.POWER_ID).amount == 0)
                    addToTop(new RemoveSpecificPowerAction(owner, owner, StrengthPower.POWER_ID));
            } else {
                if (x != 0)
                    addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, x), x));
            }
            if (strengthGrantedByResolve != x) strengthGrantedByResolve = x;
            if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID))
                AbstractDungeon.player.getPower(StrengthPower.POWER_ID).updateDescription();
        }
    }

    public boolean renderOnHpBar = true;
    public boolean adjustStrength = true;

    @Override
    public void onVictory() {
        if (owner.currentHealth > 0) {
            owner.heal(amount);
            renderOnHpBar = false;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ResolvePower(amount);
    }
}