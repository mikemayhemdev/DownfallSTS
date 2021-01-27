package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.UnknownClass;
import downfall.util.TextureLoader;

public class SneckoCommon extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoCommon");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SealOfApproval.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SealOfApproval.png"));

    public SneckoCommon() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    private boolean chosenInGeneral = true;

    @Override
    public void onEquip() {
        chosenInGeneral = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup c = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q instanceof UnknownClass) {
                if (SneckoMod.validColors.contains(((UnknownClass) q).myColor)) {
                    c.addToTop(q.makeCopy());
                }
            }
        }
        AbstractDungeon.gridSelectScreen.open(c, 1, false, CardCrawlGame.languagePack.getUIString("bronze:MiscStrings").TEXT[8]);
    }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && !chosenInGeneral) {
            chosenInGeneral = true;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            SneckoBoss.chosenChar = SneckoMod.getClassFromColor(((UnknownClass)c).myColor);
            SneckoBoss.myColor = ((UnknownClass)c).myColor;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH / 2F, Settings.HEIGHT / 2F));
            AbstractDungeon.commonCardPool.group.removeIf(q -> q instanceof UnknownClass && !q.cardID.equals(c.cardID));
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.description = getUpdatedDescription(); this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
        }
    }

    public String getUpdatedDescription() {
        if (!SneckoBoss.chosenChar.equals("UNCHOSEN")) { //I sure hope no one makes a character called UNCHOSEN.
            return DESCRIPTIONS[1] + SneckoBoss.chosenChar + DESCRIPTIONS[2];
        }
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return !AbstractDungeon.player.hasRelic(SneckoBoss.ID);
    }
}
