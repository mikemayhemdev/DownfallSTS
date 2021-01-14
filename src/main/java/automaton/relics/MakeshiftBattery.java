package automaton.relics;

import automaton.AutomatonMod;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class MakeshiftBattery extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("MakeshiftBattery");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MakeshiftBattery.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MakeshiftBattery.png"));

    public MakeshiftBattery() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        addToBot(new MakeTempCardInDiscardAction(SneckoMod.getRandomStatus(), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
