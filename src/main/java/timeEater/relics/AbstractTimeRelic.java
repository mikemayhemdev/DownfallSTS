package timeEater.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import timeEater.TimeEaterMod;
import timeEater.util.TextureLoader;

public class AbstractTimeRelic extends CustomRelic {
    public AbstractTimeRelic(String ID, RelicTier tier, LandingSound sound) {
        super(ID, TextureLoader.getTexture(TimeEaterMod.makeRelicPath(ID.replace(TimeEaterMod.getModID() + ":", "") + ".png")), TextureLoader.getTexture(TimeEaterMod.makeRelicOutlinePath(ID.replace(TimeEaterMod.getModID() + ":", "") + ".png")), tier, sound);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    private static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }
}
