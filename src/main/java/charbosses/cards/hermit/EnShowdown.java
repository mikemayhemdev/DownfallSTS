package charbosses.cards.hermit;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.ItchyTrigger;
import hermit.cards.Showdown;
import hermit.characters.hermit;
import hermit.powers.Concentration;

public class EnShowdown extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Showdown";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Showdown.ID);

    public EnShowdown() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/itchy_trigger.png", 2, cardStrings.DESCRIPTION, CardType.ATTACK, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 9;
        baseMagicNumber = magicNumber = 2;
        this.isMultiDamage = true;
        selfRetain = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnShowdown();
    }
}
