package automaton.relics;

import automaton.AutomatonMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class PlatinumCore extends CustomRelic implements OnCompileRelic {

    public static final String ID = AutomatonMod.makeID("PlatinumCore");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PlatinumCore.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BronzeCore.png"));


    //How many free functions does this relic grant?
    private static final int FUNCTIONS = 3;

    public PlatinumCore() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        counter = 0;
    }


    @Override
    public void atBattleStart() {
        counter = FUNCTIONS;
        grayscale = false;
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (counter != 0) {
            if (function.cost > 0) {
                function.freeToPlayOnce = true;
                if (forGameplay) {
                    counter -= 1;
                    flash();
                    if (counter == 0) grayscale = true;
                }
            }
        }
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
        if(Settings.language== Settings.GameLanguage.ZHS|| Settings.language== Settings.GameLanguage.ZHT){
            sb.append("[#").append(AutomatonMod.placeholderColor.toString()).append("]").append(name).append("[]");

        }else {
            for (String word : name.split(" ")) {
                sb.append("[#").append(AutomatonMod.placeholderColor.toString()).append("]").append(word).append("[] ");
            }
            sb.setLength(sb.length() - 1);
            sb.append("[#").append(AutomatonMod.placeholderColor.toString()).append("]");
        }

        return DESCRIPTIONS[0] + sb + DESCRIPTIONS[1] + FUNCTIONS + DESCRIPTIONS[2];
    }

}
