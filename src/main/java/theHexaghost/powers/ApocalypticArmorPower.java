package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.util.OnChargeSubscriber;
import theHexaghost.util.TextureLoader;

public class ApocalypticArmorPower extends AbstractPower implements CloneablePowerInterface, OnChargeSubscriber {

    public static final String POWER_ID = HexaMod.makeID("ApocalypticArmorPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power32.png");

    public ApocalypticArmorPower(final int amount) {
        this.name = "Apocalyptic Armor";
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
    public void onCharge(AbstractGhostflame g) {
        if (g instanceof InfernoGhostflame) {
            int i = 0;
            for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                if (gf.charged) i++;
            }
            if (i >= amount) {
                addToBot(new ApplyPowerAction(owner, owner, new IntangiblePlayerPower(owner, 1), 1));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (amount >= 6)
            description = "Whenever you Charge the Inferno Ghostflame and all #b" + amount + " Ghostflames are Charged, gain #b1 #yIntangible.";
        else
            description = "Whenver you Charge the Inferno Ghostflame and at least #b" + amount + " Ghostflames are Charged, gain #b1 Intangible.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new ApocalypticArmorPower(amount);
    }
}