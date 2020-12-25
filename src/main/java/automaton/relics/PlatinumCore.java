package automaton.relics;

import automaton.AutomatonMod;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class PlatinumCore extends CustomRelic implements OnCompileRelic {

    public static final String ID = AutomatonMod.makeID("PlatinumCore");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PlatinumCore.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PlatinumCore.png"));

    public PlatinumCore() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        counter = 3;
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        boolean activated = false;
        if (counter > 0) {
            for (int i = 0; i < 2; i++) {
                if (function.cost > 0) {
                    function.cost -= 1;
                    function.costForTurn -= 1;
                    activated = true;
                }
            }
        }
        if (forGameplay) {
            if (activated) flash();
            counter -= 1;
        }
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BronzeCore.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(BronzeCore.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BronzeCore.ID);
    }

    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new BronzeCore().name;
        StringBuilder sb = new StringBuilder();
        for (String word : name.split(" ")) {
            sb.append("[#").append(AutomatonMod.placeholderColor.toString()).append("]").append(word).append("[] ");
        }
        sb.setLength(sb.length() - 1);
        sb.append("[#").append(AutomatonMod.placeholderColor.toString()).append("]");

        return DESCRIPTIONS[0] + sb.toString() + DESCRIPTIONS[1];
    }

}
