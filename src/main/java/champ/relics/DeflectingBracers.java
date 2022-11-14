package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.powers.CounterPower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;
import static collector.util.Wiz.atb;

public class DeflectingBracers extends CustomRelic {

    public static final String ID = ChampMod.makeID("DeflectingBracers");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DeflectingBracers.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DeflectingBracers.png"));

    public DeflectingBracers() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        if (!AbstractDungeon.player.hasPower(CounterPower.POWER_ID)){
            atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CounterPower(1), 1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
