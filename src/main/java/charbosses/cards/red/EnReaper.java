package charbosses.cards.red;

import charbosses.actions.unique.CustomReaperAction;
import charbosses.cards.AbstractBossCard;
import charbosses.vfx.EnemyReaperEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;
import downfall.monsters.NeowBoss;

public class EnReaper extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Reaper";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Reaper");
    }

    public EnReaper() {
        super(ID, cardStrings.NAME, "red/attack/reaper", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ALL_ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        this.baseDamage = 4;
        //this.isMultiDamage = true;
        // this.exhaust = true;
        this.tags.add(CardTags.HEALING);

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new VFXAction(new EnemyReaperEffect()));
        for (int x = AbstractDungeon.getCurrRoom().monsters.monsters.size() -1; x >= 0; x--) {
            AbstractMonster q = AbstractDungeon.getCurrRoom().monsters.monsters.get(x);
            if (!q.isDead && !q.isDying && !q.id.equals(m.id) && !q.id.equals(NeowBoss.ID)) {
                addToBot(new VampireDamageAction(q, new DamageInfo(m, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                //TODO - Technically this won't work if a modded effect has given the mushrooms Block or Buffer or something.  If that ends up being a problem, we'll need a custom action here.
                if (q.currentHealth <= damage){
                    addToBot(new WaitAction(0.1F));
                    addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, 1),1));
                }
            }
        }
        addToBot(new VampireDamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnReaper();
    }
}
