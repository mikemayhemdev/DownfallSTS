package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.HermitMod;
import hermit.cards.MementoCard;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class Memento extends CustomRelic {
    public static final String ID = HermitMod.makeID("Momento");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("memento.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("memento.png"));

    private final int TURNS = 0;

    public Memento() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToTop(new MakeTempCardInHandAction(new MementoCard(), 1, false));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
