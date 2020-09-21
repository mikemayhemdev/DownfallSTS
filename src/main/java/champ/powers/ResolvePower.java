package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class ResolvePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("ResolvePower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Again84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Again32.png");
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
        super.stackPower(stackAmount);
        equivStrCheck();
    }

    public void equivStrCheck() {
        int x = amount / 5;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            owner.getPower(StrengthPower.POWER_ID).amount += x - strengthGrantedByResolve;
        } else {
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, x), x)); //TODO: this also should be unable to be artifact cheesed later on
        }
        if (strengthGrantedByResolve != x) strengthGrantedByResolve = x;
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