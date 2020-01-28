package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.BurnPower;
import theHexaghost.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class SoulConsumer extends CustomRelic {

    public static final String ID = HexaMod.makeID("SoulConsumer");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SoulConsumer.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SoulConsumer.png"));

    public SoulConsumer() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth == 0 && m.hasPower(BurnPower.POWER_ID)) {// 42
            this.flash();// 43
            this.addToBot(new RelicAboveCreatureAction(m, this));// 44
            this.addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
        }
    }// 48

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
