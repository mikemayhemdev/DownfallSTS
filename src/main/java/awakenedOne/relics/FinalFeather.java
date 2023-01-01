package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.util.TextureLoader;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;
import static awakenedOne.util.Wiz.adp;
import static awakenedOne.util.Wiz.atb;

public class FinalFeather extends CustomRelic implements OnAwakenRelic {

    public static final String ID = AwakenedOneMod.makeID("FinalFeather");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BronzeCore.png")); //TODO: Images
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BronzeCore.png"));

    public FinalFeather() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.STARTER, LandingSound.MAGICAL);
    }

    boolean activated = false;

    @Override
    public void atBattleStart() {
        activated = false;
        grayscale = false;
    }

    @Override
    public void onAwaken(int amount) {
        if (!activated) {
            activated = true;
            grayscale = true;
            flash();
            atb(new DamageAllEnemiesAction(adp(), DamageInfo.createDamageMatrix(5, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void onVictory() {
        activated = false;
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
