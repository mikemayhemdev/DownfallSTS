package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
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

    public TheContract() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.gainGold(200);
    }

    @Override
    public void atBattleStart() {
        flash();
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractMonster q = AbstractDungeon.getRandomMonster();
                applyToEnemyTop(q, new StrengthPower(q, 1));
                att(new RelicAboveCreatureAction(q, TheContract.this));
            }
        });
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

