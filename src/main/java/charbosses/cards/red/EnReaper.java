package charbosses.cards.red;

import charbosses.actions.unique.CustomReaperAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;
import downfall.downfallMod;

public class EnReaper extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Reaper";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Reaper");
    }

    public EnReaper() {
        super(ID, cardStrings.NAME, "red/attack/reaper", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 4;
        this.isMultiDamage = true;
       // this.exhaust = true;
        this.tags.add(CardTags.HEALING);

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new VFXAction(new ReaperEffect()));
        this.addToBot(new CustomReaperAction(m, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));    }

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
