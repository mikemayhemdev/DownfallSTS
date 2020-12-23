package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;

import java.util.ArrayList;

public class GremlinSack extends CustomRelic {

    public static final String ID = downfallMod.makeID("GremlinSack");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/GremlinSack.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/GremlinSack.png"));

    public GremlinSack() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        String starterCardName = "";
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.getStartCardForEvent() != null) {
                starterCardName = AbstractDungeon.player.getStartCardForEvent().name;
            }

        }
        if (AbstractDungeon.ascensionLevel >= 15) {
            if (!starterCardName.equals("")) {
                return DESCRIPTIONS[0] + starterCardName + DESCRIPTIONS[3];
            } else {
                return DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[3];
            }
        } else if (!starterCardName.equals("")) {
            return DESCRIPTIONS[0] + starterCardName + DESCRIPTIONS[2];
        } else {
            return DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2];
        }


    }

    public void onEquip() {

        ArrayList<AbstractCard> retVal = new ArrayList<>();

        retVal.add(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE).makeCopy());
        retVal.add(AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON).makeCopy());
        retVal.add(AbstractDungeon.getCard(AbstractCard.CardRarity.COMMON).makeCopy());
        retVal.add(AbstractDungeon.returnRandomCurse());
        retVal.add(AbstractDungeon.player.getStartCardForEvent());
        if (AbstractDungeon.ascensionLevel >= 15) {
            retVal.add(AbstractDungeon.returnRandomCurse());
        } else {
            retVal.add(AbstractDungeon.returnColorlessCard(AbstractCard.CardRarity.UNCOMMON).makeCopy());
        }
        for (AbstractCard c : retVal) {
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (MathUtils.random((float) Settings.WIDTH * 0.1F, (float) Settings.WIDTH * 0.9F)), (MathUtils.random((float) Settings.HEIGHT * 0.2F, (float) Settings.HEIGHT * 0.8F))));
        }
    }
}
