package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class ShreddedDoll extends CustomRelic implements OnAwakenRelic {

    public static final String ID = AwakenedOneMod.makeID("ShreddedDoll");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("ShreddedDoll.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("ShreddedDoll.png"));
    private boolean activatedthiscombat = false;

    public ShreddedDoll() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);

        //Shredded Doll

    }



    @Override
    public void atBattleStart() {
        this.beginLongPulse();
        activatedthiscombat = false;
    }

    @Override
    public void onVictory() {
        stopPulse();
    }

    //Check AwakenButton.java. I'm just using this override for convenience.
    @Override
    public void onAwaken(int amount) {
        if (amount == 5 && activatedthiscombat == false) {
            stopPulse();
            activatedthiscombat = true;
            flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            applyToSelf(new RitualPower(AbstractDungeon.player, 1, true));
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        atb(new ConjureAction(false));
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(RippedDoll.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(RippedDoll.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(RippedDoll.ID);
    }

    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new RippedDoll().name;
        StringBuilder sb = new StringBuilder();
        if(Settings.language== Settings.GameLanguage.ZHS|| Settings.language== Settings.GameLanguage.ZHT){
            sb.append("[#").append(AwakenedOneMod.placeholderColor.toString()).append("]").append(name).append("[]");

        }else {
            for (String word : name.split(" ")) {
                sb.append("[#").append(AwakenedOneMod.placeholderColor.toString()).append("]").append(word).append("[] ");
            }
            sb.setLength(sb.length() - 1);
            sb.append("[#").append(AwakenedOneMod.placeholderColor.toString()).append("]");
        }

        return DESCRIPTIONS[0] + sb + DESCRIPTIONS[1];
    }

}
