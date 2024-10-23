package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;
@Deprecated
@CardIgnore
public class UnknownRarePower extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownRarePower");

    public UnknownRarePower() {
        super(ID, CardType.POWER, CardRarity.SPECIAL);
        SneckoMod.loadJokeCardImage(this, "UnknownRarePower.png");
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownRarePowerReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerRareP;
    }
}
