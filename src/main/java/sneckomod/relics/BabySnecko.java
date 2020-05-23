package sneckomod.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.vfx.CustomAnimatedNPC;
import slimebound.SlimeboundMod;
import sneckomod.SneckoMod;
import sneckomod.actions.BabySneckoAttackAction;
import sneckomod.patches.BottledD8Patch;
import theHexaghost.util.TextureLoader;

import java.util.function.Predicate;

public class BabySnecko extends CustomRelic {

    public static final String ID = SneckoMod.makeID("BabySnecko");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("BabySnecko.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("BabySnecko.png"));


    public CustomAnimatedNPC baby;

    public BabySnecko() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }


    public void atPreBattle() {
        this.flash();
        this.baby = new CustomAnimatedNPC(AbstractDungeon.player.hb.cX + 230F * Settings.scale, AbstractDungeon.player.hb.cY + 130.0F * Settings.scale, "sneckomodResources/images/monsters/BabySnecko/BabySnecko.atlas", "sneckomodResources/images/monsters/BabySnecko/BabySnecko.json", "idle", false, 0);
        this.baby.customFlipX = true;
        this.baby.setTimeScale(0.9F);
    }


    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);

        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new BabySneckoAttackAction(m, this));
        }
    }

    @Override
    public void onVictory() {
        if (this.baby != null){
        this.baby.dispose();
        this.baby = null;
        SlimeboundMod.logger.info("Disposing baby snecko ");
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.baby != null){
            this.baby.update();
        }
    }

    public void renderBaby(SpriteBatch sb) {
        //SlimeboundMod.logger.info("Rendering");
        if (this.baby != null){
            this.baby.render(sb);
           // SlimeboundMod.logger.info("Rendering baby snecko " + this.baby.skeleton.getX() + " " + this.baby.skeleton.getY());
        }
    }


    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
