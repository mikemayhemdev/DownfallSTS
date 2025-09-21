package collector.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.util.TextureLoader;
import utilityClasses.DFL;

public class ForbiddenFruit extends CustomRelic {
    public static final String ID = CollectorMod.makeID(ForbiddenFruit.class.getSimpleName());
    private static final String IMG_PATH = ForbiddenFruit.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = ForbiddenFruit.class.getSimpleName() + ".png";

    public static final int MAX_HP = 10;
    public static final int DEX = 1;

    public ForbiddenFruit() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.SHOP, LandingSound.MAGICAL);
        tips.add(new CardPowerTip(new Parasite()));
    }

    public void onEquip() {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Parasite(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        UnlockTracker.markCardAsSeen("Parasite");
        DFL.pl().increaseMaxHp(MAX_HP, true);
    }

    @Override
    public void atBattleStart(){
        addToBot(new ApplyPowerAction(DFL.pl(), DFL.pl(), new DexterityPower(DFL.pl(), DEX), DEX));
    }

    public boolean canSpawn() {
        return ((AbstractDungeon.floorNum < 69));// :?
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MAX_HP + DESCRIPTIONS[1] + DEX + DESCRIPTIONS[2];
    }
}
