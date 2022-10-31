package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UnknownRareSkill extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownRareSkill");

    public UnknownRareSkill() {
        super(ID, CardType.SKILL, CardRarity.RARE);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownRareSkillReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerRareS;
    }
}
