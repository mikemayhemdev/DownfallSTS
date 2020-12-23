package automaton.relics;

import automaton.AutomatonMod;
import automaton.actions.AddToFuncAction;
import automaton.cards.Replicate;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class SilverBullet extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("SilverBullet");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SilverBullet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SilverBullet.png"));

    public SilverBullet() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new AddToFuncAction(new Replicate(), null));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
