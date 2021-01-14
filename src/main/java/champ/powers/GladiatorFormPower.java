package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.util.OnFinisherSubscriber;
import champ.util.OnTechniqueSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import downfall.util.TextureLoader;

public class GladiatorFormPower extends AbstractPower implements CloneablePowerInterface, OnTechniqueSubscriber, OnFinisherSubscriber {

    public static final String POWER_ID = ChampMod.makeID("GladiatorFormPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/FocusGladiator84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/FocusGladiator32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GladiatorFormPower(final int amount) {
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

    @Override
    public void onTechnique() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(owner, 1));
    }

    @Override
    public void onFinisher() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, amount), amount));
    }

    @Override
    public void updateDescription() {
        description = amount == 1 ? (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]) : DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GladiatorFormPower(amount);
    }
}