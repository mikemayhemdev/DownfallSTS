package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.general.EnemyMarkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

import java.util.ArrayList;

public class EnPressurePoints extends AbstractBossCard {
      public static final String ID = "downfall_Charboss:Pressure Points";
      private static final CardStrings cardStrings;

      static {
            cardStrings = CardCrawlGame.languagePack.getCardStrings("PathToVictory");
      }

      public EnPressurePoints() {
            super(ID, cardStrings.NAME, "purple/skill/pressure_points", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
            this.baseDamage = 0;
            this.baseMagicNumber = this.magicNumber = 8;
      }

      public String overrideIntentText() {
            return String.valueOf(countMarks());
      }

      @Override
      public void use(final AbstractPlayer p, final AbstractMonster m) {
            //add vfx, this below wont work, still strikes the player from left to right like you use on a monster
            //still probably worth using imo, removing the to-do.
            AbstractGameEffect pressure_vfx = new PressurePointEffect(p.hb.cX, p.hb.cY);
            pressure_vfx.renderBehind = true;
            addToBot(new VFXAction(    pressure_vfx   ));
            addToBot(new ApplyPowerAction(p, m, new EnemyMarkPower(p, m, magicNumber), magicNumber));
            if(countMarks()>0) {
                  this.addToBot(new DamageAction(p, new DamageInfo(m, countMarks(), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
      }

      public int getPriority(ArrayList<AbstractCard> hand) {
            return countMarks();
      }

      public int countMarks() {
            int count = 0;
            if (AbstractDungeon.player.hasPower(EnemyMarkPower.POWER_ID)) {
                  count += (AbstractDungeon.player.getPower(EnemyMarkPower.POWER_ID)).amount;
            }
            if(!AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)) {
                  count += this.magicNumber;
            }
            if(AbstractDungeon.player.hasPower(IntangiblePower.POWER_ID)||AbstractDungeon.player.hasPower(IntangiblePlayerPower.POWER_ID)) {
                  if (count>0)
                        count =1;
            }
            return count;
      }
      @Override
      public void upgrade() {
            if (!this.upgraded) {
                  this.upgradeName();
                  this.upgradeMagicNumber(3);
            }
      }

      @Override
      public AbstractCard makeCopy() {
            return new EnPressurePoints();
      }
}