package expansioncontent.powers;


import awakenedOne.powers.OnLoseEnergyPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;

import static awakenedOne.util.Wiz.applyToSelf;

public class DarknessFallsPower extends TwoAmountPower implements OnLoseEnergyPower {
    // intellij stuff buff
    public static final String NAME = DarknessFallsPower.class.getSimpleName();
    public static final String POWER_ID = "expansioncontent:DarknessFallsPower";

    private static final Texture tex84 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/StudyHexaghost84.png");
    private static final Texture tex32 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/StudyHexaghost32.png");

    public DarknessFallsPower(int amount, int amount2) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.amount2 = amount2;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void LoseEnergyAction(int gained) {
        flash();
        addToBot(new GainBlockAction(owner, gained*amount));
        applyToSelf(new StrengthPower(AbstractDungeon.player, gained*amount2));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }
}




