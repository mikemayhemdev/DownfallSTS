package charbosses.cards.hermit;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.actions.common.EnemyUseCardAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnHandOfGreedHermitNecro;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import charbosses.relics.CBR_Necronomicon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.GhostlyPresence;
import hermit.cards.Shortfuse;
import hermit.characters.hermit;

public class EnShortFuseNecro extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:ShortFuse";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Shortfuse.ID);

    public EnShortFuseNecro() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/short_fuse.png", 3, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 18;
        this.isMultiDamage = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractBossCard copy = new EnShortFuse();
        copy.purgeOnUse = true;
        if (this.upgraded) copy.upgrade();
        copy.freeToPlayOnce = true;
        this.addToBot(new EnemyMakeTempCardInHandAction(copy, 1));
    }

    public void updateCostToSpecific(int specific) {
        setCostForTurn(specific);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnShortFuseNecro();
    }
}
