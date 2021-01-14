package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import sneckomod.SneckoMod;
import sneckomod.cards.AbstractSneckoCard;
import downfall.util.TextureLoader;

public class ConfusingCodex extends CustomRelic {

    public static final String ID = SneckoMod.makeID("ConfusingCodex");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("ConfusingCodex.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("ConfusingCodex.png"));

    public ConfusingCodex() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        int x = AbstractSneckoCard.getRandomNum(0, 2);
        if (x != 0)
            for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new ApplyPowerAction(q, AbstractDungeon.player, new WeakPower(q, x, false), x));
                addToBot(new ApplyPowerAction(q, AbstractDungeon.player, new VulnerablePower(q, x, false), x));
            }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
