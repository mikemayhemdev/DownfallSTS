package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import downfall.util.TextureLoader;
import slimebound.actions.AddPreparedAction;

import static downfall.patches.EvilModeCharacterSelect.evilMode;

public class PreparedRelic extends CustomRelic {
    public static final String ID = "Slimebound:PreparedRelic";
    public static final String IMG_PATH = "relics/slimedteaSet.png";
    public static final String IMG_PATH_LARGE = "relics/slimedteaSetLarge.png";
    public static final String OUTLINE_IMG_PATH = "relics/slimedteaSetOutline.png";
    private boolean firstTurn = true;

    public PreparedRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.largeImg = TextureLoader.getTexture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH_LARGE));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {

    }

    public void atTurnStart() {
        if (this.firstTurn) {
            if (this.counter == -2) {
                this.pulse = false;
                this.counter = -1;
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new AddPreparedAction(false));
            }

            this.firstTurn = false;
        }
    }

    public void atPreBattle() {
        this.firstTurn = true;
    }

    public void setCounter(int counter) {
        super.setCounter(counter);
        if (counter == -2) {
            this.pulse = true;
        }
    }

    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof RestRoom) {
            this.flash();
            this.counter = -2;
            this.pulse = true;
        }
    }


    public boolean canSpawn() {
        return Settings.isEndless || (AbstractDungeon.floorNum <= 48 && !evilMode) || evilMode;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new PreparedRelic();
    }

}