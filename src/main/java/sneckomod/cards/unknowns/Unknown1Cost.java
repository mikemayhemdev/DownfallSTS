package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Unknown1Cost extends AbstractUnknownCard {
    public final static String ID = makeID("Unknown1Cost");

    public Unknown1Cost() {
        super(ID, CardType.SKILL, CardRarity.COMMON);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.cost == 1;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknown1CostReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBanner1;
    }
}
