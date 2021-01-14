package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class Sixitude extends CustomRelic {

    public static final String ID = HexaMod.makeID("Sixitude");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Sixitude.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Sixitude.png"));

    public Sixitude() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        ++this.counter;// 35
        if (this.counter % 6 == 0) {// 37
            this.flash();// 38
            this.counter = 0;// 39
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 40
            this.addToBot(new DamageRandomEnemyAction(new DamageInfo(null, 6, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));// 29
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
