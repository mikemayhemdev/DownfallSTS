package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.TextureLoader;

public class RoughDiamond extends CustomRelic {
    public static final String ID = CollectorMod.makeID(RoughDiamond.class.getSimpleName());
    private static final String IMG_PATH = RoughDiamond.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = RoughDiamond.class.getSimpleName() + ".png";

    public RoughDiamond() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.rarity == AbstractCard.CardRarity.RARE) {
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

