package downfall.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.cards.BloodySacrifice;
import downfall.cards.curses.Scatterbrained;
import downfall.downfallMod;
import expansioncontent.actions.EchoACardAction;
import expansioncontent.cards.AwakenDeath;

import java.util.Iterator;

import static hermit.util.Wiz.atb;

public class ShatteredFragment extends CustomRelic {

    public static final String ID = downfallMod.makeID("ShatteredFragment");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/WingShiv.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/WingShiv.png"));

    public ShatteredFragment() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
        //tips.add(new CardPowerTip( new AwakenDeath() ) );
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

        public void onUseCard(AbstractCard card, UseCardAction action) {
            if (card.type == AbstractCard.CardType.POWER) {
                boolean isEliteOrBoss = AbstractDungeon.getCurrRoom().eliteTrigger;
                Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
                while(var2.hasNext()) {
                   AbstractMonster m = (AbstractMonster)var2.next();
                   if (m.type == AbstractMonster.EnemyType.BOSS) {
                       isEliteOrBoss = true;
                    }
                }

               if (isEliteOrBoss) {
                    this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    atb((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new StrengthPower((AbstractCreature)AbstractDungeon.player, 1), 1));
                }
            }
        }



//    public void atBattleStart() {
//        boolean isEliteOrBoss = AbstractDungeon.getCurrRoom().eliteTrigger;
//        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();
//
//        while(var2.hasNext()) {
//            AbstractMonster m = (AbstractMonster)var2.next();
//            if (m.type == AbstractMonster.EnemyType.BOSS) {
//                isEliteOrBoss = true;
//            }
//        }
//
//        if (isEliteOrBoss) {
//            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//           // AbstractCard q = new AwakenDeath();
//            //            addToTop(new EchoACardAction(q, true));
//            atb((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new CuriosityPower((AbstractCreature)AbstractDungeon.player, 1), 1));
//
//        }
//
//    }


}
