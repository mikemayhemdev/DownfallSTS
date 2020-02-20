package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import guardian.GuardianMod;

public class PocketSentry extends CustomRelic {
    public static final String ID = "Guardian:PocketSentry";
    public static final String IMG_PATH = "relics/pocketSentry.png";
    public static final String OUTLINE_IMG_PATH = "relics/pocketSentryOutline.png";
    public static final String LARGE_IMG_PATH = "relics/pocketSentryLarge.png";
    private static final int DAMAGE = 7;

    private boolean beaming = true;

    public PocketSentry() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.FLAT);
        this.largeImg = ImageMaster.loadImage(GuardianMod.getResourcePath(LARGE_IMG_PATH));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + DAMAGE + this.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        this.flash();
        if (beaming) {
            beaming = false;
            AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);

            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(this.hb.cX - (5F * Settings.scale), this.hb.cY + (10F * Settings.scale), m.hb.cX, m.hb.cY), 0.3F));

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

        } else {
            beaming = true;

            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP"));

            for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
                if (!m2.isDead && !m2.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m2, AbstractDungeon.player, new WeakPower(m2, 1, false), 1));
                }
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PocketSentry();
    }
}