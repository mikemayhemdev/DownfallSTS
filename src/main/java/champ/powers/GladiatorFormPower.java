package champ.powers;

import automaton.cards.Branch;
import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.cards.CrookedStrike;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.powers.NextTurnPowerPower;
import downfall.util.TextureLoader;
import hermit.util.Wiz;

public class GladiatorFormPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("GladiatorFormPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/FocusGladiator84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/FocusGladiator32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    int remainingVigor = 0;
    int remainingCounter = 0;

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
        if (!(card instanceof CrookedStrike || card instanceof Branch)) {
            if (card.type == AbstractCard.CardType.ATTACK && owner.hasPower(VigorPower.POWER_ID))
                addNextTurnPower(AbstractDungeon.player.getPower(VigorPower.POWER_ID));
        }
    }

    public void onSpecificTriggerBranch() {
        if (owner.hasPower(CounterPower.POWER_ID))
            addNextTurnPower(AbstractDungeon.player.getPower(CounterPower.POWER_ID));
    }


    @Override
    public void onSpecificTrigger() {
        if (owner.hasPower(CounterPower.POWER_ID))
            addNextTurnPower(AbstractDungeon.player.getPower(CounterPower.POWER_ID));
    }

    public void atTurnStart()
    {
        remainingVigor = 0;
        remainingCounter = 0;
    }

    public void addNextTurnPower(AbstractPower power)
    {
        int found = power.amount;
        int totaled = found / 3;
        int finalized = totaled * amount;
        boolean isVigor = (VigorPower.POWER_ID.equals(power.ID));
        boolean isCounter = (CounterPower.POWER_ID.equals(power.ID));

        // Add remainder.
        if (isVigor)
            remainingVigor += found % 3;
        if (isCounter)
            remainingCounter += found % 3;

        // If remainder is beyond threshold, add to total and subtract.
        if (remainingVigor >= 3) {
            remainingVigor -= 3;
            finalized += amount;
        }
        if (remainingCounter >= 3) {
            remainingCounter -= 3;
            finalized += amount;
        }

        // Only do anything if the amount is greater than 0.
        if (finalized > 0) {
            flash();

            if (isVigor)
            Wiz.atb(new ApplyPowerAction(owner, owner, new NextTurnPowerPower(owner, new VigorPower(owner,finalized)), finalized));
            if (isCounter)
            Wiz.atb(new ApplyPowerAction(owner, owner, new NextTurnPowerPower(owner, new CounterPower(finalized)), finalized));
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