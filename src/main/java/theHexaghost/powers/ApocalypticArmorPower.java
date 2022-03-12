package theHexaghost.powers;

import automaton.powers.InfiniteBeamsPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.util.OnChargeSubscriber;
import downfall.util.TextureLoader;

public class ApocalypticArmorPower extends TwoAmountPower implements OnChargeSubscriber, NonStackablePower {

    public static final String POWER_ID = HexaMod.makeID("ApocalypticArmorPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ApocalypseArmor84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ApocalypseArmor32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ApocalypticArmorPower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = 1;
        this.amount2 = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        if (power instanceof ApocalypticArmorPower) {
            return ((ApocalypticArmorPower) power).amount2 == this.amount2;
        }
        return false;
    }

    @Override
    public void onCharge(AbstractGhostflame g) {
        for (AbstractPower p: owner.powers) {
            if (p instanceof ApocalypticArmorPower && ((ApocalypticArmorPower) p).amount2 < this.amount2) {
                return;
            }
        }
        if (g instanceof InfernoGhostflame) {
            int i = 0;
            for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                if (gf.charged) i++;
            }
            if (i >= amount) {
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TimeStopPower(AbstractDungeon.player, 1), 1));
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(owner, owner, this, 1));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (amount >= GhostflameHelper.hexaGhostFlames.size())
            description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1];
        else
            description = DESCRIPTIONS[2] + amount2 + DESCRIPTIONS[1];
    }
}