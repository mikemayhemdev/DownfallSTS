package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class CawingCask extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("CawingCask");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("CawingCask.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("CawingCask.png"));

    //How much Ritual does drinking a potion give?

    private static final int AMOUNT = 1;

    public CawingCask() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    //Cawing Cask

    //or, the cask of amontillado

    public void onUsePotion() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.flash();
            this.playSfx();
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Byrd.DIALOG[0], 1.2F, 1.2F));
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(AbstractDungeon.player, AMOUNT, true), AMOUNT));
        }
    }

    private void playSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1C"));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}
