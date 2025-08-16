package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.util.TextureLoader;

import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class AutoCurser extends CustomRelic {
    public static final String ID = CollectorMod.makeID(AutoCurser.class.getSimpleName());
    private static final String IMG_PATH = AutoCurser.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = AutoCurser.class.getSimpleName() + ".png";

    //hexx talisman

    public AutoCurser() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractMonster q = AbstractDungeon.getRandomMonster();
        if(q != null){
            atb(new RelicAboveCreatureAction(q, this));
            applyToEnemy(q, new WeakPower(q, 1, false));
            applyToEnemy(q, new VulnerablePower(q, 1, false));            
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
