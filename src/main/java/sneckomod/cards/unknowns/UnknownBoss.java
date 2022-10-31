package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import expansioncontent.expansionContentMod;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UnknownBoss extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownBoss");

    public UnknownBoss() {
        super(ID, CardType.SKILL, CardRarity.RARE);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity != CardRarity.SPECIAL && c.hasTag(expansionContentMod.STUDY);
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownBossReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerBoss;
    }
}
