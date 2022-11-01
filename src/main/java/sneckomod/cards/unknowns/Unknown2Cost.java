package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Unknown2Cost extends AbstractUnknownCard {
    public final static String ID = makeID("Unknown2Cost");

    public Unknown2Cost() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.cost == 2;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknown2CostReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBanner2;
    }
}
