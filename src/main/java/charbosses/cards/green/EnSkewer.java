package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnSkewer extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Skewer";
    private static final CardStrings cardStrings;
    private final int cost;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Skewer");
    }

    public EnSkewer(int cost) {
        super(ID, EnSkewer.cardStrings.NAME, "green/attack/skewer", cost, EnSkewer.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 7;
        this.baseMagicNumber = cost;
        this.magicNumber = this.baseMagicNumber;
        this.isMultiDamage = true;
        intentMultiAmt = 2;
        this.cost = cost;
    }


    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (int i = 0; i < cost; ++i) {
            this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 2;
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
        return new EnSkewer(3);
    }
}
