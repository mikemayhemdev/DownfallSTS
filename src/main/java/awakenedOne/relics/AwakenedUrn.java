package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class AwakenedUrn extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("AwakenedUrn");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("AwakenedUrn.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("AwakenedUrn.png"));

    public AwakenedUrn() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
        this.counter = 4;

    }


    @Override
    public void atBattleStart() {
        this.counter = 4;
    }

    @Override
    public void onVictory() {
        this.counter = 4;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && this.counter > 0) {
            this.flash();
            this.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 3));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.counter --;
        }

    }

}