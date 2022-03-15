package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.powers.NextTurnPowerPower;
import downfall.util.TextureLoader;

public class GladiatorFormPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("GladiatorFormPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/FocusGladiator84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/FocusGladiator32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    int remainingVigor = 3;
    int remainingCounter = 3;

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
        priority = -99; // This might need some fiddling. It's either this or 99 haha
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && owner.hasPower(VigorPower.POWER_ID)) {
            this.flash();
            int found = AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;
            int totaled = found / 3;
            int remainder = found % 3;
            for (int i = 0; i < totaled; i++) {
                addToBot(new ApplyPowerAction(owner, owner, new NextTurnPowerPower(owner, new VigorPower(owner, amount)), amount));
            }
            remainingVigor -= remainder;
            if (remainingVigor <= 0) {
                remainingVigor = 3;
                addToBot(new ApplyPowerAction(owner, owner, new NextTurnPowerPower(owner, new VigorPower(owner, amount)), amount));
            }
        }
    }

    @Override
    public void onSpecificTrigger() {
        if (owner.hasPower(CounterPower.POWER_ID)) {
            this.flash();
            int found = AbstractDungeon.player.getPower(CounterPower.POWER_ID).amount;
            int totaled = found / 3;
            int remainder = found % 3;
            for (int i = 0; i < totaled; i++) {
                addToBot(new ApplyPowerAction(owner, owner, new NextTurnPowerPower(owner, new CounterPower(amount)), amount));
            }
            remainingCounter -= remainder;
            if (remainingCounter <= 0) {
                remainingCounter = 3;
                addToBot(new ApplyPowerAction(owner, owner, new NextTurnPowerPower(owner, new CounterPower(amount)), amount));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GladiatorFormPower(amount);
    }
}