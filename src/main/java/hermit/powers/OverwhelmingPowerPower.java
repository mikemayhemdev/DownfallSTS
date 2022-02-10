package hermit.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hermit.HermitMod;
import hermit.util.TextureLoader;

public class OverwhelmingPowerPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = HermitMod.makeID("OverwhelmingPowerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int power=0;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("hermitResources/images/powers/overwhelming_power_p.png");
    private static final Texture tex32 = TextureLoader.getTexture("hermitResources/images/powers/overwhelming_power.png");

    public OverwhelmingPowerPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.power = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.amount = amount;

        updateDescription();
    }

    /*
    @Override
    public void atStartOfTurnPostDraw() { // At the start of your turn
        amount=4;
        updateDescription();
    }
     */

    /*
    public void onUseCard(AbstractCard card, UseCardAction action) {

        this.flash();
        amount--;

        if (amount==0){
            amount=4;
            this.addToBot(new GainEnergyAction(power));
            this.addToBot(new DrawCardAction(power));
            this.addToBot(new LoseHPAction(AbstractDungeon.player,AbstractDungeon.player,2*power));
        }

        updateDescription();
    }
     */

    public void atEndOfTurn(boolean isPlayer)
    {
        if (EnergyPanel.totalCount == 0) {
            this.addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, amount));
        }
    }

    public void updateDescription() {
            description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
    }
    /*
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.power += stackAmount;
        this.updateDescription();
    }
     */

    @Override
    public AbstractPower makeCopy() {
        return new OverwhelmingPowerPower(owner, source, amount);
    }


}
