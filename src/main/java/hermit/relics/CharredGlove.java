package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hermit.HermitMod;
import hermit.util.TextureLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import static champ.ChampMod.vigor;
import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class CharredGlove extends CustomRelic {

    // ID, images, text.
    public static final String ID = HermitMod.makeID("CharredGlove");

    public static final Texture IMG = TextureLoader.getTexture(makeRelicPath("charred_glove.png"));
    public static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("charred_glove.png"));

    public CharredGlove() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    //vigor gain per turn
    private static final int AMOUNT = 3;

    public void onCardDraw(AbstractCard card) {
        if (card.color == AbstractCard.CardColor.CURSE) {
            this.flash();
            //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 3), 3));

            //This should NEVER happen but maybe there's some cross-mod nonsense that allows it to happen.
            //Mechanically speaking, this is basically identical.
            vigor(AMOUNT);
        }

    }



    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}
