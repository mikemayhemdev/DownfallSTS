package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import slimebound.actions.SlimeSpawnAction;
import slimebound.characters.SlimeboundCharacter;
import slimebound.powers.SlimeSacrificePower;

public class SlimedTailRelic extends CustomRelic {
    public static final String ID = "Slimebound:SlimedTailRelic";
    public static final String IMG_PATH = "relics/slimedTail.png";
    public static final String IMG_PATH_LARGE = "relics/slimedTailLarge.png";
    public static final String OUTLINE_IMG_PATH = "relics/slimedTailOutline.png";
    private static final int HP_PER_CARD = 1;
    private boolean isActive = false;

    public SlimedTailRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.RARE, LandingSound.SOLID);
        this.largeImg = ImageMaster.loadImage(slimebound.SlimeboundMod.getResourcePath(IMG_PATH_LARGE));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.isActive = true;
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                if (SlimedTailRelic.this.isActive && AbstractDungeon.player.isBloodied) {
                    activate();

                }

                this.isDone = true;
            }
        });
    }

    public void activate() {
        AbstractPlayer p = AbstractDungeon.player;
        flash();

        AbstractDungeon.actionManager.addToTop(new SlimeSpawnAction(new slimebound.orbs.PoisonSlime(true), false, false));

        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, this));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SlimeSacrificePower(AbstractDungeon.player, 1), 1, true));

        this.isActive = false;
        AbstractDungeon.player.hand.applyPowers();
        this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/relics/slimedTailUsed.png");

        this.usedUp = true;
        this.description = this.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void onBloodied() {


        if (this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            activate();
        }

    }


    public void onVictory() {
        if (!isActive) {
            this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/relics/slimedTail.png");

            this.isActive = true;
            this.usedUp = false;
            this.description = this.getUpdatedDescription();

            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlimeboundCharacter;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new SlimedTailRelic();
    }

}