package sneckomod.cards.unknowns;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

@NoCompendium
public class UnknownX extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownX");

    public UnknownX() {
        super(ID, CardType.SKILL, CardRarity.SPECIAL);
        SneckoMod.loadJokeCardImage(this, "UnknownX.png");
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.cost == -1;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownXCostReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerX;
    }
}