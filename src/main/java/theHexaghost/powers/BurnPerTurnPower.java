package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;
import theHexaghost.actions.BurnAction;
import theHexaghost.util.TextureLoader;

public class BurnPerTurnPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("BurnPerTurnPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/BurnPerTurn84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/BurnPerTurn32.png");

    public BurnPerTurnPower(final AbstractCreature owner, final int amount) {
        this.name = "Melting Metal";
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
            addToBot(new BurnAction(((AbstractMonster) owner), amount));
        }
    }

    @Override
    public void updateDescription() {
        description = "Gains #b" + amount + " #yBurn at the start of its turn.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new BurnPerTurnPower(owner, amount);
    }
}