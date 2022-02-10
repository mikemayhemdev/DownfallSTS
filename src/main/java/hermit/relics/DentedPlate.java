package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.RedSkull;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hermit.HermitMod;
import hermit.actions.RedScarfAction;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class DentedPlate extends CustomRelic {

    // ID, images, text.
    public static final String ID = HermitMod.makeID("DentedPlate");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("dented_plate.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("dented_plate.png"));

    private boolean isActive = false;

    public DentedPlate() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    public void atBattleStart() {
        this.isActive = false;
        this.addToBot(new AbstractGameAction() {
            public void update() {
                if (!DentedPlate.this.isActive && AbstractDungeon.player.isBloodied) {
                    DentedPlate.this.flash();
                    DentedPlate.this.pulse = true;
                    DentedPlate.this.isActive = true;
                }

                this.isDone = true;
            }
        });
    }

    public void onBloodied() {
        this.flash();
        this.pulse = true;
        if (!this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            this.isActive = true;
        }

    }

    public void onNotBloodied() {
        if (this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
        }

        this.stopPulse();
        this.isActive = false;
    }

    public void onVictory() {
        this.pulse = false;
        this.isActive = false;
    }

    public void atTurnStartPostDraw()
    {
        if (AbstractDungeon.player.currentHealth <= AbstractDungeon.player.maxHealth*0.5)
        {

            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(1));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
