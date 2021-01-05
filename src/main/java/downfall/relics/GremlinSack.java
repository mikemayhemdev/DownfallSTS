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
import slimebound.SlimeboundMod;

import java.util.ArrayList;

public class GremlinSack extends CustomRelic {

    public static final String ID = downfallMod.makeID("GremlinSack");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/GremlinSack.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/GremlinSack.png"));

    public ArrayList<AbstractCard> sackCards;

    public GremlinSack() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);

    }

    @Override
    public void onTrigger() {
        sackCards = new ArrayList<>();
        if (AbstractDungeon.isPlayerInDungeon()) {
            if (AbstractDungeon.player != null) {
                if (AbstractDungeon.player.getStartCardForEvent() != null) {
                    sackCards.add(AbstractDungeon.player.getStartCardForEvent().makeStatEquivalentCopy());
                }
                //TODO - Does this need to be seeded?
                sackCards.add(AbstractDungeon.getCard(AbstractCard.CardRarity.COMMON).makeCopy());
                sackCards.add(AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON).makeCopy());
                sackCards.add(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE).makeCopy());
                sackCards.add(AbstractDungeon.returnColorlessCard(AbstractCard.CardRarity.UNCOMMON).makeCopy());
                sackCards.add(AbstractDungeon.returnRandomCurse());

                this.description = getUpdatedDescription();
                this.tips.clear();
                this.tips.add(new PowerTip(this.name, this.description));

            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (sackCards == null) onTrigger();
    }

    @Override
    public String getUpdatedDescription() {
        if (AbstractDungeon.player != null) {
            // //SlimeboundMod.logger.info("passed player check");
            if (sackCards != null) {
                //  //SlimeboundMod.logger.info("passed null check - " + sackCards.size());
                if (sackCards.size() >= 5) {
                    //   //SlimeboundMod.logger.info("passed count check - " + sackCards.size());
                    String fullDesc = DESCRIPTIONS[0];
                    fullDesc = fullDesc + DESCRIPTIONS[2] + sackCards.get(0).name + " NL ";
                    fullDesc = fullDesc + DESCRIPTIONS[3] + sackCards.get(1).name + " NL ";
                    fullDesc = fullDesc + DESCRIPTIONS[4] + sackCards.get(2).name + " NL ";
                    fullDesc = fullDesc + DESCRIPTIONS[5] + sackCards.get(3).name + " NL ";
                    fullDesc = fullDesc + DESCRIPTIONS[6] + sackCards.get(4).name + " NL ";
                    fullDesc = fullDesc + DESCRIPTIONS[7] + sackCards.get(5).name;
                    return fullDesc;
                }
            } else {
                return DESCRIPTIONS[1];
            }
        } else {
            return DESCRIPTIONS[1];
        }
        return DESCRIPTIONS[1];
    }

    public void onEquip() {

        if (sackCards == null) onTrigger();
        for (AbstractCard c : sackCards) {
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (MathUtils.random((float) Settings.WIDTH * 0.1F, (float) Settings.WIDTH * 0.9F)), (MathUtils.random((float) Settings.HEIGHT * 0.2F, (float) Settings.HEIGHT * 0.8F))));
        }
    }
}
