package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;

public class ExtraCursedKey extends CustomRelic {

    public static final String ID = downfallMod.makeID("ExtraCursedKey");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/ExtraCursedKey.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/ExtraCursedKey.png"));
    private boolean triggeredThisTurn = false;

    public ExtraCursedKey() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void justEnteredRoom(AbstractRoom room) {
        if (room instanceof TreasureRoom) {
            this.flash();
            this.pulse = true;
        } else {
            this.pulse = false;
        }

    }

    public void onChestOpen(boolean bossChest) {
        if (!bossChest) {
            for (int i = 0; i < 1; i++) {
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Injury(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    public void onCardDraw(AbstractCard card) {
        if (card.color == AbstractCard.CardColor.CURSE) {
            if (!this.triggeredThisTurn) {
                this.triggeredThisTurn = true;
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.addToBot(new GainEnergyAction(1));
            }

        }
    }
}
