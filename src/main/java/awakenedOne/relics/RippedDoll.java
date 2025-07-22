package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.actions.SetUpNextSpellAction;
import awakenedOne.cards.tokens.spells.BurningStudy;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class RippedDoll extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("RippedDoll");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("RippedDoll.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("RippedDoll.png"));

    //Ripped Doll

    public RippedDoll() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        AbstractCard q = new BurningStudy();
        //q.upgrade();
        tips.add(new CardPowerTip(q));
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //this.addToTop(new MakeTempCardInHandAction(new Ceremony(), 1, false));
        atb(new ConjureAction(false, false, true, new BurningStudy()));
        addToBot(new SetUpNextSpellAction());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
