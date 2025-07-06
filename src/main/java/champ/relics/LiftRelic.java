package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.TextureLoader;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class LiftRelic extends CustomRelic {
    public static final String ID = ChampMod.makeID("LiftRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("LiftRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("LiftRelic.png"));

    public LiftRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    //Strength at the start of combat
    private static final int STR = 1;

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + STR + this.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STR), STR));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
}