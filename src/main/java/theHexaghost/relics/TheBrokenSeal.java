package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.GameOverStat;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class TheBrokenSeal extends CustomRelic {

    public static final String ID = HexaMod.makeID("TheBrokenSeal");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TheBrokenSeal.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TheBrokenSeal.png"));

    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("Unfettered"));

    public TheBrokenSeal() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(10, true);
        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
        if( AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 2), 2));
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnhancePower(2), 2));
        }
    }

    @Override
    public void atBattleStart() {
        this.flash();// 24
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 2), 2));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnhancePower(2), 2));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
