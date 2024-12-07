package sneckomod.cards.unknowns;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

@NoCompendium
public class Unknown3Cost extends AbstractUnknownCard {
    public final static String ID = makeID("Unknown3Cost");

    public Unknown3Cost() {
        super(ID, CardType.SKILL, CardRarity.SPECIAL);
        SneckoMod.loadJokeCardImage(this, "Unknown3Cost.png");
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.cost == 3;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknown3CostReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBanner3;
    }
}
