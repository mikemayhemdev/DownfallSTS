package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.combat.FastingEffect;
import evilWithin.EvilWithinMod;
import evilWithin.actions.BanditIOUAction;
import evilWithin.vfx.BanditIOUEffect;

public class RedIOU extends CustomRelic {

    public static String ID = EvilWithinMod.makeID("RedIOU");
    private static Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/BanditContract.png"));
    private static Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/BanditContract.png"));

    public RedIOU() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void atBattleStartPreDraw() {
        if (!usedUp) {
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && AbstractDungeon.actNum == 3) {
                flash();

                this.addToBot(new BanditIOUAction(AbstractDungeon.player, AbstractDungeon.getMonsters().getRandomMonster(true)));
                this.usedUp();
            }
        }

    }
}
