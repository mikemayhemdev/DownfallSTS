package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.BurnPower;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class SoulConsumer extends CustomRelic {

    public static final String ID = HexaMod.makeID("SoulConsumer");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SoulConsumer.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SoulConsumer.png"));
    private boolean activated = false;
    private static final int DAMAGE = 2;
    // soul stone
    public SoulConsumer() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(targetCard.hasTag(HexaMod.AFTERLIFE)){
            this.addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(DAMAGE, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    //    @Override
//    public void onTrigger() {
//        if (!this.activated) {
//            this.img = TextureLoader.getTexture(makeRelicPath("SoulConsumerOn.png"));
//            this.flash();
//            this.activated = true;
//        }
//    }

//    @Override
//    public void onVictory() {
//        if (this.activated) {
//            this.flash();
//            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 44
//              AbstractDungeon.player.heal(4, true);
//            this.img = TextureLoader.getTexture(makeRelicPath("SoulConsumer.png"));
//            this.activated = false;
//        }
//    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
