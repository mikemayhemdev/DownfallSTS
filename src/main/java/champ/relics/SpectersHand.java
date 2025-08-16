package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.cards.Defend;
import champ.cards.Strike;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.util.TextureLoader;
import expansioncontent.actions.EchoACardAction;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class SpectersHand extends CustomRelic {

    public static final String ID = ChampMod.makeID("SpectersHand");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpectresHand.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SpectresHand.png"));

    public SpectersHand() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }


    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (!newStance.ID.equals(NeutralStance.STANCE_ID) && !(oldStance.ID.equals(newStance.ID))) {
            flash();
            if (AbstractDungeon.cardRng.randomBoolean()) {
                AbstractCard c2 = new Strike();
                addToBot(new EchoACardAction(c2, true));
            } else {
                AbstractCard c2 = new Defend();
                addToBot(new EchoACardAction(c2, true));
            }

        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
