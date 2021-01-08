package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        return c -> c.color == CardColor.RED;
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
