package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import guardian.GuardianMod;

public class ObsidianScales extends CustomRelic implements OnReceivePowerRelic{
    public static final String ID = "Guardian:ObsidianScales";
    public static final String IMG_PATH = "relics/obsidianscales.png";
    public static final String OUTLINE_IMG_PATH = "relics/obsidianscalesOutline.png";

    //todo: make a variable for thorns gain

    public ObsidianScales() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)),
                new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.BOSS, LandingSound.CLINK);
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 3), 3));
    }


    @Override
    public boolean onReceivePower(AbstractPower var1, AbstractCreature var2) {
        // was told that checking for flex / speed / thorns down was "not fun" so it uses determination rules
       if (var1.type == AbstractPower.PowerType.DEBUFF){
           this.flash();
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 1), 1));
        }
        return true;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
