package slimebound.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import slimebound.SlimeboundMod;
import slimebound.actions.TrigggerSpecificSlimeAttackAction;
import slimebound.orbs.SpawnedSlime;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class CommandOnPlayPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = "Slimebound:CommandOnPlayPower";

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance32.png");

    public CommandOnPlayPower(final int amount) {
        this.name = "Rallying";
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
    public void onAfterCardPlayed(AbstractCard usedCard) {
        flash();
        AbstractOrb o = SlimeboundMod.getLeadingSlime();
        if (o != null) {
            addToBot(new TrigggerSpecificSlimeAttackAction(o));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {// 73
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ID));// 74
        }

    }// 76

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = "Whenever you play a card this turn, #yCommand.";
        else
            description = "Whenever you play a card this turn, #yCommand #b" + amount + " times.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new CommandOnPlayPower(amount);
    }
}