package sneckomod.cards.unknowns;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;

@NoCompendium
public class UnknownCommonSkill extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownCommonSkill");

    public UnknownCommonSkill() {
        super(ID, CardType.SKILL, CardRarity.SPECIAL);
        SneckoMod.loadJokeCardImage(this, "UnknownCommonSkill.png");
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownCommonSkillReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerCommonS;
    }
}
