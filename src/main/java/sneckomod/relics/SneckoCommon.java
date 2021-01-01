package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.UnknownClass;
import theHexaghost.util.TextureLoader;

public class SneckoCommon extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoCommon");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoCommon.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SneckoCommon.png"));

    public SneckoCommon() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    private String chosenChar = "UNCHOSEN";
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
        for (AbstractCard q : AbstractDungeon.commonCardPool.group) {
            if (q instanceof UnknownClass) {
                c.addToTop(q);
            }
        }
        AbstractDungeon.gridSelectScreen.open(c, 1, false, "Choose."); //TODO: Localize
    }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && !chosenInGeneral) {
            chosenInGeneral = true;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            chosenChar = SneckoMod.getClassFromColor(c.color);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH / 2F, Settings.HEIGHT / 2F));
            AbstractDungeon.commonCardPool.group.removeIf(q -> q instanceof UnknownClass && !q.cardID.equals(c.cardID));
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    public String getUpdatedDescription() {
        if (!chosenChar.equals("UNCHOSEN")) { //I sure hope no one makes a character called The UNCHOSEN.
            return DESCRIPTIONS[1] + chosenChar + DESCRIPTIONS[2];
        }
        return DESCRIPTIONS[0];
    }
}
