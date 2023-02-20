package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.*;
import hermit.HermitMod;
import hermit.powers.Concentration;
import hermit.powers.RyeStalkPower;
import hermit.util.TextureLoader;

import java.util.Iterator;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class BloodyTooth extends CustomRelic {

    // ID, images, text.
    public static final String ID = HermitMod.makeID("BloodyTooth");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bloody_tooth.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bloody_tooth_outline.png"));

    public BloodyTooth() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public void onVictory(){
        if ((AbstractDungeon.getCurrRoom()).eliteTrigger)
        {
            this.flash();
            this.counter++;
        }
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    public void atBattleStart() {
        if (this.counter > 0) {
            this.flash();
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.counter), this.counter));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
