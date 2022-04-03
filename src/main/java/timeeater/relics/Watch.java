package timeeater.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.TextureLoader;
import timeeater.TimeEaterMod;

import static timeeater.TimeEaterMod.makeRelicOutlinePath;
import static timeeater.TimeEaterMod.makeRelicPath;
import static timeeater.util.Wiz.adp;
import static timeeater.util.Wiz.applyToSelfTop;

public class Watch extends CustomRelic {

    public static final String ID = TimeEaterMod.makeID("Watch");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Watch.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Watch.png"));

    public Watch() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        counter = 0;
        grayscale = false;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (counter < 12) {
            counter++;
            if (counter == 12) {
                flash();
                applyToSelfTop(new StrengthPower(adp(), 2));
            }
        }
    }

    @Override
    public void onVictory() {
        counter = -1;
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
