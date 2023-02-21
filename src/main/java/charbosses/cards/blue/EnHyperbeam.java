package charbosses.cards.blue;

import charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

public class EnHyperbeam extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Hyperbeam";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hyperbeam");
    }

    public EnHyperbeam() {
        super(ID, cardStrings.NAME, "blue/attack/hyper_beam", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ALL_ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseDamage = 26;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArchetypeAct3OrbsNewAge.resetPretendFocus();
                isDone = true;
            }
        });
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(p, new MindblastEffect(m.dialogX, m.dialogY, m.flipHorizontal), 0.1F));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

        this.addToBot(new ApplyPowerAction(m, m, new FocusPower(m, -this.magicNumber), -this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(8);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnHyperbeam();
    }
}
