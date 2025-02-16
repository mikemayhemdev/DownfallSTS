package downfall.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.cards.BloodySacrifice;
import downfall.cards.curses.Scatterbrained;
import downfall.downfallMod;
import expansioncontent.actions.EchoACardAction;
import expansioncontent.cards.AwakenDeath;

public class ShatteredFragment extends CustomRelic {

    public static final String ID = downfallMod.makeID("ShatteredFragment");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/WingShiv.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/WingShiv.png"));

    public ShatteredFragment() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
        tips.add(new CardPowerTip( new AwakenDeath() ) );
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

//    @Override
//    public void atTurnStart() {
//        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//        this.addToBot(new MakeTempCardInHandAction(new CrystalShiv(), 1, false));
//    }

//    @Override
//    public void atBattleStartPreDraw() {
//        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 24
//        this.addToBot(new MakeTempCardInHandAction(new BloodySacrifice()));// 25
//    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().anyMatch(q -> q.type == AbstractMonster.EnemyType.ELITE)) {
            flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(AbstractDungeon.player, 1, true), 1));
        }

        if (AbstractDungeon.getCurrRoom().monsters.monsters.stream().anyMatch(q -> q.type == AbstractMonster.EnemyType.BOSS)) {
            flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard q = new AwakenDeath();
            addToTop(new EchoACardAction(q, true));
        }
    }


}
