package theHexaghost.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.util.OnChargeSubscriber;
import theHexaghost.util.TextureLoader;

public class ApocalypticArmorPower extends TwoAmountPower implements OnChargeSubscriber, NonStackablePower {

    public static final String POWER_ID = HexaMod.makeID("ApocalypticArmorPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ApocalypseArmor84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ApocalypseArmor32.png");

    public ApocalypticArmorPower(final int amount, final int amount2) {
        this.name = "Apocalypse Armor";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.amount2 = amount2;
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
                addToTop(new ApplyPowerAction(owner, owner, new IntangiblePlayerPower(owner, amount2), amount2));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (amount2 >= GhostflameHelper.hexaGhostFlames.size())
            description = "Whenever you Charge the Inferno Ghostflame and all #b" + amount + " Ghostflames are Charged, gain #b" + amount2 + "#yIntangible.";
        else
            description = "Whenver you Charge the Inferno Ghostflame and at least #b" + amount + " Ghostflames are Charged, gain #b" + amount2  + " #yIntangible.";
    }
}