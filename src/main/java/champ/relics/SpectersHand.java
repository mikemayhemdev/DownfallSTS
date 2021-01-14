package champ.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import champ.cards.Defend;
import champ.cards.Strike;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.cardmods.ExhaustMod;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class SpectersHand extends CustomRelic {

    public static final String ID = ChampMod.makeID("SpectersHand");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SpectresHand.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SpectresHand.png"));

    public SpectersHand() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.hasTag(ChampMod.TECHNIQUE)){
            if (AbstractDungeon.player.stance instanceof NeutralStance){
                if (AbstractDungeon.cardRng.randomBoolean()){
                    AbstractCard c2 = new Strike();
                    CardModifierManager.addModifier(c2, new ExhaustMod());
                    c2.freeToPlayOnce = true;
                    addToBot(new MakeTempCardInHandAction(c2));
                } else {
                    AbstractCard c2 = new Defend();
                    CardModifierManager.addModifier(c2, new ExhaustMod());
                    c2.freeToPlayOnce = true;
                    addToBot(new MakeTempCardInHandAction(c2));

                }
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
