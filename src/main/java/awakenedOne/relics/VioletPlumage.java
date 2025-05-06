package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;

import static awakenedOne.AwakenedOneMod.*;

public class VioletPlumage extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("VioletPlumage");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("VioletPlumage.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("VioletPlumage.png"));

    //Hey, you! Go to VioletPlumagePatch! This relic also uses code from Aspiration, the GitHub and relevant patch is linked there!

    public VioletPlumage() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
        AbstractCard q = new VoidCard();
        tips.add(new CardPowerTip(q));
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action) {
        if ((c.type == AbstractCard.CardType.POWER) && !this.grayscale);
        {
            flash();
            addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true));
            this.grayscale = true;
        }
    }

    @Override
    public void atBattleStart() {
        this.grayscale = false;
    }

    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
