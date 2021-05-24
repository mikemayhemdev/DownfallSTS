package collector.cards.AbilityCards;

import basemod.AutoAdd;
import collector.CollectorChar;
import collector.TorchChar;
import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class AllyStrike extends AbstractCollectorCard {
    public final static String ID = makeID("AllyStrike");
// intellij stuff attack, enemy, basic, 6, 3,  , , ,
    public AbstractMonster Target;
    public AllyStrike() {
        super(ID, -2, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY);
        douDamage = douBaseDamage = 6;
        tags.add(AbstractCard.CardTags.STRIKE);
    }

    public AllyStrike(AbstractMonster enemy){
        super(ID, -2, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY);
        douDamage = douBaseDamage = 6;
        Target = enemy;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        TorchChar dragon = CollectorChar.getLivingDragon();
        addToBot(new DamageAction(Target, new DamageInfo(dragon, douDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    public void onChoseThisOption() {
        TorchChar dragon = CollectorChar.getLivingDragon();
        this.applyPowers();
        this.calculateCardDamage(Target);
        addToBot(new DamageAction(Target, new DamageInfo(dragon, douDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
    public void upp() {
    }
}