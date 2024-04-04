package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
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
    // soul stone thermal stone(old)
    public SoulConsumer() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        ++this.counter;
        if (this.counter % 3 == 0) {
            this.counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, 1, false), 1));
        }
    }

    public void onVictory() {
        this.counter = -1;
    }

    //    @Override
//    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
//        if(targetCard.hasTag(HexaMod.AFTERLIFE)){
//            this.addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(DAMAGE, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
//        }
//    }

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
