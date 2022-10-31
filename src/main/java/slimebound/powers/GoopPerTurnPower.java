package slimebound.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import slimebound.SlimeboundMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.BurnPower;
import downfall.util.TextureLoader;

public class GoopPerTurnPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = "Slimebound:GoopPerTurnPower";

    private static final Texture tex84 = TextureLoader.getTexture(SlimeboundMod.getResourcePath("powers/PoisonThorns.png"));
    private static final Texture tex32 = TextureLoader.getTexture(SlimeboundMod.getResourcePath("powers/PoisonThornsS.png"));

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GoopPerTurnPower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (owner instanceof AbstractMonster) {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new SlimedPower(owner, AbstractDungeon.player, amount), amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GoopPerTurnPower(owner, amount);
    }
}