package charbosses.cards.green;

import charbosses.actions.unique.EnemyBaneAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Bane;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import charbosses.powers.general.EnemyPoisonPower;

import java.util.ArrayList;

public class EnBane extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Bane";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Bane.ID);
    }

    public EnBane() {
        super(ID, EnBane.cardStrings.NAME, "green/attack/bane", 1, EnBane.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 7;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        if (AbstractDungeon.player != null){
            if (AbstractDungeon.player.hasPower(EnemyPoisonPower.POWER_ID)){
                return autoPriority() * 2;
            }
        }
        return autoPriority();
    }


    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        this.addToBot(new EnemyBaneAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn)));// 35
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBane();
    }
}
