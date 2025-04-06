package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import sneckomod.SneckoMod;

public class LacerateDebuff extends AbstractPower implements CloneablePowerInterface, OnReceivePowerPower {
    public static final String POWER_ID = SneckoMod.makeID("LacerateDebuff");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/BurnPerTurn84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/BurnPerTurn32.png");


    public LacerateDebuff(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!this.owner.isPlayer) {
            this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == PowerType.DEBUFF && target == this.owner && !(power instanceof VenomDebuff) && !power.ID.equals("Shackled") && !target.hasPower("Artifact")) {
            int additionalAmount = 0;

            if (AbstractDungeon.player.hasPower(ToxicPersonalityPower.POWER_ID)) {
                AbstractPower toxicPersonality = AbstractDungeon.player.getPower(ToxicPersonalityPower.POWER_ID);
                additionalAmount += toxicPersonality.amount;
            }

            if (AbstractDungeon.player.hasPower(ToxicPersonalityPower.POWER_ID)) {
                AbstractPower toxicPersonality = AbstractDungeon.player.getPower(ToxicPersonalityPower.POWER_ID);
                additionalAmount += toxicPersonality.amount;
            }

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new VenomDebuff(target, this.amount), this.amount));
            //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new VenomDebuff(target, additionalAmount), additionalAmount));
        }
        return true;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LacerateDebuff(this.owner, this.amount);
    }
}
