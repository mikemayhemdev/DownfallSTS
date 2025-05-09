package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.spells.ESPSpell;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;

import static awakenedOne.AwakenedOneMod.*;

public class ZenerDeck extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("ZenerDeck");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("ZenerDeck.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("ZenerDeck.png"));

    public ZenerDeck() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.tips.add(new CardPowerTip(new ESPSpell()));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
