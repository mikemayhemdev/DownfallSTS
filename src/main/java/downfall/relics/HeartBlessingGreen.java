package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import downfall.downfallMod;

public class HeartBlessingGreen extends CustomRelic {

    public static final String ID = downfallMod.makeID("HeartBlessingGreen");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/HeartBlessingGreen.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/HeartBlessing.png"));

    public HeartBlessingGreen() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().anyMatch(q -> q.type == AbstractMonster.EnemyType.BOSS)) {
            this.flash();
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

}
