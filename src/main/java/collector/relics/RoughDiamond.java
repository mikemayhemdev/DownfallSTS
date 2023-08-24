package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorCollection;
import collector.CollectorMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;

public class RoughDiamond extends CustomRelic {
    public static final String ID = CollectorMod.makeID(RoughDiamond.class.getSimpleName());
    private static final String IMG_PATH = RoughDiamond.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = RoughDiamond.class.getSimpleName() + ".png";

    public RoughDiamond() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.rarity == AbstractCard.CardRarity.RARE && c.cost > 1) {
                c.updateCost(-1);
            }
        }

        for (AbstractCard c : CollectorCollection.combatCollection.group) {
            if (c.rarity == AbstractCard.CardRarity.RARE && c.cost > 1) {
                c.updateCost(-1);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

