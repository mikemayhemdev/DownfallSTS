package awakenedOne.powers;


import basemod.interfaces.OnPowersModifiedSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;

import static awakenedOne.powers.AbstractAwakenedPower.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
import static com.megacrit.cardcrawl.ui.panels.EnergyPanel.getCurrentEnergy;


public class DarknessFallsPower extends TwoAmountPower implements OnLoseEnergyPower {
    // intellij stuff buff
    public static final String NAME = DarknessFallsPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

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

        this.updateDescription();
    }

    @Override
    public void LoseEnergyAction(int gained) {
            flash();
            addToBot(new GainBlockAction(owner, amount));
            applyToSelf(new StrengthPower(AbstractDungeon.player, amount2));
    }

    @Override
    //todo: ACTUALLY PUT THIS INTO EXPANSIONCONTENT POWERSTRINGS IMPORTANT
    public void updateDescription() {
        this.description = "Whenever you lose [E] , gain #b" + amount + " #yBlock and #b" + amount2 + " #yStrength.";
    }
}




