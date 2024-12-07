package sneckomod.cards.unknowns;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

@NoCompendium
public class Unknown0Cost extends AbstractUnknownCard {
    public final static String ID = makeID("Unknown0Cost");

    public Unknown0Cost() {
        super(ID, CardType.SKILL, CardRarity.SPECIAL);
        SneckoMod.loadJokeCardImage(this, "Unknown0Cost.png");
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.cost == 0;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknown0CostReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBanner0;
    }
}
