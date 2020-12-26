package automaton.relics;

import automaton.AutomatonMod;
import automaton.actions.AddToFuncAction;
import automaton.cards.SpaghettiCode;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.RetainCardMod;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class VexRelic extends CustomRelic implements OnCompileRelic {

    public static final String ID = AutomatonMod.makeID("VexRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Barbells.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Barbells.png"));

    public VexRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    boolean activated = false;

    @Override
    public void atBattleStart() {
        activated = false;
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        for (int i = 0; i < 3; i++) {
            addToBot(new AddToFuncAction(SpaghettiCode.getRandomEncode(), null));
        }
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (!activated) {
            CardModifierManager.addModifier(function, new RetainCardMod());
            if (forGameplay) {
                activated = true;
                flash();
                grayscale = true;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
