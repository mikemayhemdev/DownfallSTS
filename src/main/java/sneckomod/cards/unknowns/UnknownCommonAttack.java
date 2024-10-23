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
public class UnknownCommonAttack extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownCommonAttack");

    public UnknownCommonAttack() {
        super(ID, CardType.ATTACK, CardRarity.SPECIAL);
        SneckoMod.loadJokeCardImage(this, "UnknownCommonAttack.png");
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownCommonAttackReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerCommonA;
    }
}
