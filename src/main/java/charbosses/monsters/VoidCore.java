package charbosses.monsters;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.powers.bossmechanicpowers.VoidOrbPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import downfall.downfallMod;
import hermit.util.Wiz;

public class VoidCore extends AbstractMonster {
    public static final String ID = downfallMod.makeID("VoidCore");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    private int count;

    public VoidCore(float x, float y) {
        super(monsterStrings.NAME, ID, 15, 0.0F, 0.0F, 160.0F, 160.0F, downfallMod.assetPath("images/monsters/Voidorb.png"), x, y);

    }

    @Override
    public void usePreBattleAction() {
        addToBot(new ApplyPowerAction(this, this, new VoidOrbPower(this)));

    }

    public void takeTurn() {
    }

    public void update() {
        super.update();
        if (this.count % 2 == 0) {
            this.animY = MathUtils.cosDeg((float)(System.currentTimeMillis() / 6L % 360L)) * 6.0F * Settings.scale;
        } else {
            this.animY = -MathUtils.cosDeg((float)(System.currentTimeMillis() / 6L % 360L)) * 6.0F * Settings.scale;
        }

    }

    protected void getMove(int num) {
        this.setMove((byte)0, Intent.SLEEP);
    }

}
