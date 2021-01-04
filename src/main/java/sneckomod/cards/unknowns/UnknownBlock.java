package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.Keyword;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UnknownBlock extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownBlock");
    public static boolean bruh = false;

    public UnknownBlock() {
        super(ID, CardType.SKILL, CardRarity.COMMON);
    }

    public boolean useCheck(AbstractCard card) {
        bruh = false;

        Keyword keywordString = CardCrawlGame.languagePack.getKeywordString("Game Dictionary").BLOCK;
        for (int i = 0; i < keywordString.NAMES.length; i++) {
            if (!bruh){
                String key = keywordString.NAMES[i];
                key = key.toLowerCase();
                String test = card.rawDescription.toLowerCase();
                bruh = (test.contains(" " + key + " ") || test.contains(" " + key + ",") || test.contains(" " + key + ".") || test.contains(" " + key + "。"));
            }
        }
        return bruh;
    }


    @Override
    public Predicate<AbstractCard> myNeeds() {
        return this::useCheck;
    }


    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownBlockReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerBlock;
    }
}
