package automaton.relics;

import automaton.AutomatonMod;
import automaton.powers.CloningPower;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class CableSpool extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("CableSpool");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CableSpool.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CableSpool.png"));
    boolean activated = false;

    public CableSpool() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CloningPower(1), 1));

    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
