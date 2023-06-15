package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class AutoCurser extends CustomRelic {
    public static final String ID = CollectorMod.makeID(AutoCurser.class.getSimpleName());
    private static final String IMG_PATH = AutoCurser.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = AutoCurser.class.getSimpleName() + ".png";

    public AutoCurser() {
        super(ID, new Texture(CollectorMod.makeRelicPath(IMG_PATH)), new Texture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractMonster q = AbstractDungeon.getRandomMonster();
        atb(new RelicAboveCreatureAction(q, this));
        applyToEnemy(q, new WeakPower(q, 1, false));
        applyToEnemy(q, new VulnerablePower(q, 1, false));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

