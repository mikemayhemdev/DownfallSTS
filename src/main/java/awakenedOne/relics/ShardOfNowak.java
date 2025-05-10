package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class ShardOfNowak extends CustomRelic implements OnLoseTempHpRelic {

    public static final String ID = AwakenedOneMod.makeID("ShardOfNowak");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("ShardOfNowak.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("ShardOfNowak.png"));

    //Gilded Bone Shard

    //Brutal Orchestra mentioned!!! this is so brutal

    private static final int FOCUS = 2;

    private boolean isActive = false;

    public ShardOfNowak() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.grayscale = false;
        isActive = true;
        this.beginLongPulse();
        AbstractDungeon.player.addPower(new StrengthPower(AbstractDungeon.player, FOCUS));
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }


    @Override
    public void onLoseHp(int damageAmount) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                if (!this.grayscale) {
                    this.grayscale = true;
                    AbstractPlayer p = AbstractDungeon.player;
                    this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, -2), -2));
                }
            }
            this.stopPulse();
            isActive = false;
            AbstractDungeon.player.hand.applyPowers();
     }


    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth == 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (this.grayscale) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, FOCUS), FOCUS));
                this.grayscale = false;
            }
        }
    }

    @Override
    public void onVictory() {
        stopPulse();
        this.isActive = false;
        this.grayscale = false;
    }


    @Override
    public int onLoseTempHp(DamageInfo info, int i) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (!this.grayscale) {
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractPlayer p = AbstractDungeon.player;
                this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, -FOCUS), -FOCUS));
            }
        }

        this.stopPulse();
        this.isActive = false;
        this.grayscale = true;
        AbstractDungeon.player.hand.applyPowers();
        return i;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + FOCUS + DESCRIPTIONS[1];
    }

}
