package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import slimebound.actions.BuffCidStrengthAction;
import slimebound.actions.BuffPikeStrengthAction;

import slimebound.characters.SlimeboundCharacter;
import slimebound.orbs.GreedOozeSlime;

public class GreedOozeRelic extends CustomRelic {
    public static final String ID = "Slimebound:GreedOozeRelic";
    public static final String IMG_PATH = "relics/greedOoze.png";
    public static final String OUTLINE_IMG_PATH = "relics/greedOozeOutline.png";
    public int scrapAmount = 0;

    private boolean used = false;

    public GreedOozeRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter = 4;
    }


    public String getUpdatedDescription() {
        return (this.DESCRIPTIONS[0]);
    }

    public void atBattleStartPreDraw() {
        this.flash();
        addToBot(new BuffCidStrengthAction(counter));
        //AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.GreedOozeSlime(), false, false));
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (!(room instanceof RestRoom)){
            used = false;
        }
    }


    public void onEnterRestRoom() {
        if (!used) {
            AbstractPlayer p = AbstractDungeon.player;

            used = true;
            if (AbstractDungeon.player.gold >= 50) {
                this.counter += 1;

                this.flash();
                p.loseGold(50);
                for (int i = 0; i < 20; i++) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(p, p.hb.cX, p.hb.cY, this.hb.cX, this.hb.cY, false));

                }

            }
        }

    }


    @Override
    public AbstractRelic makeCopy() {
        return new GreedOozeRelic();
    }

}