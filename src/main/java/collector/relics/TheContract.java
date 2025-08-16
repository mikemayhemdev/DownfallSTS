package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import collector.util.EssenceSystem;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.TextureLoader;

import static collector.util.Wiz.*;

public class TheContract extends CustomRelic {
    public static final String ID = CollectorMod.makeID(TheContract.class.getSimpleName());
    private static final String IMG_PATH = TheContract.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = TheContract.class.getSimpleName() + ".png";

    //Essence granted
    private static final int ESSENCE = 10;

    public TheContract() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        EssenceSystem.changeEssence(ESSENCE);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + ESSENCE + DESCRIPTIONS[1];
    }
}

