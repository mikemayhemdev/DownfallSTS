package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Unknown3Cost extends AbstractUnknownCard {
    public final static String ID = makeID("Unknown3Cost");

    public Unknown3Cost() {
        super(ID, CardType.SKILL, CardRarity.RARE);
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
