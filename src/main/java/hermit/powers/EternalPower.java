package hermit.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.actions.unique.EnlightenmentAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Survivor;
import com.megacrit.cardcrawl.cards.red.InfernalBlade;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.HermitMod;
import hermit.actions.EternalAction;
import hermit.actions.EternalFormAction;
import hermit.patches.PreDrawPatch;
import hermit.util.TextureLoader;

import java.util.Iterator;

public class EternalPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = HermitMod.makeID("EternalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int total=4;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("hermitResources/images/powers/power_eternal_p.png");
    private static final Texture tex32 = TextureLoader.getTexture("hermitResources/images/powers/power_eternal.png");

    public EternalPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.total = amount;
        this.amount = amount;
        this.source = source;

        priority = 99;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);


        updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if(!PreDrawPatch.DRAWN_DURING_TURN && total>0){ // && card.canUse(AbstractDungeon.player,null)
            total--;

            if (card.costForTurn > 0) {
                card.costForTurn = Math.max(card.costForTurn - this.amount,0);
                card.isCostModifiedForTurn = true;
                card.superFlash(Color.GOLD.cpy());
            }
        }
    }

    /*
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (amount>0) {
            this.flash();
            updateDescription();
        }
    }
    */

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
         /*
        this.amount=total-AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        if (amount <= 0)
            description = DESCRIPTIONS[3];
        else if (amount == 1) {
            description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[2];
        }
        */
    }

    /*
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.total += stackAmount;
        this.updateDescription();
    }
    */

    @Override
    public AbstractPower makeCopy() {
        return new EternalPower(owner, source, amount);
    }
}
