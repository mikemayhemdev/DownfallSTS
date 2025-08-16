package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyWrathNextTurnPower;
import charbosses.powers.general.EnemyDrawCardNextTurnPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.JudgementAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Judgement;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantTextEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static collector.util.Wiz.att;

public class EnJudgment extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Judgment";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Judgement.ID);
    }

    public EnJudgment() {
        super(ID, cardStrings.NAME, "purple/skill/judgment", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.MAGIC);
        this.baseMagicNumber = 30;
        this.magicNumber = 30;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (p != null) {
            this.addToBot(new VFXAction(new WeightyImpactEffect(p.hb.cX, p.hb.cY, Color.GOLD.cpy())));
            this.addToBot(new WaitAction(0.8F));
            this.addToBot(new VFXAction(new GiantTextEffect(p.hb.cX, p.hb.cY)));
        }

        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                att(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;  if (this.duration == Settings.ACTION_DUR_FAST && p.currentHealth <= magicNumber) {
                            this.addToTop(new InstantKillAction(p));
                        }

                    }
                });

            }
        });

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(10);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnJudgment();
    }
}
