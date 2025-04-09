package sneckomod.cards.unknowns;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

@NoCompendium
public class UnknownColorless extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownColorless");

    public UnknownColorless() {
        super(ID, CardType.SKILL, CardRarity.SPECIAL);
        SneckoMod.loadJokeCardImage(this, "UnknownColorless.png");
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.color == CardColor.COLORLESS;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownColorlessReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerColorless;
    }
}
