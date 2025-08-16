package downfall.powers.neowpowers;

import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;
import downfall.util.TextureLoader;

public class WheelOfDeath extends AbstractBossMechanicPower {
    public static final String POWER_ID = downfallMod.makeID("NeowWheelOfDeath");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowHermit284.png"));
    private static final Texture tex32 = TextureLoader.getTexture(downfallMod.assetPath("images/powers/NeowHermit232.png"));

    private final int STRENGTHGAIN = 2;
    private final int EVERYXCARDS = 13;

    public WheelOfDeath(final AbstractCreature owner, final int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.name = NAME;

        this.updateDescription();

        this.canGoNegative = false;
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        this.amount -= 1;
        if (amount == 0) {
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, STRENGTHGAIN), STRENGTHGAIN));
            addToBot(new ApplyPowerAction(owner, owner, new WheelOfDeath(owner, EVERYXCARDS)));
        }
        updateDescription();
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + EVERYXCARDS + DESCRIPTIONS[1] + STRENGTHGAIN + DESCRIPTIONS[2];
    }

}