package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.getEnemies;

public class EyeOfTheOccult extends CustomRelic {

    //Eye of the Occult

    public static final String ID = AwakenedOneMod.makeID("EyeOfTheOccult");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("EyeOfTheOccult.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("EyeOfTheOccult.png"));

    //Yeah... you need to go look at the Spell cards for this one.
    public EyeOfTheOccult() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if ((card instanceof AbstractSpellCard) && (card.target == AbstractCard.CardTarget.ALL_ENEMY)) {
            if (getEnemies().size() > 1) {
                flash();
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
